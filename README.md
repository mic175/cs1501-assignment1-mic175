# CS 1501 – Algorithm Implementation – Assignment #1 

Due: Friday, February 9th, @ 11:59pm on Gradescope

Late submission deadline: Sunday, February 11th @11:59pm with 10% penalty per late day

You should submit the Java file `BoggleGame.java` to GradeScope (the link is on Canvas). You must also submit a writeup named `a1.md` and an Assignment Information Sheet `InfoSheet.md` as described below. _The code submitted to GradeScope is what will be graded_.

**You are encouraged to use code from Lab 1.**

## Table of Contents

- [Overview](#overview)
- [TASK - Implementing the `BoggleGameInterface`](#task)
- [Important Notes](#important-notes)
- [Writeup](#writeup)
- [Submission Requirements](#submission-requirements)
- [Rubrics](#rubrics)

## Overview
 
__Purpose__: In this assignment, you will complete the implementation of a [Boggle game](https://wordshake.com/boggle) program that generates and answers a variety of queries on a Boggle board. The Boggle Game in this assignment is defined as follows. Given a two-dimensional board of letters, find all words with at least three adjacent letters. Adjacent letters are horizontal, vertical, or diagonal neighbors. Any tile in the board can only be used once per word, but can be used for multiple words.

The starter code has been provided to you with the following content:

- `Main.java` - An example driver program for testing your code.
- `BoggleGameInterface.java` - This interface defines the methods that you will implement, __do not modify__.
- `Tile.java` - A class that defines a tile in the Boggle board, __do not modify__.
- `BoggleGame.java` - The file you will edit. It includes eight methods to complete. The `TODO` comments mark them.
- `DictInterface.java` and `MyDictionary.java`- Used for the dictionary implementation for the assignment, __do not modify__.
- `dict8.txt` - An example dictionary.
- `CallableMenuItem` and `MenuProgram`- These files provide a generic framework for building menu-based programs, __do not modify__.
- `a1.md` and `InfoSheet.md` - Placeholders for the assignment writeup and the information sheet.

## Task

You will complete the implementation of the `BoggleGame` class, which implements the `BoggleGameInterface` defined in `BoggleGameInterface.java`. The interface has eight methods that you will have to implement in `BoggleGame.java`.

```java
public interface BoggleGameInterface {

    /**
     * Randomly generate and return a Boggle board of a given dimension
     * @param size the dimension size of the board
     * @return a randomly generated Boggle board as a 2-d array of characters or 
     *         null if size <= 0 or size is too big
     */
    public char[][] generateBoggleBoard(int size);

    /**
     * Returns the total number of words of length three or more found in the 
     * board following the rules of the Boggle Game
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @return total number of words found in the board
     */
    public int countWords(char[][] boggleBoard, DictInterface dictionary);

    /**
     * Returns the total number of words of a given length found in the board
     * following the rules of the Boggle Game
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @param wordLength the word length
     * @return total number of words of length `wordLength` found in the board
     */
    public int countWordsOfCertainLength(char[][] boggleBoard, DictInterface dictionary, int wordLength);

    /**
     * Checks if a given word is in a given dictionary
     * @param dictionary the DictInterface dictionary
     * @param word the String word to check
     * @return true if word exists in dictionary and false otherwise
     */
    public boolean isWordInDictionary(DictInterface dictionary, String word);

    /**
     * Checks if a given word can be found in a given Boggle board following the
     * rules of the Boggle Game
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param word the String word to check
     * @return rue if word can be found in boggleBoard and false otherwise
     */
    public boolean isWordInBoard(char[][] boggleBoard, String word);

    /**
     * Finds a word of length three or more from a given dictionary in a given 
     * Boggle board
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @return a String word (from dictionary) of length three or more found in 
     * boggleBoard or null if no such word can be found
     */
    public String anyWord(char[][] boggleBoard, DictInterface dictionary);

     /**
     * Finds a word of a given length from a given dictionary in a given 
     * Boggle board
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param dictionary the DictInterface dictionary
     * @param length the int word length
     * @return a String word (from dictionary) of length characters found in 
     * boggleBoard or null if no such word can be found
     */
    public String anyWord(char[][] boggleBoard, DictInterface dictionary, int length);


    /**
     * Finds a given word in a given board and returns a list of board tiles 
     * where the word is found
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param word the String word to find
     * @return an ArrayList of board tiles where the word is found or null if the
     * word cannot be found in the board
     */
    public ArrayList<Tile> markWordInBoard(char[][] boggleBoard, String word);

    /**
     * Checks a list of board tiles to see if they are adjacent (according to
     * the rules of the Boggle Game) and that they have the letters of a given 
     * word in order
     * @param boggleBoard the 2-d character array representing the Boggle board
     * @param tiles an ArrayList of board tiles
     * @param word
     * @return true if tiles contains adjacent tiles that have the letters of 
     * word in order or false otherwise
     */
    public boolean checkTiles(char[][] boggleBoard, ArrayList<Tile> tiles, String word);
}
```

Many of the methods that you will implement in the assignment use backtracking and pruning. Backtracking is returning to an earlier part of the search to find other paths to the solution. In the case of Boggle, this means going back to the previous tile to try using letters in a different direction to form a word. Pruning is a technique that reduces the number of options to process while doing an exhaustive search of the search space of a problem. **Please refer to the lecture notes on backtracking and to Lab 1**.


## Important Notes:

- **Refer to the backtracking template that we discussed in class.**
- **The only code file that you are allowed to change is `BoggleGame.java`.**
- Make sure that the methods you implement has _NO INPUT_ or anything that would make the method require any user interaction.
- The `MyDictionary` implementation of the `DictInterface` that is provided to you should work correctly, but it is not very efficient.  Note that it is doing a linear search of an `ArrayList` to determine if the argument is a prefix or word in the dictionary.  
-	Be sure to thoroughly document your code.
- `commit` and `push` the finished code to your Github repository. You can check the following [page](https://code.visualstudio.com/docs/sourcecontrol/github) to setup GitHub access directly from VS Code. If you think the commit command is hanging in VS Code, it is probably asking for a commit message in an open file. You would then need to enter a message and close the file before it is able to commit to GitHub.
- Submit your Github repository to GradeScope for automatic grading.

## Coding Style and Documentation

Please check [this guide](https://introcs.cs.princeton.edu/java/11style/) for directions regarding the expected coding style and documentation.

## Writeup

Once you have completed your algorithm, write a short paper (500-750 words) using [Github Markdown syntax](https://guides.github.com/features/mastering-markdown/) and named `a1.md` that summarizes your project in the following ways:
1.	Discuss how you implemented each of the methods in some detail. Include
    * how you set up the data structures necessary for the problem
    * how your algorithm proceeds
    * any coding or debugging issues you faced and how you resolved them
3.	Include an asymptotic analysis of the worst-case run-time of the methods you implemented.  Some values to consider in this analysis may include:
    * Number of words in the dictionary
    * Number of characters in a word
    * Board size

## Submission Requirements

You must submit your Github repository to GradeScope. We will only grade the following file:
1)	`BoggleGame.java`
3)	`a1.md`: A well written/formatted paper explaining your method implementation (see the Writeup section above for details on the paper) 
4)	`InfoSheet.md`: Assignment Information Sheet (including compilation and execution information).

The idea from your submission is that your TA (and/or the autograder when available) can compile and run your programs from the command line WITHOUT ANY additional files or changes, so be sure to test it thoroughly before submitting it. If the TA (and/or the autograder) cannot compile or run your submitted code it will be graded as if the program does not work. 

_Note: If you use an IDE, such as NetBeans, Eclipse, or IntelliJ, to develop your programs, make sure the programs will compile and run on the command-line before submitting – this may require some modifications to your program (e.g., removing package declaration)._

## Rubric

Item|Points
----|------
Autograder Score | 75
Write-up paper|	10
Code style and documentation |	10
Assignment Information Sheet|	5

Please note that when the autograder is available, its score will be used as a guidance for the TA, not as an official final score. Please also note that the autograder rubric is the definitive rubric for the assignment. The rubric below will be used by the TA to assign partial credit in case your code scored less than 40% of the autograder score. If your code is manually graded for partial credit, the maximum you can get for the autograded part is 60%.

Item|Points
----|------
`countWords` | 15
`countWordsOfCertainLength` | 10
`isWordInDictionary` | 10 
`isWordInBoard` | 15
The two `anyWord` methods | 15 
`markWordInBoard` | 5
`checkTiles` | 5
