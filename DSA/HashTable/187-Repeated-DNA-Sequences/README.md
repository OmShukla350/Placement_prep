# 187. Repeated DNA Sequences

**Difficulty:** Medium | **Topic:** HashTable | **Tags:** Hash Table, String, Bit Manipulation, Sliding Window, Rolling Hash, Hash Function

---

## Problem

The DNA sequence is composed of a series of nucleotides abbreviated as 'A', 'C', 'G', and 'T'. When studying DNA, it is useful to identify repeated sequences within the DNA.

Given a string `s` that represents a DNA sequence, return all the 10-letter-long sequences (substrings) that occur more than once in a DNA molecule. The order of the returned sequences does not matter.

**Constraints:**

*   `1 <= s.length <= 10^5`
*   `s[i]` is either 'A', 'C', 'G', or 'T'.

## Examples

**Example 1:**
```
Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
Output: ["AAAAACCCCC","CCCCCAAAAA"]
```

**Example 2:**
```
Input: s = "AAAAAAAAAAAAA"
Output: ["AAAAAAAAAA"]
```

## Approach

The problem asks us to find all 10-letter-long substrings that appear more than once in a given DNA sequence. A straightforward way to track occurrences of elements and identify duplicates is by using hash sets.

**Intuition:**

We need to iterate through all possible 10-letter substrings in the given DNA sequence `s`. For each substring, we need to know if we have encountered it before. If we have, then it's a repeated sequence that should be added to our result. If it's the first time we're seeing it, we just need to record its presence.

**Algorithm:**

1.  Initialize two hash sets:
    *   `seen_sequences`: This set will store every unique 10-letter sequence we encounter during our scan.
    *   `repeated_sequences`: This set will store all 10-letter sequences that have been identified as repeats (i.e., seen more than once). Using a set for this ensures that each repeated sequence is only stored once in the final result, even if it appears many times.

2.  Iterate through the input string `s` using a sliding window of length 10. The window will start at index `i` and end at `i + 9`.
    *   The loop should run from `i = 0` up to `s.length() - 10`. This ensures that we always have enough characters to form a 10-letter substring. For example, if `s.length()` is 10, `i` will only be `0` (10 - 10 = 0).

3.  Inside the loop, for each `i`:
    *   Extract the current 10-letter `substring` from `s` starting at index `i`.

4.  Check if this `substring` is already present in `seen_sequences`:
    *   If `seen_sequences` *contains* the `substring`, it means we have encountered this sequence before. Therefore, this `substring` is a repeated sequence, so add it to the `repeated_sequences` set.
    *   If `seen_sequences` *does not contain* the `substring`, it means this is the first time we're seeing this sequence.

5.  Regardless of whether it was a repeat or not, always add the `substring` to `seen_sequences`. This ensures that for subsequent iterations, we can detect if this sequence appears again.

6.  After the loop completes, the `repeated_sequences` set will contain all unique 10-letter sequences that occurred more than once. Convert this set to a list and return it.

## Formula (if applicable)

Not applicable for this approach, as it primarily relies on string operations and hash set data structures rather than a central mathematical formula.

## Dry Run

Let's trace the algorithm with `s = "AAAAAAAAAAAAA"`

*   `s.length() = 13`
*   Loop will run for `i` from `0` to `13 - 10 = 3`.

Initial state:
*   `seen_sequences = {}`
*   `repeated_sequences = {}`

| Iteration `i` | Current Substring      | `seen_sequences.contains(substring)`? | Action on `repeated_sequences` | Action on `seen_sequences`        | `seen_sequences`               | `repeated_sequences`           |
| :------------ | :--------------------- | :------------------------------------ | :----------------------------- | :-------------------------------- | :----------------------------- | :----------------------------- |
| `0`           | `"AAAAAAAAAA"`         | No                                    | -                              | Add `"AAAAAAAAAA"`                | `{"AAAAAAAAAA"}`               | `{}`                           |
| `1`           | `"AAAAAAAAAA"`         | Yes                                   | Add `"AAAAAAAAAA"`             | Add `"AAAAAAAAAA"` (no change)    | `{"AAAAAAAAAA"}`               | `{"AAAAAAAAAA"}`               |
| `2`           | `"AAAAAAAAAA"`         | Yes                                   | Add `"AAAAAAAAAA"` (no change) | Add `"AAAAAAAAAA"` (no change)    | `{"AAAAAAAAAA"}`               | `{"AAAAAAAAAA"}`               |
| `3`           | `"AAAAAAAAAA"`         | Yes                                   | Add `"AAAAAAAAAA"` (no change) | Add `"AAAAAAAAAA"` (no change)    | `{"AAAAAAAAAA"}`               | `{"AAAAAAAAAA"}`               |

Loop finishes.

Final result: Convert `repeated_sequences` to a list: `["AAAAAAAAAA"]`. This matches Example 2.

## Time Complexity

The time complexity is **O(N * L)**, where `N` is the length of the input string `s` and `L` is the length of the target sequence (which is 10 in this problem).

*   There are `N - L + 1` possible substrings of length `L` in a string of length `N`. Since `L=10`, this is approximately `N` iterations.
*   In each iteration:
    *   Extracting a substring of length `L` (e.g., using `substring()` in Java) takes `O(L)` time.
    *   `HashSet` operations (like `contains()` and `add()`) for strings take `O(L)` on average because comparing and hashing strings requires iterating over their characters.

Therefore, the total time complexity is `(N - L + 1) * O(L)`, which simplifies to `O(N * L)`. Since `L=10` is a constant, this is often stated as **O(N)**.

## Space Complexity

The space complexity is **O(K * L)**, where `K` is the number of unique 10-letter sequences stored in the hash sets and `L` is the length of each sequence (10).

*   In the worst case, `seen_sequences` could store up to `N - L + 1` unique substrings.
*   `repeated_sequences` could store up to `N - L + 1` unique repeated substrings.
*   Each stored substring has length `L`.

Therefore, the total space complexity is roughly `(N - L + 1) * O(L)`, which simplifies to `O(N * L)`. Since `L=10` is a constant, this is often stated as **O(N)**.