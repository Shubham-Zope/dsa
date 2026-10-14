# Largest Subarray with Sum 0

## Problem
- **Link:** [Largest Subarray with Sum 0 — Take U Forward](https://takeuforward.org/practice/dsa/largest-subarray-with-sum-0)
- **Difficulty:** Medium
- **Pattern:** Prefix Sum / HashMap

## Problem Description

Given an array `arr` containing positive, negative, and zero values, find the length of the longest contiguous subarray whose sum is `0`.

Return `0` if no such subarray exists.

### Example 1

**Input:**
```text
arr = [15, -2, 2, -8, 1, 7, 10, 23]
```

**Output:**
```text
5
```

**Explanation:**

The subarray `[-2, 2, -8, 1, 7]` has a sum of `0` and length `5`.

### Example 2

**Input:**
```text
arr = [1, 2, 3]
```

**Output:**
```text
0
```

**Explanation:**

No non-empty subarray has a sum of `0`.

# Thought Process

The brute-force approach checks every possible subarray and calculates its sum. This takes \(O(n^2)\) time if the sum is maintained incrementally.

To optimize it, we can use a **prefix sum** and a `HashMap`.

The key observation is:

**If the same prefix sum occurs at two different indices, the subarray between those indices has a sum of zero.**

Suppose the prefix sum at index `i` is equal to the prefix sum at index `j`, where `i < j`.

\[
prefix[j] - prefix[i] = 0
\]

Therefore, the subarray from `i + 1` through `j` has a sum of zero, and its length is `j - i`.

To maximize the length, we store the **first occurrence** of every prefix sum.

# 1. Brute Force Approach

## Idea

Generate every possible subarray and check whether its sum is zero.

## Algorithm

1. Initialize `maxLength = 0`.
2. Iterate through every starting index `i`.
3. Initialize a running sum to `0`.
4. Iterate through every ending index `j >= i`.
5. Add `arr[j]` to the running sum.
6. If the sum is zero, update `maxLength` with `j - i + 1`.
7. Return `maxLength`.

## Complexity Analysis

- **Best Time:** \(O(n^2)\) for this standard nested-loop implementation.
- **Average Time:** \(O(n^2)\).
- **Worst Time:** \(O(n^2)\).
- **Auxiliary Space:** \(O(
