# Reverse Pairs

## Problem
- **Link:** [Reverse Pairs — Take U Forward](https://takeuforward.org/practice/dsa/reverse-pairs)
- **Difficulty:** Hard
- **Pattern:** Merge Sort / Divide and Conquer

## Problem Description

Given an integer array `nums`, count the number of reverse pairs `(i, j)` such that:

\[
i < j \quad \text{and} \quad nums[i] > 2 \times nums[j]
\]

A reverse pair is different from a regular inversion because the left element must be **strictly greater than twice** the right element.

### Example 1

**Input:**
```text
nums = [1, 3, 2, 3, 1]
```

**Output:**
```text
2
```

**Explanation:**

The reverse pairs are:
- `(3, 1)` at indices `(1, 4)`, because `3 > 2 × 1`.
- `(3, 1)` at indices `(3, 4)`, because `3 > 2 × 1`.

### Example 2

**Input:**
```text
nums = [2, 4, 3, 5, 1]
```

**Output:**
```text
3
```

**Explanation:**

The reverse pairs are `(4, 1)`, `(3, 1)`, and `(5, 1)`.

# Thought Process

The first approach is to check every possible pair of indices and count the pairs satisfying the condition. However, this takes quadratic time.

To improve it, we can use **merge sort**. Merge sort divides the array into smaller parts, sorts them, and combines the results. While combining two sorted halves, we can count reverse pairs efficiently because both halves are already sorted.

The key is to **count reverse pairs before merging the two halves**. The condition `nums[i] > 2L * nums[j]` is not the same as the comparison used to merge elements in ascending order.

# 1. Brute Force Approach

## Idea

Check every pair `(i, j)` where `i < j`. If `nums[i] > 2 × nums[j]`, increment the count.

## Algorithm

1. Initialize `count = 0`.
2. Iterate through each index `i`.
3. For every `i`, iterate through all indices `j > i`.
4. If `nums[i] > 2L * nums[j]`, increment the count.
5. Return the final
