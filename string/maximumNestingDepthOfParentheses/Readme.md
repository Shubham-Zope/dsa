# Maximum Nesting Depth of the Parentheses

## LeetCode

- **Problem:** [Maximum Nesting Depth of the Parentheses](https://leetcode.com/problems/maximum-nesting-depth-of-the-parentheses/)
- **Difficulty:** Easy
- **Pattern:** String / Counting / Parentheses Depth

## Problem Description

Given a valid parentheses string `s`, return its **maximum nesting depth**.

The nesting depth is the maximum number of opening parentheses that are simultaneously unmatched at any point in the string.

For example, in `"((()))"`, the maximum depth is `3` because three opening parentheses are nested inside one another.

### Examples

**Example 1**

```text
Input: s = "(1+(2*3)+((8)/4))+1"
Output: 3
```

Explanation: The deepest nested section contains three levels of parentheses.

**Example 2**

```text
Input: s = "(1)+((2))+(((3)))"
Output: 3
```

Explanation: The maximum nesting depth occurs in `"(((3)))"`.

**Example 3**

```text
Input: s = "()(())"
Output: 2
```

Explanation: The deepest section is `"(())"`, which has a nesting depth of `2`.

### Constraints

- `1 <= s.length <= 100`
- `s` consists of digits, `'+'`, `'-'`, `'*'`, `'/'`, `'('`, and `')'`.
- The input string is a valid parentheses string.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Use a stack to track opening parentheses. Whenever an opening parenthesis is encountered, push it onto the stack. Whenever a closing parenthesis is encountered, pop from the stack.

The maximum stack size reached during traversal represents the maximum nesting depth.

### Algorithm

1. Initialize an empty stack and a variable `ans = 0`.
2. Traverse each character in the string.
3. If the character is `'('`, push it onto the stack and update the maximum stack size.
4. If the character is `')'`, pop from the stack.
5. Return the maximum stack size.

### Complexity Analysis

- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(n)` for the stack in the worst case.

### Why improve it?

We only need to know the current number of unmatched opening parentheses. Storing every opening parenthesis in a stack is unnecessary.

---

# 2. Optimal Approach — Counter

## Core Idea

Maintain two integer variables:

- `cnt`: the current nesting depth.
- `ans`: the maximum nesting depth encountered so far.

Increase `cnt` when an opening parenthesis is encountered and decrease it when a closing parenthesis is encountered.

Update `ans` whenever the current depth increases.

## Step 1: Initialize variables

```java
int cnt = 0;
int ans = 0;
```

- `cnt` tracks the current depth.
- `ans` stores the maximum depth.

## Step 2: Traverse the string

```java
for (int i = 0; i < s.length(); i++) {
    char ch = s.charAt(i);
}
```

Process each character exactly once.

## Step 3: Increase the depth for opening parentheses

```java
if (ch == '(') {
    cnt++;
}
```

Each opening parenthesis adds one level of nesting.

## Step 4: Update the maximum depth

```java
ans = Math.max(ans, cnt);
```

Compare the current depth with the maximum depth recorded so far.

This update happens after processing an opening parenthesis, so the new depth is considered immediately.

## Step 5: Decrease the depth for closing parentheses

```java
if (ch == ')') {
    cnt--;
}
```

A closing parenthesis ends one level of nesting.

Since the input is guaranteed to be valid, the counter will not become negative.

## Step 6: Return the answer

```java
return ans;
```

After the traversal, `ans` contains the maximum nesting depth.

---

# Dry Run

Consider:

```text
s = "(1+(2*3)+((8)/4))+1"
```

| Character | Current depth (`cnt`) | Maximum (`ans`) |
|---|---:|---:|
| `(` | 1 | 1 |
| `1` | 1 | 1 |
| `+` | 1 | 1 |
| `(` | 2 | 2 |
| `2` | 2 | 2 |
| `*` | 2 | 2 |
| `3` | 2 | 2 |
| `)` | 1 | 2 |
| `+` | 1 | 2 |
| `(` | 2 | 2 |
| `(` | 3 | 3 |
| `8` | 3 | 3 |
| `)` | 2 | 3 |
| `)` | 1 | 3 |

The remaining characters do not increase the depth beyond `3`.

**Output:** `3`

---

# Why Does This Approach Work?

Every opening parenthesis starts a new nesting level, while every closing parenthesis ends one.

Therefore, the counter always represents the current nesting depth. By keeping track of the largest value reached by this counter, we obtain the maximum nesting depth of the string.

Other characters, such as digits and arithmetic operators, do not affect the nesting depth.

---

# Complexity Analysis

Let `n` be the length of the string.

- **Time Complexity:** `O(n)` — each character is processed once.
- **Auxiliary Space Complexity:** `O(1)` — only two integer variables are used.

This is optimal because every character must be examined to determine the maximum depth.

---

# Edge Cases

1. **No nested parentheses:** `"1+2"` has a depth of `0`.
2. **Single pair:** `"(1)"` has a depth of `1`.
3. **Deep nesting:** `"((()))"` has a depth of `3`.
4. **Sequential expressions:** `"()()"` has a maximum depth of `1`.
5. **Mixed nesting:** `"()(())"` has a maximum depth of `2`.

---

# Key Takeaway

For parentheses problems, determine whether you need the actual opening positions or only the current nesting depth.

If only the depth matters, a simple counter is sufficient.

- Increment for `'('`.
- Update the maximum depth.
- Decrement for `')'`.

This produces an optimal **O(n) time and O(1) auxiliary space** solution.

---

# Solution

[View solution.java](./solution.java)
