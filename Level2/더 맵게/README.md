# [Programmers] 더 맵게 (Level 2)

핵심 키워드: `#Heap` `#Priority_Queue` `#Efficiency` `#Greedy`

---

## 1. 문제 설명

모든 음식의 스코빌 지수를 `K` 이상으로 만들기 위해, 가장 낮은 두 음식을 특정 공식으로 섞는 **최소 횟수**를 구하는 문제.

- **섞기 공식**: `가장 맵지 않은 음식 + (두 번째로 맵지 않은 음식 * 2)`
- **핵심 포인트**: 매번 **최솟값 2개**를 찾아야 하며, 섞은 후의 결과물을 다시 정렬된 상태로 유지해야 함!

---

## 2. 풀이 방식 (Min Heap 활용)

배열의 길이가 최대 **1,000,000**이므로, 매번 `sort()`를 사용하면 시간 초과가 발생함. 삽입과 삭제가 $O(\log N)$인 **최소 힙(Min Heap)**을 직접 구현하여 해결.

### 💡 로직 설명

1. **힙 구성**: 초기 `scoville` 배열의 모든 원소를 `MinHeap`에 삽입합니다.
2. **반복 조건**: 힙의 최솟값(`peek`)이 `K`보다 작을 때까지 반복해서 섞습니다.
3. **섞기 수행**:
   - 힙에서 가장 작은 값(`pop`)과 두 번째로 작은 값(`pop`)을 꺼냅니다.
   - 공식을 적용해 새로운 값을 만든 뒤 다시 힙에 넣습니다(`push`).
4. **예외 처리**:
   - 모든 음식을 섞었는데도 최솟값이 `K` 미만이면 `-1`을 반환합니다.
   - 섞어야 하는데 남은 음식이 1개뿐이라면 더 이상 진행할 수 없으므로 `-1`을 반환합니다.

```javascript
function solution(scoville, K) {
  const heap = new MinHeap(); // 직접 구현한 최소 힙 클래스 사용
  scoville.forEach((s) => heap.push(s));

  let count = 0;

  while (heap.peek() < K) {
    if (heap.size() < 2) return -1; // 더 이상 섞을 수 없는 경우

    const first = heap.pop();
    const second = heap.pop();
    const mixed = first + second * 2;

    heap.push(mixed);
    count++;
  }

  return count;
}
```

---

## 3. 시행착오 및 해결 과정

### ❌ `sort()` 기반 풀이의 한계

- 처음에 `scoville.sort()` 후 `shift()`를 사용하면, 매번 배열을 다시 정렬하거나 원소를 앞으로 당기는 과정에서 $O(N^2)$의 시간이 걸려 **효율성 테스트를 통과할 수 없음.**

### ✅ 자료구조의 선택

- **우선순위 큐(Priority Queue)** 개념이 핵심임을 파악. JavaScript 환경 특성상 힙을 직접 구현해야 하지만, 한 번 구현해두면 **효율성 테스트를 100% 통과**할 수 있음.
