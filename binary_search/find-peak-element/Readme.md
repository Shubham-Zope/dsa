# Find Peak Element

## LeetCode

- **Problem:** [Find Peak Element](https://leetcode.com/problems/find-peak-element/)
- **Difficulty:** Medium
- **Pattern:** Binary Search

## Problem Description

A peak element is an element that is strictly greater than its neighboring elements.

Given an integer array `nums`, find a peak element and return its index.

If the array contains multiple peaks, return the index of any peak.

Assume that `nums[-1] = nums[n] = -∞`. This means that an element at either boundary can also be a peak if it is greater than its only neighbor.

Your solution must run in `O(log n)` time.

### Examples

**Example 1**

Input: `nums = [1, 2, 3, 1]`

Output: `2`

Explanation: `nums[2] = 3` is greater than both neighboring elements, `2` and `1`.

**Example 2**

Input: `nums = [1, 2, 1, 3, 5, 6, 4]`

Output: `5`

Explanation: `nums[5] = 6` is greater than its neighboring elements, `5` and `4`. The element at index `1` is also a valid peak.

### Constraints

- `1 <= nums.length <= 1000`
- `-2^31 <= nums[i] <= 2^31 - 1`
- `nums[i] != nums[i + 1]` for all valid indices.
- An element is considered a peak if it is strictly greater than its neighbors.

# Thought Process

We need to find an element that is greater than its neighboring elements.

A brute-force approach can examine every element and check whether it is a peak. However, the problem requires `O(log n)` time, so we need to use binary search.

The key observation is that **we do not need to find the global maximum**. We only need to find any local peak.

At the middle index:

- If `nums[mid] > nums[mid + 1]`, we are on a descending slope. A peak must exist at `mid` or somewhere to its left, so we move `high = mid`.
- Otherwise, `nums[mid] < nums[mid + 1]`, so we are on an ascending slope. A peak must exist to the right, so we move `low = mid + 1`.

We continue until `low == high`. That index is a peak.

# 1. Brute Force Approach

## Idea

Traverse the array and check each element against its neighbors. Return the index of the first element that is strictly greater than both neighbors.

For boundary elements, only check the neighbor that exists.

## Algorithm

1. Iterate through each index `i`.
2. Treat the missing left or right neighbor at the boundary as negative infinity.
3. If `nums[i]` is greater than both neighbors, return `i`.
4. Return a fallback value only if no peak is found. Under the problem constraints, a peak always exists.

## Complexity Analysis

- **Time Complexity:** `O(n)` — in the worst case, every element is checked.
- **Space Complexity:** `O(1)` — no additional data structures are required.

## Why Improve?

The brute-force approach checks elements one by one. Binary search uses the slope between adjacent elements to discard half of the search range in each iteration.

# 2. Optimal Approach — Binary Search

## Idea

Instead of checking whether the middle element is a peak directly, compare it with the element immediately to its right.

There are two possible cases:

**Case 1: `nums[mid] > nums[mid + 1]`**

The array is descending between `mid` and `mid + 1`. A peak exists at `mid` or in the left portion, so set:

`high = mid`

We keep `mid` in the search range because it might itself be the peak.

**Case 2: `nums[mid] < nums[mid + 1]`**

The array is ascending between `mid` and `mid + 1`. A peak must exist in the right portion, so set:

`low = mid + 1`

When `low == high`, the search range contains one index, which is a valid peak.

## Algorithm

1. Initialize `low = 0` and `high = nums.length - 1`.
2. While `low < high`:
   - Calculate `mid = low + (high - low) / 2`.
   - If `nums[mid] > nums[mid + 1]`, set `high = mid`.
   - Otherwise, set `low = mid + 1`.
3. Return `low`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — each iteration eliminates approximately half of the remaining search range.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

# Why Does the Optimal Approach Work?

Every adjacent pair is either ascending or descending because the problem guarantees that adjacent elements are different.

- On a descending slope, a peak exists at the current position or somewhere to the left.
- On an ascending slope, a peak must exist somewhere to the right.

Because the array is treated as having negative infinity beyond both boundaries, a peak is guaranteed to exist. For example, a strictly increasing array has a peak at its last index, while a strictly decreasing array has a peak at its first index.

The algorithm always retains a range containing at least one peak. Eventually, the range shrinks to a single index, which must be a peak.

# Edge Cases

| Input | Output | Explanation |
|---|---:|---|
| `[1]` | `0` | A single element is a peak |
| `[1, 2, 3, 1]` | `2` | Peak at the middle |
| `[3, 2, 1]` | `0` | First element is a peak |
| `[1, 2, 3, 4]` | `3` | Last element is a peak |
| `[1, 2, 1, 3, 5, 6, 4]` | `5` or `1` | Multiple peaks are possible |

# Key Takeaway

For **Find Peak Element**, binary search works by following the direction of the slope rather than searching for a specific value.

- If `nums[mid] > nums[mid + 1]`, search left, including `mid`.
- Otherwise, search right from `mid + 1`.
- **Time Complexity:** `O(log n)`
- **Space Complexity:** `O(1)`

The important insight is that we can find any peak without examining the entire array.

# Solution

[View solution.java](./solution.java)