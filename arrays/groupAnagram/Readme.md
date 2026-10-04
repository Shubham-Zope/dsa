# Group Anagrams

## LeetCode

- **Problem:** [Group Anagrams](https://leetcode.com/problems/group-anagrams/)
- **Difficulty:** Medium
- **Pattern:** Hashing + Sorting / Frequency Counting

---

## Problem Description

Given an array of strings `strs`, group the anagrams together.

Two strings are anagrams if they contain the same characters with the same frequencies, but possibly in a different order.

### Example

**Input:**

```text
["eat","tea","tan","ate","nat","bat"]
```

**Output:**

```text
[["eat","tea","ate"],["tan","nat"],["bat"]]
```

### Explanation

- `"eat"`, `"tea"`, and `"ate"` are anagrams.
- `"tan"` and `"nat"` are anagrams.
- `"bat"` has no other anagram.

---

## Constraints

- `1 <= strs.length <= 10⁴`
- `0 <= strs[i].length <= 100`
- `strs[i]` consists of lowercase English letters.

---

# Thought Process

The first question to ask is:

> **How can I identify whether two strings are anagrams without repeatedly comparing them?**

For example:

```text
eat
tea
ate
```

All three strings contain exactly the same characters.

There are two useful ways to create a common representation for them.

### Approach 1 — Sort the characters

```text
eat → aet
tea → aet
ate → aet
```

All anagrams produce the same sorted string.

This can be used as a HashMap key.

### Approach 2 — Count character frequencies

Instead of sorting, count how many times each character occurs.

```text
eat → a:1, e:1, t:1
tea → a:1, e:1, t:1
ate → a:1, e:1, t:1
```

Since the problem guarantees lowercase English letters, we only need an array of size `26`.

This avoids sorting completely.

Therefore, the progression is:

```text
Brute Force
    ↓
Compare strings directly
    ↓
HashMap + sorted string
    ↓
HashMap + character frequency
```

---

# 1. Brute Force Approach

## Idea

Compare every string with every other string and check whether they are anagrams.

For every string:

1. Compare it with other strings.
2. Check whether both strings contain the same characters with the same frequencies.
3. If they are anagrams, put them into the same group.
4. Keep track of strings that have already been grouped.

For example:

```text
eat ↔ tea → anagrams
eat ↔ tan → not anagrams
eat ↔ ate → anagrams
```

## Problem With This Approach

We repeatedly compare strings against each other.

If there are `n` strings, we can end up doing approximately `O(n²)` comparisons.

This becomes expensive as the input grows.

## Complexity

Let:

- `n` = number of strings
- `k` = maximum string length

Checking whether two strings are anagrams takes `O(k)`.

### Time

- **Best:** `O(nk)`
- **Average:** `O(n²k)`
- **Worst:** `O(n²k)`

### Space

- **Best:** `O(n)`
- **Average:** `O(n)`
- **Worst:** `O(n)`

The extra space is mainly for storing the result.

---

# 2. Better Approach — HashMap + Sorted String

## Thought Process

Instead of comparing every string with every other string, we want a common representation for all anagrams.

Consider:

```text
eat → aet
tea → aet
ate → aet
```

Sorting gives us exactly that.

So we can use the sorted string as the HashMap key:

```text
"aet" → ["eat", "tea", "ate"]
"ant" → ["tan", "nat"]
"abt" → ["bat"]
```

## Algorithm

For every string:

1. Convert it into a character array.
2. Sort the characters.
3. Convert the sorted characters back to a string.
4. Use that sorted string as the HashMap key.
5. Add the original string to the corresponding list.
6. Return all HashMap values.

## Complexity

Let:

- `n` = number of strings
- `k` = maximum string length

Sorting one string takes `O(k log k)`.

### Time

- **Best:** `O(nk log k)`
- **Average:** `O(nk log k)`
- **Worst:** `O(nk log k)`

### Space

- **Best:** `O(nk)`
- **Average:** `O(nk)`
- **Worst:** `O(nk)`

---

# 3. Optimal Approach — HashMap + Character Frequency

## Thought Process

The previous approach works, but there is something unnecessary happening:

```text
eat → sorting → aet
tea → sorting → aet
ate → sorting → aet
```

We only sort because we need to determine the characters and their frequencies.

But the problem gives us an important constraint:

> The strings contain only lowercase English letters.

There are only **26 possible characters**.

So instead of sorting, we can directly count the frequency of every character.

For example:

```text
eat
```

produces:

```text
a → 1
b → 0
c → 0
d → 0
e → 1
...
t → 1
...
z → 0
```

Represent this using:

```java
int[] count = new int[26];
```

Now:

```text
eat → [1,0,0,0,1,0,...,1,...]
tea → [1,0,0,0,1,0,...,1,...]
ate → [1,0,0,0,1,0,...,1,...]
```

All three have the exact same frequency array.

Therefore, the frequency representation can be used as the HashMap key.

---

## Algorithm

For every string:

1. Create an integer array of size `26`.
2. Traverse every character.
3. Increment:

```java
count[c - 'a']++;
```

4. Convert the frequency array into a key.
5. Use the key in a HashMap.
6. Add the original string to that key's list.
7. Return all HashMap values.

---

## Example

For:

```text
["eat", "tea", "tan", "ate", "nat", "bat"]
```

The frequency signatures are conceptually:

```text
eat → a1 e1 t1
tea → a1 e1 t1
tan → a1 n1 t1
ate → a1 e1 t1
nat → a1 n1 t1
bat → a1 b1 t1
```

So the HashMap becomes:

```text
a1e1t1 → ["eat", "tea", "ate"]
a1n1t1 → ["tan", "nat"]
a1b1t1 → ["bat"]
```

---

## Complexity

Let:

- `n` = number of strings
- `k` = maximum string length

For every string, we scan its characters once.

### Time

- **Best:** `O(nk)`
- **Average:** `O(nk)`
- **Worst:** `O(nk)`

The frequency array always has only 26 positions, so creating the key is effectively constant relative to the alphabet size.

### Space

- **Best:** `O(nk)`
- **Average:** `O(nk)`
- **Worst:** `O(nk)`

The output itself can contain all `n` strings, and the HashMap stores the groups and their keys.

---

# Why Is This Better?

The sorted-string approach does:

```text
String
   ↓
Convert to char[]
   ↓
Sort
   ↓
Create key
   ↓
HashMap
```

Sorting costs:

```text
O(k log k)
```

The frequency approach does:

```text
String
   ↓
Count characters
   ↓
Create key
   ↓
HashMap
```

Counting costs:

```text
O(k)
```

Therefore:

```text
Sorting approach:    O(nk log k)

Frequency approach:  O(nk)
```

The frequency approach removes the sorting step entirely.

---

# Why Does the Optimal Approach Work?

Two strings are anagrams if and only if they have the same frequency for every character.

For example:

```text
"eat"
```

contains:

```text
a → 1
e → 1
t → 1
```

and:

```text
"tea"
```

also contains:

```text
a → 1
e → 1
t → 1
```

Therefore, their frequency arrays are identical.

On the other hand:

```text
"eat"
```

and:

```text
"bat"
```

have different frequencies:

```text
eat → a1 e1 t1
bat → a1 b1 t1
```

So they receive different HashMap keys.

Therefore:

> **Two strings belong to the same anagram group if and only if their character-frequency keys are identical.**

---

# Edge Cases

### 1. Empty String

```text
[""]
```

The frequency array contains all zeros.

Result:

```text
[[""]]
```

### 2. Single String

```text
["abc"]
```

Result:

```text
[["abc"]]
```

### 3. All Strings Are Anagrams

```text
["abc", "bca", "cab"]
```

All three have the same frequency key.

Result:

```text
[["abc", "bca", "cab"]]
```

### 4. No Anagrams

```text
["abc", "def", "ghi"]
```

Each string gets a different frequency key.

---

# Key Takeaway

### Pattern

**Hashing + Canonical Representation**

The important idea is not simply:

> "Use a HashMap."

The important idea is:

> **Convert equivalent objects into the same canonical representation and use that representation as a HashMap key.**

For this problem, there are two useful canonical representations:

```text
Anagram
   ↓
Sorted characters
   ↓
HashMap key
```

or, more efficiently:

```text
Anagram
   ↓
Character frequencies
   ↓
HashMap key
```

### General Lesson

When you see a grouping problem, ask:

> **Can I create a unique signature/key for things that should belong to the same group?**

This is a very important hashing pattern.

---

# Solution

See [`solution.java`](./solution.java) for the Java implementation.

The current implementation uses the **HashMap + sorted string** approach.