# Top K Frequent Elements

## LeetCode

- **Problem:** [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/)
- **Difficulty:** Medium
- **Pattern:** Hashing + Bucket Sort

---

## Problem Description

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements.

The answer may be returned in any order.

### Example

**Input:**

```text
nums = [1,2,1,2,1,2,3,1,3,2]
k = 2
```

**Output:**

```text
[1,2]
```

### Explanation

The frequencies are:

```text
1 → 4
2 → 4
3 → 2
```

The two most frequent elements are `1` and `2`.

---

## Constraints

- `1 <= nums.length <= 10⁵`
- `-10⁴ <= nums[i] <= 10⁴`
- `k` is in the range `[1, number of unique elements]`
- The answer is guaranteed to be unique.

---

# Thought Process

The first thing to notice is that the problem is not asking us to find the largest values.

It is asking for the elements with the **highest frequencies**.

So the first step should naturally be:

> **Count how many times each number appears.**

For example:

```text
nums = [1,2,1,2,1,2,3,1,3,2]

Frequency:
1 → 4
2 → 4
3 → 2
```

Now the problem becomes:

> **How do we efficiently find the `k` largest frequencies?**

There are several ways to approach this.

### Approach 1 — Brute Force

Repeatedly find the element with the highest frequency.

This requires repeatedly scanning the frequency information.

### Approach 2 — Sorting

Store the frequency of every number and sort the numbers based on their frequency.

This improves the solution, but sorting costs `O(n log n)`.

### Approach 3 — Bucket Sort

There is an important observation:

> A number can appear at most `n` times, where `n = nums.length`.

Therefore, we can create buckets indexed by frequency.

For example:

```text
Frequency → Numbers

0 → []
1 → [3]
2 → []
3 → []
4 → [1, 2]
```

Then we simply iterate from the highest frequency to the lowest frequency.

This removes the need for sorting.

---

# 1. Brute Force Approach

## Idea

First count the frequency of every number.

Then repeatedly:

1. Find the number with the highest frequency that has not already been selected.
2. Add it to the result.
3. Repeat until we have `k` elements.

For example:

```text
1 → 4
2 → 4
3 → 2
```

Find `1`, then `2`.

## Problem With This Approach

To find the next most frequent element, we may need to scan all unique elements again.

If there are many unique elements and `k` is large, this repeated scanning becomes expensive.

## Complexity

Let:

- `n` = number of elements
- `u` = number of unique elements

### Time

- Frequency counting: `O(n)`
- Finding the next maximum repeatedly: up to `O(uk)`

Worst case:

```text
O(n + uk)
```

Since `u <= n`, worst case can become:

```text
O(n²)
```

### Space

```text
O(u)
```

for storing frequencies.

---

# 2. Better Approach — HashMap + Sorting

## Thought Process

After building the frequency map:

```text
1 → 4
2 → 4
3 → 2
```

we could sort the numbers according to their frequency.

For example:

```text
[3, 1, 2]

frequencies:
3 → 2
1 → 4
2 → 4

after sorting:
[1, 2, 3]
```

Then take the first `k` elements.

This is much better than repeatedly searching for the maximum.

## Algorithm

1. Create a HashMap storing:

```text
number → frequency
```

2. Convert the unique numbers into a list.
3. Sort the list according to frequency in descending order.
4. Return the first `k` elements.

## Complexity

### Time

- Frequency counting: `O(n)`
- Sorting unique elements: `O(u log u)`

Overall:

```text
O(n + u log u)
```

Since `u <= n`:

```text
O(n log n)
```

### Space

```text
O(u)
```

for the frequency map and list.

---

# 3. Optimal Approach — HashMap + Bucket Sort

## Thought Process

The sorting approach still has one unnecessary operation:

```text
Sorting → O(u log u)
```

Can we avoid sorting?

Look at the possible frequencies.

If:

```text
nums.length = 10
```

then a number can appear anywhere from:

```text
1 → 10
```

times.

So instead of sorting numbers by frequency, we can create an array where the **index represents the frequency**.

```text
bucket[frequency] = numbers having that frequency
```

For example:

```text
Frequency
   ↓
0 → []
1 → []
2 → [3]
3 → []
4 → [1, 2]
```

Now we don't need to sort.

We simply start from the largest possible frequency:

```text
n → n-1 → n-2 → ... → 1
```

and collect elements until we have `k`.

---

## Algorithm

### Step 1 — Count frequencies

Use a HashMap:

```text
number → frequency
```

For:

```text
[1,2,1,2,1,2,3,1,3,2]
```

we get:

```text
1 → 4
2 → 4
3 → 2
```

### Step 2 — Create buckets

Create:

```java
List<Integer>[] list = new List[n + 1];
```

Why `n + 1`?

Because the maximum possible frequency is `n`.

For example, if:

```text
nums = [5,5,5,5]
```

then:

```text
5 → frequency 4
```

So we need:

```text
bucket[4]
```

### Step 3 — Put numbers into their frequency bucket

For every entry:

```text
1 → 4
2 → 4
3 → 2
```

we place:

```text
bucket[4] → [1, 2]
bucket[2] → [3]
```

### Step 4 — Traverse from highest frequency

Start from:

```text
bucket[n]
```

and move downward:

```text
n → n-1 → ... → 1
```

Whenever we find numbers in a bucket, add them to the result.

Stop once we have `k` elements.

---

## Example

Given:

```text
nums = [1,2,1,2,1,2,3,1,3,2]
k = 2
```

Frequency map:

```text
1 → 4
2 → 4
3 → 2
```

Buckets:

```text
frequency 10 → []
frequency 9  → []
...
frequency 4  → [1, 2]
frequency 3  → []
frequency 2  → [3]
frequency 1  → []
```

Start from frequency `10` and move downward.

When we reach frequency `4`:

```text
[1, 2]
```

We add both.

We now have `k = 2` elements, so we stop.

Result:

```text
[1, 2]
```

---

## Complexity

Let:

- `n` = number of elements
- `u` = number of unique elements

### Time

Frequency counting:

```text
O(n)
```

Creating buckets:

```text
O(n)
```

Adding unique numbers to buckets:

```text
O(u)
```

Traversing buckets:

```text
O(n)
```

Overall:

```text
O(n + u)
```

Since `u <= n`:

```text
O(n)
```

### Space

Frequency map:

```text
O(u)
```

Buckets:

```text
O(n)
```

Result:

```text
O(k)
```

Overall:

```text
O(n)
```

---

# Why Does the Optimal Approach Work?

The key observation is:

> **Frequency has a bounded range.**

A number cannot appear more than `n` times.

Therefore, instead of sorting the frequencies, we can use the frequency itself as an array index.

For example:

```text
number → frequency

1 → 4
2 → 4
3 → 2
```

becomes:

```text
frequency → numbers

4 → [1, 2]
2 → [3]
```

Now the bucket index tells us exactly how frequent the numbers are.

If we traverse the buckets from:

```text
n → 1
```

we are automatically processing numbers from the highest frequency to the lowest frequency.

Therefore, the first `k` numbers we encounter are the `k` most frequent elements.

No sorting is required.

---

# Why Is Bucket Sort Better Than Sorting?

### Sorting Approach

```text
Count frequencies
       ↓
Sort by frequency
       ↓
Take k elements

O(n log n)
```

### Bucket Approach

```text
Count frequencies
       ↓
Place into frequency buckets
       ↓
Traverse from highest frequency
       ↓
Take k elements

O(n)
```

The important pattern is:

> **When the value you need to sort by has a known/bounded range, bucket sort can replace comparison-based sorting.**

---

# Edge Cases

### 1. One Unique Element

```text
nums = [1,1,1,1]
k = 1
```

Frequency:

```text
1 → 4
```

Result:

```text
[1]
```

### 2. Every Element Has the Same Frequency

```text
nums = [1,1,2,2,3,3]
k = 2
```

Frequencies:

```text
1 → 2
2 → 2
3 → 2
```

Any two valid elements can be returned according to the problem's valid-answer conditions.

### 3. `k` Equals Number of Unique Elements

If:

```text
k = 3
```

and there are three unique numbers, all three must be returned.

---

# Key Takeaway

### Pattern

**HashMap + Bucket Sort**

The main thought process is:

```text
Need top K by frequency
        ↓
Count frequencies
        ↓
Can we avoid sorting?
        ↓
Frequency is bounded by n
        ↓
Use frequency as bucket index
        ↓
Traverse buckets from high → low
        ↓
Take first k elements
```

### General Lesson

When you see:

> **"Find the top K elements based on frequency/count"**

think:

```text
HashMap → Frequency
+
Heap / Sorting / Bucket Sort
```

Which one to choose depends on the constraints.

For this problem, because frequency is bounded by `n`, **Bucket Sort gives an `O(n)` solution**.

---

# Solution

See [`solution.java`](./solution.java) for the Java implementation.