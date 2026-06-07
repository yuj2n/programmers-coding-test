# [백준 14889] 스타트와 링크 - Java 풀이 정리

## 1. 문제 개요

N명의 사람을 각각 N/2명씩 스타트 팀과 링크 팀으로 나눈 뒤, 각 팀의 능력치 합의 차이가 최소가 되도록 팀을 구성하는 문제입니다. 팀의 능력치는 팀원들 간의 시너지 배열인 S[i][j]와 S[j][i]의 합으로 계산됩니다.

## 2. 접근 방식 및 구현 아이디어

문제를 해결하기 위해 크게 두 가지 단계로 나누어 접근했습니다. N의 크기가 정해져 있지 않으므로 팀을 구성할 때는 재귀 함수를 사용했고, 팀이 결정된 후 능력치를 계산할 때는 고정된 인원이므로 반복문을 활용했습니다.

1. **팀 나누기 (조합 구하기)**

- N명 중 N/2명을 선택하는 조합($\binom{N}{N/2}$) 문제입니다.
- N의 크기가 고정되어 있지 않아 3중, 4중 for문으로는 한계가 있으므로, 백트래킹(재귀 호출)을 이용해 구현했습니다.
- `visited` 배열을 활용해 `true`인 사람은 스타트 팀, `false`인 사람은 링크 팀으로 분류합니다.

2. **팀별 능력치 계산 및 최솟값 갱신**

- 팀 구성이 완료되면(선택된 인원이 N/2명이 되면) 각 팀의 능력치를 구합니다.
- 이때는 고정된 인원(2명)의 쌍을 짓는 순열/조합 문제이므로, 굳이 재귀를 쓸 필요 없이 **2중 반복문**을 사용하여 효율적으로 계산할 수 있습니다.

### Java 소스 코드

```java
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

        // 0번 사람을 스타트 팀에 무조건 포함되도록 고정 (탐색 범위 절반으로 최적화)
        backtracking(0, 0);
        System.out.println(minDiff);
    }

    // N명 중 N/2명을 뽑는 조합 함수
    static void backtracking(int idx, int count) {
        // 팀원이 절반 채워졌으면 능력치 비교 시작
        if (count == N / 2) {
            getDiff();
            return;
        }

        for (int i = idx; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                backtracking(i + 1, count + 1);
                visited[i] = false; // 다음 탐색을 위해 원상복구
            }
        }
    }

    // 두 팀의 능력치 차이를 계산하는 함수
    static void getDiff() {
        int startTeamScore = 0;
        int linkTeamScore = 0;

        // 2중 반복문으로 팀원 간의 시너지 합산
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                // i와 j가 모두 스타트 팀인 경우
                if (visited[i] && visited[j]) {
                    startTeamScore += S[i][j] + S[j][i];
                }
                // i와 j가 모두 링크 팀인 경우
                else if (!visited[i] && !visited[j]) {
                    linkTeamScore += S[i][j] + S[j][i];
                }
            }
        }

        int diff = Math.abs(startTeamScore - linkTeamScore);

        // 차이가 0이면 더 이상 최적화가 불가능하므로 즉시 프로그램 종료
        if (diff == 0) {
            System.out.println(0);
            System.exit(0);
        }

        minDiff = Math.min(minDiff, diff);
    }
}

```

## 3. 복잡도 및 최적화 분석

- **시간 복잡도**: $O(\binom{N}{N/2} \times N^2)$
- N의 최댓값은 20으로, 조합의 수 자체는 최대 $\binom{20}{10} = 184,756$번 일어납니다.
- 여기에 팀 스코어를 정산할 때 매번 약 $20 \times 20 = 400$번의 루프를 돌게 되므로 전체 연산량은 약 7,300만 번 수준입니다. 자바 기준 제한 시간이 2초이므로 여유롭게 통과할 수 있습니다.

- **최적화 포인트**
- 스타트 팀과 링크 팀은 단순히 이름만 다른 대칭 구조입니다. 따라서 0번 사람을 무조건 스타트 팀에 고정시켜 두면 연산량이 $\binom{20}{10}$에서 $\binom{19}{9}$로 정확히 절반이 줄어듭니다.
- 탐색 도중 두 팀의 점수 차가 0이 되는 순간 최적의 답을 찾은 것이므로 뒤의 탐색을 생략하고 즉시 종료하도록 구현하여 시간을 더 단축했습니다.

## 4. 풀이 소감

가변적인 크기의 팀을 구성할 때는 재귀(백트래킹)를 쓰고, 인원이 고정된 팀 안에서 시너지를 구할 때는 2중 반복문을 쓰는 구조적 차이를 명확히 이해할 수 있는 문제였습니다. 무작정 모든 경우를 다 구하기보다 대칭성을 이용해 연산 횟수를 절반으로 줄이는 아이디어가 효율적인 알고리즘 설계에 얼마나 중요한지 배울 수 있었습니다.
