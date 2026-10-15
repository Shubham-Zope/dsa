# Search Insert Position

## LeetCode

- **Problem:** [Search Insert Position](https://leetcode.com/problems/search-insert-position/)
- **Difficulty:** Easy
- **Pattern:** Binary Search / Lower Bound

## Problem Description

Given a sorted array of distinct integers `nums` and an integer `target`, return the index if the target is found. If it is not found, return the index where it would be inserted in order.

The solution must run in \(O(\log n)\) time.

### Examples

**Example 1**

- Input: `nums = [1, 3, 5, 6]`, `target = 5`
- Output: `2`
- Explanation: The target exists at index `2`.

**Example 2**

- Input: `nums = [1, 3, 5, 6]`, `target = 2`
- Output: `1`
- Explanation: Inserting `2` at index `1` keeps the array sorted.

**Example 3**

- Input: `nums = [1, 3, 5, 6]`, `target = 7`
- Output: `4`
- Explanation: The target belongs at the end of the array.

### Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` contains distinct values sorted in ascending order.
- `-10^4 <= target <= 10^4`

# Thought Process

A straightforward solution is to iterate through the array and find the first element greater than or equal to the target. Its index is the correct position, whether the target exists or needs to be inserted.

However, because the array is sorted, we can use binary search to find this position in \(O(\log n)\) time.

The key observation is that the answer is the **first index where `nums[i] >= target`**. If no such index exists, the answer is `nums.length`.

# 1. Brute Force Approach

### Idea

Traverse the array from left to right. Return the index of the first element greater than or equal to `target`. If every element is smaller, return `nums.length`.

### Algorithm

1. Iterate through the array.
2. If `nums[i] >= target`, return `i`.
3. If the loop finishes, return `nums.length`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the first element is greater than or equal to the target.
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\) — the target belongs at the end.
- **Auxiliary Space:** \(O(1)\)

### Why Improve It?

The array is sorted, so we can eliminate half of the remaining search space after each comparison instead of examining every element.

# 2. Optimal Approach — Binary Search

### Key Insight

We need the first position where `nums[mid] >= target`.

At each iteration, there are two possibilities:

- **`target > nums[mid]`:** The middle element is too small. Every element to its left is also too small, so search the right half.
- **`target <= nums[mid]`:** The middle element is a valid insertion position. Save `mid` in `ans` and search the left half to check whether an earlier valid position exists.

Initialize `ans = nums.length` to handle the case where the target is greater than every element.

### Algorithm

1. Initialize `low = 0` and `high = nums.length - 1`.
2. Initialize `ans = nums.length`.
3. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - If `target > nums[mid]`, set `low = mid + 1`.
   - Otherwise, save `mid` in `ans` and set `high = mid - 1`.
4. Return `ans`.

### Complexity Analysis

- **Best Case:** \(O(1)\) — the first midpoint checked is a valid position and no further search is needed.
- **Average Case:** \(O(\log n)\)
- **Worst Case:** \(O(\log n)\)
- **Auxiliary Space:** \(O(1)\)

# Why Does the Optimal Approach Work?

The algorithm searches for the smallest index whose element is greater than or equal to the target.

Whenever a valid position is found, it saves that index and continues searching left. This ensures that the answer is the earliest valid position, rather than just any position that satisfies the condition.

When the middle element is smaller than the target, the sorted order guarantees that all elements before it are also too small. Therefore, the algorithm can safely discard the left half.

When the search ends, `ans` contains the correct insertion index. If no valid position was found, it remains `nums.length`, meaning the target belongs at the end.

# Edge Cases

- **Target already exists:** Return its index.
- **Target belongs at the beginning:** Return `0`.
- **Target belongs at the end:** Return `nums.length`.
- **Target lies between two elements:** Return the index of the larger element.
- **Single-element array:** Return `0` if the target is less than or equal to the element; otherwise, return `1`.

# Key Takeaway

Search Insert Position is a lower-bound problem.

The answer is the first index satisfying `nums[i] >= target`, which can be found using binary search in \(O(\log n)\) time and \(O(1)\) auxiliary space.

The same pattern is useful for lower bound and other problems that ask for the first position satisfying a condition in a sorted array.

# Solution

[View solution.java](./solution.java)