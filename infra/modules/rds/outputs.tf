output "db_endpoint" {
  description = "Endpoint de la base de datos RDS"
  value       = aws_db_instance.rds_mysql.endpoint
}
