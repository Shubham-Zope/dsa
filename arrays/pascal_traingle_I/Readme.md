# Pascal's Triangle

## LeetCode
- **Problem:** [118. Pascal's Triangle](https://leetcode.com/problems/pascals-triangle/)
- **Difficulty:** Easy
- **Pattern:** Arrays / Dynamic Programming

## Problem Description

Given an integer `numRows`, return the first `numRows` of Pascal's Triangle.

In Pascal's Triangle:
- The first and last elements of every row are `1`.
- Every other element is the sum of the two elements directly above it in the previous row.

### Example

**Input:**
```text
numRows = 5
```

**Output:**
```text
[[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
```

**Explanation:**

Each row starts and ends with `1`. The elements between them are calculated by adding the two adjacent elements from the previous row.

### Constraints
- `1 <= numRows <= 30`

# Thought Process

The key observation is that **each row can be constructed using the row immediately before it**.

For example, given the previous row `[1, 2, 1]`, the next row is `[1, 3, 3, 1]`:

- Start with `1`.
- Add adjacent elements: `1 + 2 = 3` and `2 + 1 = 3`.
- End with `1`.

This means we don't need to calculate factorials or use a separate formula for each element. We can build the triangle row by row.

# 1. Brute Force Approach

### Idea

Calculate each element using the binomial coefficient formula:

\[
C(i,j)=\frac{i!}{j!(i-j)!}
\]

Here, `i` represents the row index and `j` represents the element's index within that row.

### Algorithm
1. Iterate through every row.
2. For each element, calculate its binomial coefficient.
3. Add the calculated values to the corresponding row.

### Complexity Analysis

- **Time:** Up to \(O(n^3)\) with straightforward factorial calculations repeated for each element.
- **Auxiliary Space:** \(O(1)\), excluding the output, if factorials are calculated without storing additional collections.

### Problem with This Approach

Repeated factorial calculations are unnecessary. Each element can be obtained directly from two elements in the previous row.

# 2. Optimal Approach — Build Rows Using the Previous Row

### Key Insight

Every element inside a row is the sum of two adjacent elements from the previous row:

\[
triangle[i][j] =
triangle[i-1][j-1] + triangle[i-1][j]
\]

This applies only to interior elements. The first and last elements are always `1`.

### Algorithm
1. Create an empty list to store the triangle.
2. Iterate from row `0` to `numRows - 1`.
3. Create a new row and add `1` as its first element.
4. For every interior position, add the sum of the two adjacent elements from the previous row.
5. If the row has more than one element, append `1` at the end.
6. Add the completed row to the triangle.
7. Return the triangle.

### Complexity Analysis

Let `n` be `numRows`.

- **Time Complexity:** \(O(n^2)\), because the triangle contains \(n(n+1)/2\) elements.
- **Auxiliary Space:** \(O(1)\), excluding the output, since only a few variables are used beyond the triangle being constructed.
- **Output Space:** \(O(n^2)\), to store all elements of the triangle.

# Why Does the Optimal Approach Work?

Each row follows a fixed rule: its boundary elements are `1`, while every interior element is the sum of two adjacent elements in the preceding row.

Since the previous row has already been constructed when we generate the current row, every required value is immediately available. Therefore, we can build the complete triangle without repeated factorial calculations or unnecessary data structures.

The \(O(n^2)\) time complexity is optimal because the output itself contains \(O(n^2)\) elements, all of which must be generated.

# Edge Cases

- **`numRows = 1`:** Returns `[[1]]`.
- **`numRows = 2`:** Returns `[[1], [1, 1]]`.
- **Larger values:** Every new row is generated using the preceding row.

# Key Takeaway

When a problem asks you to construct a sequence where each new element depends on previously calculated elements, look for a recurrence relation.

For Pascal's Triangle, building each row from the previous row is simpler and more efficient than calculating every element independently.

# Solution

[View solution.java](./solution.java)
