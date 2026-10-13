# Count Inversions

**Problem:** [Count Inversions — TakeUForward](https://takeuforward.org/practice/dsa/count-inversions)

## Approach: Merge Sort

### Thought Process

The brute-force approach checks every pair of indices `(i, j)` where `i < j` and counts pairs satisfying `nums[i] > nums[j]`. This takes `O(n²)` time.

To optimize, we use **Merge Sort** to count inversions while merging sorted halves.

An inversion is a pair of indices `(i, j)` such that:

- `i < j`
- `nums[i] > nums[j]`

### Algorithm

1. Divide the array into two halves using the midpoint.
2. Recursively count inversions in the left half.
3. Recursively count inversions in the right half.
4. Merge both sorted halves while counting cross-inversions.
5. Return the sum of left, right, and cross-inversions.

### Key Optimization

During merging, if `nums[i] <= nums[j]`, copy the left element into the temporary array.

Otherwise, `nums[i] > nums[j]`. Since the left half is sorted, every remaining element from index `i` through `mid` is also greater than `nums[j]`.

Therefore, we count all these inversions at once:

```java
count += mid - i + 1;
```

This avoids checking every pair individually.

## Complexity Analysis

| Complexity | Value | Explanation |
|---|---|---|
| Time | `O(n log n)` | Merge Sort divides the array into logarithmic levels, processing all elements at each level. |
| Auxiliary Space | `O(n)` | Temporary arrays are used during merging, with `O(log n)` recursion stack space. |

## Key Takeaways

- Merge Sort enables efficient inversion counting by maintaining sorted halves.
- Two pointers compare elements during the merge operation.
- The expression `mid - i + 1` counts multiple inversions in constant time.
- The algorithm modifies the input array by sorting it.
- A `long` return type prevents overflow when the inversion count exceeds the `int` range.

## Alternative Approaches

**Brute Force:** Check every pair using nested loops. Time: `O(n²)`, auxiliary space: `O(1)`.

**Optimal:** Merge Sort with inversion counting. Time: `O(n log n)`, auxiliary space: `O(n)`.
