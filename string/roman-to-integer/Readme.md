# Roman to Integer

## LeetCode

- **Problem:** [Roman to Integer](https://leetcode.com/problems/roman-to-integer/)
- **Difficulty:** Easy
- **Pattern:** String / Greedy

## Problem Description

Roman numerals use seven symbols to represent values:

| Symbol | Value |
|---|---:|
| I | 1 |
| V | 5 |
| X | 10 |
| L | 50 |
| C | 100 |
| D | 500 |
| M | 1000 |

Given a string `s` representing a Roman numeral, convert it into an integer.

Normally, Roman numeral values are added together. However, when a smaller value appears before a larger value, the smaller value is subtracted instead.

For example:
- `VI = 5 + 1 = 6`
- `IV = 5 - 1 = 4`
- `IX = 10 - 1 = 9`
- `MCMXCIV = 1000 + 900 + 90 + 4 = 1994`

### Examples

**Example 1**

```text
Input: s = "III"
Output: 3
```

Explanation: `1 + 1 + 1 = 3`.

**Example 2**

```text
Input: s = "LVIII"
Output: 58
```

Explanation: `50 + 5 + 1 + 1 + 1 = 58`.

**Example 3**

```text
Input: s = "MCMXCIV"
Output: 1994
```

Explanation: `M = 1000`, `CM = 900`, `XC = 90`, and `IV = 4`.

### Constraints

- `1 <= s.length <= 15`
- `s` contains only the characters `I`, `V`, `X`, `L`, `C`, `D`, and `M`.
- The input is guaranteed to be a valid Roman numeral in the range `1` to `3999`.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Identify the six special subtractive combinations:

- `IV = 4`
- `IX = 9`
- `XL = 40`
- `XC = 90`
- `CD = 400`
- `CM = 900`

Replace each special combination with its corresponding value, then add the values of the remaining symbols.

### Algorithm

1. Traverse the string and check for subtractive pairs.
2. If a pair is found, add its combined value and skip both characters.
3. Otherwise, add the current symbol's value.
4. Continue until all characters are processed.

### Complexity Analysis

- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(1)` auxiliary space if the string is processed directly without creating a modified copy.

### Why improve it?

Explicitly checking every special pair adds unnecessary conditions. We can use a single rule that handles all subtractive cases.

---

# 2. Optimal Approach — Compare With the Next Symbol

## Core Idea

For each Roman numeral, compare its value with the value of the next symbol.

- If the current value is **smaller than the next value**, subtract it.
- Otherwise, add it.

This works because a smaller symbol before a larger symbol represents subtraction in a valid Roman numeral.

## Step 1: Initialize the total

```java
int total = 0;
int length = s.length();
```

`total` accumulates the final integer value.

## Step 2: Convert the current symbol

```java
int currentVal = getVal(s.charAt(i));
```

The `getVal()` helper method maps each Roman numeral to its integer value using a `switch` statement.

For example:

```java
case 'I': return 1;
case 'V': return 5;
case 'X': return 10;
```

The same mapping is defined for all seven symbols.

## Step 3: Compare the current symbol with the next symbol

```java
if (i < length - 1 && currentVal < getVal(s.charAt(i + 1))) {
    total -= currentVal;
} else {
    total += currentVal;
}
```

The condition `i < length - 1` ensures that the next character is accessed only when it exists.

**Case 1: Current value is smaller than the next value**

For `"IV"`:

- `I = 1`, and the next symbol is `V = 5`.
- Since `1 < 5`, subtract `1`.
- Add `5` when it is processed.

Result: `-1 + 5 = 4`.

**Case 2: Current value is greater than or equal to the next value**

For `"VI"`:

- `V = 5`, and the next symbol is `I = 1`.
- Since `5` is not smaller than `1`, add `5`.
- Add `1` when it is processed.

Result: `5 + 1 = 6`.

## Step 4: Return the total

```java
return total;
```

After every symbol has been processed, `total` contains the integer representation of the Roman numeral.

---

# Dry Run

Consider:

```text
s = "MCMXCIV"
```

| Symbol | Current value | Next value | Operation | Total |
|---|---:|---:|---|---:|
| M | 1000 | 100 | Add 1000 | 1000 |
| C | 100 | 1000 | Subtract 100 | 900 |
| M | 1000 | 100 | Add 1000 | 1900 |
| X | 10 | 100 | Subtract 10 | 1890 |
| C | 100 | 1 | Add 100 | 1990 |
| I | 1 | 5 | Subtract 1 | 1989 |
| V | 5 | — | Add 5 | 1994 |

**Output:** `1994`

---

# Why Does This Approach Work?

In a valid Roman numeral, a symbol is subtracted when it appears immediately before a symbol with a greater value. Otherwise, its value is added.

By applying this rule to every character, the algorithm correctly handles both ordinary addition and subtractive combinations without needing to identify each special pair separately.

---

# Complexity Analysis

Let `n` be the length of the Roman numeral.

- **Time Complexity:** `O(n)` — each character is visited once, and each lookup takes constant time.
- **Auxiliary Space Complexity:** `O(1)` — only a fixed number of integer variables are used.

This is optimal because each character must be examined to calculate the result.

---

# Edge Cases

1. **Single symbol:** `"V"` returns `5`.
2. **Repeated symbols:** `"III"` returns `3`.
3. **Subtractive pair:** `"IV"` returns `4`.
4. **Multiple subtractive pairs:** `"MCMXCIV"` returns `1994`.
5. **Last character:** The bounds check ensures the algorithm does not access a nonexistent next character.

---

# Key Takeaway

The important rule for converting Roman numerals is:

- **Current value smaller than next value:** subtract the current value.
- **Otherwise:** add the current value.

This simple comparison handles all valid Roman numeral combinations in one pass, giving **O(n) time and O(1) auxiliary space**.

---

# Solution

[View solution.java](./solution.java)
