# Sum of Beauty of All Substrings

## LeetCode

- **Problem:** [Sum of Beauty of All Substrings](https://leetcode.com/problems/sum-of-beauty-of-all-substrings/)
- **Difficulty:** Medium
- **Pattern:** Strings / HashMap / Frequency Counting

## Problem Description

The **beauty of a string** is defined as the difference between the highest frequency and the lowest frequency of any character that appears in that string.

Given a string `s`, return the sum of the beauty values of all its substrings.

For every substring:

```text
Beauty = Maximum Character Frequency - Minimum Character Frequency
```

**Important:** The minimum frequency is calculated only among characters that actually appear in the substring. Characters with a frequency of zero are ignored.

### Examples

**Example 1**

```text
Input: s = "aabcb"
Output: 5
```

Explanation:

| Substring | Beauty |
|---|---:|
| `aab` | 1 |
| `aabc` | 1 |
| `aabcb` | 1 |
| `ab` | 0 |
| `abc` | 0 |
| `abcb` | 1 |
| `b` | 0 |
| `bc` | 0 |
| `bcb` | 1 |
| `cb` | 0 |
| `b` | 0 |
| `a` | 0 |
| `aa` | 0 |
| `aab` | 1 |
| `ab` | 0 |

Total beauty = `5`.

**Example 2**

```text
Input: s = "aabcbaa"
Output: 17
```

### Constraints

- `1 <= s.length <= 500`
- `s` consists of lowercase English letters.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Generate every possible substring, calculate the frequency of each character, and find the difference between the maximum and minimum frequencies.

### Algorithm

1. Generate all substrings using two nested loops.
2. For every substring, maintain a frequency map.
3. Traverse the map values to find:
   - Maximum character frequency.
   - Minimum character frequency.
4. Calculate the beauty of the current substring.
5. Add it to the total beauty.

### Complexity Analysis

Let:
- `n` = length of the string.
- `k` = number of distinct characters in a substring, at most 26.

There are `O(n²)` substrings.

For each substring, we may scan its frequency map, which takes `O(26)` time.

- **Time Complexity:** `O(n² × 26)` = `O(n²)` because the alphabet size is fixed.
- **Space Complexity:** `O(26)` = `O(1)` for the frequency map.

### Why improve it?

Recalculating the frequency map for every substring would be unnecessary.

Instead, we can fix the starting index and extend the substring one character at a time, updating the frequency map incrementally.

---

# 2. Optimal Approach — Expand Substrings with Frequency Counting

## Core Idea

Fix a starting index `i` and gradually expand the ending index `j`.

Maintain a frequency map that stores the character counts for the current substring.

Whenever a new character is included:

1. Update its frequency.
2. Calculate the maximum frequency.
3. Calculate the minimum frequency among characters present.
4. Add the difference to the total beauty.

This avoids rebuilding the frequency map for every substring.

## Step 1: Initialize the total beauty

```java
int beautySum = 0;
```

This variable stores the sum of beauty values of all substrings.

## Step 2: Fix the starting index

```java
for (int i = 0; i < s.length(); i++) {
    HashMap<Character, Integer> map = new HashMap<>();
}
```

The outer loop chooses the starting index of each substring.

A new frequency map is created for every starting index because we begin constructing a new set of substrings.

## Step 3: Expand the substring

```java
for (int j = i; j < s.length(); j++) {
    char ch = s.charAt(j);

    map.put(ch, map.getOrDefault(ch, 0) + 1);
}
```

The inner loop extends the substring one character at a time.

For example, if:

```text
s = "aabcb"
```

Starting at index `0`, the substrings grow as follows:

```text
a
aa
aab
aabc
aabcb
```

The frequency map is updated incrementally instead of being recreated for each substring.

## Step 4: Find maximum and minimum frequencies

```java
int maxCount = 0;
int minCount = Integer.MAX_VALUE;

for (int count : map.values()) {
    maxCount = Math.max(maxCount, count);
    minCount = Math.min(minCount, count);
}
```

Traverse the frequency map to determine:

- `maxCount`: highest frequency among the characters.
- `minCount`: lowest frequency among the characters present.

We initialize `minCount` with `Integer.MAX_VALUE` so that the first frequency encountered correctly updates it.

**Important:** We iterate over `map.values()`, so characters that do not appear in the substring are automatically excluded from the minimum frequency calculation.

## Step 5: Calculate the beauty

```java
beautySum += (maxCount - minCount);
```

The beauty of the current substring is:

```text
Beauty = maxCount - minCount
```

Add this value to the overall answer.

## Step 6: Return the result

```java
return beautySum;
```

After processing every possible starting and ending index, return the accumulated beauty sum.

---

# Dry Run

Consider:

```text
s = "aabcb"
```

Fix the starting index at `0`.

| Substring | Frequency Map | Max | Min | Beauty |
|---|---|---:|---:|---:|
| `a` | a:1 | 1 | 1 | 0 |
| `aa` | a:2 | 2 | 2 | 0 |
| `aab` | a:2, b:1 | 2 | 1 | 1 |
| `aabc` | a:2, b:1, c:1 | 2 | 1 | 1 |
| `aabcb` | a:2, b:2, c:1 | 2 | 1 | 1 |

The beauty values for these substrings sum to `3`.

Continue with starting indices `1`, `2`, `3`, and `4`.

The total beauty across all substrings is:

```text
Output: 5
```

---

# Why Does This Approach Work?

Every substring can be uniquely represented by its starting and ending indices.

By fixing the starting index and extending the ending index, we generate every possible substring exactly once.

The frequency map maintains the correct character counts as the substring grows. Since the maximum and minimum frequencies are recalculated after every extension, the beauty of each substring is calculated correctly.

Finally, adding these values gives the sum of beauty across all substrings.

---

# Complexity Analysis

Let `n` be the length of the string and `k` be the number of distinct characters, where `k <= 26`.

- **Time Complexity:** `O(n² × k)` — there are `O(n²)` substrings, and each substring extension scans at most 26 frequency values.
- **Space Complexity:** `O(k)` = `O(1)` — the frequency map stores at most 26 distinct characters.

Since the input contains only lowercase English letters, the alphabet size is constant.

---

# Edge Cases

1. **Single character:** `"a"` returns `0`, because maximum and minimum frequencies are equal.
2. **All characters identical:** `"aaaa"` returns `0` because every substring has only one distinct character.
3. **All characters distinct:** `"abc"` returns `0` because every character has frequency `1`.
4. **Repeated characters:** `"aabcb"` produces positive beauty for substrings with unequal character frequencies.
5. **Multiple characters with the same minimum frequency:** The minimum frequency is counted once as a value; the number of characters having that frequency does not affect beauty.

---

# Key Takeaway

The important optimization is to **reuse the frequency map while expanding each substring**.

Instead of rebuilding character frequencies for every substring:

1. Fix the starting index.
2. Expand the ending index.
3. Update the frequency of the newly added character.
4. Calculate maximum and minimum frequencies.
5. Add the beauty to the result.

This gives an `O(n² × 26)` solution, which is efficient for the given constraints.

---

# Solution

[View solution.java](./solution.java)
