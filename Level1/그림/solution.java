const fs = require('fs');
const input = fs.readFileSync('/dev/stdin').toString().trim().split('\n');

function solve() {
    // 입력 파싱
    const [n, m] = input[0].split(' ').map(Number);
    const board = [];
    for (let i = 1; i <= n; i++) {
        board.push(input[i].split(' ').map(Number));
    }

    // 방문 여부를 저장할 2차원 배열
    const visited = Array.from({ length: n }, () => Array(m).fill(false));

    // 방향 벡터 (상, 하, 좌, 우)
    const dx = [-1, 1, 0, 0];
    const dy = [0, 0, -1, 1];

    let pictureCount = 0; 
    let maxArea = 0;      // 가장 넓은 그림의 넓이

    // BFS 탐색 함수
    function bfs(startX, startY) {
        let area = 0;
        const queue = [];
        
        // 시작점 방문 처리 및 큐 삽입
        visited[startX][startY] = true;
        queue.push([startX, startY]);

        // JS에서 Shift()는 O(N)이지만, 이 문제의 범위(500x500) 내에서는 통과 가능합니다.
        // 더 최적화하려면 포인터 변수(head)를 두는 것이 좋습니다.
        let head = 0;
        while (head < queue.length) {
            const [x, y] = queue[head++];
            area++; // 큐에서 꺼낼 때마다 넓이 증가

            // 상하좌우 네 방향 확인
            for (let i = 0; i < 4; i++) {
                const nx = x + dx[i];
                const ny = y + dy[i];

                // 범위를 벗어나지 않고, 그림(1)이며, 아직 방문하지 않은 경우
                if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    if (board[nx][ny] === 1 && !visited[nx][ny]) {
                        visited[nx][ny] = true;
                        queue.push([nx, ny]);
                    }
                }
            }
        }
        return area;
    }

    // 3. 전체 격자 순회
    for (let i = 0; i < n; i++) {
        for (let j = 0; j < m; j++) {
            // 그림이 있고 아직 방문하지 않은 곳 발견 시 탐색 시작
            if (board[i][j] === 1 && !visited[i][j]) {
                pictureCount++;
                const currentArea = bfs(i, j);
                maxArea = Math.max(maxArea, currentArea);
            }
        }
    }

    // 4. 결과 출력
    console.log(pictureCount);
    console.log(maxArea);
}

solve();