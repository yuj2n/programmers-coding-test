function solution(participant, completion) {
  const pMap = new Map();

  // 1. 참가자 명단을 Map에 기록 (이름: 인원수)
  // pMap.get(name) || 0 을 통해 처음 등장하는 이름은 0으로 초기화 후 +1
  for (const name of participant) {
    pMap.set(name, (pMap.get(name) || 0) + 1);
  }

  // 2. 완주자 명단을 확인하며 인원수 차감
  for (const name of completion) {
    pMap.set(name, pMap.get(name) - 1);
  }

  // 3. 인원수가 남아있는 한 명이 완주하지 못한 선수
  for (const [name, count] of pMap) {
    if (count > 0) return name;
  }
}
