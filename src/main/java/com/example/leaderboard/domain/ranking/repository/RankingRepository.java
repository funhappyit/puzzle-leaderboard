package com.example.leaderboard.domain.ranking.repository;

import com.example.leaderboard.domain.ranking.dto.RankingEntry;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class RankingRepository {

    private final EntityManager em;

    // DB 풀스캔 ORDER BY — Redis ZSET 데이터 없을 때 fallback 및 성능 비교 기준선
    public List<RankingEntry> findTopRankingByPuzzleId(String puzzleId, int limit) {
        List<Object[]> rows = em.createQuery(
                        """
                        SELECT ps.userId, u.username, SUM(ps.score)
                        FROM PuzzleScore ps
                        JOIN User u ON u.id = ps.userId
                        WHERE ps.puzzleId = :puzzleId AND ps.isValid = true
                        GROUP BY ps.userId, u.username
                        ORDER BY SUM(ps.score) DESC
                        """,
                        Object[].class)
                .setParameter("puzzleId", puzzleId)
                .setMaxResults(limit)
                .getResultList();

        return IntStream.range(0, rows.size())
                .mapToObj(i -> {
                    Object[] row = rows.get(i);
                    return new RankingEntry(
                            i + 1,
                            ((Number) row[0]).longValue(),
                            (String) row[1],
                            ((Number) row[2]).longValue()
                    );
                })
                .toList();
    }
}
