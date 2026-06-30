package com.example.leaderboard.domain.ranking.controller

import com.example.leaderboard.common.response.ApiResponse
import com.example.leaderboard.domain.ranking.dto.RankingResponse
import com.example.leaderboard.domain.ranking.service.RankingService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/rankings")
class RankingController(private val rankingService: RankingService) {

    @GetMapping
    fun getRanking(
        @RequestParam puzzleId: String,
        @RequestParam(defaultValue = "100") limit: Int,
    ): ApiResponse<RankingResponse> =
        ApiResponse.ok(rankingService.getRanking(puzzleId, limit))
}
