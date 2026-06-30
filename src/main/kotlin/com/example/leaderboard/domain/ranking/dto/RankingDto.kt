package com.example.leaderboard.domain.ranking.dto

data class RankingEntry(
    val rank: Int,
    val userId: Long,
    val username: String,
    val totalScore: Long,
)

data class RankingResponse(
    val puzzleId: String,
    val entries: List<RankingEntry>,
)
