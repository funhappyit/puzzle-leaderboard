const BASE = import.meta.env.VITE_API_BASE_URL ?? ''

export interface RankingEntry {
  rank: number
  userId: number
  username: string
  totalScore: number
}

export interface RankingResponse {
  puzzleId: string
  entries: RankingEntry[]
}

export interface ScoreSubmitRequest {
  userId: number
  puzzleId: string
  score: number
  elapsedSec: number
}

export async function fetchRanking(puzzleId: string): Promise<RankingResponse> {
  const res = await fetch(`${BASE}/api/v1/rankings?puzzleId=${encodeURIComponent(puzzleId)}`)
  if (!res.ok) throw new Error('랭킹 조회 실패')
  const body = await res.json()
  return body.data as RankingResponse
}

export async function submitScore(req: ScoreSubmitRequest): Promise<void> {
  const res = await fetch(`${BASE}/api/v1/scores`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(req),
  })
  if (!res.ok) throw new Error('점수 제출 실패')
}
