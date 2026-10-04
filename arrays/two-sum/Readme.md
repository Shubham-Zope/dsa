# Two Sum

## LeetCode

- **Problem:** [Two Sum](https://leetcode.com/problems/two-sum/)
- **Difficulty:** Easy
- **Pattern:** Hashing / HashMap

---

## Problem Description

Given an array of integers `nums` and an integer `target`, return the indices of the two numbers such that they add up to `target`.

You may assume that each input has exactly one solution, and you cannot use the same element twice.

### Example

**Input:**

```text
nums = [2, 7, 11, 15]
target = 9
```

**Output:**

```text
[0, 1]
```

**Explanation:**

`nums[0] + nums[1] = 2 + 7 = 9`.

### Constraints

- `2 <= nums.length <= 10⁴`
- `-10⁹ <= nums[i] <= 10⁹`
- `-10⁹ <= target <= 10⁹`
- Exactly one valid answer exists.
- The same element cannot be used twice.

---

# 1. Brute Force Approach

### Idea

Check every possible pair of elements and return the pair whose sum equals `target`.

### Algorithm

1. Start with the first element.
2. Compare it with every element after it.
3. If their sum equals `target`, return their indices.
4. Continue until a valid pair is found.

### Complexity

**Time Complexity:**

- **Best Case:** `O(1)` — the first two elements form the answer.
- **Average Case:** `O(n²)`
- **Worst Case:** `O(n²)` — the valid pair is found near the end.

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(1)`
- **Worst Case:** `O(1)`

### Problem With This Approach

We repeatedly search for a matching pair, resulting in quadratic time complexity.

We can avoid this repeated searching by storing previously seen numbers.

---

# 2. Better Approach — HashMap

### Key Insight

For every number `num`, the number we need is:

```text
complement = target - num
```

Instead of searching the entire array for this complement, store previously seen numbers in a HashMap.

The map stores:

```text
number -> index
```

This gives us an average `O(1)` lookup.

### Algorithm

1. Create a `HashMap` to store numbers and their indices.
2. Iterate through the array.
3. Calculate:

```text
complement = target - num
```

4. Check whether the complement exists in the HashMap.
5. If it exists, return the stored index and current index.
6. Otherwise, store the current number and its index.
7. Continue until the pair is found.

### Complexity

**Time Complexity:**

- **Best Case:** `O(1)` — the first two elements form the answer.
- **Average Case:** `O(n)` — each HashMap lookup/insertion is `O(1)` on average.
- **Worst Case:** `O(n²)` — in the theoretical worst case, HashMap operations can degrade due to collisions.

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

### Why Is This Better?

The brute-force solution checks pairs in `O(n²)` time.

The HashMap allows us to find the required complement while traversing the array, reducing the **average time complexity to `O(n)`**.

---

# Why Does the Optimal Approach Work?

For every number `num`, there is exactly one value that can complete the target:

```text
complement = target - num
```

By storing previously visited numbers in the HashMap, we can immediately check whether the required complement has already appeared.

We check for the complement **before** inserting the current number. Therefore, the same array element cannot be used twice.

---

# Edge Cases

### Duplicate Values

```text
nums = [3, 3]
target = 6
```

The first `3` is stored. When the second `3` is encountered, the complement is found.

### Negative Numbers

```text
nums = [-3, 4, 3, 90]
target = 0
```

The complement calculation works for negative numbers as well.

### Answer Appears at the Beginning

The algorithm immediately returns after finding the pair, giving a best-case time complexity of `O(1)`.

### Answer Appears Near the End

Most of the array must be processed before finding the answer, resulting in `O(n)` average-case traversal.

---

# Key Takeaway

### Pattern

**HashMap for fast lookup**

### General Lesson

When a problem asks you to find a pair satisfying a condition, try to calculate the value you need.

For Two Sum:

```text
required value = target - current value
```

Instead of repeatedly searching for that value, store previously seen values in a HashMap.

This changes the solution from:

```text
O(n²) → O(n) average
```

while using:

```text
O(n)
```

additional space.

---

# Solution

The implementation is available in:

[`solution.java`](./solution.java)