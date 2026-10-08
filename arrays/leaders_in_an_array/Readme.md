# Leaders in an Array

## Problem

Given an integer array `nums`, find all the **leaders** in the array.

An element is called a leader if it is **greater than or equal to every element to its right**.

The last element is always a leader because there are no elements after it.

### Example

```text
Input:
nums = [16, 17, 4, 3, 5, 2]

Output:
[17, 5, 2]
```

Explanation:

- `16` → not a leader because `17` is greater
- `17` → leader
- `4` → not a leader because `5` is greater
- `3` → not a leader because `5` is greater
- `5` → leader
- `2` → leader because nothing is greater to its right

### Constraints

- `1 <= nums.length <= 10⁵`
- `-10⁹ <= nums[i] <= 10⁹`

# Thought Process

The straightforward way is to check every element against all elements to its right.

For example, for:

```text
[16, 17, 4, 3, 5, 2]
```

when checking `16`, we need to compare it with:

```text
17, 4, 3, 5, 2
```

Then for `17`, compare it with:

```text
4, 3, 5, 2
```

This results in repeated comparisons.

The key observation is:

> To determine whether an element is a leader, we only need to know the maximum element to its right.

Instead of repeatedly searching for the maximum on the right, we can maintain that maximum while traversing the array **from right to left**.

For each element:

```text
if nums[i] > maxRight
```

then it is a leader.

After checking it, update:

```text
maxRight = max(maxRight, nums[i])
```

Since we discover leaders from right to left, we reverse the result at the end to restore the original left-to-right order.

# 1. Brute Force Approach

## Idea

For every element, check all elements to its right.

If no element on the right is greater than the current element, it is a leader.

## Algorithm

1. Start from the first element.
2. Compare it with every element to its right.
3. If a greater element is found, it is not a leader.
4. Otherwise, add it to the result.
5. Repeat for every element.

## Complexity

- **Best Time:** `O(n²)`
- **Average Time:** `O(n²)`
- **Worst Time:** `O(n²)`
- **Space:** `O(1)` excluding the output.

## Problem With This Approach

The same elements on the right are repeatedly checked.

For example, while checking multiple elements on the left, we repeatedly scan the same suffix of the array.

We can avoid this repeated work by maintaining the maximum element seen so far.

# 2. Optimal Approach — Right-to-Left Traversal

## Key Insight

The last element is always a leader.

So start from the rightmost element and maintain:

```text
maxRight
```

which represents the largest element encountered so far from the right.

For each element:

```text
if nums[i] > maxRight
```

then it is a leader.

After checking:

```text
maxRight = max(maxRight, nums[i])
```

This allows every element to be processed exactly once.

## Algorithm

1. Initialize `maxRight` with the last element.
2. Add the last element to the result.
3. Traverse from the second-last element toward the beginning.
4. If `nums[i] > maxRight`:
   - Add `nums[i]` to the result.
   - Update `maxRight`.
5. Since leaders are collected from right to left, reverse the result.
6. Return the result.

# Example Walkthrough

Consider:

```text
nums = [16, 17, 4, 3, 5, 2]
```

Start from the right:

```text
maxRight = 2
result = [2]
```

### `5`

```text
5 > 2
```

So `5` is a leader.

```text
result = [2, 5]
maxRight = 5
```

### `3`

```text
3 > 5 → false
```

Not a leader.

### `4`

```text
4 > 5 → false
```

Not a leader.

### `17`

```text
17 > 5
```

Leader.

```text
result = [2, 5, 17]
maxRight = 17
```

### `16`

```text
16 > 17 → false
```

Not a leader.

At this point:

```text
result = [2, 5, 17]
```

But the required order is left to right:

```text
[17, 5, 2]
```

So we reverse the result.

# Why Does the Optimal Approach Work?

When processing from right to left, `maxRight` contains the maximum value among all elements that are to the right of the current element.

Therefore:

```text
nums[i] > maxRight
```

means that `nums[i]` is greater than **every element to its right**.

So it satisfies the definition of a leader.

If:

```text
nums[i] <= maxRight
```

then there is already an element on its right that is greater than or equal to it, so it cannot be a leader.

The right-to-left traversal lets us maintain this information in `O(1)` time for every element.

# Complexity

### Time Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n)`
- **Worst Time:** `O(n)`

The array is traversed once, and reversing the result takes `O(n)` in the worst case.

Therefore, the total remains:

```text
O(n)
```

### Space Complexity

- **Auxiliary Space:** `O(1)` excluding the output.
- **Output Space:** `O(n)` in the worst case.

The result list can contain all `n` elements if the array is strictly decreasing.

# Edge Cases

### Single Element

```text
nums = [5]

Output = [5]
```

The only element is always a leader.

### Strictly Increasing

```text
nums = [1, 2, 3, 4]

Output = [4]
```

Only the last element is a leader.

### Strictly Decreasing

```text
nums = [5, 4, 3, 2, 1]

Output = [5, 4, 3, 2, 1]
```

Every element is a leader.

### Duplicate Values

If the definition allows an element to be greater than or **equal to** every element on its right, use:

```text
nums[i] >= maxRight
```

instead of:

```text
nums[i] > maxRight
```

The current implementation uses `>` and therefore follows the definition where a leader must be **strictly greater** than all elements to its right.

# Key Takeaway

- **Pattern:** Array / Right-to-Left Traversal
- The last element is always a leader.
- Maintain the maximum element seen from the right.
- An element is a leader if it is greater than that maximum.
- Reverse the result because leaders are discovered from right to left.
- **Time:** `O(n)`
- **Auxiliary Space:** `O(1)` excluding the output.

The general lesson is:

> When an element depends on everything to its right, try traversing from right to left and maintain the information you need about the suffix.
 
# Solution

[View solution.java](./solution.java)
