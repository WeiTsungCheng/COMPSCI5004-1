# Implementation of Data Structures in Java

參照 University of Glasgow「COMPSCI5004 Algorithms & Data Structures」課程講義，以 Java 實作常見抽象資料型別（ADT）。

本專案以理解操作流程與底層結構為目標，保留容易閱讀的基本版本。目前包含 **5 個 ADT 介面、14 種實作，以及 Main1～Main8 示範與驗證程式**。部分批次操作採逐項處理，尚未實作講義中的最佳化合併演算法。

## 實作內容

| ADT | 陣列 | 單向鏈結串列 | 二元搜尋樹 | 雜湊表 |
|---|---|---|---|---|
| Stack | ArrayStack | LinkedStack | — | — |
| Queue | ArrayQueue（環狀陣列） | LinkedQueue | — | — |
| List | ArrayList | LinkedList | — | — |
| Set | ArraySet（排序陣列） | LinkedSet（排序串列） | BSTSet | HashSet |
| Map | ArrayMap（依 key 排序） | LinkedMap（依 key 排序） | BSTMap | HashMap |

- **Stack**：後進先出（LIFO），提供 push、pop、peek、clear。
- **Queue**：先進先出（FIFO），從尾端加入、從前端移除。
- **List**：依位置存取、插入、更新及刪除，支援串接與 iterator。
- **Set**：不接受重複成員，支援聯集、交集、差集及成員查詢。
- **Map**：儲存 key-value 配對；key 不重複，相同 key 的 put 會更新 value。

## 專案結構

```text
.
├── README.md
├── TIME_COMPLEXITY.md
├── Lecture/
│   ├── ...                     # 原始講義
│   └── 轉pdf/                  # PDF 講義
└── src/
    └── ABSTRACT_DATA_TYPES/
        ├── interfaces/         # Stack、Queue、List、Set、Map
        ├── ADTs/
        │   ├── stack/          # ArrayStack、LinkedStack
        │   ├── queue/          # ArrayQueue、LinkedQueue
        │   ├── list/           # ArrayList、LinkedList
        │   ├── set/            # ArraySet、LinkedSet、BSTSet、HashSet
        │   └── map/            # ArrayMap、LinkedMap、BSTMap、HashMap
        └── Main1.java ～ Main8.java
```

`out/` 是執行編譯指令後產生的目錄，已加入 `.gitignore`。

## 環境需求

- JDK，須包含 `java` 與 `javac`；目前程式已使用 OpenJDK 11 編譯及執行驗證。
- 不需要額外第三方套件，也不需要 Maven 或 Gradle。

先確認 Java 環境：

```bash
java -version
javac -version
```

## 編譯與執行

以下指令都在專案根目錄執行，也就是包含 `src/` 的目錄。

### 編譯全部主程式

```bash
javac -d out -sourcepath src src/ABSTRACT_DATA_TYPES/Main*.java
```

| 參數 | 用途 |
|---|---|
| `-d out` | 將編譯後的 `.class` 檔案放進 out，並依套件建立子目錄 |
| `-sourcepath src` | 讓編譯器從 src 尋找相依類別的原始碼 |
| `Main*.java` | 編譯 Main1～Main8 |

### 執行單一範例

例如執行 HashMap 的驗證：

```bash
java -ea -cp out ABSTRACT_DATA_TYPES.Main8
```

- `-cp out`：指定編譯後類別的搜尋根目錄。
- `-ea`：啟用 `assert`；若條件不成立，拋出 `AssertionError`。
- `ABSTRACT_DATA_TYPES.Main8`：包含套件名稱的主類別名稱。

可將 Main8 換成 Main1～Main7。只想編譯其中一個時：

```bash
javac -d out -sourcepath src src/ABSTRACT_DATA_TYPES/Main3.java
java -ea -cp out ABSTRACT_DATA_TYPES.Main3
```

### 一次執行 Main1～Main8

以下適用於 macOS／Linux 的 zsh 或 bash；先完成編譯，再執行：

```bash
for i in {1..8}; do
    java -ea -cp out "ABSTRACT_DATA_TYPES.Main$i" || break
    echo "Main$i 通過"
done
```

部分 Main 沒有成功訊息，正常結束時可能沒有輸出。請保留 `-ea`，否則 Java 預設不執行斷言檢查。部分早期範例僅列印結果，因此「主程式執行成功」不等於每個方法都已完整測試。

### 編譯警告

目前部分泛型陣列轉型及原始型別使用會產生 unchecked 警告；這些警告不會阻止編譯。查看詳細位置：

```bash
javac -Xlint:unchecked -d out -sourcepath src src/ABSTRACT_DATA_TYPES/Main*.java
```

## Main1～Main8 對照

| 主程式 | 實作 | 主要示範／檢查 |
|---|---|---|
| Main1 | ArrayStack、LinkedStack、ArrayQueue、LinkedQueue | 清空與重用、Stack 基本操作、Queue FIFO 與大小 |
| Main2 | ArrayList、LinkedList、ArraySet、LinkedSet | List 比較、串接、自我串接；Set 去重與查詢 |
| Main3 | BSTSet | 搜尋、新增、三種節點刪除情況、排序遍歷與集合運算 |
| Main4 | HashSet | 碰撞、重複成員、鏈結節點刪除、負數雜湊與集合運算 |
| Main5 | ArrayMap | 新增、更新、移除、keySet、覆蓋與容量限制 |
| Main6 | LinkedMap | 前端／中間／尾端插入刪除、覆蓋與比較 |
| Main7 | BSTMap | 樹節點刪除、key-value 配對維護、覆蓋與 null value |
| Main8 | HashMap | 碰撞、更新、刪除、keySet、覆蓋與 null value |

目前 Main1～Main8 已全部開啟斷言執行通過。另曾使用暫存測試，對 14 種實作進行隨機操作及容量、碰撞、自我運算等邊界檢查；這些額外測試不是專案內可直接重跑的正式測試套件。

## Map 操作規則

| 方法 | 行為 |
|---|---|
| `get(key)` | 回傳對應 value；不存在則回傳 null |
| `put(key, value)` | 新 key 建立配對；既有 key 更新 value；回傳舊 value，不存在則回傳 null |
| `remove(key)` | 移除配對並回傳舊 value；不存在則回傳 null |
| `keySet()` | 回傳所有 key 組成的獨立集合 |
| `putAll(that)` | 加入來源配對，相同 key 以來源 value 覆蓋 |
| `equals(that)` | 比較配對內容；目前以相同實作之間的比較為使用範圍 |

Map 介面中的 `Set<K>` 使用本專案自訂的 Set 介面。Map 本身不繼承 Set，而是用 Set 表示所有 key 的集合。

Map 允許 null value，所以 `get(key) == null` 無法單獨區分「key 不存在」與「value 為 null」。需要時可使用 `keySet().contains(key)` 確認，但建立 keySet 有額外成本。

## 時間複雜度

完整方法表與推導請見 [TIME_COMPLEXITY.md](TIME_COMPLEXITY.md)。

四種 Map 的主要操作摘要：

| 操作 | ArrayMap | LinkedMap | BSTMap | HashMap |
|---|---:|---:|---:|---:|
| 查詢 | O(log n) | O(n) | O(h) | 預期 O(1 + n/b) |
| 新增新 key | O(n) | O(n) | O(h) | 預期 O(1 + n/b) |
| 更新既有 key | O(log n) | O(n) | O(h) | 預期 O(1 + n/b) |
| 刪除 | O(n) | O(n) | O(h) | 預期 O(1 + n/b) |

n 是配對數量、h 是樹高、b 是桶子數量；假設單次比較與雜湊計算為 O(1)。BST 退化時 h 可達 O(n)，Hash 大量碰撞時操作最壞為 O(n)。`keySet()`、`equals()` 與 `putAll()` 需另外計算建立集合及重複搜尋的成本。

## 目前設計與限制

- **固定容量陣列**：ArrayStack、ArrayQueue、ArrayList、ArraySet、ArrayMap 不會自動擴充；需要新增元素但空間不足時拋出例外。
- **固定桶子數量**：HashSet、HashMap 以鏈結串列處理碰撞。建構子的數字是桶子數量，不是元素上限；尚未實作重新雜湊與自動擴充。
- **一般 BST**：BSTSet、BSTMap 沒有自動平衡；排序資料依序插入可能退化成鏈，深樹的遞迴也受呼叫堆疊限制。
- **簡化批次操作**：Set 集合運算與 Map.putAll() 多採逐項操作，尚未全面實作講義的線性合併效率。
- **相等比較範圍**：目前以同一種 Set／Map 實作之間的比較為主。排序實作依賴遍歷順序，不保證與 Hash 版本交互比較的結果。自訂 `equals(Set)`、`equals(Map)`、`equals(List)` 是多載，並未覆寫 `Object.equals(Object)`。
- **null**：HashSet 明確拒絕 null 成員，排序 Set 目前也不以 null 成員為使用範圍。四種 Map 都拒絕 null key，但允許 null value。
- **iterator**：BSTSet、HashSet 使用 ArrayList 快照；建立 iterator 需要走訪資料。其 iterator.remove() 只修改快照，不會移除原 Set 的元素。
- **keySet**：修改回傳的集合，不會直接改變原 Map。key 物件本身沒有深層複製。
- **元素相等性**：排序 Set／Map 使用 compareTo 判斷成員／key 是否相同；Hash 使用 equals 與 hashCode。自訂型別應遵守對應的比較及雜湊規則，儲存後避免修改會影響排序或雜湊的欄位。
- **清空成本**：ArrayStack.clear() 逐格清除引用，是 O(n)。ArrayList.clear() 與 ArrayQueue.clear() 目前只重設狀態，是 O(1)，但陣列仍可能保留舊引用。

## 講義與後續練習

參考講義位於 `Lecture/轉pdf/`：

| 章節 | 對應主題 |
|---|---|
| 05 | ADT 與集合 |
| 06 | Stack |
| 07 | Queue |
| 08 | List 與 Iterator |
| 09 | Set |
| 10 | BST |
| 11 | Map |
| 12 | Hash Table |