import { useState } from 'react'
import { RankingTable } from './components/RankingTable'
import { ScoreSubmitForm } from './components/ScoreSubmitForm'

const DEFAULT_PUZZLE = 'wordle-ko'

export default function App() {
  const [puzzleId] = useState(DEFAULT_PUZZLE)
  const [refreshKey, setRefreshKey] = useState(0)

  return (
    <div style={{ maxWidth: 600, margin: '40px auto', padding: '0 16px' }}>
      <h1>퍼즐 리더보드</h1>
      <h2>{puzzleId}</h2>

      <section style={{ marginBottom: 32 }}>
        <h3>점수 제출</h3>
        <ScoreSubmitForm
          puzzleId={puzzleId}
          onSubmitted={() => setRefreshKey((k) => k + 1)}
        />
      </section>

      <section>
        <h3>랭킹</h3>
        <RankingTable key={refreshKey} puzzleId={puzzleId} />
      </section>
    </div>
  )
}
