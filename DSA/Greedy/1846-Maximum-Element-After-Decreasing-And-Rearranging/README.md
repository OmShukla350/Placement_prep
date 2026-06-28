# 1846. Maximum Element After Decreasing and Rearranging

**Difficulty:** Medium | **Topic:** Greedy | **Tags:** Array, Greedy, Sorting

---

## Problem

You are given an array of positive integers `arr`. Perform some operations (possibly none) on `arr` so that it satisfies these conditions:

1.  The value of the first element in `arr` must be 1.
2.  The absolute difference between any 2 adjacent elements must be less than or equal to 1. In other words, `abs(arr[i] - arr[i - 1]) <= 1` for each `i` where `1 <= i < arr.length` (0-indexed). `abs(x)` is the absolute value of `x`.

There are 2 types of operations that you can perform any number of times:

1.  Decrease the value of any element of `arr` to a smaller positive integer.
2.  Rearrange the elements of `arr` to be in any order.

Return the maximum possible value of an element in `arr` after performing the operations to satisfy the conditions.

**Constraints:**

*   `1 <= arr.length <= 10^5`
*   `1 <= arr[i] <= 10^9`

## Examples

**Example 1:**

```
Input: arr = [2,2,1,2,1]
Output: 2
Explanation: 
We can satisfy the conditions by rearranging arr so it becomes [1,2,2,2,1].
The largest element in arr is 2.
```

**Example 2:**

```
Input: arr = [100,1,1000]
Output: 3
Explanation: 
One possible way to satisfy the conditions is by doing the following:
1. Rearrange arr so it becomes [1,100,1000].
2. Decrease the value of the second element to 2.
3. Decrease the value of the third element to 3.
Now arr = [1,2,3], which satisfies the conditions.
The largest element in arr is 3.
```

**Example 3:**

```
Input: arr = [1,2,3,4,5]
Output: 5
Explanation: The array already satisfies the conditions, and the largest element is 5.
```

## Approach

**Intuition:**
The problem asks us to maximize the largest element in the array while ensuring two conditions: the first element is 1, and adjacent elements differ by at most 1. We have two powerful operations: decreasing any element and rearranging the entire array.

The ability to rearrange elements is crucial. If we want to achieve a sequence like `1, 2, 3, ... k`, we should use the smallest available numbers from the original array to construct this sequence. Sorting the array initially allows us to pick the smallest values first.

Consider an array `[a1, a2, a3, ..., an]` that satisfies the conditions.
1. `a1` must be 1.
2. `abs(ai - a(i-1)) <= 1` implies `a(i-1) - 1 <= ai <= a(i-1) + 1`.
To maximize the last element `an`, we want to make each subsequent element `ai` as large as possible. This means we should aim for `ai = a(i-1) + 1` whenever possible.

Combining these ideas, if we sort the array, we get access to elements in non-decreasing order.
Let the sorted array be `[s1, s2, s3, ..., sn]`.
1. We must make the first element 1. We can always decrease `s1` to 1. So, `arr[0] = 1`.
2. For subsequent elements `s_i`, we need `s_i <= s_(i-1) + 1` to satisfy the adjacency condition and maximize the current element. Since we can decrease `s_i` to any smaller positive integer, if the original `s_i` (from the sorted array) is greater than `s_(i-1) + 1`, we must decrease it to `s_(i-1) + 1`. If `s_i` is already less than or equal to `s_(i-1) + 1`, we can use its original value (or decrease it further, but that would not help maximize the final element). So, for each `arr[i]`, its value after operations should be `min(original_arr[i], arr[i-1] + 1)`.

This greedy strategy works because:
*   Sorting ensures we're using the smallest possible numbers to build our increasing sequence, which leaves larger numbers for later positions if needed, or allows us to maintain a compact sequence like `1, 2, 3, ...`.
*   Setting `arr[0] = 1` is a must.
*   For `arr[i]`, `min(arr[i], arr[i-1] + 1)` makes the locally optimal choice. It ensures `abs(arr[i] - arr[i-1]) <= 1` (specifically `arr[i] <= arr[i-1] + 1`) while trying to keep `arr[i]` as large as its original value allows, without violating the adjacency condition. This greedy choice does not harm future elements because any `arr[i]` we pick is as large as possible given the preceding `arr[i-1]` and its own original value, maximizing the potential for `arr[i+1]` to also be large.

**Algorithm:**

1.  **Sort the array:** Sort `arr` in non-decreasing order. This allows us to process elements from smallest to largest, making optimal local choices.
2.  **Set the first element:** Set `arr[0]` to `1`. This fulfills the first condition. Since we can decrease any element, we can always make the smallest element (after sorting) equal to 1.
3.  **Iterate and adjust:** Loop through the array starting from the second element (`i = 1` to `arr.length - 1`). For each element `arr[i]`:
    *   To satisfy `abs(arr[i] - arr[i-1]) <= 1`, we need `arr[i] <= arr[i-1] + 1`.
    *   Since we want to maximize the final element, we should make `arr[i]` as large as possible while respecting this constraint and its original value.
    *   Therefore, update `arr[i]` to `min(arr[i], arr[i-1] + 1)`.
4.  **Return the last element:** After the loop completes, the last element `arr[arr.length - 1]` will be the maximum possible value that satisfies all conditions.

## Formula (if applicable)

Not applicable.

## Dry Run

Let's trace the algorithm with `arr = [100, 1, 1000]`.

1.  **Initial array:** `arr = [100, 1, 1000]`

2.  **Sort the array:**
    `arr` becomes `[1, 100, 1000]`

3.  **Set the first element:**
    `arr[0]` is already `1`. No change needed.
    `arr = [1, 100, 1000]`

4.  **Iterate and adjust:**
    *   **For `i = 1`:**
        *   Current `arr[1]` is `100`.
        *   `arr[0]` is `1`.
        *   We need `arr[1] <= arr[0] + 1`, which is `arr[1] <= 1 + 1 = 2`.
        *   Update `arr[1] = min(arr[1], arr[0] + 1) = min(100, 2) = 2`.
        *   `arr` becomes `[1, 2, 1000]`

    *   **For `i = 2`:**
        *   Current `arr[2]` is `1000`.
        *   `arr[1]` is `2`.
        *   We need `arr[2] <= arr[1] + 1`, which is `arr[2] <= 2 + 1 = 3`.
        *   Update `arr[2] = min(arr[2], arr[1] + 1) = min(1000, 3) = 3`.
        *   `arr` becomes `[1, 2, 3]`

5.  **Return the last element:**
    The loop finishes. The last element is `arr[2] = 3`.

    Output: `3`

This matches Example 2.

## Time Complexity

The dominant operation is sorting the array, which takes `O(N log N)` time, where `N` is the number of elements in `arr`. The subsequent loop iterates through the array once, taking `O(N)` time.
Therefore, the total time complexity is `O(N log N)`.

## Space Complexity

The space complexity depends on the sorting algorithm used.
*   If an in-place sorting algorithm (like heapsort or dual-pivot quicksort for primitives) is used, the auxiliary space complexity for sorting is `O(log N)` due to the recursion stack.
*   If a sorting algorithm like Timsort (used for `Object` arrays in Java, but for primitives `Arrays.sort` uses Dual-Pivot Quicksort) is used, it might require `O(N)` auxiliary space in the worst case.
The modification of the array is done in-place.
Considering standard library sorts, the space complexity is typically `O(log N)` or `O(N)`. For primitive arrays, `O(log N)` is common for the sort itself.
Therefore, the space complexity is `O(log N)` (for recursion stack in sorting) or `O(N)` (if auxiliary space is used for sorting), and `O(1)` additional space if the input array is modified in-place and excluding the sorting algorithm's internal space.