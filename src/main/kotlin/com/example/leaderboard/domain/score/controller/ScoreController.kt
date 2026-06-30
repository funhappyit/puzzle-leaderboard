package com.example.leaderboard.domain.score.controller

import com.example.leaderboard.common.response.ApiResponse
import com.example.leaderboard.domain.score.dto.ScoreResponse
import com.example.leaderboard.domain.score.dto.ScoreSubmitRequest
import com.example.leaderboard.domain.score.service.ScoreService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/scores")
class ScoreController(private val scoreService: ScoreService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun submitScore(@Valid @RequestBody request: ScoreSubmitRequest): ApiResponse<ScoreResponse> =
        ApiResponse.ok(scoreService.submitScore(request))
}
