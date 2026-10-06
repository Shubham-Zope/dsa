# Product of Array Except Self

## LeetCode

- **Problem:** [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/)
- **Difficulty:** Medium
- **Pattern:** Prefix / Postfix

---

## Problem Description

Given an integer array `nums`, return an array `answer` such that:

```text
answer[i] = product of all nums[j] where j != i
```

The solution must run in `O(n)` time.

### Example 1

**Input:**

```text
nums = [1,2,3,4]
```

**Output:**

```text
[24,12,8,6]
```

### Explanation

For each position:

```text
index 0 → 2 × 3 × 4 = 24
index 1 → 1 × 3 × 4 = 12
index 2 → 1 × 2 × 4 = 8
index 3 → 1 × 2 × 3 = 6
```

### Example 2

**Input:**

```text
nums = [-1,1,0,-3,3]
```

**Output:**

```text
[0,0,9,0,0]
```

---

## Constraints

- `2 <= nums.length <= 10⁵`
- `-30 <= nums[i] <= 30`
- The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer.
- Division is not allowed.

---

# Thought Process

The first thing to notice is that for every index `i`, we need:

```text
product of everything before i
×
product of everything after i
```

For example:

```text
nums = [1, 2, 3, 4]
```

For index `2`:

```text
1 × 2 | 3 | 4
      ↑
    index 2

left product  = 1 × 2 = 2
right product = 4

answer[2] = 2 × 4 = 8
```

So the problem can be broken into two parts:

```text
answer[i] =
    prefix product before i
    ×
    postfix product after i
```

The question then becomes:

> **How can we calculate all prefix and postfix products efficiently without recalculating them for every index?**

---

# 1. Brute Force Approach

## Idea

For every index `i`:

1. Start a product at `1`.
2. Traverse the entire array.
3. Skip `nums[i]`.
4. Multiply all other elements.
5. Store the result.

For example:

```text
nums = [1,2,3,4]
```

For index `0`:

```text
2 × 3 × 4 = 24
```

For index `1`:

```text
1 × 3 × 4 = 12
```

And so on.

## Problem With This Approach

For every element, we traverse the entire array again.

If there are `n` elements:

```text
n elements × n traversal
```

This results in `O(n²)` time.

This is too slow for large inputs.

## Complexity

### Time

- **Best:** `O(n²)`
- **Average:** `O(n²)`
- **Worst:** `O(n²)`

### Space

- **Best:** `O(1)` extra space
- **Average:** `O(1)` extra space
- **Worst:** `O(1)` extra space

The output array is not counted as extra space.

---

# 2. Better Approach — Prefix and Postfix Arrays

## Thought Process

We already identified that:

```text
answer[i] =
left product × right product
```

So we can precompute both.

### Prefix Array

For:

```text
nums = [1,2,3,4]
```

The prefix products before each index are:

```text
index:       0   1   2   3
nums:        1   2   3   4

prefix:      1   1   2   6
```

Notice:

```text
prefix[0] = 1
prefix[1] = 1
prefix[2] = 1 × 2 = 2
prefix[3] = 1 × 2 × 3 = 6
```

### Postfix Array

The products after each index are:

```text
postfix:     24  12  4   1
```

Then:

```text
answer[i] = prefix[i] × postfix[i]
```

Giving:

```text
[24,12,8,6]
```

## Complexity

### Time

- Prefix calculation: `O(n)`
- Postfix calculation: `O(n)`
- Final combination: `O(n)`

Overall:

```text
O(n)
```

### Space

We need prefix and postfix arrays:

```text
O(n)
```

in addition to the result.

This is better than brute force, but we can still reduce the extra space.

---

# 3. Optimal Approach — Prefix + Postfix Using the Result Array

## Thought Process

The previous approach uses separate arrays for prefix and postfix products.

But do we actually need both arrays?

No.

We can use the **answer array itself to store the prefix products**.

### First Pass — Prefix

Start with:

```java
int prefix = 1;
```

Traverse from left to right.

At each index:

```java
answer[i] = prefix;
```

Then update:

```java
prefix = prefix * nums[i];
```

For:

```text
nums = [1,2,3,4]
```

the first pass produces:

```text
answer = [1,1,2,6]
```

These values represent the product of everything **before** each index.

---

### Second Pass — Postfix

Now traverse from right to left.

Start with:

```java
int postfix = 1;
```

At each index:

```java
answer[i] = answer[i] * postfix;
```

This combines:

```text
prefix product × postfix product
```

Then update:

```java
postfix = postfix * nums[i];
```

For example:

```text
nums    = [1, 2, 3, 4]
answer  = [1, 1, 2, 6]
```

From right to left:

```text
index 3:
answer[3] = 6 × 1 = 6
postfix = 4

index 2:
answer[2] = 2 × 4 = 8
postfix = 12

index 1:
answer[1] = 1 × 12 = 12
postfix = 24

index 0:
answer[0] = 1 × 24 = 24
```

Final result:

```text
[24,12,8,6]
```

---

## Algorithm

### Pass 1 — Prefix Products

```text
prefix = 1

for i = 0 → n-1:

    answer[i] = prefix

    prefix = prefix × nums[i]
```

### Pass 2 — Postfix Products

```text
postfix = 1

for i = n-1 → 0:

    answer[i] = answer[i] × postfix

    postfix = postfix × nums[i]
```

---

## Complexity

### Time

We traverse the array twice:

```text
O(n) + O(n) = O(n)
```

So:

- **Best:** `O(n)`
- **Average:** `O(n)`
- **Worst:** `O(n)`

### Space

Only two variables are used:

```java
int prefix;
int postfix;
```

The output array is required by the problem and is not counted as extra space.

Therefore:

- **Best:** `O(1)` extra space
- **Average:** `O(1)` extra space
- **Worst:** `O(1)` extra space

Overall:

```text
Time:  O(n)
Space: O(1) extra
```

---

# Why Does the Optimal Approach Work?

For every index `i`:

```text
answer[i] =
product of elements to the left
×
product of elements to the right
```

The first pass stores the left-side product in `answer[i]`.

For example:

```text
nums = [1,2,3,4]

after first pass:

answer = [1,1,2,6]
```

These represent:

```text
index 0 → nothing on left → 1
index 1 → 1 → 1
index 2 → 1×2 → 2
index 3 → 1×2×3 → 6
```

The second pass maintains the product of everything on the right.

When we multiply:

```text
answer[i] × postfix
```

we get:

```text
left product × right product
```

which is exactly the required answer.

---

# Why Don't We Use Division?

A tempting solution would be:

```text
total product = product of all elements

answer[i] = total product / nums[i]
```

But the problem explicitly does not allow division.

More importantly, division causes problems when the array contains zero.

For example:

```text
[1,2,0,4]
```

The total product is `0`, so simply dividing by `nums[i]` cannot correctly determine every answer.

The prefix/postfix approach naturally handles zeros without any special logic.

---

# Edge Cases

### 1. One Zero

```text
nums = [1,2,0,4]
```

Result:

```text
[0,0,8,0]
```

The element at index `2` gets:

```text
1 × 2 × 4 = 8
```

Every other position has the zero in its product.

---

### 2. Two Zeros

```text
nums = [1,0,3,0]
```

Every answer is zero because every position has at least one zero among the other elements.

```text
[0,0,0,0]
```

---

### 3. Negative Numbers

```text
nums = [-1,1,0,-3,3]
```

The prefix/postfix multiplication works normally with negative values.

Result:

```text
[0,0,9,0,0]
```

---

### 4. No Zeros

```text
nums = [1,2,3,4]
```

Result:

```text
[24,12,8,6]
```

---

# Key Takeaway

### Pattern

**Prefix + Postfix**

The main insight is:

```text
answer[i]
    =
product of everything before i
    ×
product of everything after i
```

Instead of calculating these products repeatedly, maintain running products from both directions.

```text
Left → Right
    ↓
Prefix product

Right → Left
    ↓
Postfix product
```

The important optimization is that the **answer array can store the prefix products**, so we don't need separate prefix/postfix arrays.

### General Lesson

When a problem asks for information about:

- everything before an index
- everything after an index
- range products/sums
- left/right contributions

think about whether **prefix and suffix accumulation** can avoid repeated work.

---

# Solution

See [`solution.java`](./solution.java) for the Java implementation.
