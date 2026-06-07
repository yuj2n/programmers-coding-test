import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] S;
    static boolean[] visited;
    static int minDiff = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        S = new int[N][N];
        visited = new boolean[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 0번 사람을 스타트 팀에 고정해두고 조합을 돌리면 연산량이 절반으로 줄어듭니다.
        backtracking(0, 0);
        System.out.println(minDiff);
    }

    // 팀 구성을 위한 재귀 호출 (N C N/2 조합)
    static void backtracking(int idx, int count) {
        if (count == N / 2) {
            calculateDiff();
            return;
        }

        for (int i = idx; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                backtracking(i + 1, count + 1);
                visited[i] = false; // 백트래킹을 위한 원상복구
            }
        }
    }

    // 두 팀의 능력치 차이를 계산 (2중 반복문 구현)
    static void calculateDiff() {
        int startTeam = 0;
        int linkTeam = 0;

        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                // 두 사람이 모두 스타트 팀(visited == true)인 경우
                if (visited[i] && visited[j]) {
                    startTeam += S[i][j] + S[j][i];
                }
                // 두 사람이 모두 링크 팀(visited == false)인 경우
                else if (!visited[i] && !visited[j]) {
                    linkTeam += S[i][j] + S[j][i];
                }
            }
        }

        int diff = Math.abs(startTeam - linkTeam);

        // 차이가 0이면 이보다 더 작아질 수 없으므로 즉시 종료 및 출력
        if (diff == 0) {
            System.out.println(0);
            System.exit(0);
        }

        minDiff = Math.min(minDiff, diff);
    }
}