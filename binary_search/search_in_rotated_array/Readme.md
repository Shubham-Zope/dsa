# Search in Rotated Sorted Array

## LeetCode

- **Problem:** [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
- **Difficulty:** Medium
- **Pattern:** Binary Search

## Problem Description

Given an integer array `nums` that was originally sorted in ascending order and then rotated at an unknown pivot, search for a given `target`.

Return the index of `target` if it exists in the array. Otherwise, return `-1`.

The array contains **distinct values**, and the solution must run in \(O(\log n)\) time.

### Examples

**Example 1**

- Input: `nums = [4,5,6,7,0,1,2]`, `target = 0`
- Output: `4`
- Explanation: The target `0` is located at index `4`.

**Example 2**

- Input: `nums = [4,5,6,7,0,1,2]`, `target = 3`
- Output: `-1`
- Explanation: The target does not exist in the array.

**Example 3**

- Input: `nums = [1]`, `target = 0`
- Output: `-1`
- Explanation: The only element does not match the target.

### Constraints

- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- All values in `nums` are distinct.
- `nums` is guaranteed to have been rotated between `1` and `nums.length` times.
- `-10^4 <= target <= 10^4`

# Thought Process

A brute force approach would check every element until the target is found. However, this takes \(O(n)\) time.

Although the array is rotated, it still has an important property: **at least one half of the current search interval is always sorted**.

We can use this property to perform binary search:

1. Find the middle element.
2. Determine which half is sorted.
3. Check whether the target lies within that sorted half.
4. Search the appropriate half and discard the other.

This lets us retain the \(O(\log n)\) time complexity of binary search.

# 1. Brute Force Approach

### Idea

Traverse the array from left to right and compare each element with `target`.

If a match is found, return its index. If the entire array is checked without finding the target, return `-1`.

### Algorithm

1. Iterate through the array.
2. If `nums[i] == target`, return `i`.
3. If the loop finishes, return `-1`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the target is at the first index.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\) — the target is at the end or is absent.
- **Auxiliary Space:** \(O(1)\)

### Why Improve It?

The brute force approach does not use the array's ordering. By identifying a sorted half at every step, we can eliminate half of the remaining search space.

# 2. Optimal Approach — Modified Binary Search

### Key Insight

At every iteration, calculate `mid` and first check whether `nums[mid] == target`.

If the target is not found, determine which half is sorted.

**Case 1: The left half is sorted**

If `nums[low] <= nums[mid]`, the interval from `low` to `mid` is sorted.

- If `nums[low] <= target && target < nums[mid]`, the target lies within the left half. Set `high = mid - 1`.
- Otherwise, search the right half by setting `low = mid + 1`.

**Case 2: The right half is sorted**

Otherwise, the interval from `mid` to `high` is sorted.

- If `nums[mid] < target && target <= nums[high]`, the target lies within the right half. Set `low = mid + 1`.
- Otherwise, search the left half by setting `high = mid - 1`.

Repeat until the target is found or the search interval becomes empty.

### Algorithm

1. Initialize `low = 0` and `high = nums.length - 1`.
2. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - If `nums[mid] == target`, return `mid`.
   - If the left half is sorted:
     - If the target lies within its range, search left.
     - Otherwise, search right.
   - If the right half is sorted:
     - If the target lies within its range, search right.
     - Otherwise, search left.
3. Return `-1` if the target is not found.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the target is at the middle index.
- **Average Case:** \(O(\log n)\)
- **Worst Case:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\)

# Why Does the Optimal Approach Work?

Rotation breaks the original sorted array into two sorted sections. However, at each midpoint, at least one half of the current interval remains sorted.

The sorted half provides a reliable range for deciding whether the target can exist there.

If the target lies within that half's range, we continue searching there. Otherwise, we discard that half and search the other one.

Since all elements are distinct, the sorted half can be identified unambiguously using `nums[low] <= nums[mid]`.

Each iteration eliminates approximately half of the remaining candidates, allowing the algorithm to find the target in \(O(\log n)\) time.

### Example Walkthrough

Consider `nums = [4,5,6,7,0,1,2]` and `target = 0`.

| Step | Low | High | Mid | `nums[mid]` | Decision |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 6 | 3 | 7 | Left half is sorted; target is not in it, so search right |
| 2 | 4 | 6 | 5 | 1 | Right half is sorted; target is not in it, so search left |
| 3 | 4 | 4 | 4 | 0 | Target found at index `4` |

The algorithm returns `4`.

# Edge Cases

- **Target found at the middle:** Return `mid` immediately.
- **Target is absent:** Return `-1` when `low > high`.
- **Single-element array:** Return `0` if it matches the target; otherwise, return `-1`.
- **Rotation at different pivot positions:** The algorithm works regardless of where the original array was rotated.
- **Target at either end:** The inclusive range checks correctly handle boundary elements.

# Key Takeaway

Search in Rotated Sorted Array is a modified binary search problem.

The key is to identify the sorted half, check whether the target belongs to its range, and eliminate the half that cannot contain the target.

- **Time Complexity:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\)

This technique works because the array contains distinct elements. Variants that allow duplicates require additional handling when the sorted half cannot be identified clearly.

# Solution

[View solution.java](./solution.java)