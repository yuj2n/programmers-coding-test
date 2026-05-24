# [Programmers] 배달 (Level 2)

핵심 키워드: `#Dijkstra` `#Graph` `#Shortest_Path` `#Priority_Queue`

---

## 1. 문제 설명

1번 마을에서 출발하여 다른 마을로 음식을 배달할 때, 주어진 제한 시간 `K` 이하로 배달이 가능한 마을의 총 개수를 구하는 문제.

- **그래프 구조**: 마을 간의 연결 도로 정보와 이동 시간이 주어지며, **양방향 통행**이 가능함.
- **핵심 포인트**: 두 마을을 연결하는 도로가 여러 개 있을 수 있으므로 최단 거리를 구해야 하며, 1번 정점으로부터 모든 정점까지의 **최단 경로(Dijkstra)**를 구하는 것이 핵심!

---

## 2. 풀이 방식 (Dijkstra 알고리즘 활용)

마을의 개수 $N$은 최대 50개, 도로 정보 `road`의 길이는 최대 2,000입니다. 1번 노드에서 시작하여 다른 모든 노드로 가는 최단 경로를 구해야 하므로, 우선순위 큐(Priority Queue)를 이용한 **다익스트라(Dijkstra) 알고리즘**을 활용하면 매우 효율적으로 해결할 수 있습니다.

### 💡 로직 설명

1. **그래프 구축**: 인접 리스트(`ArrayList<ArrayList<Node>>`)를 생성하고, 양방향 도로 정보를 저장합니다.
2. **최단 거리 배열 초기화**: 1번 마을에서 각 마을까지의 최단 거리를 저장할 `dist` 배열을 최대치(`Integer.MAX_VALUE`)로 초기화한 뒤, 시작점인 1번 마을의 거리는 `0`으로 설정합니다.
3. **우선순위 큐 탐색**:
   - `PriorityQueue`에 시작 노드(1번 마을, 비용 0)를 넣고 탐색을 시작합니다.
   - 현재 꺼낸 노드의 비용이 이미 기록된 최단 거리(`dist`)보다 크다면 탐색을 건너넙니다(`continue`).
   - 현재 노드와 연결된 인접 노드들을 확인하며, **[현재까지의 거리 + 다음 노드로의 가중치]**가 기존에 기록된 `dist[next]`보다 작다면 값을 갱신하고 큐에 삽입합니다.
4. **결과 도출**: 탐색이 끝난 후 `dist` 배열을 순회하며 값이 `K` 이하인 마을의 개수를 카운트하여 반환합니다.

```java
import java.util.*;

class Solution {
    static class Node implements Comparable<Node> {
        int vertex, weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public int solution(int N, int[][] road, int K) {
        // 인접 리스트 생성 및 초기화
        ArrayList<ArrayList<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        // 양방향 간선 정보 입력
        for (int i = 0; i < road.length; i++) {
            int u = road[i][0];
            int v = road[i][1];
            int w = road[i][2];

            graph.get(u).add(new Node(v, w));
            graph.get(v).add(new Node(u, w));
        }

        // 최단 거리 배열 초기화
        int[] dist = new int[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        // 다익스트라를 위한 우선순위 큐 생성
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(new Node(1, 0));

        while (!q.isEmpty()) {
            Node current = q.remove();

            // 이미 처리된 적이 있는 최적 경로라면 패스
            if (current.weight > dist[current.vertex]) continue;

            // 인접 정점 확인
            for (Node next : graph.get(current.vertex)) {
                int cost = next.weight + dist[current.vertex];

                // 더 짧은 경로를 찾은 경우 갱신 후 큐에 삽입
                if (cost < dist[next.vertex]) {
                    dist[next.vertex] = cost;
                    q.add(new Node(next.vertex, cost));
                }
            }
        }

        // K 이하의 시간 내에 배달이 가능한 마을 개수 계산
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            if (dist[i] <= K) answer++;
        }
        return answer;
    }
}
```
