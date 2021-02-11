/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 2, 2020.
 *****************************************************************************/
How do we use your program? 
---------------------------
Simply enter the player names, and the width & height of the board to set up the game.
Then everything will be set up with a few letters placed on the board at the start.
Words must use letters already on the board.
Pick a word and the coordinates(top to bottom, left to right), whichever letters you use on the board will be claimed by the player who initiated
the action. When the board is full, a player pressed ctrl d to quit or there are no
letters are left, the game will end and the winner will be chosen.

- The case is not sensitive, lower case or upper case will be detected.
- Any string will be accepted but will not be validated when checking against the board if it's not a match.
- There are no restrictions on words, 'abc' 'fa' etc will work in this version.

Explain your reasoning behind the modifications you have made to the design of 
the program
------------------------------------------------------------------------------
For the most part i've kept the provided code inplace. And used the specs as a guide.
Some changes:
*Felt it was better to add a random letter on the board for each player to use rather than extra restrictions. 
 These will reward no points by default, but taking them will grant points and take points from the opposing player.
 If the board is more than 5 spaces high, there will be 2 letters per player for more options.

*I changed use of enum orientation to grab a 1 or 2 rather than h or v for simplicity. 
 Adds unnecessary complexity to grab a char then convert it to an integer anyway. This is how it would be done
 with a GUI anyway.

*Added score to the board nodes to avoid having to loop through every letter 
 in the file and match them up to calculate the scores. Easier and faster to increment and decrement
 the score on each move with simple player->... +- commands. 

