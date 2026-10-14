# Upper Bound

## Problem

- **Platform:** [TakeUForward — Upper Bound](https://takeuforward.org/practice/dsa/upper-bound)
- **Pattern:** Binary Search

## Problem Description

Given a sorted array `nums` and an integer `x`, find the index of the **first element strictly greater than `x`**.

If no such element exists, return `nums.length`.

### Examples

**Example 1**

- Input: `nums = [1, 2, 2, 3, 4, 5]`, `x = 2`
- Output: `3`
- Explanation: The first element strictly greater than `2` is `3`, located at index `3`.

**Example 2**

- Input: `nums = [1, 2, 3, 4, 5]`, `x = 5`
- Output: `5`
- Explanation: No element is greater than `5`, so return the array length.

**Example 3**

- Input: `nums = [1, 3, 5, 7]`, `x = 4`
- Output: `2`
- Explanation: The first element greater than `4` is `5`, located at index `2`.

# Thought Process

Since the array is sorted, we can use binary search instead of checking every element.

The key difference between upper bound and lower bound is the condition:

- **Lower Bound:** Find the first element greater than or equal to `x` (`nums[i] >= x`).
- **Upper Bound:** Find the first element strictly greater than `x` (`nums[i] > x`).

We need to find the smallest index satisfying the upper-bound condition.

# 1. Brute Force Approach

### Idea

Traverse the array from left to right and return the index of the first element strictly greater than `x`.

If no such element exists, return `nums.length`.

### Algorithm

1. Iterate through the array from index `0`.
2. If `nums[i] > x`, return `i`.
3. If no element satisfies the condition, return `nums.length`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the first element is greater than `x`.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\) — the qualifying element is near the end or does not exist.
- **Auxiliary Space:** \(O(1)\)

### Why Improve It?

The array is already sorted, so binary search can eliminate half of the remaining search space after each comparison.

# 2. Optimal Approach — Binary Search

### Key Insight

We need the first index where `nums[mid] > x`.

There are two possibilities:

- If `nums[mid] <= x`, the current element is not a valid answer. Since the array is sorted, every element to its left is also less than or equal to `x`. Search the right half.
- If `nums[mid] > x`, `mid` is a valid answer, but an earlier valid index might exist. Save `mid` and search the left half.

Initialize `ans = nums.length` to handle the case where no element is greater than `x`.

### Algorithm

1. Initialize `low = 0` and `high = nums.length - 1`.
2. Initialize `ans = nums.length`.
3. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - If `nums[mid] <= x`, set `low = mid + 1`.
   - Otherwise, set `ans = mid` and `high = mid - 1`.
4. Return `ans`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the first midpoint checked is greater than `x` and the remaining search interval is empty.
- **Average Case:** \(O(\log n)\)
- **Worst Case:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\)

# Why Does the Optimal Approach Work?

The algorithm maintains `ans` as the smallest valid index found so far.

Whenever `nums[mid] > x`, the midpoint is a possible answer. However, there may be another element greater than `x` at an earlier index, so we save `mid` and continue searching to the left.

Whenever `nums[mid] <= x`, neither the midpoint nor any element before it can be the answer. Therefore, we move to the right.

Because the array is sorted, each decision safely eliminates half of the remaining search space. When the loop ends, `ans` contains the first index where the element is strictly greater than `x`, or `nums.length` if no such element exists.

# Edge Cases

- **All elements are greater than `x`:** Return `0`.
- **All elements are less than or equal to `x`:** Return `nums.length`.
- **Duplicate elements equal to `x`:** Skip all of them and return the index after the last occurrence of `x`.
- **`x` lies between two elements:** Return the index of the next larger element.
- **Single-element array:** Return `0` if the element is greater than `x`; otherwise, return `1`.

# Key Takeaway

Upper bound finds the **first position where `nums[i] > x`**.

The key is to move right when `nums[mid] <= x`, and save the answer while moving left when `nums[mid] > x`.

Remember the distinction:

- Lower bound: `nums[mid] >= x`
- Upper bound: `nums[mid] > x`

# Solution

[View solution.java](./solution.java)
