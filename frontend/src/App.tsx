import { useState, useEffect, useCallback } from 'react'
import { fetchRanking, RankingEntry } from './api/leaderboard'
import './App.css'

const PUZZLES = ['wordle-ko', 'wordle-en', 'math-quiz']
const MEDAL: Record<number, string> = { 1: '🥇', 2: '🥈', 3: '🥉' }

export default function App() {
  const [puzzleId, setPuzzleId] = useState(PUZZLES[0])
  const [entries, setEntries] = useState<RankingEntry[]>([])
  const [loading, setLoading] = useState(true)
  const [lastUpdated, setLastUpdated] = useState<Date | null>(null)

  const loadRanking = useCallback(async () => {
    try {
      const data = await fetchRanking(puzzleId)
      setEntries(data.entries)
      setLastUpdated(new Date())
    } catch {
      // silent
    } finally {
      setLoading(false)
    }
  }, [puzzleId])

  useEffect(() => {
    setLoading(true)
    loadRanking()
    const timer = setInterval(loadRanking, 5000)
    return () => clearInterval(timer)
  }, [loadRanking])

  return (
    <div className="app">
      <header className="header">
        <div className="header-inner">
          <span className="logo">🧩</span>
          <div>
            <h1 className="title">퍼즐 리더보드</h1>
            <p className="subtitle">API 서버 · 실시간 랭킹 · 5초마다 갱신</p>
          </div>
          <div className="api-badge">API Server</div>
        </div>
      </header>

      <main className="main">
        {/* 퍼즐 탭 */}
        <div className="tabs">
          {PUZZLES.map((p) => (
            <button
              key={p}
              className={`tab ${puzzleId === p ? 'tab-active' : ''}`}
              onClick={() => setPuzzleId(p)}
            >
              {p}
            </button>
          ))}
        </div>

        {/* 랭킹 카드 */}
        <section className="card">
          <div className="ranking-header">
            <h2 className="card-title">🏆 랭킹</h2>
            {lastUpdated && (
              <span className="updated">
                {lastUpdated.toLocaleTimeString()} 기준
              </span>
            )}
          </div>

          {loading ? (
            <p className="empty">불러오는 중…</p>
          ) : entries.length === 0 ? (
            <div className="empty-state">
              <p className="empty">아직 데이터가 없습니다.</p>
              <code className="hint">POST /api/v1/scores 로 점수를 제출하세요</code>
            </div>
          ) : (
            <table className="ranking-table">
              <thead>
                <tr>
                  <th>순위</th>
                  <th>닉네임</th>
                  <th>총점</th>
                </tr>
              </thead>
              <tbody>
                {entries.map((e) => (
                  <tr key={e.userId} className={e.rank <= 3 ? `top${e.rank}` : ''}>
                    <td className="rank-cell">{MEDAL[e.rank] ?? e.rank}</td>
                    <td className="name-cell">{e.username}</td>
                    <td className="score-cell">{e.totalScore.toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>

        {/* API 안내 카드 */}
        <section className="card info-card">
          <h2 className="card-title">📡 API 엔드포인트</h2>
          <div className="endpoints">
            <div className="endpoint">
              <span className="method post">POST</span>
              <code>/api/v1/users</code>
              <span className="desc">유저 등록</span>
            </div>
            <div className="endpoint">
              <span className="method post">POST</span>
              <code>/api/v1/scores</code>
              <span className="desc">점수 제출 (동시성 제어 적용)</span>
            </div>
            <div className="endpoint">
              <span className="method get">GET</span>
              <code>/api/v1/rankings?puzzleId=</code>
              <span className="desc">랭킹 조회 (Redis ZSET)</span>
            </div>
          </div>
        </section>
      </main>
    </div>
  )
}
