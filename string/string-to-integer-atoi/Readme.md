# String to Integer (atoi)

## LeetCode

- **Problem:** [String to Integer (atoi)](https://leetcode.com/problems/string-to-integer-atoi/)
- **Difficulty:** Medium
- **Pattern:** String / Simulation / Overflow Handling

## Problem Description

Implement the `myAtoi(String s)` function, which converts a string into a 32-bit signed integer.

The conversion follows these rules:

1. **Ignore leading whitespace:** Skip any leading spaces.
2. **Determine the sign:** If the next character is `'-'`, the number is negative. If it is `'+'`, the number is positive. If neither appears, assume the number is positive.
3. **Convert digits:** Read consecutive numeric digits until a non-digit character is encountered or the string ends.
4. **Handle overflow:** If the number exceeds the 32-bit signed integer range, clamp it to `Integer.MAX_VALUE` or `Integer.MIN_VALUE`.
5. **No valid digits:** If no digits are found after the optional sign and leading spaces, return `0`.

The valid 32-bit signed integer range is:

```text id="g76n7x"
Integer.MIN_VALUE = -2147483648
Integer.MAX_VALUE =  2147483647
```

### Examples

**Example 1**

```text id="eq40mp"
Input: s = "42"
Output: 42
```

**Example 2**

```text id="m3kk1x"
Input: s = "   -042"
Output: -42
```

**Example 3**

```text id="w3ezrv"
Input: s = "1337c0d3"
Output: 1337
```

Explanation: Conversion stops when the first non-digit character is encountered.

**Example 4**

```text id="92g39q"
Input: s = "words and 987"
Output: 0
```

Explanation: The first non-whitespace character is not a valid sign or digit.

**Example 5**

```text id="k3px66"
Input: s = "-91283472332"
Output: -2147483648
```

Explanation: The value is below the minimum 32-bit integer, so it is clamped to `Integer.MIN_VALUE`.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Trim the leading spaces, identify the sign, extract the consecutive digits, convert them into a number, and then check whether the result is within the 32-bit integer range.

### Algorithm

1. Skip leading spaces.
2. Read the optional sign.
3. Extract consecutive digits.
4. Convert the extracted digits into a numeric value.
5. Clamp the result to the integer range if it overflows.

### Complexity Analysis

- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(n)` if a separate digit string is created.

### Why improve it?

Building a separate string and converting it afterward is unnecessary. We can process each digit directly and detect overflow before updating the number.

---

# 2. Optimal Approach — Single-Pass Conversion

## Core Idea

Traverse the input string once and maintain three variables:

- `i`: the current index.
- `sign`: `1` for positive numbers and `-1` for negative numbers.
- `base`: the numeric value accumulated from the digits.

Instead of creating a substring, update `base` for each digit. Before updating it, check whether the next operation would cause integer overflow.

## Step 1: Handle an empty input

```java
if (s == null || s.isEmpty())
    return 0;
```

If the input is null or empty, there is no number to convert.

## Step 2: Skip leading spaces

```java
while (i < n && s.charAt(i) == ' ') {
    i++;
}
```

Ignore spaces at the beginning of the string.

For example:

```text id="fh5i8z"
"   123"  ->  "123"
```

If the entire string contains only spaces, return `0`.

## Step 3: Determine the sign

```java
if (s.charAt(i) == '-' || s.charAt(i) == '+') {
    sign = (s.charAt(i) == '-') ? -1 : 1;
    i++;
}
```

Read an optional sign before processing digits.

Examples:

- `"-123"` gives `sign = -1`.
- `"+123"` gives `sign = 1`.
- `"123"` keeps the default `sign = 1`.

Only one sign is allowed, and it must appear before the digits.

## Step 4: Process consecutive digits

```java
while (i < n && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
    int digit = s.charAt(i) - '0';
}
```

Convert each digit character into its integer value.

For example, `'7' - '0'` gives `7`.

The loop stops when a non-digit character is encountered.

## Step 5: Check for overflow before updating the number

The next operation is:

```java
base = base * 10 + digit;
```

Before performing it, check whether the current value is already too large or whether adding the next digit would exceed the permitted limit.

For a positive number, the maximum allowed value is `2147483647`, so the final digit cannot exceed `7` when the current value is `214748364`.

For a negative number, the magnitude may reach `2147483648`, so the final digit can be `8`.

```java
if (base > Integer.MAX_VALUE / 10 ||
    (base == Integer.MAX_VALUE / 10 &&
     digit > (sign == 1 ? 7 : 8))) {
    return sign == 1
            ? Integer.MAX_VALUE
            : Integer.MIN_VALUE;
}
```

This check prevents overflow before multiplication or addition occurs.

## Step 6: Update the number

```java
base = base * 10 + digit;
i++;
```

If the overflow check passes, append the digit mathematically and continue.

## Step 7: Apply the sign

```java
return base * sign;
```

After all consecutive digits have been processed, apply the sign and return the result.

**Important:** To correctly support `-2147483648`, the implementation must avoid overflowing while constructing the positive magnitude. The condition above fixes the final-digit threshold, but `base` as an `int` still cannot represent `2147483648`. A fully correct implementation should accumulate using a `long`, or handle the negative limit separately.

---

# Why Does This Approach Work?

The algorithm follows the conversion rules in order: leading spaces, optional sign, and consecutive digits.

It stops at the first invalid character and checks for overflow before each numeric update. This avoids unnecessary string creation and ensures that oversized numbers are clamped to the valid 32-bit integer range.

---

# Complexity Analysis

Let `n` be the length of the input string.

- **Time Complexity:** `O(n)` — each character is visited at most once.
- **Auxiliary Space Complexity:** `O(1)` — only a fixed number of variables are used.

---

# Edge Cases

1. **Empty string:** `""` returns `0`.
2. **Leading spaces:** `"   42"` returns `42`.
3. **Negative number:** `"-42"` returns `-42`.
4. **Explicit positive sign:** `"+42"` returns `42`.
5. **Non-digit at the start:** `"abc123"` returns `0`.
6. **Digits followed by letters:** `"123abc"` returns `123`.
7. **Positive overflow:** `"2147483648"` returns `2147483647`.
8. **Negative overflow:** `"-2147483649"` returns `-2147483648`.
9. **Sign without digits:** `"+"` or `"-"` returns `0`.

---

# Key Takeaway

String-to-integer conversion is a parsing problem that requires careful handling of whitespace, signs, digit sequences, and integer limits.

The key technique is to **check for overflow before multiplying the current number by ten and adding the next digit**.

Use a `long` accumulator or a carefully designed negative-number conversion to handle the full 32-bit signed integer range safely.

---

# Solution

[View solution.java](./solution.java)
