# 18. 4Sum

**LeetCode:** [4Sum](https://leetcode.com/problems/4sum/description/)  
**Difficulty:** Medium  
**Topics:** Array, Sorting, Two Pointers

## Problem Statement

Given an array of integers `nums` and an integer `target`, return all unique quadruplets `[nums[i], nums[j], nums[k], nums[l]]` such that:

- All four indices are distinct.
- `nums[i] + nums[j] + nums[k] + nums[l] == target`.
- The answer must not contain duplicate quadruplets.

## Approach: Reduce 4Sum to 3Sum

### Thought Process

We start with the equation:

`a + b + c + d = target`

Rearranging it:

`b + c + d = target - a`

This means that if we fix one number `a`, the remaining problem becomes a **3Sum problem with a modified target**.

We can solve it efficiently by sorting the array, fixing two numbers using nested loops, and finding the remaining two numbers using the Two Pointers technique.

### Algorithm

1. **Sort the array** to enable the Two Pointers technique and simplify duplicate handling.
2. **Fix the first number:** Iterate through the array using `i`.
3. Calculate the remaining target: `newTarget = target - nums[i]`.
4. **Fix the second number:** Iterate using `j`, starting from `i + 1`.
5. Initialize two pointers:
   - `left = j + 1`
   - `right = nums.length - 1`
6. Calculate the sum of the three remaining numbers: `nums[j] + nums[left] + nums[right]`.
7. **Adjust the pointers:**
   - If the sum equals `newTarget`, add the quadruplet to the result and move both pointers.
   - If the sum is smaller than `newTarget`, increment `left`.
   - If the sum is greater than `newTarget`, decrement `right`.
8. **Skip duplicates** for both fixed numbers and the two pointers to ensure unique quadruplets.

## Java Solution

```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            long newTarget = (long) target - nums[i];

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int left = j + 1;
                int right = n - 1;

                while (left < right) {
                    long sum = (long) nums[j]
                             + nums[left] + nums[right];

                    if (sum == newTarget) {
                        result.add(Arrays.asList(
                            nums[i], nums[j],
                            nums[left], nums[right]
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

                    } else if (sum < newTarget) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return result;
    }
}
```

## Example

**Input:**

```text
nums = [0, 0, -2, -1, 1, 2]
target = 0
```

**Step 1: Sort the array**

```text
[-2, -1, 0, 0, 1, 2]
```

**Step 2: Fix the first number**

Choose `a = -2`.

`newTarget = 0 - (-2) = 2`

Now find three numbers that sum to `2`.

`-1 + 1 + 2 = 2`

This gives the quadruplet:

`[-2, -1, 1, 2]`

**Output:**

```text
[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]
```

## Complexity Analysis

- **Time Complexity:** `O(n³)` — Two nested loops combined with a linear two-pointer scan.
- **Auxiliary Space Complexity:** `O(log n)` typically for sorting, excluding the output list.

## Key Takeaways

- Transform a four-variable equation into a three-variable problem by fixing one number.
- Reduce the problem further by fixing a second number and using two pointers.
- Sorting helps find valid combinations efficiently.
- Skip duplicate values at every fixed index and after finding a valid quadruplet.
- Use `long` for intermediate arithmetic to avoid integer overflow.

**Pattern:** 4Sum → Fix one number → 3Sum → Fix another number → 2Sum using Two Pointers.
