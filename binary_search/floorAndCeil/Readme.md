# Floor and Ceil in a Sorted Array

## Problem

- **Platform:** [TakeUForward — Floor and Ceil in Sorted Array](https://takeuforward.org/practice/dsa/floor-and-ceil-in-sorted-array)
- **Pattern:** Binary Search

## Problem Description

Given a sorted array `nums` and an integer `x`, find the **floor** and **ceil** of `x`.

- **Floor:** The greatest element in the array that is less than or equal to `x`.
- **Ceil:** The smallest element in the array that is greater than or equal to `x`.

Return an array containing `[floor, ceil]`. If either value does not exist, return `-1` for that value.

### Examples

**Example 1**

- Input: `nums = [3, 4, 4, 7, 8, 10]`, `x = 5`
- Output: `[4, 7]`
- Explanation: The greatest element less than or equal to `5` is `4`, and the smallest element greater than or equal to `5` is `7`.

**Example 2**

- Input: `nums = [3, 4, 4, 7, 8, 10]`, `x = 4`
- Output: `[4, 4]`
- Explanation: Since `4` exists in the array, both the floor and ceil are `4`.

**Example 3**

- Input: `nums = [3, 4, 7, 8, 10]`, `x = 2`
- Output: `[-1, 3]`
- Explanation: No element is less than or equal to `2`, so the floor is `-1`. The ceil is `3`.

# Thought Process

A simple approach is to traverse the array and find the greatest element less than or equal to `x` and the smallest element greater than or equal to `x`.

However, the array is sorted, so binary search can find both values efficiently.

We can solve the problem using two independent binary searches:

1. Find the floor by searching for the rightmost element less than or equal to `x`.
2. Find the ceil by searching for the leftmost element greater than or equal to `x`.

# 1. Brute Force Approach

### Idea

Traverse the array and maintain two values:

- `floor`: Update it whenever an element is less than or equal to `x` and greater than the current floor.
- `ceil`: Update it whenever an element is greater than or equal to `x` and smaller than the current ceil.

Initialize both values to `-1` to handle cases where either value does not exist.

### Complexity Analysis

- **Best Case:** \(O(n)\)
- **Average Case:** \(O(n)\)
- **Worst Case:** \(O(n)\)
- **Auxiliary Space:** \(O(1)\)

### Why Improve It?

The brute force approach checks every element. Since the array is sorted, binary search allows us to find each boundary in \(O(\log n)\) time.

# 2. Optimal Approach — Two Binary Searches

### Key Insight

Floor and ceil are opposite boundary-search problems.

**Finding the floor**

We need the greatest element satisfying `nums[mid] <= x`.

- If `nums[mid] <= x`, it is a valid floor candidate. Save its value and search right for a potentially greater valid value.
- If `nums[mid] > x`, search left because the current element and all elements after it are too large.

**Finding the ceil**

We need the smallest element satisfying `nums[mid] >= x`.

- If `nums[mid] < x`, search right because the current element and all elements before it are too small.
- Otherwise, save its value and search left for a potentially smaller valid value.

### Algorithm

1. Create an answer array of size two.
2. Call `getFloor(nums, x)` to find the floor.
3. Call `getCeil(nums, x)` to find the ceil.
4. Return the answer array.

### Floor Algorithm

1. Initialize `l = 0`, `h = nums.length - 1`, and `ans = -1`.
2. While `l <= h`:
   - Calculate the middle index.
   - If `nums[mid] <= x`, save `nums[mid]` and move right.
   - Otherwise, move left.
3. Return `ans`.

### Ceil Algorithm

1. Initialize `l = 0`, `h = nums.length - 1`, and `ans = -1`.
2. While `l <= h`:
   - Calculate the middle index.
   - If `nums[mid] < x`, move right.
   - Otherwise, save `nums[mid]` and move left.
3. Return `ans`.

### Complexity Analysis

Let \(n\) be the length of the array.

- **Floor Search:** \(O(\log n)\)
- **Ceil Search:** \(O(\log n)\)
- **Total Time:** \(O(\log n) + O(\log n) = O(\log n)\)
- **Auxiliary Space:** \(O(1)\), excluding the fixed-size output array.

# Why Does the Optimal Approach Work?

The sorted order allows each search to discard half of the remaining elements.

For the floor, moving right after finding a valid candidate helps find the greatest qualifying value. For the ceil, moving left after finding a valid candidate helps find the smallest qualifying value.

If no valid candidate is found, `ans` remains `-1`, correctly indicating that the floor or ceil does not exist.

### Example Walkthrough

Consider `nums = [3, 4, 4, 7, 8, 10]` and `x = 5`.

**Floor search**

- `mid = 2`, value `4`: valid floor candidate; search right.
- `mid = 4`, value `8`: too large; search left.
- `mid = 3`, value `7`: too large; search left.
- Result: `4`.

**Ceil search**

- `mid = 2`, value `4`: too small; search right.
- `mid = 4`, value `8`: valid ceil candidate; search left.
- `mid = 3`, value `7`: valid and smaller; search left.
- Result: `7`.

Final answer: `[4, 7]`.

# Edge Cases

- **`x` exists in the array:** Floor and ceil both equal `x`.
- **`x` is smaller than every element:** Floor is `-1`; ceil is the first element.
- **`x` is greater than every element:** Floor is the last element; ceil is `-1`.
- **Duplicate values:** The searches still return the correct floor and ceil values.
- **Single-element array:** The result depends on whether the element is less than, equal to, or greater than `x`.

# Key Takeaway

Floor and ceil can be solved efficiently using two binary searches:

- **Floor:** Find the rightmost element where `nums[mid] <= x`.
- **Ceil:** Find the leftmost element where `nums[mid] >= x`.

Both searches take \(O(\log n)\) time, so the overall time complexity remains \(O(\log n)\).

# Solution

[View solution.java](./solution.java)