# Subarray Sum Equals K

## LeetCode

- **Problem:** [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)
- **Difficulty:** Medium
- **Pattern:** Prefix Sum + HashMap

## Problem Description

Given an integer array `nums` and an integer `k`, return the total number of **subarrays whose sum equals `k`**.

A subarray must be **contiguous** and **non-empty**.

### Example

```text
Input: nums = [1,1,1], k = 2

Output: 2
```

The valid subarrays are:

```text
[1, 1]  → 2
[1, 1]  → 2
```

### Constraints

- `1 <= nums.length <= 2 * 10⁴`
- `-1000 <= nums[i] <= 1000`
- `-10⁷ <= k <= 10⁷`

# Thought Process

The first idea is to consider every possible subarray and calculate its sum.

But there can be `O(n²)` subarrays, so checking all of them is too expensive.

The key observation is the **prefix sum**.

Suppose:

```text
prefixSum = sum of elements from index 0 to current index
```

For a subarray to have sum `k`:

```text
currentPrefixSum - previousPrefixSum = k
```

Rearranging:

```text
previousPrefixSum = currentPrefixSum - k
```

So, while traversing the array, we can ask:

> "Have I already seen a prefix sum equal to `currentPrefixSum - k`?"

If yes, every occurrence of that prefix sum represents a subarray ending at the current position whose sum is `k`.

A `HashMap` lets us store how many times each prefix sum has appeared.

# 1. Brute Force Approach

## Idea

Try every possible starting index and extend the subarray while maintaining its running sum.

Whenever the running sum equals `k`, increment the count.

## Algorithm

1. Start from every index `i`.
2. Set `sum = 0`.
3. Extend the subarray from `i` to the end.
4. Add each element to `sum`.
5. If `sum == k`, increment the answer.

## Complexity

- **Best Time:** `O(n²)`
- **Average Time:** `O(n²)`
- **Worst Time:** `O(n²)`
- **Space:** `O(1)`

## Problem With This Approach

There are `O(n²)` possible subarrays, so the algorithm can become slow for large arrays.

# 2. Better Approach

## Idea

We can use prefix sums to avoid recalculating the sum of every subarray.

For each index, calculate:

```text
prefixSum[i] = nums[0] + nums[1] + ... + nums[i]
```

The sum of a subarray from `j + 1` to `i` is:

```text
prefixSum[i] - prefixSum[j]
```

We need:

```text
prefixSum[i] - prefixSum[j] = k
```

Therefore:

```text
prefixSum[j] = prefixSum[i] - k
```

We can store all previous prefix sums and look up the required one.

## Complexity

- **Best Time:** `O(n)`
- **Average Time:** `O(n)`
- **Worst Time:** `O(n²)` theoretically if hash operations degrade
- **Space:** `O(n)`

This is essentially the optimal approach, because we can perform the prefix-sum lookup while traversing the array instead of storing the entire prefix-sum array first.

# 3. Optimal Approach — Prefix Sum + HashMap

## Key Insight

At every position:

```text
required = prefixSum - k
```

If `required` has appeared before, then each occurrence gives us one valid subarray ending at the current index.

Therefore, the HashMap stores:

```text
prefix sum → number of times it has appeared
```

## Algorithm

For every number:

1. Add it to `prefixSum`.
2. Calculate:
   ```text
   required = prefixSum - k
   ```
3. Check whether `required` exists in the map.
4. If it exists, add its frequency to `count`.
5. Add the current `prefixSum` to the map.
6. Return `count`.

### Why Store the Frequency?

We don't just need to know whether a prefix sum exists.

It may have appeared multiple times.

For example, if:

```text
required = 3
```

and the map contains:

```text
3 → 2
```

then there are **two different previous positions** that can form a subarray ending at the current position.

So we add `2` to the answer.

### Why `map.put(0, 1)`?

This is an important part of the solution.

It represents:

```text
prefix sum = 0
has appeared once before the array starts
```

For example:

```text
nums = [3]
k = 3
```

After processing `3`:

```text
prefixSum = 3
required = 3 - 3 = 0
```

Because the map already contains:

```text
0 → 1
```

we correctly count `[3]` as one valid subarray.

## Example Walkthrough

Consider:

```text
nums = [1, 2, 3]
k = 3
```

Initial state:

```text
map = {0=1}
prefixSum = 0
count = 0
```

### Process `1`

```text
prefixSum = 1
required = 1 - 3 = -2
```

`-2` is not present.

Store:

```text
map = {0=1, 1=1}
```

### Process `2`

```text
prefixSum = 3
required = 3 - 3 = 0
```

`0` exists once.

```text
count = 1
```

The corresponding subarray is:

```text
[1, 2]
```

Store:

```text
map = {0=1, 1=1, 3=1}
```

### Process `3`

```text
prefixSum = 6
required = 6 - 3 = 3
```

`3` exists once.

```text
count = 2
```

The corresponding subarray is:

```text
[3]
```

Final answer:

```text
2
```

The two valid subarrays are:

```text
[1, 2]
[3]
```

# Why Does the Optimal Approach Work?

Suppose the prefix sum up to index `i` is:

```text
P[i]
```

and a previous prefix sum at index `j` is:

```text
P[j]
```

The sum between them is:

```text
P[i] - P[j]
```

We want this sum to equal `k`:

```text
P[i] - P[j] = k
```

Therefore:

```text
P[j] = P[i] - k
```

So for every current prefix sum, we only need to find how many times:

```text
prefixSum - k
```

has appeared previously.

The HashMap gives us that information in expected `O(1)` time.

This is why the entire array can be processed in a single pass.

# Edge Cases

### Single Element

```text
nums = [5], k = 5

Output: 1
```

### Negative Numbers

```text
nums = [1, -1, 1], k = 0

Output: 2
```

Negative numbers are one reason a simple sliding-window approach does not work reliably here.

### Multiple Occurrences of the Same Prefix Sum

The map stores frequencies, not just presence:

```text
prefixSum → frequency
```

This is necessary because the same prefix sum can occur at multiple indices.

### No Valid Subarray

```text
nums = [1, 2, 3], k = 10

Output: 0
```

# Key Takeaway

- **Pattern:** Prefix Sum + HashMap
- Convert the subarray-sum condition:
  ```text
  currentPrefixSum - previousPrefixSum = k
  ```
  into:
  ```text
  previousPrefixSum = currentPrefixSum - k
  ```
- Store the frequency of every prefix sum.
- `map.put(0, 1)` handles subarrays that start at index `0`.
- **Expected Time:** `O(n)`
- **Space:** `O(n)`

The general lesson is:

> When a problem asks for the number of subarrays with a specific sum, think about prefix sums and whether a previous prefix sum can be used to form the required sum.

# Solution

[View solution.java](./solution.java)
