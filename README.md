# DSA — Data Structures & Algorithms

A structured collection of my solutions to Data Structures & Algorithms problems, focused on **problem-solving patterns, optimization, and interview preparation**.

The goal is not to solve as many problems as possible, but to understand the underlying patterns and be able to apply them to new problems.

## 🎯 Goals

- Build strong DSA fundamentals
- Recognize common problem-solving patterns
- Improve time and space complexity analysis
- Practice explaining solutions clearly

---

## 🧠 Problem-Solving Approach

For every problem, I try to follow this process:

1. Understand the problem
2. Identify the brute-force approach
3. Analyze its complexity
4. Look for a better pattern/data structure
5. Implement the optimized solution
6. Analyze time and space complexity
7. Test edge cases
8. Explain the solution in my own words
9. Revisit the problem later

---

## 🔄 Revision Strategy

Problems are revisited using spaced repetition:

```text
Day 0  → First attempt
Day 1  → First revision
Day 7  → Second revision
Day 30 → Final revision
```

A problem is considered **mastered** only when I can solve it again without relying on the previous implementation.

---

## 🗂️ Repository Structure

```text
dsa/
│
├── arrays/
├── hashing/
├── two-pointers/
├── sliding-window/
├── stack/
├── binary-search/
├── linked-list/
├── trees/
├── bst/
├── heaps/
├── graphs/
├── backtracking/
├── trie/
├── greedy/
├── intervals/
└── dynamic-programming/
```

Each problem follows a consistent structure:

```text
problem-name/
├── README.md
└── solution.java
```

---

## 📝 Problem Documentation

Each problem contains:

- Problem statement / link
- Pattern
- Difficulty
- Approach
- Key insight
- Complexity
- Edge cases
- What I learned

Example:

```text
# Two Sum

🔗 LeetCode: https://leetcode.com/problems/two-sum/

Difficulty: Easy
Pattern: Hash Map

## Approach

Store previously seen numbers in a hash map.

For every element, check whether:

target - currentNumber

already exists in the map.

## Complexity

Time: O(n)
Space: O(n)

## Key Insight

Trade O(n) additional space for O(1) average lookup,
reducing the brute-force O(n²) solution to O(n).
```

## 💻 Language

Primary language:

**Java**

I use Java to strengthen my problem-solving skills while staying aligned with my backend engineering experience.

## 📈 Philosophy

> **Don't memorize solutions. Learn the patterns.**

A problem is not truly solved when the code passes.

It is solved when I can recognize the underlying pattern and apply the same idea to a new problem.
