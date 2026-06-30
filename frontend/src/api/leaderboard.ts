const BASE = ''

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

export interface UserResponse {
  id: number
  username: string
  email: string
}

export async function fetchRanking(puzzleId: string): Promise<RankingResponse> {
  const res = await fetch(`${BASE}/api/v1/rankings?puzzleId=${encodeURIComponent(puzzleId)}`)
  if (!res.ok) throw new Error('랭킹 조회 실패')
  const body = await res.json()
  return body.data as RankingResponse
}

export async function findOrCreateUser(username: string): Promise<UserResponse> {
  const listRes = await fetch(`${BASE}/api/v1/users`)
  if (listRes.ok) {
    const body = await listRes.json()
    const users: UserResponse[] = body.data ?? []
    const found = users.find((u) => u.username === username)
    if (found) return found
  }
  const createRes = await fetch(`${BASE}/api/v1/users`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      username,
      email: `${username}@puzzle.local`,
      password: 'puzzle1234',
    }),
  })
  if (!createRes.ok) throw new Error('유저 생성 실패')
  const body = await createRes.json()
  return body.data as UserResponse
}

export async function submitScore(userId: number, puzzleId: string, score: number, elapsedSec: number): Promise<void> {
  const res = await fetch(`${BASE}/api/v1/scores`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userId, puzzleId, score, elapsedSec }),
  })
  if (!res.ok) throw new Error('점수 제출 실패')
}
