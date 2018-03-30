# Algorithms
Course Assignments for CS 4250  
Fall 2017  
Dr. Gerald Shultz  
Metropolitan State University of Denver

## Assignments
### 01 Number Game
This program plays a simple game with the user as follows: a series of integers are input in order by the user. Then, the user and the program (the "AI player") take turns picking either the first or the last number from the list, with the goal of having the highest score possible.

The program will automatically select the best possible option available and display a table that generates all the possible permutations of the current game state to show how it made that decision. When the game completes, the scores are tallied and a winner is proclaimed.
### 02 Floyd-Warshall Algorithm
This program implements the [Floyd-Warshall Algorithm](https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm). The input is a file containing a square, directed, weighted, connected graph. When the program is compiled and run, it can be given one of these graph files and will complete a single execution, showing a new graph for each iteration and displaying the final, shortest path between any two vertices. For an example of the format of an input file, see `test.txt`.
### 03 Knapsack Problem Solver
This program implements a potential solution for the [Knapsack Problem](https://en.wikipedia.org/wiki/Knapsack_problem) using the [Branch-and-Bound Technique](https://en.wikipedia.org/wiki/Branch_and_bound). It takes as input a file containing a table of items with values and weights, and then finds the best possible permutation of items to maximize the value of the knapsack given a provided weight capacity. For an example of the input file containing the table of items and the weight capacity, see `test1.txt`.
