# Longest Palindromic Substring

## LeetCode

- **Problem:** [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)
- **Difficulty:** Medium
- **Pattern:** Two Pointers / Expand Around Center

## Problem Description

Given a string `s`, return the longest palindromic substring in `s`.

A palindrome is a string that reads the same forward and backward.

A substring is a contiguous sequence of characters within a string.

If multiple longest palindromic substrings exist, returning any one of them is valid.

### Examples

**Example 1**

```text
Input: s = "babad"
Output: "bab"
```

`"aba"` is also a valid answer.

**Example 2**

```text
Input: s = "cbbd"
Output: "bb"
```

**Example 3**

```text
Input: s = "a"
Output: "a"
```

**Example 4**

```text
Input: s = "racecar"
Output: "racecar"
```

### Constraints

- `1 <= s.length <= 1000`
- `s` consists of only digits and English letters.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Generate every possible substring and check whether it is a palindrome. Keep track of the longest palindrome found.

### Algorithm

1. Use two nested loops to generate all possible substrings.
2. For each substring, check whether it reads the same forward and backward.
3. If it is a palindrome and its length is greater than the current maximum, update the result.
4. Return the longest palindromic substring.

### Complexity Analysis

Let `n` be the length of the string.

- **Time Complexity:** `O(n³)` — there are `O(n²)` substrings, and checking each substring takes up to `O(n)` time.
- **Space Complexity:** `O(1)` auxiliary space if palindrome checking uses two pointers without creating substring copies.

### Why improve it?

Checking every possible substring repeatedly performs unnecessary comparisons.

Instead, we can use the central property of a palindrome: **it expands symmetrically around its center**.

This allows us to find palindromes without generating every substring.

---

# 2. Optimal Approach — Expand Around Center

## Core Idea

Every palindrome has a center around which its characters are mirrored.

For example:

```text
Odd-length palindrome:
    b a b
      ^
    Center

Even-length palindrome:
    c b b d
      ^ ^
     Center
```

There are two possible types of palindrome centers:

1. **Odd-length palindrome:** A single character is the center.
2. **Even-length palindrome:** The center is between two adjacent characters.

For every index, we check both possibilities and expand outward as long as the characters on both sides match.

## Step 1: Initialize the result

```java
int start = 0;
int maxLength = 0;
```

- `start` stores the starting index of the longest palindrome found.
- `maxLength` stores its length.

## Step 2: Iterate through every possible center

```java
for (int i = 0; i < s.length(); i++) {
    int odd = expand(s, i, i);
    int even = expand(s, i, i + 1);

    int length = Math.max(odd, even);
}
```

For each index `i`, we check two possible centers.

**Odd-length palindrome:**

```java
expand(s, i, i);
```

Both pointers start at the same index.

Example:

```text
s = "babad"

        b a b a d
          ^
        Center

Expand:
        b a b
```

**Even-length palindrome:**

```java
expand(s, i, i + 1);
```

The pointers start at adjacent indices.

Example:

```text
s = "cbbd"

        c b b d
          ^ ^
        Center

Expand:
          b b
```

We take the maximum length returned by these two expansions.

## Step 3: Expand around the center

```java
private int expand(String s, int left, int right) {
    while (left >= 0 && right < s.length()
            && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }

    return right - left - 1;
}
```

The expansion continues while:

1. The left pointer stays within the string.
2. The right pointer stays within the string.
3. Characters at both pointers are equal.

After every successful comparison:

- Move `left` one position backward.
- Move `right` one position forward.

When the loop stops, the pointers have moved one position beyond the actual palindrome boundaries.

Therefore, the palindrome length is:

```java
right - left - 1
```

### Example Walkthrough

Consider:

```text
s = "babad"
```

For the center at index `1`:

```text
b a b a d
  ^
```

Expansion:

```text
b a b
```

- `s[1] == s[1]` → Match.
- `s[0] == s[2]` → Match.
- Next expansion goes out of bounds.

The resulting length is:

```text
right - left - 1
= 3 - (-1) - 1
= 3
```

The palindrome is `"bab"`.

## Step 4: Update the longest palindrome

```java
if (length > maxLength) {
    maxLength = length;
    start = i - (length - 1) / 2;
}
```

Whenever we find a longer palindrome, update its length and starting index.

The starting index formula is:

```java
start = i - (length - 1) / 2;
```

Why does this work?

For an odd-length palindrome, the center is at index `i`, and we move half the palindrome length to the left.

For an even-length palindrome, the center is between `i` and `i + 1`, so the formula accounts for the shifted center.

The same formula works for both cases.

### Example

For `"babad"`, suppose the longest palindrome is `"bab"`:

```text
Index:  0 1 2 3 4
String: b a b a d
          ^
        Center
```

```text
length = 3

start = 1 - (3 - 1) / 2
      = 1 - 1
      = 0
```

So the palindrome starts at index `0`.

## Step 5: Return the result

```java
return s.substring(start, start + maxLength);
```

Extract the longest palindrome using its starting index and length.

---

# Why Does the Optimal Approach Work?

A palindrome is symmetric around its center.

Every palindrome must have either:

- One center character (odd length).
- Two adjacent center characters (even length).

By checking both possibilities at every index, we cover every possible palindrome center.

Expanding outward finds the maximum palindrome around each center. Keeping track of the longest one gives the longest palindromic substring in the entire string.

---

# Complexity Analysis

Let `n` be the length of the string.

- **Time Complexity:** `O(n²)` — there are `n` possible odd centers and `n` possible even centers. Each expansion can take up to `O(n)` time.
- **Space Complexity:** `O(1)` auxiliary space — only a constant number of variables and pointers are used.

This improves upon the brute-force `O(n³)` approach.

---

# Edge Cases

1. **Single character:** `"a"` → `"a"`.
2. **All characters are the same:** `"aaaa"` → `"aaaa"`.
3. **Even-length palindrome:** `"cbbd"` → `"bb"`.
4. **Odd-length palindrome:** `"racecar"` → `"racecar"`.
5. **No repeating adjacent characters:** `"abcd"` → Any single character.
6. **Multiple longest palindromes:** Returning any one of them is valid.

---

# Key Takeaway

The important observation is that **every palindrome has a center**.

Instead of checking every possible substring:

1. Consider every index as an odd-length center.
2. Consider every pair of adjacent indices as an even-length center.
3. Expand outward while characters match.
4. Track the longest palindrome found.

This reduces the time complexity from `O(n³)` to `O(n²)` with constant auxiliary space.

---

# Solution

[View solution.java](./solution.java)
