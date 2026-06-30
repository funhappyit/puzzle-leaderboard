package com.example.leaderboard.batch

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

// 3주차에 실제 집계 로직 구현 예정
@Component
class DailyRankingSnapshotJob {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 0 0 * * *")
    fun run() {
        log.info("일별 랭킹 스냅샷 배치 시작")
        // TODO: Redis ZSET → MariaDB 스냅샷 저장
        log.info("일별 랭킹 스냅샷 배치 완료")
    }
}
