# Capacity To Ship Packages Within D Days

## LeetCode

- **Problem:** [Capacity To Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)
- **Difficulty:** Medium
- **Pattern:** Binary Search on Answer

## Problem Description

A conveyor belt has packages that must be shipped from one port to another within `days` days.

You are given an integer array `weights`, where `weights[i]` represents the weight of the \(i\)-th package. Packages must be shipped in the given order, and the ship has a maximum weight capacity.

Find the **minimum ship capacity** that allows all packages to be shipped within the specified number of days.

### Examples

**Example 1**

Input: `weights = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`, `days = 5`

Output: `15`

Explanation:

With capacity `15`, the packages can be shipped in five days:

- Day 1: `[1, 2, 3, 4, 5]`
- Day 2: `[6, 7]`
- Day 3: `[8]`
- Day 4: `[9]`
- Day 5: `[10]`

A smaller capacity cannot ship all packages within five days.

**Example 2**

Input: `weights = [3, 2, 2, 4, 1, 4]`, `days = 3`

Output: `6`

Explanation: With capacity `6`, the packages can be shipped as `[3, 2]`, `[2, 4]`, and `[1, 4]`.

**Example 3**

Input: `weights = [1, 2, 3, 1, 1]`, `days = 4`

Output: `3`

Explanation: A capacity of `3` allows all packages to be shipped within four days.

### Constraints

- `1 <= days <= weights.length <= 5 * 10^4`
- `1 <= weights[i] <= 500`

# Thought Process

We need to find the smallest ship capacity that allows all packages to be delivered within `days` days.

The packages must remain in their original order, so we cannot distribute them arbitrarily across days.

The answer lies within a predictable range:

- **Minimum capacity:** The maximum package weight, because the ship must carry every package individually.
- **Maximum capacity:** The sum of all package weights, because the ship could carry everything in one day.

Instead of checking every possible capacity, we can use binary search on the answer.

For each candidate capacity, a helper function determines whether all packages can be shipped within the allowed number of days.

# 1. Brute Force Approach

## Idea

Try every possible capacity from the maximum package weight to the sum of all weights. For each capacity, simulate shipping the packages in order and count the days required.

Return the first capacity that works.

## Algorithm

1. Find the maximum package weight and the sum of all weights.
2. Iterate through every capacity from the maximum weight to the total weight.
3. For each capacity:
   - Start with one shipping day.
   - Traverse the packages in their original order.
   - Add each package to the current day if it fits.
   - Otherwise, start a new day with that package.
4. Return the first capacity that requires no more than `days` days.

## Complexity Analysis

Let \(n\) be the number of packages and \(S\) be the sum of their weights.

- **Time Complexity:** `O(n * (S - maxWeight + 1))`, which simplifies to `O(nS)` as an upper bound.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

## Why Improve?

Testing every possible capacity can be expensive. Since feasibility changes monotonically as capacity increases, binary search can eliminate large portions of the search space.

# 2. Optimal Approach — Binary Search on Answer

## Idea

Search for the minimum feasible capacity between the maximum package weight and the sum of all package weights.

For each middle capacity, call `canShipWithinDays()` to check whether all packages can be shipped within the allowed number of days.

- If the capacity is sufficient, search for a smaller one using `high = mid`.
- If the capacity is insufficient, increase it using `low = mid + 1`.

When `low == high`, the minimum valid capacity has been found.

## Algorithm

1. Initialize `low = 0` and `high = 0`.
2. Traverse the array:
   - Update `low` to the maximum package weight.
   - Add every package weight to `high`.
3. While `low < high`:
   - Calculate `mid = low + (high - low) / 2`.
   - Check whether `mid` can ship all packages within `days`.
   - If it can, set `high = mid`.
   - Otherwise, set `low = mid + 1`.
4. Return `low`.

### Helper Function: `canShipWithinDays()`

1. Initialize `daysUsed = 1` and `currentWeight = 0`.
2. Traverse the packages in order.
3. For each package:
   - If adding it exceeds the capacity, start a new day and set `currentWeight` to the package's weight.
   - Otherwise, add its weight to the current day's load.
4. If `daysUsed` exceeds the allowed number of days, return `false`.
5. If all packages are processed within the limit, return `true`.

## Complexity Analysis

- **Time Complexity:** `O(n log S)` — each feasibility check takes `O(n)`, and binary search performs `O(log S)` iterations.
- **Space Complexity:** `O(1)` — the algorithm uses only a constant number of extra variables.

# Why Does the Optimal Approach Work?

The feasibility of a capacity is monotonic:

- If a capacity is too small, increasing it may make shipping possible.
- If a capacity is sufficient, every larger capacity is also sufficient.

For example, consider:

`weights = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]`, `days = 5`

- Capacity `10` requires more than five days.
- Capacity `15` works within five days.
- Any capacity greater than `15` also works, but is unnecessary.

Binary search finds the boundary between insufficient and sufficient capacities.

The greedy helper function is valid because, for a fixed capacity, filling each day with as many consecutive packages as possible minimizes the number of days needed. Starting a new day only when the next package does not fit preserves the required order.

# Edge Cases

| Input | Days | Output | Explanation |
|---|---:|---:|---|
| `[1, 2, 3, 4, 5]` | `1` | `15` | All packages must ship in one day |
| `[3, 2, 2, 4, 1, 4]` | `3` | `6` | Minimum capacity that works in three days |
| `[1, 2, 3, 1, 1]` | `4` | `3` | Capacity `3` is sufficient |
| `[5]` | `1` | `5` | One package must be shipped |

# Key Takeaway

This is a classic **Binary Search on Answer** problem.

When increasing a candidate value can turn an invalid solution into a valid one, binary search can find the minimum valid value.

- **Search Space:** Maximum package weight to total package weight.
- **Feasibility Check:** Can all packages be shipped within `days` using this capacity?
- **Time Complexity:** `O(n log S)`
- **Space Complexity:** `O(1)`

Remember that the packages must be shipped in their original order, and the capacity must be at least the heaviest individual package.

# Solution

[View solution.java](./solution.java)