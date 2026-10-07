# First Missing Positive

## LeetCode

- **Problem:** [First Missing Positive](https://leetcode.com/problems/first-missing-positive/description/)
- **Difficulty:** Hard
- **Pattern:** Arrays / Cyclic Sort / In-place Hashing

## Problem Description

Given an unsorted integer array `nums`, return the smallest missing **positive integer**.

The algorithm must run in:

- `O(n)` time
- `O(1)` auxiliary space

### Example 1

```text
Input: nums = [1,2,0]

Output: 3
```

The positive integers are:

```text
1, 2, 3, ...
```

`1` and `2` are present, so the first missing positive is `3`.

### Example 2

```text
Input: nums = [3,4,-1,1]

Output: 2
```

`1` is present, but `2` is missing.

### Example 3

```text
Input: nums = [7,8,9,11,12]

Output: 1
```

`1` is not present, so the answer is `1`.

### Constraints

- `1 <= nums.length <= 10⁵`
- `-2³¹ <= nums[i] <= 2³¹ - 1`

# Thought Process

The first important observation is that for an array of length `n`, the answer can only be between:

```text
1 and n + 1
```

For example, if:

```text
n = 4
```

the answer can only be:

```text
1, 2, 3, 4, or 5
```

Numbers such as:

```text
-10
0
100
1000
```

cannot be the answer and can be ignored.

The next observation is that we can associate every useful number with an index.

For a number `x`:

```text
x → index x - 1
```

So:

```text
1 → index 0
2 → index 1
3 → index 2
4 → index 3
```

Ideally, we want the array to look like:

```text
Index:  0  1  2  3
Value:  1  2  3  4
```

Then the first index where:

```text
nums[i] != i + 1
```

directly tells us the missing positive number.

This leads to an **in-place cyclic sort** approach.

# 1. Brute Force Approach

## Idea

Start from `1` and check whether each positive integer exists in the array.

For every candidate number:

```text
1, 2, 3, ...
```

scan the entire array to see whether it exists.

The first number that is not found is the answer.

## Algorithm

1. Start with `positive = 1`.
2. Search the entire array for `positive`.
3. If it exists, increment `positive`.
4. Otherwise, return `positive`.

## Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n²)`
- **Worst Time:** `O(n²)`
- **Space:** `O(1)`

The best case occurs when `1` is missing immediately.

## Problem With This Approach

For an array containing many consecutive positive numbers, we repeatedly scan the entire array.

For example:

```text
[1,2,3,4,5,6,...]
```

we perform many full-array searches.

This does not satisfy the required `O(n)` time complexity.

# 2. Better Approach — HashSet

## Idea

Store all numbers in a `HashSet`.

Then checking whether a number exists becomes `O(1)` on average.

After building the set, check:

```text
1, 2, 3, ...
```

until a number is missing.

## Algorithm

1. Add every number to a `HashSet`.
2. Start with `positive = 1`.
3. Check whether `positive` exists.
4. If it exists, increment `positive`.
5. Return the first number that does not exist.

## Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n)`
- **Worst Time:** `O(n²)` theoretically if hash operations degrade
- **Space:** `O(n)`

## Why Is This Better?

The HashSet eliminates the repeated linear searches.

Instead of:

```text
Search entire array
```

we can do:

```text
set.contains(number)
```

in expected `O(1)` time.

However, the problem requires **O(1) auxiliary space**, so we need to eliminate the HashSet.

# 3. Optimal Approach — In-Place Cyclic Sort

## Key Insight

Use the input array itself as our HashSet-like structure.

For every useful number `x`:

```text
1 <= x <= n
```

place it at:

```text
index = x - 1
```

For example:

```text
nums = [3, 4, -1, 1]
```

We want:

```text
1 → index 0
3 → index 2
4 → index 3
```

After rearranging:

```text
[1, -1, 3, 4]
```

Now we simply scan the array.

At index `0`:

```text
nums[0] = 1
```

Correct.

At index `1`:

```text
nums[1] = -1
```

but we expected:

```text
index + 1 = 2
```

Therefore, `2` is missing.

## Algorithm

For every index `i`:

1. Check whether `nums[i]` is a useful value:
   ```text
   1 <= nums[i] <= n
   ```
2. Find its correct position:
   ```text
   nums[i] - 1
   ```
3. Swap it into that position.
4. Continue until the current value is either:
   - already in its correct position,
   - outside the range `1...n`, or
   - a duplicate.
5. Scan the array.
6. The first index where:
   ```text
   nums[i] != i + 1
   ```
   gives the answer `i + 1`.
7. If every position is correct, return `n + 1`.

## Why Do We Need `while`?

Consider:

```text
nums = [2, 3, 1]
```

At index `0`:

```text
2
```

belongs at index `1`.

After swapping:

```text
[3, 2, 1]
```

But `3` is now at index `0`, and it belongs at index `2`.

So we need another swap:

```text
[1, 2, 3]
```

Therefore, we keep swapping while the current number can still be placed into its correct position.

## Duplicate Handling

Consider:

```text
nums = [1, 1]
```

The second `1` also wants to go to index `0`.

Without a duplicate check, we could keep swapping the same values indefinitely.

Therefore, we only swap when:

```text
nums[nums[i] - 1] != nums[i]
```

This ensures that we don't swap identical values repeatedly.

# Example Walkthrough

Consider:

```text
nums = [3, 4, -1, 1]
```

`n = 4`

### Start

```text
[3, 4, -1, 1]
 ^
```

`3` belongs at index `2`.

Swap:

```text
[-1, 4, 3, 1]
```

### Next

`-1` is not useful because it is not between `1` and `n`.

Move forward.

```text
[-1, 4, 3, 1]
    ^
```

`4` belongs at index `3`.

Swap:

```text
[-1, 1, 3, 4]
```

### Next

`3` is already at index `2`.

Move forward.

### Next

`4` is already at index `3`.

Final arrangement:

```text
[-1, 1, 3, 4]
```

Now scan:

```text
Index 0 → expected 1 → -1 ❌
```

Therefore:

```text
answer = 1
```

Wait—this example demonstrates an important issue: the swaps above depend on processing the current value again after each swap. Starting from `[3,4,-1,1]`, correctly processing index `0` gives:

```text
[ -1, 4, 3, 1 ]
```

and then index `1` gives:

```text
[ -1, 1, 3, 4 ]
```

This arrangement has `1` at index `1`, not index `0`, so the expected first missing positive is indeed `1`.

# Why Does the Optimal Approach Work?

The answer must be in the range:

```text
1 ... n + 1
```

Every number outside `1...n` can therefore be ignored.

For every useful number `x`, we place it at:

```text
x - 1
```

After this rearrangement:

```text
nums[i] == i + 1
```

means that positive number `i + 1` exists in the array.

Therefore, the first position where this condition fails identifies the smallest missing positive.

If every position from `0` to `n - 1` contains:

```text
1, 2, 3, ..., n
```

then the first missing positive must be:

```text
n + 1
```

# Complexity

### Time Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n)`
- **Worst Time:** `O(n)`

Although there is a `while` loop inside the `for` loop, each successful swap places a value into its correct position. The total number of useful swaps across the entire algorithm is linear.

### Space Complexity

- **Best:** `O(1)`
- **Average:** `O(1)`
- **Worst:** `O(1)`

The array is modified in-place and no additional data structure is required.

# Edge Cases

### Missing `1`

```text
nums = [2, 3, 4]

Output: 1
```

### All Positive Numbers Present

```text
nums = [1, 2, 3]

Output: 4
```

### Negative Numbers and Zero

```text
nums = [-1, 0, -3]

Output: 1
```

### Duplicate Values

```text
nums = [1, 1, 2, 2]

Output: 3
```

### Unordered Values

```text
nums = [3, 4, -1, 1]

Output: 2
```

# Key Takeaway

- **Pattern:** Cyclic Sort / In-place Hashing
- The answer is always between `1` and `n + 1`.
- Ignore values outside `1...n`.
- Place each useful number `x` at index `x - 1`.
- Scan the array to find the first incorrect position.
- **Time:** `O(n)`
- **Extra Space:** `O(1)`

The general lesson is:

> When values naturally map to array indices and the problem requires `O(1)` extra space, consider using the array itself as a hash table.

# Solution

[View solution.java](./solution.java)
