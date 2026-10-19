# Median of Two Sorted Arrays

## LeetCode

- **Problem:** [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/)
- **Difficulty:** Hard
- **Pattern:** Binary Search on Partition

## Problem Description

Given two sorted arrays, `nums1` and `nums2`, find the median of the combined sorted array.

The solution must run in **O(log(m + n))** time or better, where `m` and `n` are the lengths of the two arrays.

The median is:
- For an odd number of elements, the middle element.
- For an even number of elements, the average of the two middle elements.

### Examples

**Example 1**

```text
Input: nums1 = [1,3], nums2 = [2]
Output: 2.0
```

Combined array: `[1,2,3]`

The middle element is `2`.

**Example 2**

```text
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.5
```

Combined array: `[1,2,3,4]`

The median is `(2 + 3) / 2 = 2.5`.

**Example 3**

```text
Input: nums1 = [0,0], nums2 = [0,0]
Output: 0.0
```

### Constraints

- `0 <= nums1.length, nums2.length`
- `1 <= nums1.length + nums2.length`
- Both arrays are sorted in non-decreasing order.
- The arrays may have different lengths.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Merge both sorted arrays into a single sorted array. Then find the middle element or the average of the two middle elements.

### Algorithm

1. Create a new array containing all elements from both input arrays.
2. Merge the arrays while maintaining sorted order.
3. Calculate the middle index.
4. If the total length is odd, return the middle element.
5. Otherwise, return the average of the two middle elements.

### Complexity

- **Time Complexity:** `O(m + n)`
- **Space Complexity:** `O(m + n)` for the merged array.

### Why improve it?

The problem requires logarithmic time complexity. Merging every element takes linear time, so we need a binary-search-based approach that avoids processing all elements.

---

# 2. Optimal Approach — Binary Search on Partition

### Core Idea

Instead of merging the arrays, divide both arrays into left and right partitions such that:

1. The left partition contains half of the total elements, or one extra element when the total is odd.
2. Every element in the left partition is less than or equal to every element in the right partition.

Once these conditions are satisfied, we can calculate the median using only four boundary elements.

### Step 1: Always binary search on the smaller array

If `nums1` is longer than `nums2`, swap the arrays by calling the method recursively with the arguments reversed.

This keeps the binary search range as small as possible.

Let:

- `m = nums1.length`
- `n = nums2.length`
- `half = (m + n + 1) / 2`

The `+1` ensures that the left partition contains one extra element when the total length is odd.

### Step 2: Choose the partition

Binary search for `cut1`, the number of elements taken from `nums1` into the left partition.

```java
int cut1 = low + (high - low) / 2;
int cut2 = half - cut1;
```

Since the left partition must contain exactly `half` elements, `cut2` is determined by `cut1`.

For example:

```text
nums1 = [1, 3]
nums2 = [2, 7, 10]

half = (2 + 3 + 1) / 2 = 3
```

Suppose:

```text
cut1 = 1
cut2 = 2
```

The partition becomes:

```text
nums1: [1 | 3]
nums2: [2, 7 | 10]

Left partition:  [1, 2, 7]
Right partition: [3, 10]
```

The left partition has three elements, as required.

### Step 3: Find the four boundary elements

We only need the elements immediately around the partition:

```java
left1  = nums1[cut1 - 1];
right1 = nums1[cut1];

left2  = nums2[cut2 - 1];
right2 = nums2[cut2];
```

However, these indices can go out of bounds when a partition is at the beginning or end of an array.

To handle these cases, use sentinel values:

```java
int left1 = (cut1 == 0)
        ? Integer.MIN_VALUE
        : nums1[cut1 - 1];

int right1 = (cut1 == m)
        ? Integer.MAX_VALUE
        : nums1[cut1];

int left2 = (cut2 == 0)
        ? Integer.MIN_VALUE
        : nums2[cut2 - 1];

int right2 = (cut2 == n)
        ? Integer.MAX_VALUE
        : nums2[cut2];
```

- `Integer.MIN_VALUE` represents a missing left boundary.
- `Integer.MAX_VALUE` represents a missing right boundary.

These values let us check partitions without special handling for every boundary case.

### Step 4: Check whether the partition is valid

The partition is valid when:

```java
left1 <= right2 && left2 <= right1
```

Because both input arrays are already sorted, these two comparisons are enough to ensure that every element on the left is less than or equal to every element on the right.

### Step 5: Calculate the median

**If the total number of elements is odd:**

The left partition contains one extra element. Its largest element is the median.

```java
return Math.max(left1, left2);
```

**If the total number of elements is even:**

The median is the average of the largest left-side element and the smallest right-side element.

```java
return ((double) Math.max(left1, left2)
        + Math.min(right1, right2)) / 2.0;
```

Casting to `double` ensures that the result can contain a fractional value.

### Step 6: Adjust the binary search

If the partition is invalid, move the binary search boundaries.

**Case 1: `left1 > right2`**

Too many elements have been taken from `nums1` into the left partition. Move the partition left.

```java
high = cut1 - 1;
```

**Case 2: `left2 > right1`**

Too few elements have been taken from `nums1` into the left partition. Move the partition right.

```java
low = cut1 + 1;
```

Repeat until a valid partition is found.

---

# Why Does the Optimal Approach Work?

Both arrays are individually sorted. Therefore, we do not need to compare every element across the two arrays.

A valid partition only needs to satisfy:

```text
left1 <= right2
left2 <= right1
```

The left partition also contains the correct number of elements.

Together, these conditions guarantee that the median is determined by the largest element on the left and, when needed, the smallest element on the right.

Binary search works because moving the partition in the appropriate direction eliminates half of the possible partition positions at each step.

---

# Complexity Analysis

Let `m` be the length of the smaller array and `n` the length of the larger array.

- **Time Complexity:** `O(log(min(m, n)))`
- **Space Complexity:** `O(1)` auxiliary space.

The recursive swap adds only a constant number of stack frames. The algorithm does not merge or copy the arrays.

---

# Edge Cases

1. **One array is empty:** The algorithm still works by using sentinel values at the boundaries.
2. **Total length is odd:** Return the maximum of the two left-side elements.
3. **Total length is even:** Average the maximum left-side element and minimum right-side element.
4. **Arrays have different lengths:** Always binary search on the smaller array.
5. **Partition at the beginning or end:** Sentinel values prevent out-of-bounds access.
6. **Duplicate elements:** The partition conditions still work correctly.

---

# Key Takeaway

The key to this problem is **finding the correct partition, not merging the arrays**.

Remember these three things:

1. Binary search on the smaller array.
2. Calculate the second partition using `cut2 = half - cut1`.
3. Validate the partition using `left1 <= right2` and `left2 <= right1`.

Once the partition is valid, the median can be calculated in constant time.

---

# Solution

[View solution.java](./solution.java)