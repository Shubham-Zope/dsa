# Majority Element II — LeetCode 229

**Problem:** [Majority Element II](https://leetcode.com/problems/majority-element-ii/description/)

## Problem Statement

Given an integer array `nums` of size `n`, return all elements that appear **more than `⌊n/3⌋` times**.

The answer can contain at most two elements.

### Example

**Input**
```text
nums = [3,2,3]
```

**Output**
```text
[3]
```

**Explanation:** The number `3` appears twice, which is more than `3 / 3 = 1` times.

---

## Approach 1: HashMap

### Thought Process

We need to count the frequency of each element and return those whose frequency is greater than `n / 3`.

A `HashMap` stores each element and its frequency.

### Algorithm

1. Iterate through the array and count the frequency of every element.
2. Calculate the threshold `n / 3`.
3. Iterate through the map and add elements whose frequency is greater than the threshold.

### Java Solution

```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        Map<Integer, Integer> frequency = new HashMap<>();
        List<Integer> result = new ArrayList<>();

        for (int num : nums) {
            frequency.put(
                num,
                frequency.getOrDefault(num, 0) + 1
            );
        }

        int threshold = nums.length / 3;

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }

        return result;
    }
}
```

### Complexity Analysis

- **Time Complexity:** `O(n)` average
- **Space Complexity:** `O(n)`

This approach is simple, readable, and easy to implement.

---

## Approach 2: Boyer-Moore Voting Algorithm

### Thought Process

The important observation is that an element appearing more than `n / 3` times can have at most **two possible candidates**.

Why?

If three different elements each appeared more than `n / 3` times, their combined frequency would exceed `n`, which is impossible.

Instead of storing every element's frequency, we maintain two candidates and their counts.

### Algorithm

1. Maintain two candidate variables and two counters.
2. If the current number matches a candidate, increment its count.
3. If a candidate's count is zero, assign the current number to that candidate.
4. Otherwise, decrement both counts.
5. After the first pass, verify the actual frequencies of both candidates.
6. Return only candidates appearing more than `n / 3` times.

**Important:** The first pass identifies potential candidates. It does not guarantee that either candidate satisfies the threshold, so a second pass is required.

### Java Solution

```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        int candidate1 = 0, candidate2 = 0;
        int count1 = 0, count2 = 0;

        for (int num : nums) {
            if (count1 > 0 && candidate1 == num) {
                count1++;
            } else if (count2 > 0 && candidate2 == num) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;

        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            }
        }

        int threshold = nums.length / 3;
        List<Integer> result = new ArrayList<>();

        if (count1 > threshold) {
            result.add(candidate1);
        }

        if (count2 > threshold && candidate1 != candidate2) {
            result.add(candidate2);
        }

        return result;
    }
}
```

### Complexity Analysis

- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)`

The algorithm uses a fixed number of variables, regardless of the input size.

---

## Approach 3: Generalized Majority Element — More Than `n / k`

This section generalizes the same problem to a threshold of `n / k`.

### Problem Statement

Given an array `nums` and an integer `k`, find all elements that appear **more than `⌊n / k⌋` times**.

For the original Majority Element II problem, `k = 3`.

### Key Observation

If an element appears more than `n / k` times, there can be at most **`k - 1` such elements**.

If there were `k` different qualifying elements, their combined frequency would exceed `n`.

Therefore, we only need to maintain `k - 1` potential candidates.

### Generalized Boyer-Moore Algorithm

Instead of two candidates, maintain arrays of candidates and their counts.

For each number:

1. If it matches an active candidate, increment that candidate's count.
2. Otherwise, if an empty candidate slot exists, assign the number to that slot with count `1`.
3. Otherwise, decrement every candidate's count.
4. After processing the entire array, verify the actual frequency of each remaining candidate.
5. Return only candidates whose frequency is greater than `n / k`.

The decrement operation represents cancellation: `k` distinct elements are effectively removed from consideration—one incoming element and one occurrence from each of the `k - 1` candidates.

### Java Solution

```java
class Solution {
    public List<Integer> majorityElement(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();

        if (k < 2 || nums.length == 0) {
            return result;
        }

        int slots = k - 1;
        int[] candidates = new int[slots];
        int[] counts = new int[slots];

        // First pass: find potential candidates
        for (int num : nums) {
            boolean matched = false;

            // Check whether the number matches an active candidate
            for (int i = 0; i < slots; i++) {
                if (counts[i] > 0 && candidates[i] == num) {
                    counts[i]++;
                    matched = true;
                    break;
                }
            }

            if (matched) {
                continue;
            }

            // Assign the number to an empty slot
            for (int i = 0; i < slots; i++) {
                if (counts[i] == 0) {
                    candidates[i] = num;
                    counts[i] = 1;
                    matched = true;
                    break;
                }
            }

            if (matched) {
                continue;
            }

            // No match and no empty slot: cancel one occurrence
            // of every candidate
            for (int i = 0; i < slots; i++) {
                counts[i]--;
            }
        }

        // Second pass: verify actual frequencies
        Arrays.fill(counts, 0);

        for (int num : nums) {
            for (int i = 0; i < slots; i++) {
                if (candidates[i] == num) {
                    counts[i]++;
                    break;
                }
            }
        }

        int threshold = nums.length / k;

        for (int i = 0; i < slots; i++) {
            if (counts[i] > threshold) {
                result.add(candidates[i]);
            }
        }

        return result;
    }
}
```

### Example Dry Run

**Input**
```text
nums = [1,1,1,2,2,3,3,4]
k = 4
```

- Array size: `n = 8`
- Threshold: `⌊8 / 4⌋ = 2`
- Candidate slots: `k - 1 = 3`

After the first pass, the potential candidates are `1`, `2`, and `3`. Their counts may have been reduced through cancellation, so we must count them again.

| Element | Actual frequency | More than 2? |
|---|---:|---|
| 1 | 3 | Yes |
| 2 | 2 | No |
| 3 | 2 | No |
| 4 | 1 | No |

**Output**
```text
[1]
```

### Complexity Analysis

Let `n` be the array size and `k` be the divisor.

- **Time Complexity:** `O(nk)` — each element may be compared against up to `k - 1` candidates, in both passes.
- **Space Complexity:** `O(k)` — the candidate and count arrays each store at most `k - 1` entries.

When `k = 3`, there are only two candidate slots, so the generalized approach reduces to the two-candidate idea used in Majority Element II.

---

## Comparison of Approaches

| Approach | Time | Extra Space | Best Use Case |
|---|---|---|---|
| HashMap | `O(n)` average | `O(n)` | Simple implementation and readability |
| Boyer-Moore for `n/3` | `O(n)` | `O(1)` | Original LeetCode problem |
| Generalized Boyer-Moore for `n/k` | `O(nk)` | `O(k)` | Variable threshold `n/k` |

## Key Takeaways

- For a threshold greater than `n / 3`, at most two elements can qualify.
- For a threshold greater than `n / k`, at most `k - 1` elements can qualify.
- HashMap counts every distinct element directly.
- Boyer-Moore reduces the candidate set through cancellation and verifies the remaining candidates in a second pass.
- Candidate selection alone is not enough; always verify actual frequencies before returning the answer.
