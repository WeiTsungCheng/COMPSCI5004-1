# 時間複雜度整理



## 符號與計算前提

| 符號 | 意思 |
|---|---|
| n | 目前容器的元素／配對數量 |
| m | 傳入的另一個容器的元素／配對數量 |
| c | 陣列容量 |
| p | List 操作的位置，從 0 開始 |
| h | 目前 BST 的高度 |
| h₂ | 另一棵 BST 的高度 |
| H | 批次新增過程中的最大樹高 |
| b | 目前 Hash 容器的桶子數量 |
| b₂ | 另一個 Hash 容器的桶子數量 |
| α = n / b | Hash 的負載因子 |

- 假設單次 `compareTo()`、`equals()`、`hashCode()` 都是 O(1)；長字串或複雜物件需要另計比較成本。
- 除 Hash 的預期時間欄外，列的是最壞情況的時間上界，不一定是最緊的界。
- 批次操作及相等比較按同一種實作之間操作計算，不涵蓋不同實作之間的相等比較。
- O(log n)、O(n)、O(c) 的一般寫法省略空容器的固定成本；涵蓋零元素時，可分別理解為 O(log(n + 1))、O(n + 1)、O(c + 1)。
- 不把之後垃圾回收（GC）的工作計入 `clear()` 方法本身的成本。
- 目前簡單版本的排序 Set 不以 null 元素為使用範圍；Map 不接受 null key，但允許 null value。

## 1. Stack

| 方法 | ArrayStack | LinkedStack |
|---|---:|---:|
| 建構子 | O(c) | O(1) |
| isEmpty() | O(1) | O(1) |
| peek() | O(1) | O(1) |
| push() | O(1) | O(1) |
| pop() | O(1) | O(1) |
| clear() | O(n) | O(1) |

`ArrayStack.clear()` 逐格將有效元素設為 null，所以是 O(n)。`LinkedStack.clear()` 只將 top 設為 null，所以方法本身是 O(1)。

## 2. Queue

| 方法 | ArrayQueue | LinkedQueue |
|---|---:|---:|
| 建構子 | O(c) | O(1) |
| isEmpty() | O(1) | O(1) |
| size() | O(1) | O(1) |
| getFirst() | O(1) | O(1) |
| addLast() | O(1) | O(1) |
| removeFirst() | O(1) | O(1) |
| clear() | O(1) | O(1) |

- ArrayQueue 使用環狀陣列，新增與移除只更新索引，不搬動所有元素。
- LinkedQueue 保留 rear 指標，新增到尾端不需要從頭尋找。
- 目前 ArrayQueue.clear() 只重設 size、front、rear，因此是 O(1)，但陣列可能仍持有舊元素引用。

## 3. List

| 方法 | ArrayList | LinkedList |
|---|---:|---:|
| 建構子 | O(c) | O(1) |
| isEmpty() | O(1) | O(1) |
| size() | O(1) | O(1) |
| get(p) | O(1) | O(n) |
| set(p, value) | O(1) | O(n) |
| add(p, value) | O(n) | O(n) |
| addLast(value) | O(1) | O(1) |
| remove(p) | O(n) | O(n) |
| clear() | O(1) | O(1) |
| equals(that) | O(n) | O(n) |
| addAll(that) | O(m) | O(m) |
| 建立 iterator() | O(1) | O(1) |
| iterator.hasNext() | O(1) | O(1) |
| iterator.next() | O(1) | O(1) |

- LinkedList 的位置操作更精確為 O(p + 1)，最壞為 O(n)。
- ArrayList 的插入、刪除需要移動後續元素；尾端新增不用搬移。
- 目前 ArrayList 固定容量，不會自動擴充，因此 addLast() 沒有擴充造成的 O(n) 情況，滿了直接拋出例外。
- equals() 使用 iterator，各走訪一次；若改成反覆呼叫 LinkedList.get(i)，可能變成 O(n²)。
- addAll() 呼叫 m 次 O(1) 的 addLast()，所以是 O(m)。
- ArrayList.clear() 只設 size = 0，因此是 O(1)，但仍可能保留舊引用。

## 4. 排序 Set

| 方法 | ArraySet | LinkedSet | BSTSet |
|---|---:|---:|---:|
| 建構子 | O(c) | O(1) | O(1) |
| isEmpty() | O(1) | O(1) | O(1) |
| size() | O(1) | O(1) | O(1) |
| contains() | O(log n) | O(n) | O(h) |
| add() | O(n) | O(n) | O(h) |
| remove() | O(n) | O(n) | O(h) |
| clear() | O(n) | O(1) | O(1) |
| equals(that)，大小相同 | O(n) | O(n) | O(n) |
| containsAll(that) | O(m log(n + 1)) | O(m(n + 1)) | O(m(h + 1)) |
| addAll(that) | O(m(n + m + 1)) | O(m(n + m + 1)) | O(m(H + 1)) |
| removeAll(that) | O(m(n + 1)) | O(m(n + 1)) | O(m(h + 1)) |
| retainAll(that) | O(n log(m + 1) + n²) | O(n(m + 1) + n²) | O(n(h + h₂ + 1)) |
| 建立 iterator() | O(1) | O(1) | O(n) |
| iterator.hasNext() | O(1) | O(1) | O(1) |
| iterator.next() | O(1) | O(1) | O(1) |

### 批次操作

目前保留逐項呼叫 contains、add、remove 的簡單寫法，沒有改成講義的線性合併演算法。

- containsAll()：走訪另一個集合，對每個元素呼叫 contains()。
- addAll()：重複新增，集合可能逐步變大；LinkedSet 每次插入仍需搜尋位置。
- ArraySet.addAll() 的表格採保守上界。來源有排序時，可更細分為原有元素搬移與二元搜尋成本，不應把表格當成精確操作次數。
- removeAll()：對另一個集合的每個元素呼叫 remove()。
- retainAll()：走訪目前集合，呼叫另一個集合的 contains()，必要時再 remove()。

### BST 高度與 iterator

- 樹平衡時，h = O(log n)；退化成一條鏈時，h = O(n)。
- BSTSet.iterator() 先中序遍歷全部節點，建立 ArrayList 快照，因此建立時間及額外儲存空間為 O(n)。
- 建立快照後，每次 hasNext()、next() 都是 O(1)。
- BSTSet.remove() 先搜尋、再遞迴刪除；兩次 O(h) 相加仍是 O(h)。

## 5. HashSet

| 方法 | 分布合理、負載因子維持常數時的預期時間 | 最壞情況 |
|---|---:|---:|
| 建構子 | O(b) | O(b) |
| isEmpty() | O(1) | O(1) |
| size() | O(1) | O(1) |
| contains() | O(1) | O(n) |
| add() | O(1) | O(n) |
| remove() | O(1) | O(n) |
| clear() | O(b) | O(b) |
| 建立 iterator() | O(b + n) | O(b + n) |
| iterator.hasNext()、next() | O(1) | O(1) |

單次搜尋、新增、刪除更一般的預期時間為 O(1 + α)，也就是 O(1 + n/b)。

目前桶子數量固定，沒有自動擴充；元素持續增加後，不能一直假設操作為 O(1)。最壞時所有元素碰撞在同一個桶子，退化成單條鏈結串列。

| 批次方法 | 兩邊負載受控制時的預期時間 | 最壞上界 |
|---|---:|---:|
| equals(that)，大小相同 | O(b₂ + n) | O(b₂ + n²) |
| containsAll(that) | O(b₂ + m) | O(b₂ + m(n + 1)) |
| addAll(that) | O(b₂ + m) | O(b₂ + m(n + m + 1)) |
| removeAll(that) | O(b₂ + m) | O(b₂ + m(n + 1)) |
| retainAll(that) | O(b + n) | O(b + n(m + n + 1)) |

addAll() 的預期時間也要求新增過程中的負載維持常數。b、b₂ 的成本來自 iterator 建立快照時掃描桶子陣列；快照另需 O(n) 或 O(m) 元素儲存空間。

## 6. 排序 Map

| 方法 | ArrayMap | LinkedMap | BSTMap |
|---|---:|---:|---:|
| 建構子 | O(c) | O(1) | O(1) |
| isEmpty() | O(1) | O(1) | O(1) |
| size() | O(1) | O(1) | O(1) |
| get(key) | O(log n) | O(n) | O(h) |
| put(key, value) | O(n) | O(n) | O(h) |
| remove(key) | O(n) | O(n) | O(h) |
| clear() | O(n) | O(1) | O(1) |
| keySet() | O(n log(n + 1)) | O(n²) | O(n(h + 1)) |
| equals(that)，大小相同 | O(n log(n + 1)) | O(n²) | O(n(h + h₂ + 1)) |
| putAll(that) | O(m(n + m + 1)) | O(m(n + m + 1)) | O(m(h₂ + H + 1)) |

### ArrayMap 的更新與新增

- put(已存在的 key, value)：二元搜尋後更新，O(log n)。
- put(不存在的 key, value)：可能搬移後續配對，最壞 O(n)。
- 容量已滿仍可更新既有 key；新增不同 key 才需要空位。

### keySet() 的成本

不能只計算外層走訪，還要算每次 Set.add()：

- ArrayMap：按 key 排序加入 ArraySet，不必搬移，但每次仍二元搜尋，總計 O(n log(n + 1))。
- LinkedMap：按排序順序加入 LinkedSet，每次又從頭找位置，累計 0 + 1 + ... + (n - 1)，為 O(n²)。
- BSTMap：前序走訪並逐項插入 BSTSet，成本與原樹各節點深度總和有關；O(n(h + 1)) 是上界，平衡時 O(n log n)，退化時 O(n²)。

目前 equals()、putAll() 呼叫 keySet()、get()、put()，因此需要計入這些成本。簡單版不等於講義中線性比較或合併版本的效率。

## 7. HashMap

| 方法 | 分布合理、負載因子維持常數時的預期時間 | 最壞情況 |
|---|---:|---:|
| 建構子 | O(b) | O(b) |
| isEmpty() | O(1) | O(1) |
| size() | O(1) | O(1) |
| get() | O(1) | O(n) |
| put() | O(1) | O(n) |
| remove() | O(1) | O(n) |
| clear() | O(b) | O(b) |
| keySet() | O(b + n) | O(b + n²) |

keySet() 逐項呼叫 HashSet.add()。若所有 key 都碰撞，每次新增都要搜尋已加入的鏈結串列，累計可能達到 O(n²)。它不是單純複製所有 key。

| 批次方法 | 兩邊負載受控制時的預期時間 | 最壞上界 |
|---|---:|---:|
| equals(that)，大小相同 | O(b₂ + n) | O(b₂ + n²) |
| putAll(that) | O(b₂ + m) | O(b₂ + m(n + m + 1)) |

putAll() 的預期時間要求新增過程中負載也維持常數。更一般的單次 get、put、remove 預期時間為 O(1 + n/b)。

## 8. 自我運算與提早結束

| 操作 | 時間 |
|---|---:|
| List.equals(自己) | O(1) |
| Map.equals(自己) | O(1) |
| Map.putAll(自己) | O(1) |
| ArraySet.removeAll(自己) | O(n)，呼叫 clear() |
| LinkedSet.removeAll(自己) | O(1)，呼叫 clear() |
| BSTSet.removeAll(自己) | O(1)，呼叫 clear() |
| HashSet.removeAll(自己) | O(b)，呼叫 clear() |
| equals() 發現兩個容器大小不同 | O(1) |

