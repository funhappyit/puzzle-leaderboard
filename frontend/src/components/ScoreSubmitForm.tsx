import { useState } from 'react'
import { submitScore } from '../api/leaderboard'

interface Props {
  puzzleId: string
  onSubmitted: () => void
}

export function ScoreSubmitForm({ puzzleId, onSubmitted }: Props) {
  const [userId, setUserId] = useState('')
  const [score, setScore] = useState('')
  const [elapsedSec, setElapsedSec] = useState('')
  const [message, setMessage] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setMessage(null)
    try {
      await submitScore(Number(userId), puzzleId, Number(score), Number(elapsedSec))
      setMessage('점수가 제출되었습니다.')
      onSubmitted()
    } catch {
      setMessage('점수 제출에 실패했습니다.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
      <input
        type="number"
        placeholder="유저 ID"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="점수"
        value={score}
        onChange={(e) => setScore(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="풀이 소요 시간(초)"
        value={elapsedSec}
        onChange={(e) => setElapsedSec(e.target.value)}
        required
      />
      <button type="submit" disabled={loading}>
        {loading ? '제출 중...' : '점수 제출'}
      </button>
      {message && <p>{message}</p>}
    </form>
  )
}
