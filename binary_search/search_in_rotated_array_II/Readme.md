# Search in Rotated Sorted Array II

## LeetCode

- **Problem:** [Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)
- **Difficulty:** Medium
- **Pattern:** Modified Binary Search

## Problem Description

Given an integer array `nums` that was originally sorted in non-decreasing order and then rotated at an unknown pivot, determine whether a given `target` exists in the array.

Return `true` if the target exists; otherwise, return `false`.

Unlike Search in Rotated Sorted Array I, **the array may contain duplicate elements**.

### Examples

**Example 1**

- Input: `nums = [2,5,6,0,0,1,2]`, `target = 0`
- Output: `true`
- Explanation: The target `0` exists in the array.

**Example 2**

- Input: `nums = [2,5,6,0,0,1,2]`, `target = 3`
- Output: `false`
- Explanation: The target `3` does not exist in the array.

**Example 3**

- Input: `nums = [1,0,1,1,1]`, `target = 0`
- Output: `true`
- Explanation: The target `0` exists in the array.

### Constraints

- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is guaranteed to have been rotated at some pivot.
- `-10^4 <= target <= 10^4`

# Thought Process

A brute force approach would traverse the array and check whether the target exists, taking \(O(n)\) time.

Since the array is rotated and sorted, we can improve this using modified binary search. At each iteration, at least one half is sorted, allowing us to determine which half might contain the target.

However, duplicate elements introduce an important complication: **sometimes we cannot determine which half is sorted**.

For example, in `[1, 0, 1, 1, 1]`, the values at `low`, `mid`, and `high` may all be equal. In this situation, we cannot use their values to identify the sorted half reliably.

The solution is to shrink the search interval from both ends when this ambiguity occurs.

# 1. Brute Force Approach

### Idea

Check every element in the array. If any element equals `target`, return `true`. If the entire array is checked without finding the target, return `false`.

### Algorithm

1. Iterate through the array.
2. If `nums[i] == target`, return `true`.
3. If no match is found, return `false`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the first element matches.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\) — the target is absent or appears at the end.
- **Auxiliary Space:** \(O(1)\)

### Why Improve It?

The brute force approach ignores the ordering of the array. Modified binary search can often eliminate half of the remaining search space, although duplicates prevent a guaranteed logarithmic worst-case runtime.

# 2. Optimal Approach — Modified Binary Search with Duplicates

### Key Insight

At every iteration, first check whether `nums[mid] == target`. If so, return `true`.

Otherwise, handle three cases.

### Case 1: All Three Boundary Values Are Equal

If:

`nums[low] == nums[mid] && nums[mid] == nums[high]`

we cannot determine which half is sorted.

Since `nums[mid]` has already been checked and does not match the target, shrink the search interval:

- `low++`
- `high--`

This safely removes the ambiguous boundary elements.

### Case 2: The Left Half Is Sorted

If `nums[low] <= nums[mid]`, the left half is sorted.

- If `nums[low] <= target && target < nums[mid]`, search left by setting `high = mid - 1`.
- Otherwise, search right by setting `low = mid + 1`.

### Case 3: The Right Half Is Sorted

Otherwise, the right half is sorted.

- If `nums[mid] < target && target <= nums[high]`, search right by setting `low = mid + 1`.
- Otherwise, search left by setting `high = mid - 1`.

Repeat until the target is found or the search interval becomes empty.

### Algorithm

1. Initialize `low = 0` and `high = nums.length - 1`.
2. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - If `nums[mid] == target`, return `true`.
   - If the values at `low`, `mid`, and `high` are equal, increment `low` and decrement `high`.
   - Otherwise, identify the sorted half and check whether the target falls within its range.
3. If the loop finishes without finding the target, return `false`.

### Complexity Analysis

Let \(n\) be the length of the array.

- **Best Case:** \(O(1)\) — the middle element matches the target.
- **Average Case:** Often \(O(\log n)\) when the search can repeatedly eliminate large portions of the array.
- **Worst Case:** \(O(n)\) — duplicates may force the algorithm to shrink the search interval one element at a time.
- **Auxiliary Space:** \(O(1)\)

# Why Does the Optimal Approach Work?

The algorithm maintains the invariant that if the target exists in the remaining search interval, it has not been discarded.

When the left or right half is identified as sorted, the target's value determines whether it can exist in that half. The other half can safely be discarded.

When `nums[low]`, `nums[mid]`, and `nums[high]` are equal, the algorithm cannot reliably identify a sorted half. However, because the middle element has already been checked and does not equal the target, removing the equal boundary elements cannot remove the only possible occurrence of the target.

This process continues until the target is found or all possible positions have been eliminated.

### Example Walkthrough

Consider `nums = [1,0,1,1,1]` and `target = 0`.

| Step | Low | High | Mid | Values at Low, Mid, High | Action |
|---|---:|---:|---:|---|---|
| 1 | 0 | 4 | 2 | `1, 1, 1` | Ambiguous; shrink both ends |
| 2 | 1 | 3 | 2 | `0, 1, 1` | Left half is sorted; target is in the left half |
| 3 | 1 | 1 | 1 | `0` | Target found |

The algorithm returns `true`.

# Edge Cases

- **Target exists at the midpoint:** Return `true` immediately.
- **Target does not exist:** Return `false` when `low > high`.
- **All elements are identical and match the target:** Return `true`.
- **All elements are identical and do not match the target:** Repeatedly shrink the interval, then return `false`.
- **Duplicates at both boundaries:** Shrink the interval when the three boundary values are equal.
- **Single-element array:** Return whether that element equals the target.

# Key Takeaway

Search in Rotated Sorted Array II extends modified binary search to handle duplicate values.

- When a sorted half can be identified, use its value range to eliminate half of the search space.
- When `nums[low] == nums[mid] == nums[high]`, shrink both boundaries because the sorted half is ambiguous.
- **Time Complexity:** \(O(n)\) worst case due to duplicates.
- **Auxiliary Space:** \(O(1)\).

Unlike the version without duplicates, this problem cannot guarantee \(O(\log n)\) worst-case time.

# Solution

[View solution.java](./solution.java)