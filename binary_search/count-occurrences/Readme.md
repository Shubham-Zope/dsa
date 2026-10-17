# Count Occurrences in a Sorted Array

## Problem

- **Platform:** [TakeUForward](https://takeuforward.org/practice/dsa/count-occurrences-in-a-sorted-array)
- **Pattern:** Binary Search
- **Difficulty:** Easy

## Problem Description

Given a sorted array of integers and a target value, find how many times the target occurs in the array.

The array is sorted in non-decreasing order, so duplicate occurrences of the target appear consecutively.

Return `0` if the target is not present.

### Examples

**Example 1**

Input: `arr = [2, 2, 3, 3, 3, 3, 4, 4, 4, 5]`, `target = 3`

Output: `4`

Explanation: The target `3` occurs four times in the array.

**Example 2**

Input: `arr = [1, 1, 2, 2, 2, 3, 4, 4]`, `target = 5`

Output: `0`

Explanation: The target `5` is not present in the array.

**Example 3**

Input: `arr = [1, 2, 2, 2, 3]`, `target = 2`

Output: `3`

Explanation: The target `2` occurs three times.

### Constraints

- The array is sorted in non-decreasing order.
- The array may contain duplicate elements.
- The target may or may not exist in the array.

# Thought Process

Since the array is sorted, all occurrences of the target are grouped together.

Instead of counting every element with a linear scan, we can use binary search to find the first and last positions of the target.

Once these positions are known, the number of occurrences is:

`count = last - first + 1`

If the target is not found, return `0`.

# 1. Brute Force Approach

## Idea

Traverse the entire array and increment a counter whenever an element equals the target.

## Algorithm

1. Initialize `count = 0`.
2. Iterate through every element in the array.
3. If the current element equals `target`, increment `count`.
4. Return `count`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — every element is checked.
- **Space Complexity:** `O(1)` — only a counter is required.

## Why Improve?

The brute-force approach ignores the fact that the array is sorted. Binary search can locate the target's boundaries in logarithmic time.

# 2. Optimal Approach — Binary Search

## Idea

Perform two binary searches:

- **First occurrence:** Find the leftmost index containing the target.
- **Last occurrence:** Find the rightmost index containing the target.

During the first search, when the target is found, store its index and continue searching left by setting `high = mid - 1`.

During the second search, when the target is found, store its index and continue searching right by setting `low = mid + 1`.

If the first occurrence does not exist, return `0`. Otherwise, calculate the count using the two indices.

## Algorithm

1. Call `findFirst(arr, target)` to find the first occurrence.
2. If the result is `-1`, return `0`.
3. Call `findLast(arr, target)` to find the last occurrence.
4. Return `last - first + 1`.

### Finding the First Occurrence

1. Initialize `low = 0`, `high = arr.length - 1`, and `ans = -1`.
2. While `low <= high`:
   - Calculate `mid`.
   - If `arr[mid] == target`, update `ans = mid` and search left.
   - If `arr[mid] < target`, search right.
   - Otherwise, search left.
3. Return `ans`.

### Finding the Last Occurrence

1. Initialize `low = 0`, `high = arr.length - 1`, and `ans = -1`.
2. While `low <= high`:
   - Calculate `mid`.
   - If `arr[mid] == target`, update `ans = mid` and search right.
   - If `arr[mid] < target`, search right.
   - Otherwise, search left.
3. Return `ans`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — two binary searches each take `O(log n)`, so the total is `O(log n)`.
- **Space Complexity:** `O(1)` — the searches use only a constant number of variables.

# Why Does the Optimal Approach Work?

Because the array is sorted, all occurrences of the target form one continuous range.

The first binary search identifies the beginning of this range, while the second identifies its end. Continuing left after finding the target ensures the first search finds the earliest occurrence; continuing right ensures the second finds the latest occurrence.

The range length is therefore:

`last - first + 1`

If the target is absent, the first search returns `-1`, allowing us to return `0` immediately.

# Edge Cases

| Input | Target | Output | Explanation |
|---|---:|---:|---|
| `[1, 2, 2, 2, 3]` | `2` | `3` | Target occurs multiple times |
| `[1, 2, 3, 4]` | `3` | `1` | Target occurs once |
| `[1, 2, 3, 4]` | `5` | `0` | Target is absent |
| `[2, 2, 2, 2]` | `2` | `4` | Every element matches |
| `[1, 2, 3]` | `1` | `1` | Target is at the beginning |

# Key Takeaway

For a sorted array, counting occurrences can be optimized by finding the first and last positions of the target using binary search.

**Formula:** `count = last - first + 1`

- **Time Complexity:** `O(log n)`
- **Space Complexity:** `O(1)`

# Solution

[View solution.java](./solution.java)
