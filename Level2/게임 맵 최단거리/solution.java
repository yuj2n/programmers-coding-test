import java.util.*;

class Solution {
    public int solution(int[][] maps) {
        int ROW = maps.length;
        int COL = maps[0].length;

        // 방문 여부와 거리 동시 저장(-1로 초기화)
        int[][] distances = new int[ROW][COL];
        for(int[] row : distances) {
            Arrays.fill(row, -1);
        }

        // BFS 큐 
        Queue<int[]> queue = new ArrayDeque<>();

        // 시작점 설정
        queue.add(new int[]{0, 0});
        distances[0][0] = 1;

        // 상하좌우 이동을 위한 벡터
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            // 목적지 도착 시 거리 반환
            if (r == ROW -1 && c == COL -1) {
                return distances[r][c];
            }

            // 4방향 탐색
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                // 맵을 벗어나지 않고
                if(nr >= 0 && nr < ROW && nc >= 0 && nc < COL) {
                    // 갈 수 있는 길이며, 방문하지 않은 곳
                    if(maps[nr][nc] == 1 && distances[nr][nc] == -1) {
                        // 다음 칸의 거리는 현재 칸 + 1
                        distances[nr][nc] = distances[r][c] + 1;
                        queue.add(new int[]{nr, nc});
                    }
                }
            }
        } 

        return -1;
    }
}