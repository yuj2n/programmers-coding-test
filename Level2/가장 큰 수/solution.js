function solution(numbers) {
  // 1. 숫자들을 문자열로 바꾼 뒤 정렬
  const answer = numbers
    .map(String) // ["10", "2", "9"] 처럼 문자열로 변환
    .sort((a, b) => {
      // (b + a)와 (a + b)를 비교
      // 예: a="10", b="2" 일 때 "210" vs "102"
      return b + a - (a + b);
    })
    .join("");

  // 2. 모든 숫자가 0인 경우(예: [0, 0, 0]) "000"이 아니라 "0"을 리턴
  return answer[0] === "0" ? "0" : answer;
}
