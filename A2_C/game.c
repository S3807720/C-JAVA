/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "game.h"


void clear_buffer(void) {
	int ch;
	while (ch = getc(stdin), ch != EOF && ch != '\n')
		;
	clearerr(stdin);
}

int randNum(int max) {
  int integer;
  integer = rand();
  if (integer < 0) {
	  integer = -integer;
  }
  integer = integer % max;
  return(integer);
}

int getInteger(int* integer) {
	/* flag to end loop */
	int finished = FALSE;
	char input[NAMELEN + EXTRACHARS];
	int int_result = 0;
	char* end;
	do {
		printf("Please enter your choice:\n");
		/* send FALSE when empty/eof detected */
		if (fgets(input, NAMELEN + EXTRACHARS, stdin) == NULL
				|| *input == '\n') {
			return FALSE;
		}
		/* if input is too long, clear buffer  and try again*/
		if (input[strlen(input) - 1] != '\n') {
			printf("Input was too long.\n");
			clear_buffer();

		} else {
			/* replace \n with \0. */
			input[strlen(input) - 1] = '\0';
			int_result = (int) strtol(input, &end, 0);

			/* verify result. */
			if (*end != 0) {
				printf("Input was not numeric.\n");
			}
			/* change flag to end loop if everything's A-OK */
			else {
				finished = TRUE;
			}
		}
	} while (finished == FALSE);
	/* set menu choice thru pointer */
	*integer = int_result;
	return TRUE;
}

/**
 * initialise the game. Please see the assignment specification for details of
 * what is required.
 **/
BOOLEAN game_init(struct game* thegame) {
	printf("init game method\n");
	createPlayer(thegame);
	int width, height;
	struct board nboard;
	int errorCheck;
	errorCheck = NULL;
	while(errorCheck == NULL) {
		errorCheck = getInteger(&width);
		errorCheck = getInteger(&height);
		printf("%d %d\n", width, height);
		new_board(width, height);
	}
	if (errorCheck == FALSE) {
		return FALSE;
	}
	return TRUE;
}

/**
 * the core function for the management of the game.
 *
 * call game_init() to initialise the game and then manage whose player turn
 * it is and handle cleaning up and quitting the program when a player quits the
 * game.
 **/
void play_game(const char* scoresfile) {
	printf("play game method\n");
	struct game thegame;
	game_init(&thegame);
	struct score_list scoreBoard;
	scoreBoard = load_scores(scoresfile);
}
