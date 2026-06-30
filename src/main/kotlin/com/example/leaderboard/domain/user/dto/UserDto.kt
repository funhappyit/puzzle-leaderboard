package com.example.leaderboard.domain.user.dto

import com.example.leaderboard.domain.user.entity.User
import com.example.leaderboard.domain.user.entity.UserStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class UserCreateRequest(
    @field:NotBlank @field:Size(min = 2, max = 50)
    val username: String,
    @field:NotBlank @field:Email
    val email: String,
    @field:NotBlank @field:Size(min = 8)
    val password: String,
)

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val status: UserStatus,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(user: User) = UserResponse(
            id = user.id,
            username = user.username,
            email = user.email,
            status = user.status,
            createdAt = user.createdAt,
        )
    }
}
