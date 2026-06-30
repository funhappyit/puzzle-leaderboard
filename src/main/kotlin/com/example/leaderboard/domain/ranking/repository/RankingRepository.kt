package com.example.leaderboard.domain.ranking.repository

import com.example.leaderboard.domain.ranking.dto.RankingEntry
import com.example.leaderboard.domain.score.entity.PuzzleScore
import com.example.leaderboard.domain.user.entity.User
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class RankingRepository(private val em: EntityManager) {

    // DB 풀스캔 ORDER BY — 2주차 Redis ZSET 버전과 성능 비교 기준선
    fun findTopRankingByPuzzleId(puzzleId: String, limit: Int = 100): List<RankingEntry> {
        val query = em.createQuery(
            """
            SELECT NEW com.example.leaderboard.domain.ranking.dto.RankingEntry(
                0, ps.userId, u.username, SUM(ps.score)
            )
            FROM PuzzleScore ps
            JOIN User u ON u.id = ps.userId
            WHERE ps.puzzleId = :puzzleId AND ps.isValid = true
            GROUP BY ps.userId, u.username
            ORDER BY SUM(ps.score) DESC
            """.trimIndent(),
            RankingEntry::class.java,
        )
        query.setParameter("puzzleId", puzzleId)
        query.maxResults = limit

        return query.resultList.mapIndexed { index, entry ->
            entry.copy(rank = index + 1)
        }
    }
}
