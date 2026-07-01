package com.example.leaderboard.domain.user.repository

import com.example.leaderboard.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findAllByIdIn(ids: List<Long>): List<User>
}
