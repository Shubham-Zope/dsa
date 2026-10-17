# Find Minimum in Rotated Sorted Array

## LeetCode

- **Problem:** [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
- **Difficulty:** Medium
- **Pattern:** Binary Search

## Problem Description

Given an array of unique integers `nums` sorted in ascending order and then rotated between `1` and `nums.length` times, return the minimum element.

The solution must run in \(O(\log n)\) time.

### Examples

**Example 1**

- Input: `nums = [3,4,5,1,2]`
- Output: `1`
- Explanation: The original sorted array was rotated, and the minimum element is `1`.

**Example 2**

- Input: `nums = [4,5,6,7,0,1,2]`
- Output: `0`
- Explanation: The minimum element is `0`.

**Example 3**

- Input: `nums = [11,13,15,17]`
- Output: `11`
- Explanation: The array is sorted, so the first element is the minimum.

### Constraints

- `n == nums.length`
- `1 <= n <= 5000`
- `-5000 <= nums[i] <= 5000`
- All integers in `nums` are unique.
- `nums` is sorted and rotated between `1` and `nums.length` times.

# Thought Process

A brute force solution would traverse the array and track the smallest element, taking \(O(n)\) time.

However, the array was originally sorted. After rotation, it consists of two sorted portions, and the minimum is located at the rotation point—or at index `0` if the array is currently in ascending order.

We can use binary search to narrow down the location of the minimum in \(O(\log n)\) time.

# 1. Brute Force Approach

### Idea

Traverse the entire array and maintain the smallest element encountered.

### Algorithm

1. Initialize `minValue = nums[0]`.
2. Iterate through the remaining elements.
3. Update `minValue` whenever a smaller element is found.
4. Return `minValue`.

### Complexity Analysis

- **Best Case:** \(O(n)\) for a complete traversal.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\)
- **Auxiliary Space:** \(O(1)\)

### Why Improve It?

The brute force approach ignores the sorted structure. Binary search can use comparisons between elements to eliminate a large portion of the search space at each step.

# 2. Optimal Approach — Binary Search

### Key Insight

Compare the middle element with the rightmost element:

- **If `nums[mid] > nums[high]`:** The minimum must be to the right of `mid`, because the rotation point lies in that portion. Set `low = mid + 1`.
- **Otherwise, if `nums[mid] <= nums[high]`:** The minimum is at `mid` or somewhere to its left. Set `high = mid`.

When the loop finishes, `low` points to the minimum element.

### Algorithm

1. Initialize `low = 0` and `high = nums.length - 1`.
2. While `low < high`:
   - Calculate `mid = low + (high - low) / 2`.
   - If `nums[mid] > nums[high]`, set `low = mid + 1`.
   - Otherwise, set `high = mid`.
3. Return `nums[low]`.

### Complexity Analysis

- **Best Case:** \(O(\log n)\) for the standard binary-search loop; this implementation continues until one candidate remains.
- **Average Case:** \(O(\log n)\)
- **Worst Case:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\)

# Why Does the Optimal Approach Work?

The minimum element is the first element of the rotated, smaller sorted portion.

If `nums[mid] > nums[high]`, the middle element belongs to the larger sorted portion, so the minimum must be strictly to its right.

If `nums[mid] <= nums[high]`, the interval from `mid` to `high` is sorted. The minimum cannot be to the right of `mid`, so `mid` remains a valid candidate and we search the left portion, including `mid`.

The search interval shrinks on every iteration until `low == high`. At that point, only the minimum's index remains.

### Example Walkthrough

Consider `nums = [4,5,6,7,0,1,2]`.

| Step | Low | High | Mid | `nums[mid]` | `nums[high]` | Action |
|---|---:|---:|---:|---:|---:|---|
| 1 | 0 | 6 | 3 | 7 | 2 | Search right |
| 2 | 4 | 6 | 5 | 1 | 2 | Search left, including mid |
| 3 | 4 | 5 | 4 | 0 | 1 | Search left, including mid |

Now `low == high == 4`, so the minimum is `nums[4] = 0`.

# Edge Cases

- **Single-element array:** Return the only element.
- **Array rotated once:** Find the minimum at the appropriate rotation boundary.
- **Minimum at the last index:** For example, `[2,3,4,5,1]`, return `1`.
- **Array in ascending order:** Return the first element.
- **Negative values:** Comparisons work the same way for negative and positive integers.

# Key Takeaway

Finding the minimum in a rotated sorted array is a binary-search problem.

Compare `nums[mid]` with `nums[high]` to determine which portion may contain the minimum. Keep `mid` in the search interval when it could itself be the minimum.

- **Time Complexity:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\)

# Solution

[View solution.java](./solution.java)