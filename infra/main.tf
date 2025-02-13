module "ecr" {
  source = "./modules/ecr"
  # El nombre del repositorio se puede sobreescribir si lo deseas
}

module "rds" {
  source      = "./modules/rds"
  vpc_id      = var.vpc_id
  db_password = var.db_password
}

module "alb" {
  source     = "./modules/alb"
  vpc_id     = var.vpc_id
  subnet_ids = var.subnet_ids
}

module "ecs" {
  source = "./modules/ecs"
  cluster_name         = "FranchiseCluster-tf"
  subnet_ids           = var.subnet_ids
  ecs_task_sg_id       = module.alb.ecs_task_sg_id
  repository_url       = module.ecr.repository_url
  db_endpoint          = module.rds.db_endpoint
  db_password          = var.db_password
  alb_target_group_arn = module.alb.target_group_arn
  aws_region           = var.aws_region
}

module "api_gateway" {
  source      = "./modules/api_gateway"
  alb_dns_name = module.alb.alb_dns_name
  region       = var.aws_region
}
