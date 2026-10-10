# Pascal's Triangle II

## LeetCode
- **Problem:** [119. Pascal's Triangle II](https://leetcode.com/problems/pascals-triangle-ii/)
- **Difficulty:** Easy
- **Pattern:** Math / Arrays

## Problem Description

Given an integer `rowIndex`, return the `rowIndex`-th (0-indexed) row of Pascal's Triangle.

In Pascal's Triangle, the first and last elements of every row are `1`, and each interior element is the sum of the two elements directly above it.

### Example 1

**Input:**
```text
rowIndex = 3
```

**Output:**
```text
[1, 3, 3, 1]
```

### Example 2

**Input:**
```text
rowIndex = 0
```

**Output:**
```text
[1]
```

### Constraints
- `0 <= rowIndex <= 33`

# Thought Process

A straightforward approach would be to generate every row of Pascal's Triangle until reaching the requested row. However, this calculates and stores rows that we don't need.

Instead, we can use the **binomial coefficient formula** to calculate each element directly from the previous element.

The value at position `i` in row `n` is:

\[
C(n,i)=\frac{n!}{i!(n-i)!}
\]

Calculating factorials independently for every position would be inefficient. Fortunately, consecutive elements have a relationship that lets us calculate each value from the previous one.

# 1. Brute Force Approach

### Idea

Generate Pascal's Triangle row by row until reaching `rowIndex`.

### Algorithm
1. Start with the first row `[1]`.
2. For each subsequent row, add `1` at both ends.
3. Calculate every interior element by adding the two adjacent elements from the previous row.
4. Repeat until reaching the requested row.

### Complexity Analysis

Let `n = rowIndex`.

- **Time:** \(O(n^2)\), because all elements in the preceding rows are generated.
- **Space:** \(O(n^2)\) if the entire triangle is stored, or \(O(n)\) if only the previous row is retained.

### Problem with This Approach

We only need one row, so generating and storing all previous rows performs unnecessary work.

# 2. Optimal Approach — Binomial Coefficient Formula

## Key Insight

Instead of calculating each element independently using factorials, calculate it using the previous element.

The relationship between consecutive elements is:

\[
C(n,i)=C(n,i-1)\times\frac{n-i+1}{i}
\]

This allows us to generate the entire requested row in a single pass.

For the first element, `current = 1`. Then, for each position `i`, update the current value using:

```java
current = current * (rowIndex - i + 1) / i;
```

The result is added to the list.

## Algorithm
1. Create an empty list and add `1`, since every Pascal's Triangle row starts with `1`.
2. Initialize `current = 1` using a `long` variable to safely handle intermediate multiplication.
3. Iterate from `i = 1` to `rowIndex`.
4. Calculate the next element using the binomial coefficient recurrence relation.
5. Convert the calculated value to `int` and append it to the list.
6. Return the completed row.

## Complexity Analysis

Let `n = rowIndex`.

- **Time Complexity:** \(O(n)\), because each element is calculated in constant time.
- **Auxiliary Space:** \(O(1)\), excluding the output list.
- **Output Space:** \(O(n)\), to store the requested row.

# Why Does the Optimal Approach Work?

The binomial coefficient recurrence relation calculates each element from the element immediately before it.

Starting with `1`, every subsequent value is generated using:

\[
C(n,i)=C(n,i-1)\times\frac{n-i+1}{i}
\]

For example, when `rowIndex = 4`:

| `i` | Calculation | Value |
|---:|---|---:|
| 0 | Initial value | 1 |
| 1 | \(1 \times 4 / 1\) | 4 |
| 2 | \(4 \times 3 / 2\) | 6 |
| 3 | \(6 \times 2 / 3\) | 4 |
| 4 | \(4 \times 1 / 4\) | 1 |

The resulting row is `[1, 4, 6, 4, 1]`.

Since every element is derived directly from the previous one, we don't need to construct any other rows or recalculate factorials.

# Edge Cases

- **`rowIndex = 0`:** Returns `[1]`.
- **`rowIndex = 1`:** Returns `[1, 1]`.
- **Maximum constraint (`rowIndex = 33`):** The largest element fits within Java's `int` range, and `long` is used for intermediate multiplication.

# Key Takeaway

When consecutive values have a mathematical relationship, use a recurrence relation to avoid recalculating each value from scratch.

For Pascal's Triangle II, the binomial coefficient recurrence reduces the time complexity from \(O(n^2)\) to \(O(n)\), while storing only the requested row.

# Solution

[View solution.java](./solution.java)
