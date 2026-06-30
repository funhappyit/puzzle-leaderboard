package com.example.leaderboard.domain.user.service

import com.example.leaderboard.domain.user.dto.UserCreateRequest
import com.example.leaderboard.domain.user.dto.UserResponse
import com.example.leaderboard.domain.user.entity.User
import com.example.leaderboard.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(request: UserCreateRequest): UserResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("이미 사용 중인 username입니다.")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 사용 중인 email입니다.")
        }
        // 실제 운영에서는 BCrypt 등 사용. 포트폴리오 단계에서는 단순 해시 대체
        val user = User(
            username = request.username,
            email = request.email,
            passwordHash = request.password.hashCode().toString(),
        )
        return UserResponse.from(userRepository.save(user))
    }

    fun getUser(id: Long): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("유저를 찾을 수 없습니다. id=$id") }
        return UserResponse.from(user)
    }

    fun getUsers(): List<UserResponse> =
        userRepository.findAll().map { UserResponse.from(it) }
}
