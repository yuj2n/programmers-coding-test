function solution(n, times) {
  // 1. 시간의 범위를 설정하기 위해 정렬 (가장 오래 걸리는 시간을 찾기 위함)
  times.sort((a, b) => a - b);

  let left = 1;
  let right = times[times.length - 1] * n;
  let answer = right;

  while (left <= right) {
    let mid = Math.floor((left + right) / 2);
    let count = 0;

    // 2. mid 시간 동안 각 심사관이 심사할 수 있는 인원 수의 합을 구함
    for (let time of times) {
      count += Math.floor(mid / time);

      // 이미 n명을 넘었다면 더 이상 계산할 필요 없음 (효율성)
      if (count >= n) break;
    }

    // 3. n명 이상 심사 가능한지 여부에 따라 범위 조정
    if (count >= n) {
      answer = mid; // 우선 현재 시간을 정답 후보로 저장
      right = mid - 1;
    } else {
      left = mid + 1;
    }
  }

  return answer;
}
