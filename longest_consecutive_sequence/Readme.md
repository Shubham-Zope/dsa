# Longest Consecutive Sequence

## LeetCode

- **Problem:** [Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence)
- **Difficulty:** Medium
- **Pattern:** Hashing / Sequence Detection

---

## Problem Description

Given an unsorted integer array `nums`, return the length of the longest consecutive elements sequence.

The sequence must consist of consecutive integers.

The algorithm must run in `O(n)` time.

### Example 1

**Input:**

```text
nums = [100,4,200,1,3,2]
```

**Output:**

```text
4
```

### Explanation

The longest consecutive sequence is:

```text
1 → 2 → 3 → 4
```

Therefore, the answer is `4`.

### Example 2

**Input:**

```text
nums = [0,3,7,2,5,8,4,6,0,1]
```

**Output:**

```text
9
```

The longest sequence is:

```text
0 → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
```

---

## Constraints

- `0 <= nums.length <= 10⁵`
- `-10⁹ <= nums[i] <= 10⁹`

---

# Thought Process

The first thing to notice is that the array is **unsorted**.

For example:

```text
[100, 4, 200, 1, 3, 2]
```

The consecutive sequence is:

```text
1 → 2 → 3 → 4
```

The elements are not next to each other in the original array.

So the first question is:

> **How can I quickly check whether a number exists in the array?**

A `HashSet` gives us expected `O(1)` lookup.

We can store:

```text
100, 4, 200, 1, 3, 2
```

in a set and then check:

```text
Does 2 exist?
Does 3 exist?
Does 4 exist?
```

However, there is another important problem.

If we start counting from every number, we might repeatedly scan the same sequence.

For example:

```text
1 → 2 → 3 → 4
```

If we start from `1`, we scan four elements.

If we then start from `2`, we scan:

```text
2 → 3 → 4
```

Again.

If we start from `3`:

```text
3 → 4
```

Again.

This wastes work.

### Key Observation

We should only start a sequence when we find its **first element**.

How do we know whether a number is the beginning?

For a number `num`, check:

```text
num - 1
```

If it does not exist, then `num` must be the beginning of a consecutive sequence.

For example:

```text
1 → 2 → 3 → 4
```

For `1`:

```text
0 does not exist
```

So `1` is the beginning.

For `2`:

```text
1 exists
```

So we skip it.

For `3`:

```text
2 exists
```

Skip.

For `4`:

```text
3 exists
```

Skip.

This is the key optimization that allows the solution to run in `O(n)` expected time.

---

# 1. Brute Force Approach

## Idea

For every number, repeatedly check whether the next consecutive number exists in the array.

For example:

```text
num = 1

check 2
check 3
check 4
...
```

We could search the entire array each time to determine whether the next number exists.

## Problem With This Approach

Searching the array for every next number takes `O(n)`.

If a sequence contains many numbers, we repeatedly scan the entire array.

In the worst case, this can result in:

```text
O(n²)
```

time.

## Complexity

### Time

- **Best:** `O(n)`
- **Average:** `O(n²)`
- **Worst:** `O(n²)`

### Space

```text
O(1)
```

extra space if we search directly in the input array.

---

# 2. Better Approach — Sorting

## Thought Process

Instead of repeatedly searching the array, we can sort it.

For:

```text
[100,4,200,1,3,2]
```

after sorting:

```text
[1,2,3,4,100,200]
```

Now consecutive numbers are next to each other.

We can simply traverse the sorted array and count consecutive values.

```text
1 → 2 → 3 → 4
```

Then:

```text
100
```

breaks the sequence.

## Important Edge Cases

We need to handle duplicates.

For example:

```text
[1,2,2,3]
```

The duplicate `2` should not reset the consecutive sequence.

So:

```text
1 → 2 → 2 → 3
```

still represents:

```text
1 → 2 → 3
```

## Complexity

Sorting takes:

```text
O(n log n)
```

and the final traversal takes `O(n)`.

Therefore:

### Time

- **Best:** `O(n log n)`
- **Average:** `O(n log n)`
- **Worst:** `O(n log n)`

### Space

Depends on the sorting implementation.

For Java's primitive `int[]` sorting, the auxiliary space is typically `O(log n)`.

---

# 3. Optimal Approach — HashSet

## Thought Process

Sorting gives us the correct sequence order, but the problem requires `O(n)` time.

So we need to avoid sorting.

The main operation we need is:

> **Does this number exist?**

A `HashSet` provides expected `O(1)` lookup.

First, put every number into the set.

```text
nums = [100,4,200,1,3,2]

set = {100,4,200,1,3,2}
```

Now we can quickly check whether consecutive numbers exist.

### But there is a trap

Consider:

```text
1, 2, 3, 4
```

If we start a sequence from every number, we repeatedly scan the same sequence.

So we need to identify the **start of a sequence**.

A number is the start if:

```text
num - 1
```

does not exist.

For example:

```text
num = 1
```

Check:

```text
0 → doesn't exist
```

Therefore, `1` is a sequence start.

Then:

```text
1 → 2 → 3 → 4
```

We keep checking:

```text
set.contains(current)
```

until the sequence ends.

For `2`:

```text
1 exists
```

Therefore, `2` is not a sequence start, so we skip it.

This prevents us from scanning the same sequence multiple times.

---

## Algorithm

### Step 1 — Put all numbers into a HashSet

```text
nums → HashSet
```

This removes duplicates and gives expected `O(1)` lookup.

### Step 2 — Iterate through the set

For every number:

```text
if num - 1 does not exist
```

then `num` is the beginning of a sequence.

### Step 3 — Count the sequence

Starting from `num`, repeatedly check:

```text
num
num + 1
num + 2
...
```

until the next number does not exist.

### Step 4 — Update the longest sequence

Keep:

```text
longest = max(longest, currentSequenceLength)
```

---

# Example Walkthrough

Consider:

```text
nums = [100,4,200,1,3,2]
```

HashSet:

```text
{100,4,200,1,3,2}
```

### Start with `100`

Check:

```text
99 exists? No
```

So `100` starts a sequence.

```text
100 → 101
```

`101` doesn't exist.

Sequence length:

```text
1
```

---

### Start with `4`

Check:

```text
3 exists? Yes
```

So `4` is not the beginning.

Skip it.

---

### Start with `200`

Check:

```text
199 exists? No
```

Sequence:

```text
200
```

Length:

```text
1
```

---

### Start with `1`

Check:

```text
0 exists? No
```

So `1` starts a sequence.

Now:

```text
1 → 2 → 3 → 4
```

Then:

```text
5 doesn't exist
```

Length:

```text
4
```

Update:

```text
longest = 4
```

---

### Start with `3`

Check:

```text
2 exists? Yes
```

Skip.

### Start with `2`

Check:

```text
1 exists? Yes
```

Skip.

Final answer:

```text
4
```

---

# Why Does the Optimal Approach Work?

There are two important ideas.

## 1. HashSet Gives Fast Lookup

We need to repeatedly answer:

```text
Does x exist?
```

A HashSet provides expected `O(1)` lookup.

Therefore, checking:

```text
num + 1
```

is efficient.

## 2. Only Start From Sequence Beginnings

This is the most important optimization.

Suppose we have:

```text
1 → 2 → 3 → 4
```

Only `1` should start the sequence.

We identify it because:

```text
1 - 1 = 0
```

does not exist.

Every other number has a predecessor:

```text
2 → predecessor 1 exists
3 → predecessor 2 exists
4 → predecessor 3 exists
```

Therefore, they are skipped.

This means each consecutive sequence is scanned only from its beginning.

### Why does this remain O(n)?

Every number is inserted into the set once.

During the sequence traversal, numbers belonging to a consecutive sequence are effectively processed only when their sequence is started.

Numbers that are not sequence starts are skipped.

Therefore, the total expected work across all sequence scans is linear:

```text
O(n)
```

---

# Complexity

Let `n` be the number of elements.

### Time

Building the HashSet:

```text
O(n)
```

Iterating through the set:

```text
O(n)
```

Sequence expansion:

```text
O(n) total
```

Therefore:

- **Best:** `O(n)`
- **Average:** `O(n)` expected
- **Worst:** `O(n²)` theoretical if HashSet operations degrade badly

In normal interview complexity analysis, this is described as:

```text
Expected Time: O(n)
```

### Space

The HashSet stores the elements:

```text
O(n)
```

Therefore:

- **Best:** `O(n)`
- **Average:** `O(n)`
- **Worst:** `O(n)`

---

# Edge Cases

### 1. Empty Array

```text
nums = []
```

There are no consecutive elements.

Result:

```text
0
```

### 2. Single Element

```text
nums = [10]
```

Result:

```text
1
```

### 3. Duplicate Values

```text
nums = [1,2,2,3]
```

The HashSet removes the duplicate conceptually:

```text
{1,2,3}
```

The longest sequence is:

```text
1 → 2 → 3
```

Result:

```text
3
```

### 4. Negative Numbers

```text
nums = [-3,-2,-1,0,1]
```

The sequence is:

```text
-3 → -2 → -1 → 0 → 1
```

Result:

```text
5
```

### 5. No Consecutive Numbers

```text
nums = [10,20,30]
```

Every number is its own sequence.

Result:

```text
1
```

---

# Key Takeaway

### Pattern

**HashSet + Sequence Detection**

The key pattern is:

```text
Put everything in HashSet
        ↓
Find numbers with no predecessor
        ↓
Those are sequence beginnings
        ↓
Expand forward
        ↓
Track longest sequence
```

The most important line in the solution is:

```java
if (!set.contains(num - 1))
```

This prevents repeatedly scanning the same sequence.

### General Lesson

When solving a consecutive-sequence problem, think:

> **Can I quickly check whether a number exists, and can I identify where each sequence starts?**

If yes, a HashSet can often turn an `O(n log n)` sorting solution into an expected `O(n)` solution.

---

# Solution

See [`solution.java`](./solution.java) for the Java implementation.
