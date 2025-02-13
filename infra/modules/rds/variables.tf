variable "vpc_id" {
  description = "ID de la VPC"
  type        = string
}

variable "db_password" {
  description = "Contraseña para la base de datos"
  type        = string
  sensitive   = true
}
