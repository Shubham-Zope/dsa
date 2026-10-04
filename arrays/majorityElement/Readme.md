# Majority Element

## LeetCode

- **Problem:** [Majority Element](https://leetcode.com/problems/majority-element/)
- **Difficulty:** Easy
- **Pattern:** Boyer-Moore Voting Algorithm

---

## Problem Description

Given an array `nums` of size `n`, return the majority element.

The majority element is the element that appears **more than `n / 2` times**.

You may assume that the majority element always exists in the array.

### Example

**Input:**

```text
nums = [3, 2, 3]
```

**Output:**

```text
3
```

**Explanation:**

The number `3` appears 2 times out of 3 elements.

Since:

```text
2 > 3 / 2
```

`3` is the majority element.

### Constraints

- `n == nums.length`
- `1 <= n <= 5 * 10⁴`
- `-10⁹ <= nums[i] <= 10⁹`
- The majority element always exists.

---

# Thought Process

Before jumping into the optimal solution, I first need to understand what makes an element a **majority element**.

The majority element appears more than half of the time:

```text
frequency > n / 2
```

So the first idea is naturally:

> "Why don't I count how many times every number occurs?"

That leads to a HashMap solution.

But we can do better.

Since one element appears **more than all other elements combined**, perhaps we don't actually need to know the exact frequency of every number.

This observation leads to the **Boyer-Moore Voting Algorithm**.

---

# 1. Brute Force Approach

### Idea

For every element, count how many times it appears in the array.

If its count is greater than `n / 2`, return that element.

### Algorithm

1. Pick an element.
2. Traverse the entire array and count its occurrences.
3. Check whether its count is greater than `n / 2`.
4. If yes, return that element.
5. Otherwise, continue with the next element.

### Complexity

**Time Complexity:**

- **Best Case:** `O(n)` — majority element is found quickly.
- **Average Case:** `O(n²)`
- **Worst Case:** `O(n²)`

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(1)`
- **Worst Case:** `O(1)`

### Problem With This Approach

We repeatedly scan the entire array to count occurrences.

This can result in `O(n²)` time.

We can avoid repeatedly counting the same elements by storing their frequencies.

---

# 2. Better Approach — HashMap

### Thought Process

Instead of recounting every number, I can keep track of the frequency of every number while traversing the array.

For example:

```text
nums = [3, 2, 3]
```

The frequency map becomes:

```text
3 → 1
2 → 1
3 → 2
```

As soon as a number's frequency becomes greater than `n / 2`, we have found the majority element.

### Algorithm

1. Create a `HashMap` containing:
   ```text
   number → frequency
   ```
2. Traverse the array.
3. Increment the frequency of the current number.
4. If its frequency becomes greater than `n / 2`, return it.

### Complexity

**Time Complexity:**

- **Best Case:** `O(1)` — majority element reaches the required frequency immediately.
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

### Why Is This Better?

We reduced the time complexity from:

```text
O(n²) → O(n)
```

But we are using `O(n)` additional memory.

Can we find the majority element in `O(n)` time while using `O(1)` extra space?

Yes.

This is where the key observation comes in.

---

# 3. Better Approach — Sorting

### Thought Process

Another observation is that if the majority element appears more than `n / 2` times, then after sorting the array, it must occupy the middle position.

For example:

```text
[2, 3, 3, 3, 4]
```

The middle element is:

```text
3
```

And `3` is the majority element.

Therefore, we could sort the array and return:

```text
nums[n / 2]
```

### Algorithm

1. Sort the array.
2. Return the element at index `n / 2`.

### Complexity

**Time Complexity:**

- **Best Case:** `O(n log n)`
- **Average Case:** `O(n log n)`
- **Worst Case:** `O(n log n)`

**Space Complexity:**

Depends on the sorting algorithm and implementation.

- **Best Case:** `O(log n)` auxiliary space for typical comparison sorting.
- **Average Case:** `O(log n)`
- **Worst Case:** `O(log n)` for Java's primitive array sorting implementation.

### Why Is This Better?

It is simpler than maintaining a frequency map and uses less additional space.

However, sorting is unnecessary.

We only need the majority element, not a sorted array.

Can we exploit the fact that the majority element appears **more than half the time**?

Yes.

---

# 4. Optimal Approach — Boyer-Moore Voting Algorithm

## Thought Process

This is the key observation.

Suppose:

```text
nums = [3, 2, 3]
```

The majority element is `3`.

Think of every occurrence of the majority element as getting:

```text
+1
```

and every different element as:

```text
-1
```

Because the majority element appears **more than all other elements combined**, it cannot be completely cancelled out by the other elements.

This means we don't actually need to count every number.

We only need to keep track of:

```text
candidate
count
```

---

## Key Insight

Whenever `count == 0`, the previous candidate has been completely cancelled out.

At that point, we can choose the current number as the new candidate.

For every number:

```text
If count == 0:
    candidate = current number

If current number == candidate:
    count++

Otherwise:
    count--
```

The majority element will survive this cancellation process because it occurs more than `n / 2` times.

---

## Step-by-Step Example

Consider:

```text
nums = [3, 2, 3]
```

Initially:

```text
candidate = null
count = 0
```

### Step 1 — `num = 3`

Since:

```text
count == 0
```

we choose:

```text
candidate = 3
```

Then:

```text
3 == 3
```

so:

```text
count = 1
```

Current state:

```text
candidate = 3
count = 1
```

---

### Step 2 — `num = 2`

`2` is different from the candidate `3`.

Therefore:

```text
count = count - 1
```

Now:

```text
candidate = 3
count = 0
```

The `2` has effectively cancelled one occurrence of `3`.

---

### Step 3 — `num = 3`

Again:

```text
count == 0
```

So:

```text
candidate = 3
```

Then:

```text
count = 1
```

Final result:

```text
candidate = 3
```

---

# Algorithm

1. Initialize:
   ```text
   count = 0
   candidate = null
   ```
2. Traverse every number in the array.
3. If `count == 0`, make the current number the candidate.
4. If the current number equals the candidate, increment `count`.
5. Otherwise, decrement `count`.
6. After processing the entire array, return the candidate.

### Complexity

**Time Complexity:**

- **Best Case:** `O(n)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

Every element is processed exactly once.

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(1)`
- **Worst Case:** `O(1)`

Only two variables are required.

---

# Why Does the Optimal Approach Work?

The key property is:

```text
majority frequency > n / 2
```

Therefore:

```text
majority elements > non-majority elements
```

Imagine pairing one majority element with one non-majority element and cancelling both.

For example:

```text
Majority:      M M M M M
Other:         X X X Y
```

We can cancel:

```text
M X
M X
M Y
```

Some majority elements remain:

```text
M M
```

Because there are more majority elements than all non-majority elements combined, the majority element **cannot be completely cancelled**.

Boyer-Moore performs exactly this cancellation process using the `count`.

When `count` becomes zero, we know that the current candidate has been completely cancelled within the portion we've processed, so we can select a new candidate.

Since the problem guarantees that a majority element exists, the final candidate must be the majority element.

---

# Edge Cases

### 1. Single Element

```text
nums = [5]
```

The only element is automatically the majority element.

Output:

```text
5
```

### 2. Majority Element at the Beginning

```text
nums = [3, 3, 2, 1, 3]
```

The candidate remains `3` through the cancellation process.

### 3. Majority Element at the End

```text
nums = [2, 1, 2, 3, 2]
```

The algorithm does not require the majority element to appear first.

### 4. All Elements Are the Same

```text
nums = [7, 7, 7, 7]
```

The count continuously increases and `7` remains the candidate.

### 5. Negative Numbers

```text
nums = [-1, 2, -1, -1]
```

The algorithm works regardless of whether values are positive or negative.

---

# Key Takeaway

### Pattern

**Boyer-Moore Voting Algorithm**

### General Lesson

When a problem guarantees that one element appears **more than half of the time**, look for a **cancellation / voting** approach.

The progression is:

```text
Brute Force
O(n²) time
O(1) space
       ↓
HashMap
O(n) time
O(n) space
       ↓
Sorting
O(n log n) time
O(log n) auxiliary space
       ↓
Boyer-Moore
O(n) time
O(1) space
```

The most important observation is:

> **The majority element occurs more times than all other elements combined, so cancelling different elements against the majority element can never eliminate the majority completely.**

---

# Solution

The implementation is available in:

[`MajorityElement.java`](./MajorityElement.java)