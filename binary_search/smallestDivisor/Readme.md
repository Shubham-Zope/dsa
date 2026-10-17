# Find the Smallest Divisor Given a Threshold

## LeetCode

- **Problem:** [Find the Smallest Divisor Given a Threshold](https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/)
- **Difficulty:** Medium
- **Pattern:** Binary Search on Answer

## Problem Description

Given an array of positive integers `nums` and an integer `threshold`, choose a positive integer divisor and divide every element of the array by it.

For each element, round the division result up to the nearest integer. Return the **smallest divisor** such that the sum of all rounded-up division results is less than or equal to `threshold`.

### Examples

**Example 1**

Input: `nums = [1, 2, 5, 9]`, `threshold = 6`

Output: `5`

Explanation:

- Divisor `5`: `ceil(1/5) + ceil(2/5) + ceil(5/5) + ceil(9/5) = 1 + 1 + 1 + 2 = 5`
- Divisor `4`: `1 + 1 + 2 + 3 = 7`

Since divisor `4` exceeds the threshold, the smallest valid divisor is `5`.

**Example 2**

Input: `nums = [44, 22, 33, 11, 1]`, `threshold = 5`

Output: `44`

Explanation: The divisor `44` produces a sum of `1 + 1 + 1 + 1 + 1 = 5`, satisfying the threshold.

### Constraints

- `1 <= nums.length <= 5 * 10^4`
- `1 <= nums[i] <= 10^6`
- `nums.length <= threshold <= 10^6`

# Thought Process

We need to find the smallest positive integer divisor that makes the sum of the rounded-up quotients no greater than `threshold`.

For a divisor `d`, the required sum is:

\[
\text{sum}(d)=\sum_{i=0}^{n-1}\left\lceil\frac{nums[i]}{d}\right\rceil
\]

Instead of using floating-point calculations, we can calculate the ceiling of an integer division using:

\[
\left\lceil\frac{a}{b}\right\rceil
=
\frac{a+b-1}{b}
\]

Here, integer division is used on the right-hand side.

The key observation is that **increasing the divisor never increases the sum**. Therefore, if a divisor satisfies the threshold, every larger divisor also satisfies it.

This monotonic property allows us to use binary search on the answer.

# 1. Brute Force Approach

## Idea

Try every divisor starting from `1` and calculate the sum of the rounded-up quotients. Return the first divisor whose sum is less than or equal to the threshold.

## Algorithm

1. Find the maximum element in `nums`.
2. Iterate through every divisor from `1` to `max(nums)`.
3. For each divisor:
   - Calculate the sum of rounded-up divisions.
   - If the sum is at most `threshold`, return the divisor.
4. The first valid divisor is the answer.

## Complexity Analysis

Let `n` be the array length and `M` be the maximum element.

- **Time Complexity:** `O(n * M)` — up to `M` divisors are tested, and each test traverses the array.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

## Why Improve?

The maximum element can be as large as `10^6`. Testing every possible divisor can require too many operations.

Binary search reduces the number of candidate divisors from `M` to `O(log M)`.

# 2. Optimal Approach — Binary Search on Answer

## Idea

The answer must lie between `1` and `max(nums)`.

- A divisor of `1` gives the largest possible sum.
- A divisor equal to `max(nums)` makes every quotient at most `1`. Since `nums.length <= threshold`, this divisor is guaranteed to satisfy the threshold.

For each middle divisor, calculate the sum:

- If `sum <= threshold`, the divisor is valid. Store the possibility by moving `high = mid` to search for a smaller valid divisor.
- If `sum > threshold`, the divisor is too small. Increase it by setting `low = mid + 1`.

Continue until `low == high`. That value is the smallest valid divisor.

## Algorithm

1. Initialize `low = 1`.
2. Find the maximum array element and initialize `high` to that value.
3. While `low < high`:
   - Calculate `mid = low + (high - low) / 2`.
   - Initialize `sum = 0`.
   - For each number, add `(num + mid - 1) / mid` to `sum`.
   - If `sum <= threshold`, set `high = mid`.
   - Otherwise, set `low = mid + 1`.
4. Return `low`.

## Complexity Analysis

- **Time Complexity:** `O(n log M)` — each binary search iteration takes `O(n)` to calculate the sum, and binary search performs `O(log M)` iterations.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

# Why Does the Optimal Approach Work?

The sum of rounded-up quotients is a non-increasing function of the divisor.

For example, with `nums = [1, 2, 5, 9]` and `threshold = 6`:

| Divisor | Sum | Valid? |
|---:|---:|---|
| `2` | `10` | No |
| `3` | `7` | No |
| `4` | `7` | No |
| `5` | `5` | Yes |
| `6` | `5` | Yes |

Once the divisor reaches `5`, the threshold is satisfied. Every larger divisor is also valid, so the goal is to find the first valid value.

Binary search eliminates half of the remaining candidates in each iteration while preserving the smallest possible valid answer.

# Edge Cases

| Input | Threshold | Output | Explanation |
|---|---:|---:|---|
| `[1, 2, 5, 9]` | `6` | `5` | Smallest valid divisor |
| `[44, 22, 33, 11, 1]` | `5` | `44` | Threshold equals array length |
| `[2, 3, 5, 7, 11]` | `11` | `1` | Divisor `1` already satisfies the threshold |
| `[1000000]` | `1` | `1000000` | Large single element |

# Key Takeaway

This is a classic **Binary Search on Answer** problem.

When a condition becomes easier to satisfy as a candidate value increases, binary search can find the smallest value that satisfies the condition.

- **Search Space:** `1` to `max(nums)`
- **Time Complexity:** `O(n log M)`
- **Space Complexity:** `O(1)`

The ceiling division formula `(num + mid - 1) / mid` avoids floating-point operations.

# Solution

[View solution.java](./solution.java)