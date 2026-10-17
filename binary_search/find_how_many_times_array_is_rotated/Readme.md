# Find Out How Many Times the Array Is Rotated

## Problem

- **Platform:** [TakeUForward](https://takeuforward.org/practice/dsa/find-out-how-many-times-the-array-is-rotated)
- **Pattern:** Binary Search
- **Difficulty:** Medium

## Problem Description

Given a sorted array that has been rotated some number of times, find the number of rotations performed.

The array was originally sorted in ascending order and then rotated. Assume the elements are distinct.

The number of rotations is equal to the **index of the minimum element** in the array.

### Examples

**Example 1**

Input: `nums = [4, 5, 1, 2, 3]`

Output: `2`

Explanation: The minimum element `1` is at index `2`, so the array has been rotated 2 times.

**Example 2**

Input: `nums = [3, 4, 5, 1, 2]`

Output: `3`

Explanation: The minimum element `1` is at index `3`, so the array has been rotated 3 times.

**Example 3**

Input: `nums = [1, 2, 3, 4, 5]`

Output: `0`

Explanation: The array is already sorted, so no rotations were performed.

### Constraints

- The array contains distinct elements.
- The array was originally sorted in ascending order.
- The array may be rotated zero or more times.

# Thought Process

The key observation is that the number of rotations is the index of the smallest element.

A straightforward approach is to find the minimum element by traversing the entire array. However, since the array was originally sorted, we can use binary search to locate the minimum more efficiently.

# 1. Brute Force Approach

## Idea

Traverse the array and find the index of the smallest element. Since the array was originally sorted, this index represents the number of rotations.

## Algorithm

1. Initialize `minIndex = 0`.
2. Iterate through the array from index `1` to the last index.
3. If the current element is smaller than the element at `minIndex`, update `minIndex`.
4. Return `minIndex`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — traverse the array once.
- **Space Complexity:** `O(1)` — use only a constant amount of extra space.

## Why Improve?

We can take advantage of the sorted portions of the rotated array to eliminate half of the search space at each step, reducing the time complexity to `O(log n)`.

# 2. Optimal Approach — Binary Search

## Idea

A rotated sorted array consists of two sorted portions. The minimum element is the point where the rotation occurs.

We use binary search to identify sorted portions and track the index of the smallest element found so far.

- If `nums[low] <= nums[mid]`, the left portion is sorted. Its minimum is `nums[low]`, so update the answer if necessary and search the right portion.
- Otherwise, the minimum lies in the right sorted portion. Track `nums[mid]` and search the left portion.

Since the elements are distinct, these conditions allow us to narrow the search space efficiently.

## Algorithm

1. Initialize `low = 0`, `high = nums.size() - 1`.
2. Initialize `minIndex = 0` and `ans = Integer.MAX_VALUE`.
3. While `low <= high`:
   - Calculate `mid = low + (high - low) / 2`.
   - If `nums[low] <= nums[mid]`:
     - If `nums[low] < ans`, update `ans` and `minIndex = low`.
     - Move right using `low = mid + 1`.
   - Otherwise:
     - If `nums[mid] < ans`, update `ans` and `minIndex = mid`.
     - Move left using `high = mid - 1`.
4. Return `minIndex`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — binary search eliminates approximately half of the remaining search space in each iteration.
- **Space Complexity:** `O(1)` — only a constant number of variables are used.

# Why Does the Optimal Approach Work?

At every iteration, we identify a sorted portion of the array.

If the left portion is sorted, its smallest element is at `low`. We record that candidate and discard the left portion from further consideration.

Otherwise, the minimum is in the right sorted portion, and `mid` is a candidate. We record it and continue searching the left side.

By maintaining the smallest candidate and its index, we eventually identify the minimum element. Its index gives the number of rotations.

# Edge Cases

| Input | Output | Explanation |
|---|---:|---|
| `[1, 2, 3, 4, 5]` | `0` | Already sorted |
| `[2, 3, 4, 5, 1]` | `4` | Minimum is at index 4 |
| `[4, 5, 1, 2, 3]` | `2` | Minimum is at index 2 |
| `[1]` | `0` | Single-element array |

# Key Takeaway

For a rotated sorted array with distinct elements, **the number of rotations equals the index of the minimum element**.

Binary search lets us find that index in `O(log n)` time and `O(1)` extra space by using the sorted portions of the array.

# Solution

[View solution.java](./solution.java)