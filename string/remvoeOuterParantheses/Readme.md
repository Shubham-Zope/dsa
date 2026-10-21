# Remove Outermost Parentheses

## LeetCode

- **Problem:** [Remove Outermost Parentheses](https://leetcode.com/problems/remove-outermost-parentheses/)
- **Difficulty:** Easy
- **Pattern:** String / Parentheses Depth

## Problem Description

A **primitive valid parentheses string** is a non-empty valid parentheses string that cannot be split into two non-empty valid parentheses strings.

Given a valid parentheses string `s`, remove the outermost pair of parentheses from every primitive substring and return the resulting string.

### Examples

**Example 1**

```text
Input: s = "(()())(())"
Output: "()()()"
```

Explanation:

The primitive substrings are `"(()())"` and `"(())"`.

- `"(()())"` becomes `"()()"`
- `"(())"` becomes `"()"`

Combining the results gives `"()()()"`.

**Example 2**

```text
Input: s = "(()())(())(()(()))"
Output: "()()()()(())"
```

Explanation:

Each primitive substring has its outermost opening and closing parentheses removed.

**Example 3**

```text
Input: s = "()()"
Output: ""
```

Explanation:

Each `"()"` is a primitive substring. Removing its outermost pair leaves an empty string.

### Constraints

- `1 <= s.length <= 10^5`
- `s[i]` is either `'('` or `')'`.
- The input string is valid.

---

# Thought Process

## 1. Brute Force Approach

### Idea

Identify each primitive substring separately, remove its first and last characters, and append the remaining characters to the result.

### Algorithm

1. Traverse the string while tracking the parentheses depth.
2. When the depth returns to zero, a primitive substring has ended.
3. Extract that primitive substring.
4. Remove its first and last characters.
5. Append the remaining characters to the result.

### Complexity Analysis

- **Time Complexity:** `O(n)`
- **Space Complexity:** `O(n)` for the resulting string and temporary substring handling.

### Why improve it?

Creating separate substrings for every primitive is unnecessary. We can identify and remove the outermost parentheses during a single traversal.

---

# 2. Optimal Approach — Track Parentheses Depth

## Core Idea

We do not need an actual stack data structure because the problem only requires knowing the current nesting depth.

Maintain an integer variable `stack` that represents the number of unmatched opening parentheses before processing the next character.

- When processing `'('`, the current depth tells us whether it is an outermost opening parenthesis.
- When processing `')'`, decrease the depth first. The updated depth tells us whether it is an outermost closing parenthesis.

Append only the parentheses that are not outermost.

## Step 1: Initialize the result and depth

```java
StringBuilder result = new StringBuilder();
int stack = 0;
```

- `result` stores the final string.
- `stack` tracks the current nesting depth.

Despite its name, `stack` is just an integer counter, not a stack data structure.

## Step 2: Traverse the string

```java
for (char ch : s.toCharArray()) {
    // Process each parenthesis
}
```

Process each character exactly once.

## Step 3: Handle opening parentheses

```java
if (ch == '(') {
    if (stack > 0) {
        result.append(ch);
    }
    stack++;
}
```

If `stack == 0`, the opening parenthesis starts a new primitive substring. It is an outermost parenthesis, so we skip it.

If `stack > 0`, the opening parenthesis is nested inside the primitive, so we append it.

Finally, increment the depth.

## Step 4: Handle closing parentheses

```java
else {
    stack--;
    if (stack > 0) {
        result.append(ch);
    }
}
```

First, decrement the depth.

If the updated depth is zero, this closing parenthesis ends the current primitive substring. It is the outermost closing parenthesis, so we skip it.

If the updated depth is greater than zero, it is an inner closing parenthesis, so we append it.

**Important:** Decrement the depth before checking whether to append a closing parenthesis. Otherwise, the outermost closing parenthesis would incorrectly be included.

## Step 5: Return the result

```java
return result.toString();
```

The resulting string contains all the inner parentheses, with the outermost pair of each primitive removed.

---

# Dry Run

Consider:

```text
s = "(()())(())"
```

| Character | Depth after processing | Action |
|---|---:|---|
| `(` | 1 | Skip outer opening |
| `(` | 2 | Append `(` |
| `)` | 1 | Append `)` |
| `(` | 2 | Append `(` |
| `)` | 1 | Append `)` |
| `)` | 0 | Skip outer closing |
| `(` | 1 | Skip outer opening |
| `(` | 2 | Append `(` |
| `)` | 1 | Append `)` |
| `)` | 0 | Skip outer closing |

Final result:

```text
"()()()"
```

---

# Why Does This Approach Work?

A valid parentheses string has a depth that increases with every opening parenthesis and decreases with every closing parenthesis.

For each primitive substring:

- Its outermost opening parenthesis is encountered when the depth is zero before incrementing.
- Its outermost closing parenthesis is encountered when the depth becomes zero after decrementing.
- All other parentheses occur at a positive nesting depth and are appended to the result.

Therefore, the algorithm removes exactly the outermost pair of every primitive substring while preserving the order of all remaining characters.

---

# Complexity Analysis

Let `n` be the length of the input string.

- **Time Complexity:** `O(n)` — every character is processed once.
- **Auxiliary Space Complexity:** `O(1)` excluding the output — only the depth counter and a few variables are used.
- **Output Space Complexity:** `O(n)` — the `StringBuilder` can store up to `n` characters.

---

# Edge Cases

1. **Input `"()"`:** The only primitive has no inner parentheses, so the output is `""`.
2. **Input `"()()"`:** Both primitive substrings become empty, producing `""`.
3. **Deeply nested parentheses:** The depth counter handles nesting without needing a stack.
4. **Multiple primitive substrings:** The depth returns to zero between primitives, allowing each outermost pair to be skipped independently.

---

# Key Takeaway

A stack is not always necessary for parentheses problems.

When we only need to track nesting depth, an integer counter can replace a stack and simplify the solution.

Remember:

- For `'('`, append only if the current depth is greater than zero, then increment.
- For `')'`, decrement first, then append only if the updated depth is greater than zero.

This gives a clean, single-pass solution with `O(n)` time complexity.

---

# Solution

[View solution.java](./solution.java)
