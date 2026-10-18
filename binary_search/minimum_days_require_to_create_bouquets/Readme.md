# Minimum Number of Days to Make m Bouquets

## LeetCode

- **Problem:** [Minimum Number of Days to Make m Bouquets](https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/)
- **Difficulty:** Medium
- **Pattern:** Binary Search on Answer

## Problem Description

You are given an integer array `bloomDay`, where `bloomDay[i]` represents the day the \(i\)-th flower will bloom.

You also have two integers:

- `m`: The number of bouquets you need to make.
- `k`: The number of **adjacent flowers** required for each bouquet.

Each flower can be used in at most one bouquet.

Return the minimum number of days required to make `m` bouquets. If it is impossible to make the required bouquets, return `-1`.

### Examples

**Example 1**

Input: `bloomDay = [1, 10, 3, 10, 2]`, `m = 3`, `k = 1`

Output: `3`

Explanation: By day `3`, the flowers at indices `0`, `2`, and `4` have bloomed. Since each bouquet requires one flower, we can make three bouquets.

**Example 2**

Input: `bloomDay = [1, 10, 3, 10, 2]`, `m = 3`, `k = 2`

Output: `-1`

Explanation: We need six flowers to make three bouquets, but only five flowers are available.

**Example 3**

Input: `bloomDay = [7, 7, 7, 7, 12, 7, 7]`, `m = 2`, `k = 3`

Output: `12`

Explanation: By day `12`, all flowers have bloomed, allowing two bouquets of three adjacent flowers each.

### Constraints

- `bloomDay.length == n`
- `1 <= n <= 10^5`
- `1 <= bloomDay[i] <= 10^9`
- `1 <= m <= 10^6`
- `1 <= k <= n`

# Thought Process

We need to find the earliest day on which at least `m` bouquets can be made, with each bouquet requiring `k` adjacent bloomed flowers.

A brute-force approach would check every day until enough bouquets can be formed. However, the possible bloom days can be very large.

The key observation is that **if we can make at least `m` bouquets on a particular day, we can also make them on any later day**.

This monotonic property allows us to use binary search on the answer.

Before starting, we can check whether making the required bouquets is possible at all. Each bouquet requires `k` flowers, so we need at least `m * k` flowers.

Use `long` for this multiplication to avoid integer overflow.

# 1. Brute Force Approach

## Idea

Try each possible day from the earliest bloom day to the latest bloom day. For every day, count how many bouquets can be made using adjacent bloomed flowers.

Return the first day on which at least `m` bouquets can be formed.

## Algorithm

1. Calculate the minimum and maximum values in `bloomDay`.
2. If `(long) m * k > bloomDay.length`, return `-1`.
3. Iterate through each day from the minimum to the maximum bloom day.
4. For each day:
   - Traverse the array.
   - Count consecutive flowers that have bloomed.
   - Every time the consecutive count reaches `k`, form a bouquet and reset the count.
   - Reset the consecutive count whenever an unbloomed flower is encountered.
5. Return the first day on which at least `m` bouquets can be made.

## Complexity Analysis

Let `n` be the number of flowers, `minDay` the earliest bloom day, and `maxDay` the latest bloom day.

- **Time Complexity:** `O(n * (maxDay - minDay + 1))` — each candidate day requires traversing the array.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

## Why Improve?

The range of possible days can be very large. Checking every day is inefficient because many candidate days can be eliminated using binary search.

# 2. Optimal Approach — Binary Search on Answer

## Idea

Search for the minimum feasible day between the minimum and maximum bloom days.

For each candidate day `mid`, use a helper function to count how many bouquets can be formed.

- If `bouquetsBuilt >= m`, the day is feasible. Try an earlier day by setting `high = mid - 1`.
- Otherwise, not enough bouquets can be made. Try a later day by setting `low = mid + 1`.

The first feasible day is the answer.

## Algorithm

1. Calculate `n = bloomDay.length`.
2. If `(long) m * k > n`, return `-1`.
3. Find the minimum and maximum bloom days and use them as `low` and `high`.
4. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - Call `countBouquetsBuilt(bloomDay, mid, k)`.
   - If the bouquet count is at least `m`, set `high = mid - 1`.
   - Otherwise, set `low = mid + 1`.
5. Return `low`.

### Helper Function: `countBouquetsBuilt`

1. Initialize `count = 0` and `countOfFlowers = 0`.
2. Traverse every flower:
   - If `flower <= day`, increment `countOfFlowers`.
   - When `countOfFlowers == k`, increment the bouquet count and reset `countOfFlowers = 0`.
   - If `flower > day`, reset `countOfFlowers = 0` because adjacency is broken.
3. Return the total bouquet count.

## Complexity Analysis

Let \(D = \max(bloomDay) - \min(bloomDay) + 1\).

- **Time Complexity:** `O(n log D)` — each binary search iteration takes `O(n)` to count bouquets, and binary search performs `O(log D)` iterations.
- **Space Complexity:** `O(1)` — the algorithm uses only a constant number of extra variables.

# Why Does the Optimal Approach Work?

The number of bouquets that can be made never decreases as the day increases.

For example, consider:

`bloomDay = [1, 10, 3, 10, 2]`, `m = 3`, `k = 1`

- Day `1`: only one flower has bloomed, so we cannot make three bouquets.
- Day `2`: two flowers have bloomed, so we still cannot make three bouquets.
- Day `3`: three flowers have bloomed, so we can make three bouquets.

Day `3` is therefore the earliest feasible day.

Binary search uses this monotonic behavior to eliminate days that cannot be the answer.

The helper function also ensures that each bouquet uses exactly `k` adjacent bloomed flowers. Resetting the consecutive flower count after making a bouquet prevents any flower from being reused.

# Edge Cases

| Input | Output | Explanation |
|---|---:|---|
| `bloomDay = [1]`, `m = 1`, `k = 1` | `1` | One flower makes one bouquet |
| `bloomDay = [1, 10, 3, 10, 2]`, `m = 3`, `k = 1` | `3` | Three flowers have bloomed by day 3 |
| `bloomDay = [1, 10, 3, 10, 2]`, `m = 3`, `k = 2` | `-1` | Six flowers are required, but only five exist |
| `bloomDay = [7, 7, 7, 7, 12, 7, 7]`, `m = 2`, `k = 3` | `12` | Two groups of three adjacent flowers are possible by day 12 |

# Key Takeaway

This is a classic **Binary Search on Answer** problem.

Whenever a candidate value becomes feasible and remains feasible for every larger value, binary search can find the minimum feasible value efficiently.

- **Feasibility check:** Can we make at least `m` bouquets by day `mid`?
- **Search range:** Minimum to maximum bloom day.
- **Time Complexity:** `O(n log D)`
- **Space Complexity:** `O(1)`

Always check whether `(long) m * k > n` before searching, since the required number of flowers may exceed the available flowers.

# Solution

[View solution.java](./solution.java)