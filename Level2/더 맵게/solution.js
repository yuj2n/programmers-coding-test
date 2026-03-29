class MinHeap {
  constructor() {
    this.heap = [];
  }

  push(val) {
    this.heap.push(val);
    let cur = this.heap.length - 1;
    while (cur > 0) {
      let parent = Math.floor((cur - 1) / 2);
      if (this.heap[cur] < this.heap[parent]) {
        [this.heap[cur], this.heap[parent]] = [
          this.heap[parent],
          this.heap[cur],
        ];
        cur = parent;
      } else break;
    }
  }

  pop() {
    if (this.heap.length === 0) return null;
    if (this.heap.length === 1) return this.heap.pop();

    const min = this.heap[0];
    this.heap[0] = this.heap.pop();
    let cur = 0;
    while (true) {
      let left = cur * 2 + 1;
      let right = cur * 2 + 2;
      let target = cur;

      if (left < this.heap.length && this.heap[left] < this.heap[target])
        target = left;
      if (right < this.heap.length && this.heap[right] < this.heap[target])
        target = right;

      if (target !== cur) {
        [this.heap[cur], this.heap[target]] = [
          this.heap[target],
          this.heap[cur],
        ];
        cur = target;
      } else break;
    }
    return min;
  }

  size() {
    return this.heap.length;
  }

  peek() {
    return this.heap[0];
  }
}

function solution(scoville, K) {
  const heap = new MinHeap();
  scoville.forEach((s) => heap.push(s));

  let mixCount = 0;

  // 가장 작은 값이 K보다 작을 때만 반복
  while (heap.peek() < K) {
    // 음식이 하나 남았는데 그게 K보다 작으면 더 이상 섞을 수 없음
    if (heap.size() < 2) return -1;

    const first = heap.pop();
    const second = heap.pop();
    const mixed = first + second * 2;

    heap.push(mixed);
    mixCount++;
  }

  return mixCount;
}
