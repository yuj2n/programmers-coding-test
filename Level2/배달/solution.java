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
        // 인접 리스트
        ArrayList<ArrayList<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }
        
        // 간선 정보 입력
        for (int i = 0; i < road.length; i++) {
            int u = road[i][0];
            int v = road[i][1];
            int w = road[i][2];
            
            // 양방향으로 간선 추가
            graph.get(u).add(new Node(v, w));
            graph.get(v).add(new Node(u, w));
        }
        
        // 최단 시간 배열
        int[] dist = new int[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        
        // 우선순위 큐
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(new Node(1, 0));
        
        while (!q.isEmpty()) {
            Node current = q.remove();
            
            if (current.weight > dist[current.vertex]) continue;
            
            for (Node next : graph.get(current.vertex)) {
                int cost = next.weight + dist[current.vertex];
                
                if (cost < dist[next.vertex]) {
                    dist[next.vertex] = cost;
                    q.add(new Node(next.vertex, cost));
                }
            }
        }

        // K 이하 시간 내 배달 가능한 마을 개수 리턴
        int answer = 0;
        for (int i = 1; i <= N; i++) {
            if (dist[i] <= K) answer++;
        }
        return answer;
    }
}