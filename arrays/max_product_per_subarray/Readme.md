# Maximum Product Subarray

## LeetCode
- **Problem:** [152. Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)
- **Difficulty:** Medium
- **Pattern:** Dynamic Programming / Array

## Problem Description

Given an integer array `nums`, find a subarray that has the largest product and return that product.

A subarray is a contiguous, non-empty sequence of elements from the array.

### Example 1

**Input:**
```text
nums = [2, 3, -2, 4]
```

**Output:**
```text
6
```

**Explanation:**

The subarray `[2, 3]` has the maximum product: `2 × 3 = 6`.

### Example 2

**Input:**
```text
nums = [-2, 0, -1]
```

**Output:**
```text
0
```

**Explanation:**

The subarray `[-2, 0, -1]` has product `0`. The maximum product of any non-empty contiguous subarray is `0`.

### Constraints
- `1 <= nums.length <= 2 * 10^4`
- `-10 <= nums[i] <= 10`

# Thought Process

The challenge is that **negative numbers can change a minimum product into a maximum product**.

For example, multiplying a large negative product by another negative number can produce a large positive product.

Therefore, tracking only the maximum product ending at the current index is not sufficient. We must track both:

- `maxResult`: The maximum product of a subarray ending at the current index.
- `minResult`: The minimum product of a subarray ending at the current index.

At each element, we consider three possibilities:
1. Start a new subarray with the current element.
2. Extend the previous maximum-product subarray.
3. Extend the previous minimum-product subarray.

We calculate the new maximum and minimum before updating either variable.

# 1. Brute Force Approach

## Idea

Generate every possible non-empty contiguous subarray and calculate its product.

## Algorithm

1. Initialize `answer` to the smallest possible integer.
2. Iterate through every starting index `i`.
3. Initialize a running product to `1`.
4. Iterate through every ending index `j >= i`.
5. Multiply the running product by `nums[j]`.
6. Update `answer` with the maximum product found.
7. Return `answer`.

## Complexity Analysis

- **Best Time:** \(O(n^2)\)
- **Average Time:** \(O(n^2)\)
- **Worst Time:** \(O(n^2)\)
- **Auxiliary Space:** \(O(1)\)

## Problem with This Approach

There are \(O(n^2)\) possible contiguous subarrays. Even though the running product avoids recalculating each subarray's product from scratch, checking all subarrays still takes quadratic time.

# 2. Optimal Approach — Track Maximum and Minimum Products

## Key Insight

At every index, the maximum and minimum products ending at that index are enough to calculate the next state.

For the current value `x`:

\[
\text{newMax}=\max(x,\text{maxResult}\times x,\text{minResult}\times x)
\]

\[
\text{newMin}=\min(x,\text{maxResult}\times x,\text{minResult}\times x)
\]

Why track the minimum? Because if `x` is negative, multiplying it by the previous minimum can produce the new maximum.

### Algorithm

1. Initialize `maxResult`, `minResult`, and `answer` to `nums[0]`.
2. Iterate through the array starting at index `1`.
3. Calculate `newMax` using the current number, the previous maximum product, and the previous minimum product.
4. Calculate `newMin` using the same three candidates.
5. Update `maxResult` and `minResult` only after both new values have been calculated.
6. Update the global `answer` using `maxResult`.
7. Return `answer`.

## Walkthrough

Consider:

```text
nums = [2, 3, -2, 4]
```

| Current number | Maximum ending here | Minimum ending here | Global answer |
|---:|---:|---:|---:|
| 2 | 2 | 2 | 2 |
| 3 | 6 | 3 | 6 |
| -2 | -2 | -12 | 6 |
| 4 | 4 | -48 | 6 |

At `-2`, the previous maximum `6` becomes `-12`, while the previous minimum `3` becomes `-
