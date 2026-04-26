function solution(new_id) {
  // 1단계: 소문자 치환
  let answer = new_id.toLowerCase();

  // 2단계: 소문자, 숫자, -, _, . 제외한 문자 제거
  // [^...]은 '안에 포함된 문자 제외'를 의미
  answer = answer.replace(/[^a-z0-0-_\.]/g, "");

  // 3단계: 마침표(.)가 2번 이상 연속되면 하나로 치환
  // \.{2,}는 마침표가 2회 이상 반복됨을 의미
  answer = answer.replace(/\.{2,}/g, ".");

  // 4단계: 마침표(.)가 처음이나 끝에 위치한다면 제거
  // ^\.는 시작점의 마침표, \.$는 끝점의 마침표를 의미
  answer = answer.replace(/^\.|\.$/g, "");

  // 5단계: 빈 문자열이라면 "a" 대입
  if (answer === "") {
    answer = "a";
  }

  // 6단계: 길이가 16자 이상이면 첫 15개만 남기기
  if (answer.length >= 16) {
    answer = answer.slice(0, 15);
    // 만약 제거 후 끝에 마침표가 있다면 또 제거
    answer = answer.replace(/\.$/g, "");
  }

  // 7단계: 길이가 2자 이하라면 마지막 문자를 길이가 3이 될 때까지 반복
  while (answer.length <= 2) {
    answer += answer[answer.length - 1];
  }

  return answer;
}
