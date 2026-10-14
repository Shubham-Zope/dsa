# Lower Bound

## Problem

- **Platform:** [TakeUForward — Lower Bound](https://takeuforward.org/practice/dsa/lower-bound-)
- **Pattern:** Binary Search

## Problem Description

Given a sorted array `nums` and an integer `x`, find the index of the **first element greater than or equal to `x`**.

If no such element exists, return `nums.length`.

### Examples

**Example 1**

- Input: `nums = [1, 2, 2, 3, 4, 5]`, `x = 2`
- Output: `1`
- Explanation: The first element greater than or equal to `2` is at index `1`.

**Example 2**

- Input: `nums = [1, 2, 3, 4, 5]`, `x = 6`
- Output: `5`
- Explanation: No element is greater than or equal to `6`, so return the array length.

**Example 3**

- Input: `nums = [1, 3, 5, 7]`, `x = 4`
- Output: `2`
- Explanation: The first element greater than or equal to `4` is `5`, at index `2`.

# Thought Process

A simple approach would be to traverse the array from left to right and return the index of the first element greater than or equal to `x`.

However, since the array is sorted, we can use binary search to find this position more efficiently.

The key difference from ordinary binary search is that we are not looking for an exact match. We need the **first valid position**, even when multiple elements satisfy the condition.

# 1. Brute Force Approach

### Idea

Traverse the array from the beginning. Return the index of the first element satisfying `nums[i] >= x`.

If no element satisfies the condition, return `nums.length`.

### Algorithm

1. Iterate through the array from index `0`.
2. If `nums[i] >= x`, return `i`.
3. If the loop finishes, return `nums.length`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the first element satisfies the condition.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\) — the valid element is near the end or no element qualifies.
- **Auxiliary Space:** \(O(1)\)

### Why Improve It?

The array is sorted, so we can eliminate half of the remaining search space at each step instead of checking every element.

# 2. Optimal Approach — Binary Search

### Key Insight

We need the smallest index `i` for which `nums[i] >= x`.

Whenever `nums[mid] >= x`, we have found a valid position. However, there might be an earlier valid position on the left, so we save `mid` in `ans` and continue searching left.

When `nums[mid] < x`, the current element and every element before it are too small. Therefore, we search the right half.

### Algorithm

1. Initialize `low = 
