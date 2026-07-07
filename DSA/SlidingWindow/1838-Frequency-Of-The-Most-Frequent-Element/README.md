# 1838. Frequency of the Most Frequent Element

**Difficulty:** Medium | **Topic:** SlidingWindow | **Tags:** Array, Binary Search, Greedy, Sliding Window, Sorting, Prefix Sum

---

## Problem

The frequency of an element is the number of times it occurs in an array.

You are given an integer array `nums` and an integer `k`. In one operation, you can choose an index of `nums` and increment the element at that index by 1.

Return the maximum possible frequency of an element after performing at most `k` operations.

Constraints:

*   `1 <= nums.length <= 10^5`
*   `1 <= nums[i] <= 10^5`
*   `1 <= k <= 10^5`

## Examples

**Example 1:**
```
Input: nums = [1,2,4], k = 5
Output: 3
Explanation: Increment the first element three times and the second element two times to make nums = [4,4,4].
4 has a frequency of 3.
```

**Example 2:**
```
Input: nums = [1,4,8,13], k = 5
Output: 2
Explanation: There are multiple optimal solutions:
- Increment the first element three times to make nums = [4,4,8,13]. 4 has a frequency of 2.
- Increment the second element four times to make nums = [1,8,8,13]. 8 has a frequency of 2.
- Increment the third element five times to make nums = [1,4,13,13]. 13 has a frequency of 2.
```

**Example 3:**
```
Input: nums = [3,9,6], k = 2
Output: 1
```

## Approach

The core idea is to find the longest subarray where all elements can be made equal to the largest element in that subarray using at most `k` operations.

**Intuition:**
1.  **Sorting is Key:** If we want to make a group of numbers identical using only increment operations, the target value must be the largest number among them. For example, to make `[1, 2, 4]` all equal, we would change them to `[4, 4, 4]`. If we sort the array, we can iterate through possible target values efficiently.
2.  **Sliding Window:** Once the array is sorted, consider a window `[left, right]`. We want to determine if all elements `nums[left]` through `nums[right]` can be made equal to `nums[right]` (the largest element in the current window) using at most `k` operations.
3.  **Cost Calculation:** The cost to make all elements in the window `[left, right]` equal to `nums[right]` is the sum of differences: `(nums[right] - nums[left]) + (nums[right] - nums[left+1]) + ... + (nums[right] - nums[right-1])`. This can be simplified. The target value `nums[right]` appears `(right - left + 1)` times in the final window. The sum of elements in the original window is `sum(nums[left]...nums[right])`. So, the total operations needed is `(nums[right] * (right - left + 1)) - sum(nums[left]...nums[right])`.
4.  **Window Adjustment:** We expand the window by moving `right` pointer. For each `right`, we add `nums[right]` to a running `current_sum`. Then, we check if the cost for the current window `[left, right]` exceeds `k`. If it does, we must shrink the window from the left by incrementing `left` and subtracting `nums[left]` from `current_sum` until the cost is within `k`.
5.  **Maximizing Frequency:** At each valid window state (where cost <= `k`), the current window size `(right - left + 1)` represents a possible frequency. We keep track of the maximum frequency found.

**Algorithm:**

1.  **Sort the array `nums`** in non-decreasing order. This allows us to use `nums[right]` as the target value for any window `[left, right]`.
2.  Initialize two pointers, `left = 0` and `right = 0`.
3.  Initialize `current_sum = 0` to store the sum of elements in the current window `[left, right]`.
4.  Initialize `max_frequency = 0` to store the maximum frequency found so far.
5.  Iterate with the `right` pointer from `0` to `nums.length - 1`:
    a.  Add `nums[right]` to `current_sum`.
    b.  Calculate the `cost` required to make all elements in the current window `[left, right]` equal to `nums[right]`. This is `(long)nums[right] * (right - left + 1) - current_sum`. We use `long` for the product to prevent potential integer overflow, as `nums[i]` and `k` can be up to `10^5`, making the product `10^5 * 10^5 = 10^10`.
    c.  While the `cost` is greater than `k`:
        i.  The current window `[left, right]` is invalid. Shrink the window from the left by subtracting `nums[left]` from `current_sum`.
        ii. Increment `left` by 1.
        iii. Recalculate the `cost` with the new window `[left, right]`. This step is crucial because `nums[right]` (the target) and `current_sum` have changed relative to the window size. The condition `(long)nums[right] * (right - left + 1) - current_sum > k` will eventually become false as `left` increases and `current_sum` decreases relatively.
    d.  Once the window `[left, right]` is valid (cost <= `k`), update `max_frequency = Math.max(max_frequency, right - left + 1)`.
6.  After the `right` pointer has traversed the entire array, return `max_frequency`.

## Formula

The cost to make all elements in the window `nums[left...right]` equal to `nums[right]` is:

```
cost = (long)nums[right] * (right - left + 1) - current_sum
```
where `current_sum` is the sum of elements from `nums[left]` to `nums[right]`.
We need to ensure `cost <= k`.

## Dry Run

Let's use Example 1: `nums = [1,2,4], k = 5`

1.  **Sort `nums`**: `nums` remains `[1,2,4]`.
2.  Initialize: `left = 0`, `current_sum = 0`, `max_frequency = 0`.

| Step | `right` | `nums[right]` | `current_sum` (before add) | `current_sum` (after add) | Window `[left, right]` | `window_size` (`right - left + 1`) | Target (`nums[right]`) | Calculated Cost (`target * size - current_sum`) | `Cost > k`? (`k=5`) | `left` (after adjustment) | `max_frequency` |
| :-- | :----- | :------------ | :------------------------ | :----------------------- | :--------------------- | :---------------------------------- | :-------------------- | :--------------------------------------------- | :------------------ | :------------------------ | :-------------- |
| 1   | 0      | 1             | 0                         | 1                        | `[1]`                  | 1                                   | 1                     | `(1 * 1 - 1) = 0`                              | No (0 <= 5)         | 0                         | `max(0, 1) = 1` |
| 2   | 1      | 2             | 1                         | 3                        | `[1,2]`                | 2                                   | 2                     | `(2 * 2 - 3) = 1`                              | No (1 <= 5)         | 0                         | `max(1, 2) = 2` |
| 3   | 2      | 4             | 3                         | 7                        | `[1,2,4]`              | 3                                   | 4                     | `(4 * 3 - 7) = 5`                              | No (5 <= 5)         | 0                         | `max(2, 3) = 3` |

End of loop.
Return `max_frequency = 3`.

## Time Complexity

*   **Sorting the array**: `O(N log N)`, where `N` is the number of elements in `nums`.
*   **Sliding Window**: The `right` pointer traverses the array once (`N` iterations). The `left` pointer also traverses the array at most once (it only moves forward). Each operation inside the loop (addition, subtraction, comparison) takes `O(1)` time. Thus, the sliding window part takes `O(N)` time.
*   **Total Time Complexity**: `O(N log N)` due to the sorting step dominating the overall complexity.

## Space Complexity

*   **Sorting**: `O(log N)` or `O(N)` depending on the specific sorting algorithm used. For example, in-place quicksort uses `O(log N)` auxiliary space for recursion stack, while mergesort uses `O(N)` auxiliary space. Standard library sorts often use `O(N)` in the worst case (e.g., Timsort in Java/Python).
*   **Sliding Window**: `O(1)` for variables like `left`, `right`, `current_sum`, and `max_frequency`.
*   **Total Space Complexity**: `O(N)` in the worst case (assuming a sorting algorithm that requires auxiliary space proportional to `N`). If an in-place sort with `O(log N)` space is used, then the space complexity would be `O(log N)`.