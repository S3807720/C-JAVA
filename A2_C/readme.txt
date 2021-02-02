/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 2, 2020.
 *****************************************************************************/
How do we use your program? 
---------------------------
Simply enter the player names, and the width & height of the board.
Then everything will be set up with a few letters placed on the board at the start.
Words must use letters already on the board.
Pick a word and the coordinates, whichever letters you use on the board will be claimed as the
player who's turn it is. When the board is full, a player pressed ctrl d to quit or there are no
tiles left, the game will end and winner will be chosen.

Explain your reasoning behind the modifications you have made to the design of 
the program
------------------------------------------------------------------------------
Felt it was better to add a random letter on the board for each player to use rather than extra restrictions. 
These will reward no points by default, but taking them will grant points and take points from the opposing player.
I changed use of enum orientation to grab a 1 or 2 rather than h or v for simplicity. 
Adds unnecessary complexity to grab a char then convert it to an integer anyway.
Added score to the board nodes to avoid having to loop through every letter in the file and match them up to calculate the scores.

