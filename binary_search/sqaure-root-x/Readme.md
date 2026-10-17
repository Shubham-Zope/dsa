# Sqrt(x)

## LeetCode

- **Problem:** [Sqrt(x)](https://leetcode.com/problems/sqrtx/)
- **Difficulty:** Easy
- **Pattern:** Binary Search

## Problem Description

Given a non-negative integer `x`, return the square root of `x` rounded down to the nearest integer.

The returned integer must be non-negative. You must not use any built-in exponent function or operator, such as `Math.sqrt(x)` or `x ** 0.5`.

### Examples

**Example 1**

Input: `x = 4`

Output: `2`

Explanation: The square root of `4` is exactly `2`.

**Example 2**

Input: `x = 8`

Output: `2`

Explanation: The square root of `8` is approximately `2.828`. Rounding down gives `2`.

### Constraints

- `0 <= x <= 2^31 - 1`

# Thought Process

We need to find the largest integer whose square is less than or equal to `x`.

For example, when `x = 8`:

- \(2^2 = 4 \leq 8\)
- \(3^2 = 9 > 8\)

Therefore, the integer square root is `2`.

A brute-force solution can check each integer one by one. However, because the answer lies in a predictable range and squaring is monotonic for non-negative integers, we can use binary search to find the answer efficiently.

# 1. Brute Force Approach

## Idea

Start from `0` and check consecutive integers until their square exceeds `x`. The previous integer is the answer.

To avoid integer overflow, use `long` when calculating the square.

## Algorithm

1. Initialize `ans = 0`.
2. Iterate through integers starting from `1`.
3. Calculate the square of the current integer using `long`.
4. If the square exceeds `x`, stop.
5. Otherwise, update `ans` to the current integer.
6. Return `ans`.

## Complexity Analysis

- **Time Complexity:** `O(√x)` — in the worst case, we check integers up to the square root of `x`.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

## Why Improve?

For large values of `x`, checking every integer is inefficient. Binary search can eliminate half of the remaining candidates in each iteration.

# 2. Optimal Approach — Binary Search

## Idea

The integer square root must lie between `1` and `x / 2` when `x >= 2`.

Maintain a search range using `low` and `high`. At every iteration, calculate the middle value and compare its square with `x`.

- If `mid * mid == x`, return `mid` because the square root is exact.
- If `mid * mid < x`, `mid` is a valid answer. Store it in `ans` and search for a potentially larger answer on the right.
- If `mid * mid > x`, search the left half because `mid` is too large.

When the search ends, `ans` contains the largest integer whose square does not exceed `x`.

**Important:** Calculate the square using `long` to prevent integer overflow when `mid * mid` exceeds the range of an `int`.

## Algorithm

1. If `x < 2`, return `x`.
2. Initialize:
   - `low = 1`
   - `high = x / 2`
   - `ans = 0`
3. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - Calculate `square = (long) mid * mid`.
   - If `square == x`, return `mid`.
   - If `square < x`, update `ans = mid` and set `low = mid + 1`.
   - Otherwise, set `high = mid - 1`.
4. Return `ans`.

## Complexity Analysis

- **Time Complexity:** `O(log x)` — binary search halves the search space in every iteration.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

# Why Does the Optimal Approach Work?

For non-negative integers, the square increases as the integer increases.

This means:

- If `mid * mid < x`, every smaller integer is also a valid candidate, and a larger candidate might exist.
- If `mid * mid > x`, `mid` and every larger integer are too large.
- If `mid * mid == x`, the exact answer has been found.

Whenever `mid` is a valid candidate, we save it in `ans` before searching the right half. Therefore, if no exact square root exists, the last saved candidate is the largest integer whose square is less than `x`.

# Edge Cases

| Input | Output | Explanation |
|---|---:|---|
| `0` | `0` | Square root of zero |
| `1` | `1` | Smallest positive input |
| `4` | `2` | Perfect square |
| `8` | `2` | Square root rounded down |
| `15` | `3` | \(3^2 \leq 15 < 4^2\) |
| `2147395600` | `46340` | Large perfect square |
| `2147483647` | `46340` | Maximum allowed input |

# Key Takeaway

The integer square root problem is a classic binary search problem. Instead of checking every possible integer, search for the largest integer `mid` satisfying:

\[
mid^2 \leq x
\]

- **Time Complexity:** `O(log x)`
- **Space Complexity:** `O(1)`

Using `long` for the square calculation prevents integer overflow.

# Solution

[View solution.java](./solution.java)
