variable "cluster_name" {
  description = "Nombre del cluster ECS"
  type        = string
}

variable "subnet_ids" {
  description = "Lista de IDs de subredes para tareas ECS"
  type        = list(string)
}

variable "ecs_task_sg_id" {
  description = "ID del security group para las tareas ECS"
  type        = string
}

variable "repository_url" {
  description = "URL del repositorio ECR"
  type        = string
}

variable "db_endpoint" {
  description = "Endpoint de la base de datos RDS"
  type        = string
}

variable "db_password" {
  description = "Contraseña de la base de datos"
  type        = string
  sensitive   = true
}

variable "alb_target_group_arn" {
  description = "ARN del target group del ALB"
  type        = string
}

variable "aws_region" {
  description = "Región de AWS"
  type        = string
  default     = "us-east-1"
}
