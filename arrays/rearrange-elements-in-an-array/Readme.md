# Rearrange Array Elements by Sign

## LeetCode

- **Problem:** [Rearrange Array Elements by Sign](https://leetcode.com/problems/rearrange-array-elements-by-sign/)
- **Difficulty:** Medium
- **Pattern:** Arrays / Two Pointers

## Problem Description

Given an integer array `nums` containing an equal number of positive and negative integers, rearrange the elements such that:

1. Every consecutive pair has opposite signs.
2. The relative order of the positive integers remains the same.
3. The relative order of the negative integers remains the same.
4. The array starts with a positive integer.

### Example

```text
Input:
nums = [3,1,-2,-5,2,-4]

Output:
[3,-2,1,-5,2,-4]
```

The positive numbers are:

```text
[3,1,2]
```

The negative numbers are:

```text
[-2,-5,-4]
```

They are placed alternately while maintaining their relative order.

### Constraints

- `2 <= nums.length <= 2 * 10⁵`
- `nums.length` is even.
- `nums` contains an equal number of positive and negative integers.
- `nums[i] != 0`.

# Thought Process

The required arrangement has a fixed structure:

```text
Positive, Negative, Positive, Negative, ...
```

Therefore, positive numbers must occupy even indices:

```text
0, 2, 4, 6, ...
```

and negative numbers must occupy odd indices:

```text
1, 3, 5, 7, ...
```

A straightforward approach would be to create separate arrays for positive and negative numbers and then merge them.

But we can rearrange the input array itself.

The key observation is:

> We only need to find the next incorrect positive position and the next incorrect negative position.

So maintain two pointers:

```text
pos = 0
neg = 1
```

- `pos` moves through even indices.
- `neg` moves through odd indices.

If an even position already contains a positive number, move `pos` forward by `2`.

If an odd position already contains a negative number, move `neg` forward by `2`.

When both pointers find incorrect positions, swap those two values.

# 1. Brute Force Approach

## Idea

Create two separate arrays/lists:

```text
positive numbers
negative numbers
```

Then place them alternately into the result array.

## Algorithm

1. Traverse the input and store all positive numbers.
2. Store all negative numbers separately.
3. Use one pointer for positive numbers and one for negative numbers.
4. Fill:
   ```text
   result[0] = positive
   result[1] = negative
   result[2] = positive
   result[3] = negative
   ```
5. Return the result.

## Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n)`
- **Worst Time:** `O(n)`
- **Auxiliary Space:** `O(n)`
- **Output Space:** `O(n)`

## Problem With This Approach

The time complexity is already optimal, but it requires additional arrays/lists.

We can reduce the auxiliary space by rearranging the input array directly.

# 2. Optimal Approach — Two Pointers + Swapping

## Key Insight

Positive values belong at even indices:

```text
0, 2, 4, 6, ...
```

Negative values belong at odd indices:

```text
1, 3, 5, 7, ...
```

Therefore, maintain two pointers:

```text
pos = 0
neg = 1
```

### `pos`

Finds the next even index containing a negative number.

```text
while nums[pos] > 0
    pos += 2
```

### `neg`

Finds the next odd index containing a positive number.

```text
while nums[neg] < 0
    neg += 2
```

Once both are found, swap them.

For example:

```text
[3, -2, -5, 1, 2, -4]
       ^        ^
```

Suppose:

```text
pos → index 2  (-5)
neg → index 3  (1)
```

These two values are in the wrong positions.

Swap them:

```text
[3, -2, 1, -5, 2, -4]
```

Now both positions are correct.

## Algorithm

1. Initialize:
   ```text
   pos = 0
   neg = 1
   ```
2. Find the next incorrect even position.
3. Find the next incorrect odd position.
4. If both exist, swap their values.
5. Move both pointers by `2`.
6. Continue until one pointer reaches the end.
7. Return the rearranged array.

# Example Walkthrough

Consider:

```text
nums = [3, 1, -2, -5, 2, -4]
```

Expected positions:

```text
Index:  0   1   2   3   4   5
Type:   +   -   +   -   +   -
```

Initial:

```text
pos = 0
neg = 1
```

### Step 1

Index `0` contains `3`, which is positive.

So:

```text
pos = 2
```

Index `1` contains `1`, which is positive but should be negative.

So:

```text
neg = 1
```

We found:

```text
pos → index 2 = -2
neg → index 1 = 1
```

Swap:

```text
[3, -2, 1, -5, 2, -4]
```

Move both pointers:

```text
pos = 4
neg = 3
```

### Step 2

Index `4` contains `2`, which is already positive.

Move:

```text
pos = 6
```

Index `3` contains `-5`, which is already negative.

Move:

```text
neg = 5
```

Index `5` contains `-4`, already negative.

Move:

```text
neg = 7
```

Now the traversal is complete.

Final result:

```text
[3, -2, 1, -5, 2, -4]
```

# Why Does the Optimal Approach Work?

The problem guarantees that there are equal numbers of positive and negative values.

Therefore, there are exactly enough positive numbers to fill:

```text
0, 2, 4, ...
```

and exactly enough negative numbers to fill:

```text
1, 3, 5, ...
```

Whenever an even position contains a negative number, there must eventually be a positive number in another even-position slot that can be moved there.

Similarly, whenever an odd position contains a positive number, a negative value can be moved there.

The two pointers locate these two mismatches and a single swap fixes both positions.

Because both pointers always move forward, every index is processed only a constant number of times.

# Complexity

### Time Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n)`
- **Worst Time:** `O(n)`

Both pointers only move forward through the array.

### Space Complexity

- **Best:** `O(1)`
- **Average:** `O(1)`
- **Worst:** `O(1)`

The rearrangement is done directly inside the input array.

# Edge Cases

### Two Elements

```text
nums = [2, -1]

Output:
[2, -1]
```

Already correctly arranged.

### Completely Reversed Positions

```text
nums = [-1, 2, -3, 4]

Output:
[2, -1, 4, -3]
```

The pointers find the incorrect positions and swap the values.

### Already Correctly Arranged

```text
nums = [1, -2, 3, -4]

Output:
[1, -2, 3, -4]
```

No swaps are required.

# Key Takeaway

- **Pattern:** Two Pointers / In-place Array Rearrangement
- Positive values belong at even indices.
- Negative values belong at odd indices.
- Maintain one pointer for each type of incorrect position.
- Swap the misplaced positive and negative values.
- **Time:** `O(n)`
- **Auxiliary Space:** `O(1)`

The general lesson is:

> When an array has fixed positional requirements, identify the positions that are wrong and use pointers to efficiently find the elements that can correct them.

# Solution

[View solution.java](./solution.java)
