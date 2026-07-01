# 3713. Longest Balanced Substring I

**Difficulty:** Medium | **Topic:** TwoPointers | **Tags:** Hash Table, String, Counting, Enumeration

---

## Problem

You are given a string `s` consisting of lowercase English letters.

A substring of `s` is called balanced if all distinct characters in the substring appear the same number of times.

Return the length of the longest balanced substring of `s`.

**Constraints:**
*   `1 <= s.length <= 1000`
*   `s` consists of lowercase English letters.

## Examples

**Example 1:**
```
Input: s = "abbac"
Output: 4
Explanation:
The longest balanced substring is "abba" because both distinct characters 'a' and 'b' each appear exactly 2 times.
```

**Example 2:**
```
Input: s = "zzabccy"
Output: 4
Explanation:
The longest balanced substring is "zabc" because the distinct characters 'z', 'a', 'b', and 'c' each appear exactly 1 time.
```

**Example 3:**
```
Input: s = "aba"
Output: 2
Explanation:
One of the longest balanced substrings is "ab" because both distinct characters 'a' and 'b' each appear exactly 1 time. Another longest balanced substring is "ba".
```

## Approach

The problem asks us to find the longest substring where every distinct character within that substring appears the same number of times. Since the string length is relatively small (up to 1000), a brute-force approach iterating through all possible substrings and checking each for balance is feasible.

Here's the step-by-step algorithm:

1.  **Initialize `maxLen`**: Set a variable `maxLen` to 0. This will store the length of the longest balanced substring found so far.

2.  **Iterate through all possible starting points**: Use an outer loop with an index `i` from 0 to `s.length() - 1`. This `i` will represent the starting character of a potential substring.

3.  **Iterate through all possible ending points**: Inside the outer loop, use an inner loop with an index `j` from `i` to `s.length() - 1`. This `j` will represent the ending character of the current potential substring `s[i...j]`.

4.  **Maintain character frequencies**: For each substring `s[i...j]`, we need to keep track of the count of each character. A frequency array of size 26 (for 'a' through 'z') is suitable. Initialize this frequency array to all zeros at the beginning of each outer loop iteration (when `i` changes), and then update it as `j` extends the substring.
    *   When `j` increments, add `s.charAt(j)` to the current substring and increment its count in the frequency array.

5.  **Check for balance**: After adding `s.charAt(j)` and updating the frequency array, check if the current substring `s[i...j]` is balanced.
    *   To do this, iterate through the 26-element frequency array.
    *   Find the first character that has a non-zero count. Let this count be `commonCount`.
    *   Then, continue iterating through the rest of the frequency array. For any other character that has a non-zero count, check if its count is equal to `commonCount`.
    *   If all distinct characters (those with non-zero counts) have a count equal to `commonCount`, the substring is balanced. Otherwise, it is not.
    *   If no characters have non-zero counts (which won't happen for `j >= i`), it implies an empty set of distinct characters, which could be considered balanced, but our loop structure ensures at least one character.

6.  **Update `maxLen`**: If the current substring `s[i...j]` is balanced, calculate its length (`j - i + 1`) and update `maxLen` if this length is greater than the current `maxLen`.

7.  **Return `maxLen`**: After checking all possible substrings, `maxLen` will hold the length of the longest balanced substring. Return this value.

## Formula (if applicable)

Not applicable.

## Dry Run

Let's trace the algorithm with `s = "abbac"`:

Initial: `maxLen = 0`

| i | j | Substring `s[i...j]` | Frequencies (only non-zero shown) | `commonCount` | Is Balanced? | Current Length (`j-i+1`) | `maxLen` |
|---|---|-----------------------|-----------------------------------|---------------|--------------|--------------------------|----------|
| 0 |   |                       | (freq array reset for new `i`)    |               |              |                          |          |
|   | 0 | "a"                   | `{'a': 1}`                        | 1             | True         | 1                        | 1        |
|   | 1 | "ab"                  | `{'a': 1, 'b': 1}`                | 1             | True         | 2                        | 2        |
|   | 2 | "abb"                 | `{'a': 1, 'b': 2}`                | 1             | False (`b` count is 2, not 1) | 3                        | 2        |
|   | 3 | "abba"                | `{'a': 2, 'b': 2}`                | 2             | True         | 4                        | 4        |
|   | 4 | "abbac"               | `{'a': 2, 'b': 2, 'c': 1}`        | 2             | False (`c` count is 1, not 2) | 5                        | 4        |
| 1 |   |                       | (freq array reset for new `i`)    |               |              |                          |          |
|   | 1 | "b"                   | `{'b': 1}`                        | 1             | True         | 1                        | 4        |
|   | 2 | "bb"                  | `{'b': 2}`                        | 2             | True         | 2                        | 4        |
|   | 3 | "bba"                 | `{'a': 1, 'b': 2}`                | 1             | False (`b` count is 2, not 1) | 3                        | 4        |
|   | 4 | "bbac"                | `{'a': 1, 'b': 2, 'c': 1}`        | 1             | False (`b` count is 2, not 1) | 4                        | 4        |
| 2 |   |                       | (freq array reset for new `i`)    |               |              |                          |          |
|   | 2 | "b"                   | `{'b': 1}`                        | 1             | True         | 1                        | 4        |
|   | 3 | "ba"                  | `{'b': 1, 'a': 1}`                | 1             | True         | 2                        | 4        |
|   | 4 | "bac"                 | `{'b': 1, 'a': 1, 'c': 1}`        | 1             | True         | 3                        | 4        |
| 3 |   |                       | (freq array reset for new `i`)    |               |              |                          |          |
|   | 3 | "a"                   | `{'a': 1}`                        | 1             | True         | 1                        | 4        |
|   | 4 | "ac"                  | `{'a': 1, 'c': 1}`                | 1             | True         | 2                        | 4        |
| 4 |   |                       | (freq array reset for new `i`)    |               |              |                          |          |
|   | 4 | "c"                   | `{'c': 1}`                        | 1             | True         | 1                        | 4        |

The final `maxLen` returned is 4.

## Time Complexity

The time complexity is **O(N^2)**, where N is the length of the input string `s`.

*   The outer loop runs N times (for `i` from 0 to N-1).
*   The inner loop runs up to N times (for `j` from `i` to N-1).
*   Inside the inner loop:
    *   Updating character frequency is an **O(1)** operation (array access).
    *   Checking if a substring is balanced involves iterating through the 26-element frequency array, which is a constant number of operations. So, this check is also **O(1)**.

Therefore, the total time complexity is `N * N * O(1) = O(N^2)`. Given `N <= 1000`, `N^2` operations (approximately `1000^2 = 1,000,000`) are well within typical time limits for competitive programming.

## Space Complexity

The space complexity is **O(1)**.

*   A frequency array of size 26 is used to store character counts. Since the number of possible lowercase English letters is constant (26), the space required for this array does not depend on the input string's length N. This is considered constant space.