resource "aws_ecs_cluster" "franchise_cluster" {
  name = var.cluster_name
}

resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ecsTaskExecutionRole-tf"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        },
        Action = "sts:AssumeRole"
      }
    ]
  })
}

resource "aws_iam_policy" "ecs_logging_and_ecr" {
  name        = "ECSLoggingAndECRPolicy"
  description = "Permite a ECS escribir logs en CloudWatch y descargar imágenes de ECR"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect   = "Allow",
        Action   = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        Resource = "*"
      },
      {
        Effect   = "Allow",
        Action   = [
          "ecr:GetAuthorizationToken",
          "ecr:BatchCheckLayerAvailability",
          "ecr:GetDownloadUrlForLayer",
          "ecr:BatchGetImage"
        ],
        Resource = "*"
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_logging_and_ecr_attachment" {
  policy_arn = aws_iam_policy.ecs_logging_and_ecr.arn
  role       = aws_iam_role.ecs_task_execution_role.name
}

resource "aws_iam_policy_attachment" "ecs_execution_role_policy" {
  name       = "ecsExecutionRolePolicyTf"
  roles      = [aws_iam_role.ecs_task_execution_role.name]
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_ecs_task_definition" "franchise_task" {
  family                   = "FranchiseTask-tf"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"
  network_mode             = "awsvpc"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([
    {
      name  = "franchise-tf"
      image = "${var.repository_url}:latest"
      portMappings = [
        {
          containerPort = 8080
        }
      ]
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/ecs/franchise"
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
          awslogs-create-group  = "true"
        }
      }
      essential   = true
      environment = [
        {
          name  = "SPRING_R2DBC_URL"
          value = "r2dbc:mysql://${var.db_endpoint}/franchise"
        },
        {
          name  = "SPRING_R2DBC_USERNAME"
          value = "admin"
        },
        {
          name  = "SPRING_R2DBC_PASSWORD"
          value = var.db_password
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "franchise_service" {
  name            = "FranchiseServiceTf"
  cluster         = aws_ecs_cluster.franchise_cluster.id
  task_definition = aws_ecs_task_definition.franchise_task.arn
  launch_type     = "FARGATE"
  desired_count   = 2

  network_configuration {
    subnets         = var.subnet_ids
    security_groups = [var.ecs_task_sg_id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = var.alb_target_group_arn
    container_name   = "franchise-tf"
    container_port   = 8080
  }

  depends_on = [
    aws_ecs_task_definition.franchise_task
  ]
}
