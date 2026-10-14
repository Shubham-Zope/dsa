# Longest Consecutive Sequence

## LeetCode
- **Problem:** [128. Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/)
- **Difficulty:** Medium
- **Pattern:** Arrays / HashSet

## Problem Description

Given an unsorted integer array `nums`, return the length of the longest consecutive elements sequence.

The algorithm must run in \(O(n)\) time.

### Example 1

**Input:**
```text
nums = [100, 4, 200, 1, 3, 2]
```

**Output:**
```text
4
```

**Explanation:**

The longest consecutive sequence is `[1, 2, 3, 4]`, so the answer is `4`.

### Example 2

**Input:**
```text
nums = [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]
```

**Output:**
```text
9
```

**Explanation:**

The longest consecutive sequence is `[0, 1, 2, 3, 4, 5, 6, 7, 8]`, so the answer is `9`.

### Constraints
- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

# Thought Process

The array is unsorted, so consecutive numbers may appear at any positions. Sorting would make consecutive elements easier to identify, but it takes \(O(n \log n)\) time and does not meet the required \(O(n)\) time complexity.

A `HashSet` provides average \(O(1)\) membership checks. We can use it to determine whether a number exists without sorting the array.

Your solution improves on the standard HashSet approach by first checking the range of values:

- If the range is small, use a boolean array to represent which numbers exist.
- If the range is large, use a `HashSet` to avoid allocating an unnecessarily large array.

# 1. Brute Force Approach

## Idea

For every number, repeatedly check whether the next consecutive number exists in the original array.

## Algorithm

1. Iterate through each number as a potential sequence starting point.
2. Set the current sequence length to `1`.
3. Search the array for the next number, `current + 1`.
4. If found, increment the current number and sequence length.
5. Repeat until the next number is missing.
6. Track and return the maximum sequence length.

## Complexity Analysis

- **Best Time:** \(O(n)\) for cases where each number immediately fails to extend a sequence.
- **Average Time:** Can reach \(O(n^2)\).
- **Worst Time:** \(O(n^2)\), because searching the array for each consecutive number can require scanning the entire array repeatedly.
- **Auxiliary Space:** \(O(1)\).

## Problem with This Approach

Repeated linear searches make the solution inefficient for large arrays.

# 2. Better Approach — Sorting

## Idea

Sort the array so consecutive values appear next to each other. Then scan the sorted array and count consecutive numbers.

## Algorithm

1. Sort the array.
2. Skip duplicate values.
3. If the current number is one greater than the previous distinct number, extend the sequence.
4. Otherwise, reset the current sequence length.
5. Return the maximum sequence length.

## Complexity Analysis

- **Best Time:** \(O(n \log n)\) for standard comparison sorting.
- **Average Time:** \(O(n \log n)\).
- **Worst Time:** \(O(n \log n)\) for a guaranteed \(O(n \log n)\) sorting algorithm.
- **Auxiliary Space:** Depends on the sorting implementation.

## Problem with This Approach

Sorting improves on brute force, but its \(O(n \log n)\) time complexity does not satisfy the problem's required \(O(n)\) time.

# 3. Optimal Approach — Boolean Array or HashSet

Your solution chooses between two approaches based on the range of input values.

## Approach A: Boolean Array for a Small Range

### Key Insight

If the difference between the maximum and minimum values is small, we can represent every possible value using a boolean array.

The index for a number is:

```java
b[num - min] = true;
```

Subtracting `min` shifts the smallest value to index `0`, allowing negative numbers to be represented as well.

### Algorithm

1. Find the minimum and maximum values in the array.
2. Calculate the range using `(long) max - min`.
3. If the range is at most `100000`, create a boolean array of size `max - min + 1`.
4. Mark each number that appears in the input.
5. Scan the boolean array:
   - If the value is `true`, extend the current consecutive sequence.
   - If the value is `false`, update the longest sequence and reset the current count.
6. Check the final count after the loop, because a sequence may end at the last array index.

### Complexity Analysis

Let \(R = \text{max} - \text{min} + 1\).

- **Time:** \(O(n + R)\).
- **Auxiliary Space:** \(O(R)\).

Since this branch limits the range, the boolean array is practical. However, the complexity is not strictly \(O(n)\) for arbitrary ranges unless the range is bounded relative to the input size.

## Approach B: HashSet for a Large Range

### Key Insight

Store all distinct numbers in a `HashSet`. A sequence should only be counted from its starting number, meaning a number whose predecessor is absent.

For example, with `[1, 2, 3, 4]`:

- `1` is a starting point because `0` is absent.
- `2`, `3`, and `4` are not starting points because their predecessors exist.

This avoids repeatedly counting the same sequence from every element.

### Algorithm

1. Insert all array elements into a `HashSet`.
2. Iterate over the distinct numbers in the set.
3. For each number, check whether its predecessor exists.
4. If its predecessor exists, skip it because it belongs to a sequence that starts earlier.
5. Otherwise, count consecutive numbers by checking `curr + 1`.
6. Update the longest sequence length.
7. Return the maximum length.

### Integer Boundary Handling

Your implementation checks `Integer.MIN_VALUE` before calculating `num - 1`, and `Integer.MAX_VALUE` before calculating `curr + 1`.

These checks prevent integer overflow at the boundaries of Java's `int` range.

### Complexity Analysis

Let \(n\) be the length of the input array.

- **Best Time:** \(O(n)\) expected.
- **Average Time:** \(O(n)\) expected.
- **Worst Time:** Can degrade to \(O(n^2)\) under pathological hash collisions.
- **Auxiliary Space:** \(O(n)\).

Although the inner `while` loop appears nested inside the outer loop, each consecutive sequence is traversed only from its starting number. Therefore, under expected constant-time HashSet operations, the total work is linear.

# Why Does the Optimal Approach Work?

The boolean array directly represents the presence or absence of every number within a bounded range. Consecutive `true` entries correspond to consecutive integers, so a single scan finds the longest sequence.

For larger ranges, the HashSet lets us check membership efficiently without allocating space for every possible integer between the minimum and maximum values.

The predecessor check is the key to avoiding redundant work: each sequence is traversed only once, from its first element.

# Edge Cases

- **Empty array:** Returns `0`.
- **Single element:** Returns `1`.
- **Duplicates:** Duplicates do not increase the sequence length.
- **Negative numbers:** Supported by both approaches.
- **Large value range:** Uses the HashSet instead of allocating a huge boolean array.
- **Integer boundaries:** The HashSet approach avoids overflow when checking predecessors and successors.

# Key Takeaway

For consecutive-sequence problems, a HashSet allows fast membership checks, while checking for the absence of a predecessor ensures that each sequence is counted only once.

Your additional boolean-array branch is a useful optimization when the value range is small. The HashSet branch handles large or widely spaced values without allocating memory proportional to that range.

# Solution

[View solution.java](./solution.java)
