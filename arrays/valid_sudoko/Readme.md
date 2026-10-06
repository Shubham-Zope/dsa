# Valid Sudoku

## LeetCode

- **Problem:** [Valid Sudoku](https://leetcode.com/problems/valid-sudoku/)
- **Difficulty:** Medium
- **Pattern:** Hashing / Matrix

---

## Problem Description

Determine if a `9 x 9` Sudoku board is valid.

A Sudoku board is valid if:

1. Each **row** contains the digits `1-9` without repetition.
2. Each **column** contains the digits `1-9` without repetition.
3. Each **3 x 3` sub-box** contains the digits `1-9` without repetition.

Empty cells are represented by `'.'`.

The board does **not** need to be completely filled to be valid.

### Example

**Input:**

```text id="m2j4xv"
[
 ["5","3",".",".","7",".",".",".","."],
 ["6",".",".","1","9","5",".",".","."],
 [".","9","8",".",".",".",".","6","."],
 ["8",".",".",".","6",".",".",".","3"],
 ["4",".",".","8",".","3",".",".","1"],
 ["7",".",".",".","2",".",".",".","6"],
 [".","6",".",".",".",".","2","8","."],
 [".",".",".","4","1","9",".",".","5"],
 [".",".",".",".","8",".",".","7","9"]
]
```

**Output:**

```text id="e7yrpp"
true
```

---

## Constraints

- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]` is a digit `1-9` or `'.'`.

---

# Thought Process

The first thing to notice is that we **don't need to solve the Sudoku**.

We only need to check whether the current board violates any rule.

There are exactly three things we need to validate:

```text id="k7x3ju"
1. Every row
2. Every column
3. Every 3 × 3 box
```

For each of these, the rule is essentially the same:

> **A digit must not appear more than once.**

This suggests using a `HashSet`.

For example, while checking a row:

```text id="u0zn8a"
[5, 3, ., ., 7, ., ., ., .]
```

we keep track of the digits we've already seen:

```text id="f5i5te"
seen = {5, 3, 7}
```

If we encounter a digit already in `seen`, the board is invalid.

The same idea can be applied independently to columns and 3×3 boxes.

---

# 1. Brute Force Approach

## Idea

For every row, column, and 3×3 box:

1. Collect all non-empty digits.
2. Compare every digit against every other digit.
3. If the same digit appears twice, return `false`.

For example, for a row:

```text id="4l7c4w"
[5, 3, 5, ., 7, ., ., ., .]
```

we could compare:

```text id="j4j8ye"
5 ↔ 3
5 ↔ 5  ← duplicate
```

and immediately return `false`.

## Problem With This Approach

We repeatedly compare values inside the same row, column, or box.

A `HashSet` can tell us whether we have already seen a value in approximately `O(1)` time, making these repeated comparisons unnecessary.

## Complexity

The Sudoku board is always `9 × 9`, so technically every approach here is bounded by a constant.

However, generalizing the board size to `n × n`:

### Time

- **Best:** `O(n²)`
- **Average:** `O(n³)`
- **Worst:** `O(n³)`

### Space

```text id="9w8p3k"
O(1)
```

because each row/column/box contains only a bounded number of values.

---

# 2. Optimal Approach — HashSet

## Thought Process

Instead of comparing every digit with every other digit, maintain a set of digits we've already encountered.

For every row:

```text id="0vnj9v"
if digit is already in seen
    → duplicate → false

otherwise
    → add digit to seen
```

Do the same for:

```text id="h7w8m9"
Rows
Columns
3 × 3 boxes
```

This gives us three straightforward validation passes.

---

## Step 1 — Validate Rows

For every row:

```java id="l5zj5c"
Set<Character> seen = new HashSet<>();
```

Then scan all 9 cells.

If the cell is empty:

```java id="j5r3f7"
if (board[row][i] == '.') continue;
```

Otherwise, check whether we've already seen the digit.

```text id="n4n4bq"
Already seen → invalid
Not seen → add it
```

---

## Step 2 — Validate Columns

The exact same logic applies to columns.

Instead of:

```text id="rj4t9p"
board[row][column]
```

we traverse:

```text id="2p8k9a"
board[row][col]
```

with `row` changing from `0` to `8`.

For each column, create a new `HashSet`.

---

## Step 3 — Validate 3 × 3 Boxes

This is the only slightly tricky part.

There are 9 boxes:

```text id="0u6x4j"
+-------+-------+-------+
| box 0 | box 1 | box 2 |
+-------+-------+-------+
| box 3 | box 4 | box 5 |
+-------+-------+-------+
| box 6 | box 7 | box 8 |
+-------+-------+-------+
```

We can identify each box using:

```java id="9hbyqv"
square / 3
square % 3
```

For a given `square`:

```java id="l4h3sd"
int row = (square / 3) * 3 + i;
int col = (square % 3) * 3 + j;
```

### Why does this work?

Suppose:

```text
square = 4
```

Then:

```text id="r2f8yk"
square / 3 = 1
square % 3 = 1
```

So the box starts at:

```text id="w3t1fy"
row = 1 × 3 = 3
col = 1 × 3 = 3
```

which is the center 3×3 box.

Then `i` and `j` move through its 9 cells:

```text id="l5rpg3"
(3,3) (3,4) (3,5)
(4,3) (4,4) (4,5)
(5,3) (5,4) (5,5)
```

---

## Algorithm

```text id="qz85ig"
1. Check every row for duplicate digits.
2. Check every column for duplicate digits.
3. Check every 3 × 3 box for duplicate digits.
4. Ignore '.'.
5. If any duplicate is found → return false.
6. If all checks pass → return true.
```

---

## Complexity

For the actual `9 × 9` Sudoku board:

### Time

We inspect every cell a constant number of times.

```text id="1z4u7s"
O(81) → O(1)
```

For a generalized `n × n` Sudoku board:

```text id="c5w8g5"
O(n²)
```

### Space

At most 9 digits are stored in a `HashSet` at a time for each row, column, or box.

For the fixed Sudoku board:

```text id="m9d1w7"
O(1)
```

For a generalized board:

```text id="xv6o4v"
O(n)
```

---

# Why Does the Optimal Approach Work?

The Sudoku rules are independent:

```text id="yq6v4j"
Rows
Columns
Boxes
```

A board is valid only if **all three conditions** are satisfied.

Our algorithm checks each condition independently.

For each group, the `HashSet` maintains exactly the digits already encountered.

Therefore:

```text id="a0j57h"
duplicate found
      ↓
Sudoku rule violated
      ↓
return false
```

If we finish checking all rows, columns, and boxes without finding a duplicate:

```text id="4x5o6r"
no row violation
+ no column violation
+ no box violation
        ↓
      valid
```

Therefore, the algorithm correctly determines whether the Sudoku board is valid.

---

# Understanding the 3 × 3 Box Formula

This is the most important part to remember from the implementation.

For box number `square`:

```java id="6b7w9f"
int row = (square / 3) * 3 + i;
int col = (square % 3) * 3 + j;
```

Think of it as two parts.

### Find the starting row

```text id="2p4xko"
(square / 3) * 3
```

### Find the starting column

```text id="aj9z8m"
(square % 3) * 3
```

Then:

```text id="2a6mhy"
+i
+j
```

moves inside that 3×3 box.

For example:

```text id="q70g4j"
square = 7

square / 3 = 2 → starting row = 6
square % 3 = 1 → starting col = 3
```

So box 7 starts at:

```text id="u2k4j5"
(6,3)
```

and covers:

```text id="f4o7bw"
(6,3) (6,4) (6,5)
(7,3) (7,4) (7,5)
(8,3) (8,4) (8,5)
```

---

# Edge Cases

### 1. Empty Board

A completely empty board contains no duplicates.

Result:

```text id="4at6h2"
true
```

### 2. Duplicate in a Row

```text id="0ojkpb"
5 3 . . 7 . . . 5
```

The second `5` is already in the set.

Result:

```text id="u0h3ey"
false
```

### 3. Duplicate in a Column

Even if every row is individually valid, a duplicate in a column makes the board invalid.

### 4. Duplicate in a 3 × 3 Box

The same digit can appear in different rows and columns but still violate the 3×3 box rule.

This is why checking only rows and columns is not enough.

### 5. Empty Cells

`.` should simply be ignored.

---

# Key Takeaway

### Pattern

**HashSet + Matrix Traversal**

The core pattern is:

```text id="9m0shb"
Identify independent groups
        ↓
Rows
Columns
3 × 3 boxes
        ↓
Track values seen in each group
        ↓
Duplicate → invalid
```

### General Lesson

When a problem asks:

> **"Does anything repeat within a group?"**

think about using a **HashSet**.

For matrix problems, also ask:

> **"What are the independent regions/groups I need to validate?"**

For Sudoku, those groups are:

```text id="jjxwpy"
9 rows
9 columns
9 boxes
```

Your implementation directly follows this structure, which makes it clean and easy to reason about.

---

# Solution

See [`solution.java`](./solution.java) for the Java implementation.
