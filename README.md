# README - Project 1 - Algorithm Design

For the implementation of each task, I used the laboratory skeleton for data reading and outputting results.

---

## Problem 1: Servers

The implementation of this problem relies on a binary search executed within the range of the minimum and maximum power limits. The `getValues` function calculates the server power values at the point `x` given as a parameter. For each new midpoint selected, it calculates the minimum value to the left and right of it by one unit. The search aims to find the global maximum within the range of values, stopping either when `left > right` or when a maximum point is found (where the minimum value is greater than the neighboring values at left and right by one unit).

**Binary Search**: If the minimum value calculated at `mid` is greater than that at `mid - 1` but less than that at `mid + 1`, the search continues to the right; otherwise, it continues to the left.  
Once a global maximum value (`result`) is found, we calculate the minimum for (`result + 0.5`) and (`result - 0.5`). We know that the value at the point where we calculate the server power expressions can only reach a maximized minimum at values of the form `x.0` or `x.5` (for other possible values, the target value would not be reached). Finally, the result will be the maximized minimum power value calculated between `result - 0.5`, `result`, and `result + 0.5`.

**Complexity**: O(log n)

---

## Problem 2: Coloring

This problem can be solved using mathematical formulas since we know how the current result is affected based on the orientation and color of the piece being added. We start with a base case (i.e., the first piece):

1. **Horizontal Piece** -> 6 different coloring cases appear
2. **Vertical Piece** -> 3 different coloring cases appear

Subsequently, for updating the number of possibilities, we analyze the current and previous piece:
1. If the current piece is horizontal:
    1.1) If the previous piece is horizontal, then the number of possibilities multiplies by 3.
    1.2) If the previous piece is vertical, then the number of possibilities multiplies by 2 (for the **first** vertical piece added) and then by 3 for the rest.
    
2. If the current piece is vertical:
    2.1) If the previous piece is horizontal, then the number of possibilities multiplies by 2 for `X - 1` vertical pieces (since there is only one possible coloring of the vertical piece after a horizontal one, the first multiplication would be by 1, so it is not counted).
    2.2) If the previous piece is vertical, the number of possibilities multiplies by 2.

All multiplications with powers of 2 and 3 were calculated using an auxiliary function based on divide and conquer.

**Complexity**: O(n * log n)

---

## Problem 3: Compression

The compression problem analyzes both arrays simultaneously, traversing them in an alternating manner as follows:

- **If** `A[i] > B[i]`, traverse `B` until the sum of traversed elements is greater than or equal to `A[i]`. As elements are added to the sum, they are also removed from the array. If the sum exactly equals `A[i]`, move forward in both arrays as the elements have become equal.
  
- **If** `A[i] < B[i]`, traverse `A` until the sum of traversed elements is greater than or equal to `B[i]`. As elements are added to the sum, they are also removed from the array. If the sum exactly equals `B[i]`, move forward in both arrays as the elements have become equal.

- **If** `A[i] == B[i]` at the start, move forward in both arrays.

The algorithm alternates between these three conditions, maximizing the length of the arrays and making them equal. If, at the end, `A` differs from `B`, then compression is not possible, and `-1` is returned; otherwise, the size of either array is returned.

**Complexity**: O(n) because traversal is performed only once, both in the compression function and in the array equality check.

---

## Problem 4: Encrypted

For each of the maximum 8 different letters present in the words, the following algorithm is executed:

Each word is assigned a ratio: `letter_occurrences / word_length`. These ratios are introduced into a vector (`values`) sorted in descending order. The closer the ratio is to 1, the more likely that word is used in the maximum-length password with the respective letter.

Subsequently, each word is added to a list of maps. A list of maps was used because maps do not tolerate duplicate elements, and the problem does not specify that a word appears only once in the word list. In other words, the list will contain maps with a single element each, marked with `0`. 

Then, in the `computeList` function, for each value in `values`, we iterate through the list of maps until a word with the respective ratio is found. Once found, it is added to an ordered word array and marked with `1` to avoid reusing it if ratios appear multiple times. For example, if executing for letter `a`, the words “ban” and “balcan” both have a ratio of 0.5 but differ in length, thus contributing differently to the final password.

Once ordered, we form the final password by iterating through the word array and adding words to the password as long as the letter upon which the password is formed appears more than `password_length / 2` times.  
The final result will be the maximum length of the password calculated for all existing letters.

**Complexity**: O(n^3 * log n) - considering that maps in the list always contain only a single pair, they represent a minimal difference in the time complexity of the algorithm. However, factoring this in, the complexity would be O(n^4 * log n).

---

## Problem 5: Offer

This problem reduces to an `N * N` matrix where the elements on the main diagonal represent the prices of the items. It is only filled in the upper part, and elements in the form `dp[i][j]` represent the minimum price possible calculated from position `i` to position `j`.

The implementation is recursive, and solutions are calculated based on each other. There are two base cases on which all solutions can be calculated:
1. **If `i == j`** (i.e., the price itself)  
2. **If `j - i == 1`**, when applying the rule of reducing the cheaper product by 50% among the two grouped products.

For `j - i == 3`, the minimum price is chosen among three cases:
- `(A, B), C` or `A, (B, C)` or `(A, B, C)`, where parentheses represent product grouping.

For `j - i > 3`, the problem can be divided into previously calculated subproblems, but only the last products added are relevant for optimal grouping. For example, for the products `A, B, C, D, E, F, G, H`, to calculate the final result (`dp[1][8]`), it will be the minimum between `dp[1][4] + dp[5][8]`, `dp[1][5] + dp[6][8]`, or `dp[1][6] + dp[7][8]`. These, in turn, are recursively calculated.

**Complexity**: The algorithm is highly inefficient even for smaller values of `N`.
