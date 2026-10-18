# Kth Missing Positive Number

## LeetCode

- **Problem:** [Kth Missing Positive Number](https://leetcode.com/problems/kth-missing-positive-number/)
- **Difficulty:** Easy
- **Pattern:** Binary Search on Answer

## Problem Description

Given a strictly increasing array of positive integers `arr` and an integer `k`, return the \(k\)-th positive integer that is missing from the array.

### Examples

**Example 1**

Input: `arr = [2, 3, 4, 7, 11]`, `k = 5`

Output: `9`

Explanation: The missing positive integers are `[1, 5, 6, 8, 9, 10, ...]`. The fifth missing positive integer is `9`.

**Example 2**

Input: `arr = [1, 2, 3, 4]`, `k = 2`

Output: `6`

Explanation: The missing positive integers are `[5, 6, 7, ...]`. The second missing positive integer is `6`.

### Constraints

- `1 <= arr.length <= 1000`
- `1 <= arr[i] <= 1000`
- `1 <= k <= 1000`
- `arr` is strictly increasing.

# Thought Process

We need to find the \(k\)-th positive integer that does not appear in the array.

A brute-force approach can check every positive integer and count how many are missing. However, the sorted nature of the array allows us to solve the problem efficiently using binary search.

The key observation is that the number of positive integers missing before index `i` is:

\[
\text{missing}(i) = arr[i] - (i + 1)
\]

Why?

- The number of positive integers from `1` through `arr[i]` is `arr[i]`.
- Among them, `i + 1` numbers belong to the array up to index `i`.
- The difference gives the number of missing positive integers before `arr[i]`.

We can use this formula to find the first index where the number of missing elements is at least `k`.

# 1. Brute Force Approach

## Idea

Start from the smallest positive integer and check whether it appears in the array. Count each missing number until we find the \(k\)-th one.

## Algorithm

1. Initialize `current = 1` and `missing = 0`.
2. Traverse the positive integers in increasing order.
3. If `current` does not appear in the array, increment `missing`.
4. When `missing == k`, return `current`.
5. Otherwise, increment `current` and continue.

## Complexity Analysis

Let `n` be the array length and `A` be the answer.

- **Time Complexity:** `O(n + A)` using a straightforward implementation that checks array membership by scanning the array for each candidate.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

## Why Improve?

Repeatedly checking array membership is unnecessary because the array is sorted. Binary search can find the answer using the number of missing elements at each index.

# 2. Optimal Approach — Binary Search

## Idea

For every index `mid`, calculate:

`missing = arr[mid] - (mid + 1)`

This tells us how many positive integers are missing before `arr[mid]`.

- If `missing < k`, fewer than `k` numbers are missing before this position, so the answer must lie to the right. Set `low = mid + 1`.
- If `missing >= k`, the \(k\)-th missing number is at or before this position. Set `high = mid - 1`.

When the search ends, `low` identifies how many array elements lie before the boundary where the \(k\)-th missing number belongs.

The answer is:

\[
\boxed{\text{answer} = low + k}
\]

## Algorithm

1. Initialize `low = 0` and `high = arr.length - 1`.
2. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - Calculate `missing = arr[mid] - (mid + 1)`.
   - If `missing < k`, move right by setting `low = mid + 1`.
   - Otherwise, move left by setting `high = mid - 1`.
3. Return `low + k`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — binary search halves the search space in each iteration.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

# Why Does the Optimal Approach Work?

The number of missing positive integers increases or stays the same as we move through the array.

Binary search finds the first index where the missing count reaches at least `k`.

At the end of the search, `low` is the number of array