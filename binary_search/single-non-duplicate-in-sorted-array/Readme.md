# Single Element in a Sorted Array

## LeetCode

- **Problem:** [Single Element in a Sorted Array](https://leetcode.com/problems/single-element-in-a-sorted-array/)
- **Difficulty:** Medium
- **Pattern:** Binary Search

## Problem Description

You are given a sorted array consisting of integers where every element appears exactly twice, except for one element that appears exactly once.

Find and return the single element.

Your solution must run in `O(log n)` time and use `O(1)` extra space.

### Examples

**Example 1**

Input: `nums = [1,1,2,3,3,4,4,8,8]`

Output: `2`

Explanation: The element `2` appears only once, while every other element appears twice.

**Example 2**

Input: `nums = [3,3,7,7,10,11,11]`

Output: `10`

Explanation: The element `10` appears only once.

### Constraints

- `1 <= nums.length <= 10^5`
- `0 <= nums[i] <= 10^5`
- The array is sorted in ascending order.
- Every element appears exactly twice, except for one element that appears once.

# Thought Process

Since the array is sorted, duplicate elements appear next to each other.

A brute-force approach can count occurrences or compare adjacent elements. However, we need `O(log n)` time, so binary search is the right choice.

The key observation is that **the single element changes the expected pairing pattern**:

- Before the single element, pairs begin at even indices: `(0,1)`, `(2,3)`, `(4,5)`, and so on.
- After the single element, this pattern shifts, and pairs begin at odd indices.

We can use this change in index parity to determine which half contains the single element.

# 1. Brute Force Approach

## Idea

Traverse the array and compare each element with its neighbors. The single element is the one that differs from both neighbors, taking care of the first and last positions separately.

## Algorithm

1. If the array has one element, return it.
2. Check whether the first element differs from the second.
3. Check whether the last element differs from the second-last.
4. Traverse the remaining elements and find the element that differs from both neighbors.
5. Return that element.

## Complexity Analysis

- **Time Complexity:** `O(n)` — in the worst case, we examine every element.
- **Space Complexity:** `O(1)` — no additional data structure is needed.

## Why Improve?

A linear scan does not use the sorted array's pairing structure. Binary search can use the parity of indices to eliminate half of the search space at each step.

# 2. Optimal Approach — Binary Search

## Idea

Use the middle index to determine whether the single element lies on the left or right side.

First, handle the boundary cases where the single element is at the beginning or end. Then, during binary search:

- If `nums[mid]` differs from both neighbors, it is the answer.
- If `mid` is even:
  - If `nums[mid] == nums[mid + 1]`, the pair is correctly aligned, so the single element lies to the right.
  - Otherwise, the single element lies to the left, including `mid`.
- If `mid` is odd:
  - If `nums[mid] == nums[mid - 1]`, the pair is correctly aligned, so the single element lies to the right.
  - Otherwise, the single element lies to the left, including `mid`.

## Algorithm

1. Handle the single-element array.
2. Check whether the first or last element is unique.
3. Initialize `low = 1` and `high = n - 2`.
4. While `low <= high`:
   - Calculate `mid`.
   - If `nums[mid]` differs from both neighbors, return it.
   - Use the parity of `mid` and compare it with the appropriate neighbor.
   - Update `low` or `high` to eliminate half of the search space.
5. Return a fallback value only if no answer is found; under the problem constraints, a valid answer will always be found.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — each iteration eliminates approximately half of the remaining search space.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

# Why Does the Optimal Approach Work?

Every element other than the single element belongs to a pair.

Before the single element, pairs occupy the expected even-odd positions. Once the single element is encountered, all subsequent pairs shift by one position.

For example, in:

`[1, 1, 2, 3, 3, 4, 4]`

Before `2`, the pair `(1, 1)` occupies indices `(0, 1)`. After `2`, the pairs begin at indices `(3, 4)` and `(5, 6)`.

By checking whether a pair follows the expected index pattern, binary search identifies which half contains the disruption. Repeating this process eventually locates the unique element.

# Edge Cases

| Input | Output | Explanation |
|---|---:|---|
| `[1]` | `1` | Only one element |
| `[1, 2, 2, 3, 3]` | `1` | Single element at the beginning |
| `[1, 1, 2, 3, 3]` | `2` | Single element in the middle |
| `[1, 1, 2]` | `2` | Single element at the end |
| `[3, 3, 7, 7, 10, 11, 11]` | `10` | Single element between pairs |

# Key Takeaway

In a sorted array where every element appears twice except one, the single element disrupts the even-odd pairing pattern.

**Use binary search and index parity to find the unique element in `O(log n)` time and `O(1)` extra space.**

# Solution

[View solution.java](./solution.java)
