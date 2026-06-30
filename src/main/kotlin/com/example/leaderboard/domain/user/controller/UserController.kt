package com.example.leaderboard.domain.user.controller

import com.example.leaderboard.common.response.ApiResponse
import com.example.leaderboard.domain.user.dto.UserCreateRequest
import com.example.leaderboard.domain.user.dto.UserResponse
import com.example.leaderboard.domain.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@Valid @RequestBody request: UserCreateRequest): ApiResponse<UserResponse> =
        ApiResponse.ok(userService.createUser(request))

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ApiResponse<UserResponse> =
        ApiResponse.ok(userService.getUser(id))

    @GetMapping
    fun getUsers(): ApiResponse<List<UserResponse>> =
        ApiResponse.ok(userService.getUsers())
}
