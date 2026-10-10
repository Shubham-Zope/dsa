# 15. 3Sum

**LeetCode:** [3Sum](https://leetcode.com/problems/3sum/description/)  
**Difficulty:** Medium  
**Topics:** Array, Sorting, Two Pointers

## Problem Statement

Given an integer array `nums`, return all triplets `[nums[i], nums[j], nums[k]]` such that:

- `i != j`, `i != k`, and `j != k`
- `nums[i] + nums[j] + nums[k] == 0`

The solution must not contain duplicate triplets.

## Approach 1: Brute Force

### Thought Process

Check every possible combination of three elements using three nested loops. If their sum is zero, add the triplet to the result.

To avoid duplicates, we would need additional checks or a set to store unique triplets.

### Complexity Analysis

- **Time Complexity:** `O(n³)`
- **Space Complexity:** Depends on the data structure used to store unique triplets.

This approach is inefficient for large arrays.

## Approach 2: Sorting + Two Pointers (Optimal)

### Thought Process

We know that:

`a + b + c = 0`

Rearranging the equation:

`b + c = -a`

Instead of searching for all three numbers simultaneously, we can fix one number and find the other two using the Two Sum technique.

### Steps

1. **Sort the array** so that we can use two pointers efficiently.
2. **Fix the first number:** Iterate through the array using `i`.
3. **Initialize two pointers:**
   - `left = i + 1`
   - `right = nums.length - 1`
4. **Calculate the sum:** `nums[i] + nums[left] + nums[right]`.
5. **Adjust the pointers:**
   - If the sum is `0`, add the triplet to the result and move both pointers.
   - If the sum is less than `0`, move `left` forward to increase the sum.
   - If the sum is greater than `0`, move `right` backward to decrease the sum.
6. **Skip duplicates:** Skip repeated values for the fixed number and both pointers to ensure unique triplets.
7. **Stop early:** If the fixed number becomes positive, break the loop because the array is sorted and the sum cannot become zero.

### Java Solution

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            if (nums[i] > 0) {
                break;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    result.add(Arrays.asList(
                        nums[i], nums[left], nums[right]
                    ));

                    left++;
                    right--;

                    while (left < right &&
                           nums[left] == nums[left - 1]) {
                        left++;
                    }

                    while (left < right &&
                           nums[right] == nums[right + 1]) {
                        right--;
                    }

                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }
}
```

### Why `i < nums.length - 2`?

We need three elements: one fixed element at index `i` and two more elements to its right.

For an array of length `5`, the last valid fixed index is `2`, because indices `3` and `4` are needed for the remaining two elements.

Therefore, the loop condition is:

```java
i < nums.length - 2
```

### Example

**Input:**

```text
nums = [-1, 0, 1, 2, -1, -4]
```

**After sorting:**

```text
[-4, -1, -1, 0, 1, 2]
```

**Output:**

```text
[[-1, -1, 2], [-1, 0, 1]]
```

Both triplets sum to zero, and duplicate triplets are excluded.

### Complexity Analysis

- **Time Complexity:** `O(n²)` — Sorting takes `O(n log n)`, and for each fixed element, the two pointers traverse the remaining array in `O(n)` time.
- **Auxiliary Space Complexity:** `O(log n)` typically for sorting, excluding the output list.

## Key Takeaways

- Transform a three-variable equation into a two-variable problem by fixing one variable.
- Sorting enables the Two Pointers technique.
- Move `left` when the sum is too small and `right` when the sum is too large.
- Skip duplicates to ensure unique triplets.
- Recognize the pattern: **Fix one element + solve the remaining Two Sum problem**.
