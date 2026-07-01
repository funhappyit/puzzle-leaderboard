variable "aws_region" {
  description = "AWS 리전"
  type        = string
  default     = "ap-northeast-2"
}

variable "app_name" {
  description = "애플리케이션 이름"
  type        = string
  default     = "puzzle-leaderboard"
}

variable "db_username" {
  description = "RDS 마스터 사용자명"
  type        = string
  default     = "admin"
}

variable "db_password" {
  description = "RDS 마스터 비밀번호"
  type        = string
  sensitive   = true
}

variable "db_name" {
  description = "데이터베이스 이름"
  type        = string
  default     = "puzzle_leaderboard"
}
