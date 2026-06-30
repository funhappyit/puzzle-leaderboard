package com.example.leaderboard.score

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

// 3주차 동시성 처리 구현 후 활성화 예정
@Disabled("3주차 동시성 처리 구현 후 활성화")
class ScoreServiceConcurrencyTest {

    @Test
    fun `동시에 100명이 점수를 제출해도 race condition 없이 저장된다`() {
        // TODO: CountDownLatch + ExecutorService로 동시 제출 시뮬레이션
        // 분산락 적용 전/후 결과 비교
    }
}
