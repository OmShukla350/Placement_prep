# 3. Longest Substring Without Repeating Characters

**Difficulty:** Medium | **Topic:** SlidingWindow | **Tags:** Hash Table, String, Sliding Window

---

## Problem

Given a string `s`, find the length of the longest substring without duplicate characters.

**Constraints:**
*   `0 <= s.length <= 5 * 10^4`
*   `s` consists of English letters, digits, symbols and spaces.

## Examples

**Example 1:**
```
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.
```

**Example 2:**
```
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
```

**Example 3:**
```
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
```

## Approach

This problem can be efficiently solved using the **Sliding Window** technique combined with a **Hash Set** (or frequency map/array) to keep track of characters within the current window.

The core idea is to maintain a "window" of characters that currently contains no repeating characters. We use two pointers, `left` (or `i`) and `right` (or `j`), to define this window `[left, right]`.

1.  **Initialization:**
    *   Initialize `left = 0` and `right = 0`. These pointers define the current window `s[left...right-1]`.
    *   Initialize `maxLength = 0` to store the maximum length found so far.
    *   Initialize a `HashSet` (or a `boolean` array for ASCII characters) called `charSet` to store characters currently present in the window `s[left...right-1]`.

2.  **Expand Window (Move `right` pointer):**
    *   Iterate the `right` pointer from the beginning of the string to its end.
    *   For each character `s[right]`:

3.  **Handle Duplicates (Shrink Window - Move `left` pointer):**
    *   Before adding `s[right]` to `charSet`, check if `s[right]` is already present in `charSet`.
    *   If `s[right]` **is** already in `charSet`: This means we have a duplicate. To resolve this, we need to shrink the window from the `left`.
        *   Remove `s[left]` from `charSet`.
        *   Increment `left`.
        *   Repeat this process (remove `s[left]`, increment `left`) until `s[right]` is no longer a duplicate within the current window (i.e., `charSet` no longer contains `s[right]`).
    *   If `s[right]` **is not** in `charSet` (or after resolving duplicates):

4.  **Add Current Character and Update Max Length:**
    *   Add `s[right]` to `charSet`.
    *   Calculate the current window's length: `right - left + 1`.
    *   Update `maxLength = max(maxLength, right - left + 1)`.

5.  **Move `right` pointer forward:**
    *   Increment `right` to expand the window to the next character.

6.  **Termination:**
    *   Continue this process until `right` reaches the end of the string.
    *   Finally, return `maxLength`.

This approach ensures that at any point, the window `s[left...right-1]` contains only unique characters, and we continuously update `maxLength` with the largest such window encountered.

## Dry Run

Let's trace the algorithm with `s = "abcabcbb"`:

*   `maxLen = 0`
*   `left = 0`
*   `right = 0`
*   `charSet = {}` (empty set)

| Step | `right` | `s[right]` | `charSet.contains(s[right])` | `while` loop action (`charSet.remove(s[left++])`) | `charSet` (after `while`) | `charSet.add(s[right])` | `charSet` (final) | `left` | Current Length (`right - left + 1`) | `maxLen` |
|------|---------|------------|------------------------------|----------------------------------------------------|---------------------------|-------------------------|-------------------|--------|---------------------------------------|----------|
| 1    | 0       | 'a'        | `false`                      | -                                                  | `{}`                      | `add('a')`              | `{'a'}`           | 0      | `0 - 0 + 1 = 1`                       | `1`      |
| 2    | 1       | 'b'        | `false`                      | -                                                  | `{'a'}`                   | `add('b')`              | `{'a', 'b'}`      | 0      | `1 - 0 + 1 = 2`                       | `2`      |
| 3    | 2       | 'c'        | `false`                      | -                                                  | `{'a', 'b'}`              | `add('c')`              | `{'a', 'b', 'c'}` | 0      | `2 - 0 + 1 = 3`                       | `3`      |
| 4    | 3       | 'a'        | `true`                       | `remove('a')`, `left=1`                            | `{'b', 'c'}`              | `add('a')`              | `{'b', 'c', 'a'}` | 1      | `3 - 1 + 1 = 3`                       | `3`      |
| 5    | 4       | 'b'        | `true`                       | `remove('b')`, `left=2`                            | `{'c', 'a'}`              | `add('b')`              | `{'c', 'a', 'b'}` | 2      | `4 - 2 + 1 = 3`                       | `3`      |
| 6    | 5       | 'c'        | `true`                       | `remove('c')`, `left=3`                            | `{'a', 'b'}`              | `add('c')`              | `{'a', 'b', 'c'}` | 3      | `5 - 3 + 1 = 3`                       | `3`      |
| 7    | 6       | 'b'        | `true`                       | `remove('a')`, `left=4`                            | `{'b', 'c'}`              |                         |                   |        |                                       |          |
|      |         |            |                              | `remove('b')`, `left=5`                            | `{'c'}`                   | `add('b')`              | `{'c', 'b'}`      | 5      | `6 - 5 + 1 = 2`                       | `3`      |
| 8    | 7       | 'b'        | `true`                       | `remove('c')`, `left=6`                            | `{'b'}`                   |                         |                   |        |                                       |          |
|      |         |            |                              | `remove('b')`, `left=7`                            | `{}`                      | `add('b')`              | `{'b'}`           | 7      | `7 - 7 + 1 = 1`                       | `3`      |

`right` is now 8, which is `s.length()`. The loop terminates.

**Result:** `maxLen = 3`

## Time Complexity

The time complexity is **O(N)**, where `N` is the length of the string `s`.
This is because both the `left` and `right` pointers traverse the string at most once. Each character `s[right]` is processed by the `right` pointer once, and `s[left]` is removed from the set by the `left` pointer at most once. Hash set operations (insertion, deletion, and lookup) take O(1) time on average.

## Space Complexity

The space complexity is **O(min(N, M))**, where `N` is the length of the string `s`, and `M` is the size of the character set (e.g., 128 for ASCII, 256 for extended ASCII).
In the worst case, if all characters in the string are unique, the `HashSet` will store up to `N` characters. However, if the character set is bounded (like ASCII characters, as specified by the constraints for English letters, digits, symbols and spaces), the maximum size of the `HashSet` will be limited by the size of the character set, which is a constant (e.g., 128 or 256). Thus, for a typical bounded character set, it can be considered **O(1)**.
For the generalized case without character set constraints, it would be O(N).