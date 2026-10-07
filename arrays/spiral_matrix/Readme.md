# Spiral Matrix

## LeetCode

- **Problem:** [Spiral Matrix](https://leetcode.com/problems/spiral-matrix/description/)
- **Difficulty:** Medium
- **Pattern:** Matrix / Boundary Traversal

## Problem Description

Given an `m x n` matrix, return all elements of the matrix in **spiral order**.

### Example

```text
Input:
[
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9]
]

Output:
[1, 2, 3, 6, 9, 8, 7, 4, 5]
```

The traversal follows:

```text
→ → →
      ↓
      ↓
← ← ←
↑
↑
→
```

### Another Example

```text
Input:
[
  [1, 2, 3, 4],
  [5, 6, 7, 8],
  [9,10,11,12]
]

Output:
[1,2,3,4,8,12,11,10,9,5,6,7]
```

### Constraints

- `m == matrix.length`
- `n == matrix[i].length`
- `1 <= m, n <= 10`
- `-100 <= matrix[i][j] <= 100`

# Thought Process

The main challenge is not visiting every element, but controlling the direction of traversal without visiting an element more than once.

A natural first thought is to move in four directions:

```text
right → down → left → up
```

and change direction whenever we hit a boundary or an already visited cell.

That approach works, but it requires keeping track of visited cells.

A cleaner observation is that spiral traversal processes the matrix **layer by layer**.

For every layer, there are four boundaries:

```text
top
bottom
left
right
```

We can traverse the current layer in four steps:

```text
1. top row       → left to right
2. right column  ↓ top to bottom
3. bottom row    ← right to left
4. left column   ↑ bottom to top
```

After each traversal, shrink the corresponding boundary.

This allows us to traverse the entire matrix without a visited array.

# 1. Brute Force Approach

## Idea

Traverse the matrix while maintaining the current direction.

The directions are:

```text
right → down → left → up
```

Whenever the next cell is outside the matrix or has already been visited, change direction.

Use a `boolean[][] visited` array to keep track of visited cells.

## Algorithm

1. Start at `(0, 0)`.
2. Start moving right.
3. Add the current cell to the result.
4. Mark the cell as visited.
5. If the next cell is invalid or already visited, change direction.
6. Continue until all cells are processed.

## Complexity

- **Best Time:** `O(m × n)`
- **Average Time:** `O(m × n)`
- **Worst Time:** `O(m × n)`
- **Extra Space:** `O(m × n)` for the visited array
- **Output Space:** `O(m × n)`

## Problem With This Approach

The time complexity is already optimal because every element must be visited.

However, the `visited` matrix requires additional `O(m × n)` space.

We can avoid this extra space by tracking the boundaries instead.

# 2. Optimal Approach — Boundary Traversal

## Key Insight

Instead of tracking individual visited cells, track the boundaries of the remaining matrix.

Initially:

```text
top = 0
bottom = m - 1
left = 0
right = n - 1
```

For every layer:

### 1. Traverse the top row

```text
left → right
```

Then move the top boundary down:

```text
top++
```

### 2. Traverse the right column

```text
top → bottom
```

Then move the right boundary left:

```text
right--
```

### 3. Traverse the bottom row

```text
right → left
```

Then move the bottom boundary up:

```text
bottom--
```

### 4. Traverse the left column

```text
bottom → top
```

Then move the left boundary right:

```text
left++
```

Repeat while:

```text
top <= bottom && left <= right
```

## Algorithm

1. Initialize `top`, `bottom`, `left`, and `right`.
2. Traverse the top row from left to right.
3. Increment `top`.
4. Traverse the right column from top to bottom.
5. Decrement `right`.
6. If rows remain, traverse the bottom row from right to left.
7. Decrement `bottom`.
8. If columns remain, traverse the left column from bottom to top.
9. Increment `left`.
10. Repeat until all boundaries cross.

### Why Do We Need the Boundary Checks?

Consider a matrix with only one remaining row:

```text
[1 2 3 4]
```

After traversing the top row, `top` becomes greater than `bottom`.

Without:

```text
if (top <= bottom)
```

we could traverse the same row again in reverse.

Similarly, for a matrix with one remaining column, we need:

```text
if (left <= right)
```

to avoid duplicating elements.

## Complexity

- **Best Time:** `O(m × n)`
- **Average Time:** `O(m × n)`
- **Worst Time:** `O(m × n)`
- **Extra Space:** `O(1)`
- **Output Space:** `O(m × n)`

The time is `O(m × n)` because every matrix element is visited exactly once.

# Why Does the Optimal Approach Work?

At any point, the unvisited portion of the matrix can be represented by four boundaries:

```text
       left       right
         ↓          ↓
top  →  [----------]
        [          ]
        [          ]
bottom →[----------]
```

Each traversal completely consumes one boundary:

```text
Top row      → top++
Right column → right--
Bottom row   → bottom--
Left column  → left++
```

Therefore, after completing one spiral layer, the boundaries move inward and define the next smaller layer.

No element needs to be marked as visited because the boundaries themselves tell us which elements are still unvisited.

# Example Walkthrough

For:

```text
1  2  3
4  5  6
7  8  9
```

Initial boundaries:

```text
top = 0
bottom = 2
left = 0
right = 2
```

### Step 1 — Top Row

```text
1 2 3
```

Result:

```text
[1, 2, 3]
```

`top++`

```text
top = 1
```

### Step 2 — Right Column

```text
6
9
```

Result:

```text
[1, 2, 3, 6, 9]
```

`right--`

```text
right = 1
```

### Step 3 — Bottom Row

```text
8 7
```

Result:

```text
[1, 2, 3, 6, 9, 8, 7]
```

`bottom--`

```text
bottom = 1
```

### Step 4 — Left Column

```text
4
```

Result:

```text
[1, 2, 3, 6, 9, 8, 7, 4]
```

`left++`

```text
left = 1
```

Only `5` remains.

Final result:

```text
[1, 2, 3, 6, 9, 8, 7, 4, 5]
```

# Edge Cases

### Single Element

```text
Input:
[[1]]

Output:
[1]
```

### Single Row

```text
Input:
[[1,2,3,4]]

Output:
[1,2,3,4]
```

### Single Column

```text
Input:
[
 [1],
 [2],
 [3],
 [4]
]

Output:
[1,2,3,4]
```

### Rectangular Matrix

The approach works for both:

```text
m > n
```

and:

```text
n > m
```

### Matrix With One Remaining Row or Column

The additional boundary checks prevent elements from being traversed twice.

# Key Takeaway

- **Pattern:** Matrix / Boundary Traversal
- Use four boundaries: `top`, `bottom`, `left`, `right`.
- Traverse each layer in four directions:
  ```text
  → ↓ ← ↑
  ```
- Shrink the corresponding boundary after each traversal.
- No `visited[][]` array is required.
- **Time:** `O(m × n)`
- **Extra Space:** `O(1)` excluding the output.

The general lesson is:

> For matrix problems involving layers, spirals, or shrinking regions, think about maintaining boundaries instead of tracking individual visited cells.

# Solution

[View solution.java](./solution.java)
