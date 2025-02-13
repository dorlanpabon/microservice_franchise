output "repository_url" {
  description = "URL del repositorio ECR"
  value       = aws_ecr_repository.franchise_api.repository_url
}

output "repository_name" {
  description = "Nombre del repositorio ECR"
  value       = aws_ecr_repository.franchise_api.name
}
