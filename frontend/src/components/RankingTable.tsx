import { useEffect, useState } from 'react'
import { fetchRanking, RankingEntry } from '../api/leaderboard'

interface Props {
  puzzleId: string
}

export function RankingTable({ puzzleId }: Props) {
  const [entries, setEntries] = useState<RankingEntry[]>([])
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let active = true

    const load = async () => {
      try {
        const data = await fetchRanking(puzzleId)
        if (active) setEntries(data.entries)
      } catch {
        if (active) setError('랭킹을 불러오는 중 오류가 발생했습니다.')
      }
    }

    load()
    const timer = setInterval(load, 5000)
    return () => {
      active = false
      clearInterval(timer)
    }
  }, [puzzleId])

  if (error) return <p style={{ color: 'red' }}>{error}</p>

  return (
    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
      <thead>
        <tr>
          <th>#</th>
          <th>유저명</th>
          <th>총점</th>
        </tr>
      </thead>
      <tbody>
        {entries.map((e) => (
          <tr key={e.userId}>
            <td>{e.rank}</td>
            <td>{e.username}</td>
            <td>{e.totalScore.toLocaleString()}</td>
          </tr>
        ))}
        {entries.length === 0 && (
          <tr>
            <td colSpan={3} style={{ textAlign: 'center' }}>
              아직 점수가 없습니다.
            </td>
          </tr>
        )}
      </tbody>
    </table>
  )
}
