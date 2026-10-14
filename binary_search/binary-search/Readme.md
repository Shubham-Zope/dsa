# Binary Search

## LeetCode

- **Problem:** [Binary Search](https://leetcode.com/problems/binary-search/)
- **Difficulty:** Easy
- **Pattern:** Binary Search

## Problem Description

Given an array of integers `nums` sorted in ascending order and an integer `target`, return the index of `target` if it exists in the array. Otherwise, return `-1`.

The solution must run in \(O(\log n)\) time.

### Examples

**Example 1**

- Input: `nums = [-1,0,3,5,9,12]`, `target = 9`
- Output: `4`
- Explanation: The target `9` is present at index `4`.

**Example 2**

- Input: `nums = [-1,0,3,5,9,12]`, `target = 2`
- Output: `-1`
- Explanation: The target `2` is not present in the array.

### Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 < nums[i], target < 10^4`
- All integers in `nums` are unique.
- `nums` is sorted in ascending order.

# Thought Process

The array is sorted, which means we do not need to check every element individually. We can use the ordering to eliminate half of the remaining search space after each comparison.

# 1. Brute Force Approach

### Idea

Traverse the array from left to right and compare each element with the target. Return the index when a match is found; if no match exists, return `-1`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the target is at the first index.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\) — the target is at the end or is absent.
- **Auxiliary Space:** \(O(1)\)

### Why Not Use It?

The brute force approach ignores the fact that the array is sorted. In the worst case, it checks every element, whereas binary search can eliminate half the remaining elements at each step.

# 2. Optimal Approach — Binary Search

### Key Insight

Because the array is sorted, comparing the target with the middle element tells us which half may contain the target.

- If `nums[mid] == target`, return `mid`.
- If `nums[mid] < target`, search the right half.
- If `nums[mid] > target`, search the left half.

Each comparison reduces the search space approximately by half.

### Algorithm

1. Initialize `left = 0` and `right = nums.length - 1`.
2. Continue while `left <= right`.
3. Calculate the middle index using `left + (right - left) / 2`.
4. Compare the middle element with the target.
5. If they match, return the middle index.
6. If the middle element is smaller, update `left = mid + 1`.
7. Otherwise, update `right = mid - 1`.
8. If the loop ends without a match, return `-1`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the target is the middle element.
- **Average Case:** \(O(\log n)\)
- **Worst Case:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\)

# Why Does the Optimal Approach Work?

At every iteration, the search interval contains all possible positions where the target could exist.

Since the array is sorted:

- Every element to the left of `mid` is smaller than `nums[mid]`.
- Every element to the right of `mid` is greater than `nums[mid]`.

Therefore, if the middle element does not match the target, one entire half can be safely discarded. Repeating this process eventually finds the target or leaves an empty search interval.

### Example Walkthrough

For `nums = [-1, 0, 3, 5, 9, 12]` and `target = 9`:

| Step | Left | Right | Mid | `nums[mid]` | Action |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 5 | 2 | 3 | Search right half |
| 2 | 3 | 5 | 4 | 9 | Target found |

The algorithm returns index `4`.

# Edge Cases

- **Target at the beginning:** The algorithm returns index `0`.
- **Target at the end:** The algorithm returns the last index.
- **Target absent:** The search interval eventually becomes empty, and the algorithm returns `-1`.
- **Single-element array:** The element is checked once.
- **Target smaller than all elements or larger than all elements:** The search terminates with `-1`.

# Key Takeaway

Binary search works on sorted arrays by repeatedly halving the search space. Remember the loop condition `left <= right`, update the boundaries using `mid + 1` or `mid - 1`, and calculate the middle index as `left + (right - left) / 2`.

# Solution

[View solution.java](./solution.java)
