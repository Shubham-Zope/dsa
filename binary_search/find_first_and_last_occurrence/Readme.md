# Find First and Last Position of Element in Sorted Array

## LeetCode

- **Problem:** [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
- **Difficulty:** Medium
- **Pattern:** Binary Search

## Problem Description

Given an array of integers `nums` sorted in non-decreasing order and an integer `target`, find the starting and ending positions of the target.

If the target is not found, return `[-1, -1]`.

The solution must run in \(O(\log n)\) time.

### Examples

**Example 1**

- Input: `nums = [5, 7, 7, 8, 8, 10]`, `target = 8`
- Output: `[3, 4]`
- Explanation: The target appears at indices `3` and `4`.

**Example 2**

- Input: `nums = [5, 7, 7, 8, 8, 10]`, `target = 6`
- Output: `[-1, -1]`
- Explanation: The target does not exist in the array.

**Example 3**

- Input: `nums = []`, `target = 0`
- Output: `[-1, -1]`
- Explanation: The array is empty, so the target cannot be found.

### Constraints

- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- `nums` is sorted in non-decreasing order.
- `-10^9 <= target <= 10^9`

# Thought Process

A straightforward approach is to traverse the array and record the first and last indices where the target appears.

However, this takes \(O(n)\) time in the worst case. Since the array is sorted, we can use binary search to find both positions efficiently.

The key observation is that finding the first occurrence and finding the last occurrence are two slightly different binary search problems.

- **First occurrence:** When the target is found, save its index and continue searching left.
- **Last occurrence:** When the target is found, save its index and continue searching right.

# 1. Brute Force Approach

### Idea

Traverse the array from left to right. Record the first index where `nums[i] == target` and update the last index every time another occurrence is found.

If the target never appears, return `[-1, -1]`.

### Algorithm

1. Initialize `first = -1` and `last = -1`.
2. Traverse the array.
3. Whenever `nums[i] == target`:
   - If `first == -1`, set `first = i`.
   - Set `last = i`.
4. Return `[first, last]`.

### Complexity Analysis

- **Best Case:** \(O(n)\) for a full traversal; it can be \(O(1)\) if the implementation stops early after determining the range, but this approach uses a full traversal.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\)
- **Auxiliary Space:** \(O(1)\), excluding the output array.

### Why Improve It?

The brute force approach may inspect every element. Because the array is sorted, binary search can eliminate half of the remaining search space at each step.

# 2. Optimal Approach — Two Binary Searches

### Key Insight

We can find the first and last positions independently using binary search.

**Finding the first occurrence**

- If `nums[mid] == target`, save `mid` and search left using `high = mid - 1`.
- If `nums[mid] < target`, search right.
- If `nums[mid] > target`, search left.

**Finding the last occurrence**

- If `nums[mid] == target`, save `mid` and search right using `low = mid + 1`.
- If `nums[mid] < target`, search right.
- If `nums[mid] > target`, search left.

In both searches, initialize `ans = -1`. If the target is absent, the answer remains `-1`.

### Algorithm

1. Call `findFirst(nums, target)` to find the first occurrence.
2. Call `findLast(nums, target)` to find the last occurrence.
3. Return both indices in an array.

### Complexity Analysis

Let \(n\) be the length of the array.

- **First Occurrence:** \(O(\log n)\)
- **Last Occurrence:** \(O(\log n)\)
- **Total Time:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\), excluding the output array.

# Why Does the Optimal Approach Work?

Because the array is sorted, all occurrences of the target appear consecutively.

When the first binary search finds the target, it continues searching to the left to determine whether an earlier occurrence exists. The last valid index it records is therefore the first occurrence.

Similarly, the second binary search continues to the right whenever it finds the target. The last valid index it records is therefore the last occurrence.

If the target does not exist, neither search records an index, so both return `-1`. The final result is `[-1, -1]`.

### Example Walkthrough

Consider `nums = [5, 7, 7, 8, 8, 10]` and `target = 8`.

**First occurrence search**

- `mid = 2`, value `7`: search right.
- `mid = 4`, value `8`: save index `4`, then search left.
- `mid = 3`, value `8`: save index `3`, then search left.
- Result: `3`.

**Last occurrence search**

- `mid = 2`, value `7`: search right.
- `mid = 4`, value `8`: save index `4`, then search right.
- `mid = 5`, value `10`: search left.
- Result: `4`.

Final answer: `[3, 4]`.

# Edge Cases

- **Target appears once:** Both indices are the same.
- **Target appears multiple times:** Return the first and last occurrences.
- **Target does not exist:** Return `[-1, -1]`.
- **Empty array:** Return `[-1, -1]`.
- **Target appears at the beginning or end:** Ensure the search boundaries are updated correctly.

# Key Takeaway

Finding the first and last positions in a sorted array is a classic binary search problem.

- To find the **first occurrence**, save a match and continue left.
- To find the **last occurrence**, save a match and continue right.

Two binary searches achieve \(O(\log n)\) time with \(O(1)\) auxiliary space.

# Solution

[View solution.java](./solution.java)