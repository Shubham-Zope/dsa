# Maximum Subarray

## LeetCode

- **Problem:** [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)
- **Difficulty:** Medium
- **Pattern:** Kadane's Algorithm / Dynamic Programming

## Problem Description

Given an integer array `nums`, find the contiguous subarray with the largest sum and return its sum.

### Example

```text
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]

Output: 6
```

The subarray `[4,-1,2,1]` has the maximum sum:

```text
4 + (-1) + 2 + 1 = 6
```

### Constraints

- `1 <= nums.length <= 10⁵`
- `-10⁴ <= nums[i] <= 10⁴`

# Thought Process

The important part is that the subarray must be **contiguous**.

A brute-force approach would be to generate every possible subarray and calculate its sum. But there are `O(n²)` possible subarrays, so this becomes expensive.

The key observation is:

> If the sum of the current subarray becomes negative, keeping that subarray cannot help any future subarray.

For example:

```text
[-2, 1]
```

The current sum becomes negative after `-2`.

Starting a new subarray from `1` is better than carrying the negative sum forward:

```text
-2 + 1 = -1
1     =  1
```

So we can keep a running sum and reset it whenever it becomes negative.

This leads to **Kadane's Algorithm**.

# 1. Brute Force Approach

## Idea

Try every possible starting position and every possible ending position.

For each subarray, calculate its sum and keep track of the maximum.

## Algorithm

1. Start from every index `i`.
2. Start a subarray from `i`.
3. Extend the subarray one element at a time.
4. Keep a running sum.
5. Update the maximum sum.

## Complexity

- **Best Time:** `O(n²)`
- **Average Time:** `O(n²)`
- **Worst Time:** `O(n²)`
- **Space:** `O(1)`

## Problem With This Approach

There are `O(n²)` possible subarrays, so this approach becomes too slow for large arrays.

# 2. Better Approach

A small improvement is to use **prefix sums**.

Instead of calculating every subarray sum from scratch, we can precompute the cumulative sum up to every index.

Then the sum of any subarray `[i...j]` can be calculated in `O(1)`.

## Idea

Build:

```text
prefix[i] = nums[0] + nums[1] + ... + nums[i]
```

Then:

```text
sum(i, j) = prefix[j] - prefix[i - 1]
```

We still need to consider all possible pairs of starting and ending positions, so there are still `O(n²)` subarrays.

## Complexity

- **Best Time:** `O(n²)`
- **Average Time:** `O(n²)`
- **Worst Time:** `O(n²)`
- **Space:** `O(n)`

## Why Is This Better?

Each subarray sum can now be calculated in `O(1)` instead of repeatedly summing its elements.

However, the number of subarrays is still `O(n²)`, so we need a better observation.

# 3. Optimal Approach — Kadane's Algorithm

## Key Insight

For every position, we only need to know:

> What is the maximum subarray sum ending at the current position?

Suppose the current running sum is negative.

If we add that negative sum to the next number, it can only make the next subarray worse.

For example:

```text
[-5, 4]
```

Carrying `-5` forward gives:

```text
-5 + 4 = -1
```

Starting fresh from `4` gives:

```text
4
```

So whenever:

```text
sum < 0
```

we can safely discard the previous subarray and start a new one.

## Algorithm

For every element:

1. Add the current number to `sum`.
2. Update `max` using the current `sum`.
3. If `sum < 0`, reset `sum` to `0`.
4. Continue to the next element.

The maximum value encountered is the answer.

## Example

For:

```text
[-2, 1, -3, 4, -1, 2, 1, -5, 4]
```

The important part is:

```text
4 → 3 → 5 → 6
```

The maximum sum becomes:

```text
6
```

corresponding to:

```text
[4, -1, 2, 1]
```

## Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n)`
- **Worst Time:** `O(n)`
- **Space:** `O(1)`

# Why Does the Optimal Approach Work?

At every index, there are only two useful choices:

1. Extend the previous subarray.
2. Start a new subarray from the current element.

If the previous running sum is positive, extending it helps:

```text
current + positive_sum
```

If the previous running sum is negative, extending it hurts:

```text
current + negative_sum < current
```

Therefore, a negative running sum should be discarded.

The algorithm effectively keeps:

```text
best subarray sum ending at current index
```

and simultaneously tracks the maximum value seen across the entire array.

This allows us to solve the problem in a single pass.

# Edge Cases

### All Negative Numbers

```text
nums = [-3, -2, -5]

Output: -2
```

The answer must still be negative.

This is why `max` is initialized to:

```text
Integer.MIN_VALUE
```

rather than `0`.

### Single Element

```text
nums = [5]

Output: 5
```

### One Large Positive Element

```text
nums = [-5, -2, 10, -3]

Output: 10
```

The algorithm discards the negative sum before reaching `10`.

### Entire Array Is the Maximum Subarray

```text
nums = [1, 2, 3, 4]

Output: 10
```

The running sum is never negative, so the entire array is selected.

# Key Takeaway

- **Pattern:** Kadane's Algorithm / Dynamic Programming
- **Core Idea:** Maintain the best subarray sum ending at the current position.
- If the running sum becomes negative, discard it and start fresh.
- Track the maximum running sum seen so far.
- **Time:** `O(n)`
- **Space:** `O(1)`

The general lesson is:

> When a previous partial result can only make future results worse, discard it and start from the current position.

# Solution

[View solution.java](./solution.java)
