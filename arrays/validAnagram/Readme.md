# Valid Anagram

## LeetCode

- **Problem:** [Valid Anagram](https://leetcode.com/problems/valid-anagram/description/)
- **Difficulty:** Easy
- **Pattern:** Hashing / Frequency Counting

---

## Problem Description

Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.

An anagram is a word or phrase formed by rearranging the letters of another word or phrase, using all the original letters exactly once.

### Example 1

**Input:**

```text
s = "anagram"
t = "nagaram"
```

**Output:**

```text
true
```

**Explanation:**

Both strings contain the same characters with the same frequencies.

### Example 2

**Input:**

```text
s = "rat"
t = "car"
```

**Output:**

```text
false
```

**Explanation:**

The character frequencies are different. `s` contains `t`, while `t` contains `c`.

### Constraints

- `1 <= s.length, t.length <= 5 * 10⁴`
- `s` and `t` consist of lowercase English letters.

---

# 1. Brute Force Approach

### Idea

An anagram contains exactly the same characters with the same frequencies.

One straightforward approach is to take every character from `s` and search for the same character in `t`.

Once a matching character is found, mark it as used so that it cannot be matched again.

### Algorithm

1. If the lengths of `s` and `t` are different, return `false`.
2. Create a boolean array to keep track of characters in `t` that have already been matched.
3. For every character in `s`:
   - Search through `t`.
   - Find an unused matching character.
   - Mark it as used.
4. If any character cannot be matched, return `false`.
5. If every character is successfully matched, return `true`.

### Complexity

**Time Complexity:**

- **Best Case:** `O(n)` — matches are found near the beginning.
- **Average Case:** `O(n²)`
- **Worst Case:** `O(n²)` — each character may require scanning most of `t`.

**Space Complexity:**

- **Best Case:** `O(n)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)` — because of the used-character tracking array.

### Problem With This Approach

We repeatedly search through `t` for characters that we have already encountered.

This can result in `O(n²)` time.

Instead of searching for every character, we can directly keep track of how many times each character occurs.

---

# 2. Better Approach — Sorting

### Idea

Two strings are anagrams if they contain the same characters with the same frequencies.

If we sort both strings, anagrams will produce exactly the same sorted sequence.

For example:

```text
"anagram" → "aaagmnr"
"nagaram" → "aaagmnr"
```

Therefore, we can compare the sorted strings.

### Algorithm

1. If the lengths of `s` and `t` are different, return `false`.
2. Convert both strings to character arrays.
3. Sort both character arrays.
4. Compare the sorted arrays.
5. If they are equal, return `true`; otherwise return `false`.

### Complexity

**Time Complexity:**

- **Best Case:** `O(n log n)`
- **Average Case:** `O(n log n)`
- **Worst Case:** `O(n log n)`

Sorting dominates the complexity.

**Space Complexity:**

- **Best Case:** `O(n)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

Additional space is required for the character arrays and sorting operations.

### Why Is This Better?

Sorting removes the need for repeatedly searching for matching characters.

However, we can do even better because the problem guarantees that the strings contain only lowercase English letters.

There are only **26 possible characters**.

Instead of sorting, we can simply count the frequency of each character.

---

# 3. Optimal Approach — Character Frequency Counting

### Key Insight

For two strings to be anagrams, every character must occur the same number of times in both strings.

Since the input contains only lowercase English letters, we only need an array of size `26`.

```text
a → index 0
b → index 1
c → index 2
...
z → index 25
```

For every character in `s`, increment its count.

For every character in `t`, decrement its count.

If the strings are anagrams, all counts will balance to zero.

### Example

For:

```text
s = "rat"
t = "tar"
```

After processing `s`:

```text
r → +1
a → +1
t → +1
```

After processing `t`:

```text
t → 0
a → 0
r → 0
```

Everything balances, so the strings are anagrams.

### Algorithm

1. If the lengths of `s` and `t` are different, return `false`.
2. Create an integer array of size `26`.
3. Traverse `s` and increment the count for each character.
4. Traverse `t` and decrement the count for each character.
5. If any count becomes negative while processing `t`, return `false`.
6. If the entire string is processed without a negative count, return `true`.

### Complexity

Let `n` be the length of the strings.

**Time Complexity:**

- **Best Case:** `O(n)`
- **Average Case:** `O(n)`
- **Worst Case:** `O(n)`

We traverse the strings once.

**Space Complexity:**

- **Best Case:** `O(1)`
- **Average Case:** `O(1)`
- **Worst Case:** `O(1)`

The frequency array always contains exactly `26` elements, regardless of the input size.

Therefore, its space complexity is technically `O(1)`.

### Why Is This Better?

The sorting approach requires:

```text
O(n log n)
```

time.

The frequency-counting approach only needs to traverse the strings:

```text
O(n)
```

time.

Since the character set is fixed at 26 lowercase letters, the additional space remains:

```text
O(1)
```

---

# Why Does the Optimal Approach Work?

Anagrams must have exactly the same frequency for every character.

For example:

```text
s = "aabbc"
t = "abcab"
```

Both contain:

```text
a → 2
b → 2
c → 1
```

Our algorithm maintains the difference in character frequencies.

For every character in `s`:

```java
count[c - 'a']++;
```

For every character in `t`:

```java
count[c - 'a']--;
```

If `t` contains a character more times than `s`, its count becomes negative:

```java
if (count[c - 'a'] < 0) {
    return false;
}
```

Therefore, a negative count proves that the strings cannot be anagrams.

If we finish processing `t` without finding a negative count, all characters in `t` have matching frequencies from `s`, and because the lengths are already confirmed to be equal, the strings must be anagrams.

---

# Edge Cases

### 1. Different Lengths

```text
s = "rat"
t = "rats"
```

Immediately return `false`.

There cannot be an anagram if the lengths are different.

### 2. Same Characters

```text
s = "anagram"
t = "nagaram"
```

All character frequencies match, so return `true`.

### 3. Same Characters but Different Frequencies

```text
s = "aab"
t = "abb"
```

The frequency of `a` and `b` differs, so return `false`.

### 4. Single Character

```text
s = "a"
t = "a"
```

Return `true`.

### 5. Same Length but Different Characters

```text
s = "rat"
t = "car"
```

The frequency count for `r`/`t` and `c` differs, so return `false`.

---

# Key Takeaway

### Pattern

**Frequency Counting / Hashing**

### General Lesson

When a problem asks whether two collections of items contain the same elements with the same frequencies, think about **frequency counting**.

For a fixed and small character set, an array can be more efficient than a `HashMap`.

Here, because there are only 26 lowercase English letters:

```text
int[26]
```

is enough.

The progression is:

```text
Brute Force
O(n²)
    ↓
Sorting
O(n log n)
    ↓
Frequency Counting
O(n) time
O(1) space
```

The important pattern to recognize is:

> **"Do these two strings contain the same elements with the same frequencies?" → Think frequency counting.**

---

# Solution

The implementation is available in:

[`solution.java`](./solution.java)