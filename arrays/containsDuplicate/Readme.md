# Contains Duplicate

## LeetCode

- **Problem:** [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/description/)
- **Difficulty:** Easy
- **Pattern:** Hashing / HashSet

---

## Problem Description

Given an integer array `nums`, return `true` if any value appears at least twice in the array.

Return `false` if every element appears only once.

### Example

**Input:**

```text
nums = [1, 2, 3, 4, 1]
```

**Output:**

```text
true
```

**Explanation:**

The value `1` appears more than once in the array.

### Constraints

- `1 <= nums.length <= 10⁵`
- `-10⁹ <= nums[i] <= 10⁹`

---

# 1. Brute Force Approach

### Idea

Compare every element with every other element to check whether any two elements are equal.

### Algorithm

1. Start with the first element.
2. Compare it with every element after it.
3. If any two elements are equal, return `true`.
4. Continue checking all pairs.
5. If no duplicate is found, return `false`.

### Complexity

**Time Complexity:**

- **Best Case:** `O(1)` — the first two elements are duplicates.
- **Average Case:** `O(n²)`
- **Worst Case:** `O(n²)` — no duplicates exist or the duplicate is found near the end.

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(1)`
- **Worst Case:** `O(1)`

### Problem With This Approach

For every element, we may need to compare it with many other elements.

This results in `O(n²)` time complexity, which becomes inefficient for large arrays.

We need a way to remember which values we have already seen.

---

# 2. Optimal Approach — HashSet

### Key Insight

We only need to know whether we have already encountered a particular number.

A `HashSet` is ideal because it stores unique values and provides **average `O(1)` lookup and insertion**.

### Idea

Traverse the array once.

For every number:

- If it already exists in the `HashSet`, a duplicate has been found.
- Otherwise, add it to the `HashSet`.

### Algorithm

1. Create an empty `HashSet`.
2. Iterate through every number in `nums`.
3. Check whether the number already exists in the set.
4. If it exists, return `true`.
5. Otherwise, add the number to the set.
6. If the loop finishes without finding a duplicate, return `false`.

### Example

For:

```text
nums = [1, 2, 3, 4, 1]
```

The set changes as follows:

```text
1 → {1}
2 → {1, 2}
3 → {1, 2, 3}
4 → {1, 2, 3, 4}
1 → already exists
```

Therefore, return:

```text
true
```

### Complexity

**Time Complexity:**

- **Best Case:** `O(1)` — the first duplicate is found immediately.
- **Average Case:** `O(n)` — each lookup and insertion takes `O(1)` on average.
- **Worst Case:** `O(n²)` — theoretical worst case if HashSet operations degrade due to hash collisions.

**Space Complexity:**

- **Best Case:** `O(1)` — duplicate is found immediately.
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)` — all elements are unique and must be stored.

### Why Is This Better?

The brute-force approach compares pairs of elements, resulting in `O(n²)` time.

The HashSet allows us to detect duplicates while making a single pass through the array.

Therefore, the average complexity improves from:

```text
O(n²) → O(n)
```

at the cost of `O(n)` additional space.

---

# Why Does the Optimal Approach Work?

A duplicate exists if and only if we encounter a value that we have already seen.

The `HashSet` keeps track of every value encountered so far.

For each number:

```text
If number is already in set → duplicate exists
Otherwise → add number to set
```

Therefore, when `set.contains(num)` returns `true`, we can immediately conclude that the array contains a duplicate.

If the entire array is processed without finding an existing value, then every element is unique.

---

# Edge Cases

### 1. Array With No Duplicates

```text
nums = [1, 2, 3, 4]
```

Every value is unique, so return `false`.

### 2. Duplicate At The Beginning

```text
nums = [1, 1, 2, 3]
```

The duplicate is detected immediately.

### 3. Duplicate At The End

```text
nums = [1, 2, 3, 4, 1]
```

Most of the array is processed before detecting the duplicate.

### 4. Negative Numbers

```text
nums = [-1, -2, -3, -1]
```

The same HashSet approach works with negative values.

### 5. Single Element

```text
nums = [1]
```

There cannot be a duplicate, so return `false`.

---

# Key Takeaway

### Pattern

**HashSet for detecting duplicates**

### General Lesson

When you need to determine whether an element has appeared before, a `HashSet` is often the right data structure.

Instead of repeatedly comparing elements:

```text
O(n²)
```

track previously seen values:

```text
O(n) average time
O(n) space
```

The important pattern to recognize is:

> **"Have I seen this value before?" → Think HashSet.**

---

# Solution

The implementation is available in:

[`solution.java`](./solution.java)