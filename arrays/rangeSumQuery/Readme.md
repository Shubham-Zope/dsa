# Range Sum Query - Immutable

## LeetCode

- **Problem:** [Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/)
- **Difficulty:** Easy
- **Pattern:** Prefix Sum

---

## Problem Description

Given an integer array `nums`, handle multiple queries where each query asks for the sum of elements between indices `left` and `right`, inclusive.

Implement the `NumArray` class:

- `NumArray(int[] nums)` initializes the object with the integer array.
- `sumRange(int left, int right)` returns the sum of elements from `left` to `right`, inclusive.

### Example

**Input:**

```text
nums = [-2, 0, 3, -5, 2, -1]

sumRange(0, 2)
sumRange(2, 5)
sumRange(0, 5)
```

**Output:**

```text
1
-1
-3
```

**Explanation:**

```text
sumRange(0, 2) = -2 + 0 + 3 = 1

sumRange(2, 5) = 3 + (-5) + 2 + (-1) = -1

sumRange(0, 5) = -2 + 0 + 3 + (-5) + 2 + (-1) = -3
```

### Constraints

- `1 <= nums.length <= 10⁴`
- `-10⁵ <= nums[i] <= 10⁵`
- `0 <= left <= right < nums.length`
- At most `10⁴` calls will be made to `sumRange`.

---

# 1. Brute Force Approach

### Idea

For every `sumRange(left, right)` query, simply iterate from `left` to `right` and add all the elements.

### Algorithm

1. Start with `sum = 0`.
2. Iterate from `left` to `right`.
3. Add each element to `sum`.
4. Return `sum`.

### Complexity

**Time Complexity:**

- **Best Case:** `O(1)` — when `left == right`.
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)` — when the query covers almost the entire array.

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(1)`
- **Worst Case:** `O(1)`

### Problem With This Approach

The same elements may be added repeatedly across different queries.

For example, if we have:

```text
sumRange(0, 5)
sumRange(0, 5)
sumRange(0, 5)
```

we calculate the same sum three times.

Since the array does not change, we can preprocess the array once and reuse the calculated sums.

---

# 2. Optimal Approach — Prefix Sum

### Key Insight

We can precompute the cumulative sum up to every index.

For example:

```text
nums   = [2, 4, 1, 5]

prefix = [2, 6, 7, 12]
```

Meaning:

```text
prefix[0] = 2
prefix[1] = 2 + 4 = 6
prefix[2] = 2 + 4 + 1 = 7
prefix[3] = 2 + 4 + 1 + 5 = 12
```

Once these prefix sums are available, any range sum can be calculated using subtraction.

For:

```text
left = 1
right = 3
```

we need:

```text
4 + 1 + 5 = 10
```

Using the prefix array:

```text
prefix[3] - prefix[0]
= 12 - 2
= 10
```

### Algorithm

#### During Initialization

1. Create a `prefix` array of the same size as `nums`.
2. Store `nums[0]` in `prefix[0]`.
3. For every remaining index:
   ```text
   prefix[i] = prefix[i - 1] + nums[i]
   ```

#### During `sumRange`

If `left == 0`:

```text
sum = prefix[right]
```

Otherwise:

```text
sum = prefix[right] - prefix[left - 1]
```

### Why Does Subtraction Work?

Consider:

```text
nums = [2, 4, 1, 5]
```

The prefix sums are:

```text
index:   0   1   2   3
nums:    2   4   1   5
prefix:  2   6   7  12
```

For:

```text
left = 1
right = 3
```

We want:

```text
4 + 1 + 5
```

`prefix[3]` contains:

```text
2 + 4 + 1 + 5
```

`prefix[0]` contains:

```text
2
```

Subtracting:

```text
(2 + 4 + 1 + 5) - 2
= 4 + 1 + 5
```

Therefore:

```text
prefix[right] - prefix[left - 1]
```

gives exactly the required range sum.

### Complexity

**Time Complexity:**

#### Constructor

- **Best Case:** `O(n)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

We need to calculate the prefix sum for every element.

#### `sumRange()`

- **Best Case:** `O(1)`
- **Average Case:** `O(1)`
- **Worst Case:** `O(1)`

Only one subtraction is required.

**Space Complexity:**

- **Best Case:** `O(n)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

The prefix array stores one value for every element.

### Why Is This Better?

The brute-force approach takes `O(n)` for every query.

With prefix sums, we spend `O(n)` once during initialization and then answer every query in `O(1)`.

Therefore, for many queries:

```text
Brute Force:
O(n) per query

Prefix Sum:
O(n) preprocessing
O(1) per query
```

This is a major improvement when the same array is queried many times.

---

# Why Does the Optimal Approach Work?

The prefix array stores the cumulative sum from index `0` to every index.

For any range `[left, right]`:

```text
prefix[right]
```

contains everything from:

```text
0 → right
```

while:

```text
prefix[left - 1]
```

contains everything from:

```text
0 → left - 1
```

Subtracting them removes the unwanted prefix:

```text
0 → left - 1
```

leaving only:

```text
left → right
```

Therefore:

```text
sumRange(left, right)
=
prefix[right] - prefix[left - 1]
```

When `left == 0`, there is nothing before `left`, so the answer is simply:

```text
prefix[right]
```

---

# Edge Cases

### 1. Query Starts at Index 0

```text
sumRange(0, 2)
```

There is no `prefix[-1]`, so return:

```text
prefix[2]
```

### 2. Single Element Range

```text
sumRange(3, 3)
```

The result is simply:

```text
nums[3]
```

### 3. Entire Array

```text
sumRange(0, nums.length - 1)
```

Return:

```text
prefix[nums.length - 1]
```

### 4. Negative Numbers

Prefix sums work normally with negative values.

For example:

```text
nums = [-2, 3, -1]
prefix = [-2, 1, 0]
```

### 5. Repeated Queries

This is where prefix sums provide the biggest advantage.

The prefix array is calculated once, allowing every subsequent query to execute in `O(1)`.

---

# Key Takeaway

### Pattern

**Prefix Sum**

### General Lesson

When an array is **immutable** and there are many range-sum queries, avoid recalculating the same sums.

Instead:

```text
Preprocess → Prefix Sum
```

Then each range query becomes:

```text
prefix[right] - prefix[left - 1]
```

The important pattern to recognize is:

> **"Many range queries on an array that does not change" → Think Prefix Sum.**

The overall progression is:

```text
Brute Force
O(n) per query
       ↓
Prefix Sum
O(n) preprocessing
O(1) per query
```

---

# Solution

The implementation is available in:

[`solution.java`](./solution.java)