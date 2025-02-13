output "alb_dns_name" {
  description = "DNS del ALB"
  value       = aws_lb.franchise_alb.dns_name
}

output "target_group_arn" {
  description = "ARN del target group"
  value       = aws_lb_target_group.target_group.arn
}

output "ecs_task_sg_id" {
  description = "ID del security group para tareas ECS"
  value       = aws_security_group.ecs_task_sg.id
}
