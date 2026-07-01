output "alb_dns_name" {
  description = "ALB DNS 주소 (API 엔드포인트)"
  value       = aws_lb.main.dns_name
}

output "ecr_repository_url" {
  description = "ECR 이미지 저장소 URL"
  value       = aws_ecr_repository.app.repository_url
}

output "rds_endpoint" {
  description = "RDS 엔드포인트"
  value       = aws_db_instance.main.address
}

output "redis_endpoint" {
  description = "ElastiCache Redis 엔드포인트"
  value       = aws_elasticache_cluster.redis.cache_nodes[0].address
}

output "ecs_cluster_name" {
  description = "ECS 클러스터 이름"
  value       = aws_ecs_cluster.main.name
}
