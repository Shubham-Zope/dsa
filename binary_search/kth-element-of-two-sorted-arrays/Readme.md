# K-th Element of Two Sorted Arrays

## Problem

- **Platform:** [TakeUForward](https://takeuforward.org/practice/dsa/kth-element-of-2-sorted-arrays)
- **Difficulty:** Hard
- **Pattern:** Binary Search on Partition

## Problem Description

Given two sorted arrays `a` and `b` of sizes `m` and `n`, find the **k-th smallest element** in the combined sorted array.

The arrays must be treated as one sorted collection, but we should avoid merging them to achieve better time complexity.

**Note:** `k` is **1-indexed**. For example, `k = 1` means the smallest element, and `k = 3` means the third-smallest element.

### Examples

**Example 1**

```text
Input: a = [2, 3, 6, 7, 9]
       b = [1, 4, 8, 10]
       k = 5

Output: 6
```

Combined sorted array: `[1, 2, 3, 4, 6, 7, 8, 9, 10]`

The fifth element is `6`.

**Example 2**

```text
Input: a = [1, 2, 3, 4, 5]
       b = [6, 7, 8, 9, 10]
       k = 6

Output: 6
```

The sixth element is `6`.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Merge both sorted arrays into a single sorted array, then return the element at index `k - 1`.

### Algorithm

1. Create a new array to store the elements from both arrays.
2. Merge the arrays while preserving sorted order.
3. Return `merged[k - 1]`.

### Complexity Analysis

Let `m` and `n` be the lengths of the two arrays.

- **Time Complexity:** `O(m + n)`
- **Space Complexity:** `O(m + n)`

### Why improve it?

We only need one element, not the entire merged array. We can use binary search to find the correct partition without processing every element.

---

# 2. Optimal Approach — Binary Search on Partition

## Core Idea

Instead of merging the arrays, divide their elements into two partitions:

- The **left partition** contains exactly `k` elements.
- The **right partition** contains all remaining elements.
- Every element in the left partition must be less than or equal to every element in the right partition.

Once these conditions are satisfied, the k-th smallest element is the **largest element in the left partition**.

## Step 1: Binary search on the smaller array

If array `a` is longer than array `b`, swap them by calling the method recursively with the arguments reversed.

This ensures that binary search runs on the smaller array, reducing the search space.

```java
if (a.length > b.length) {
    return kthElement(b, a, k);
}
```

Let:

```java
int m = a.length;
int n = b.length;
```

## Step 2: Calculate the search range

We need exactly `k` elements in the left partition.

Let `cut1` be the number of elements taken from array `a`, and `cut2` be the number taken from array `b`.

Therefore:

```java
int cut2 = k - cut1;
```

The valid search range is:

```java
int low = Math.max(0, k - n);
int high = Math.min(k, m);
```

Why?

- `cut1` cannot be negative, so its minimum is `0`.
- `cut1` cannot exceed the length of `a`, so its maximum is `m`.
- `cut2` cannot exceed `n`, which means `k - cut1 <= n`, or `cut1 >= k - n`.
- We cannot take more than `k` elements from either array into the left partition.

Combining these conditions gives:

```text
low  = max(0, k - n)
high = min(k, m)
```

## Step 3: Choose the partition

```java
int cut1 = low + (high - low) / 2;
int cut2 = k - cut1;
```

For example:

```text
a = [2, 3, 6, 7, 9]
b = [1, 4, 8, 10]
k = 5
```

Suppose `cut1 = 2` and `cut2 = 3`.

```text
a: [2, 3 | 6, 7, 9]
b: [1, 4, 8 | 10]

Left partition:  [2, 3, 1, 4, 8]  -> 5 elements
Right partition: [6, 7, 9, 10]
```

The left partition has exactly `k = 5` elements, but this partition is invalid because `8 > 6`. We must adjust the partition until all left-side elements are less than or equal to all right-side elements.

## Step 4: Find the four boundary elements

We only need the elements immediately before and after each partition.

```java
int left1 = cut1 == 0
        ? Integer.MIN_VALUE
        : a[cut1 - 1];

int right1 = cut1 == m
        ? Integer.MAX_VALUE
        : a[cut1];

int left2 = cut2 == 0
        ? Integer.MIN_VALUE
        : b[cut2 - 1];

int right2 = cut2 == n
        ? Integer.MAX_VALUE
        : b[cut2];
```

- `left1`: last element in the left partition of `a`.
- `right1`: first element in the right partition of `a`.
- `left2`: last element in the left partition of `b`.
- `right2`: first element in the right partition of `b`.

The sentinel values handle partitions at the beginning or end of an array without causing index errors.

## Step 5: Check whether the partition is valid

The partition is valid if:

```java
left1 <= right2 && left2 <= right1
```

Because both arrays are sorted, these two comparisons ensure that every element in the left partition is less than or equal to every element in the right partition.

When the partition is valid, return:

```java
return Math.max(left1, left2);
```

Why?

The left partition contains exactly `k` elements. Its largest element is therefore the k-th smallest element in the combined arrays.

## Step 6: Adjust binary search

If the partition is invalid, move the binary search boundaries.

**Case 1: `left1 > right2`**

Too many elements have been taken from array `a` into the left partition. Move the partition left.

```java
high = cut1 - 1;
```

**Case 2: `left2 > right1`**

Too few elements have been taken from array `a` into the left partition. Move the partition right.

```java
low = cut1 + 1;
```

Continue until a valid partition is found.

---

# Why Does the Optimal Approach Work?

The left partition always contains exactly `k` elements.

When the partition is valid:

```text
left1 <= right2
left2 <= right1
```

all the elements in the left partition are less than or equal to those in the right partition.

Therefore, the largest element in the left partition is exactly the k-th smallest element.

Binary search efficiently finds this partition without merging or traversing all the elements.

---

# Complexity Analysis

Let `m` be the length of the smaller array and `n` the length of the larger array.

- **Time Complexity:** `O(log(min(m, n) + 1))`
- **Auxiliary Space Complexity:** `O(1)`

The algorithm uses binary search on the smaller array and only a constant number of variables.

---

# Edge Cases

1. **`k = 1`:** Return the smallest element across both arrays.
2. **`k = m + n`:** Return the largest element across both arrays.
3. **One array is empty:** The result comes from the other array at index `k - 1`.
4. **Different array lengths:** Binary search on the smaller array.
5. **Partition at an array boundary:** Sentinel values avoid out-of-bounds access.
6. **Duplicate elements:** The partition conditions still work correctly.

---

# Key Takeaway

The main idea is **binary search on a partition**, not on individual array values.

Remember:

1. Put exactly `k` elements in the left partition.
2. Use `cut2 = k - cut1` to determine the second partition.
3. Find a valid partition using `left1 <= right2` and `left2 <= right1`.
4. Return `Math.max(left1, left2)`.

This finds the k-th element efficiently without creating a merged array.

---

# Solution

[View solution.java](./solution.java)