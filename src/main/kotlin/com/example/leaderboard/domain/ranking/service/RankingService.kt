package com.example.leaderboard.domain.ranking.service

import com.example.leaderboard.domain.ranking.dto.RankingResponse
import com.example.leaderboard.domain.ranking.repository.RankingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RankingService(private val rankingRepository: RankingRepository) {

    fun getRanking(puzzleId: String, limit: Int = 100): RankingResponse {
        val entries = rankingRepository.findTopRankingByPuzzleId(puzzleId, limit)
        return RankingResponse(puzzleId = puzzleId, entries = entries)
    }
}
