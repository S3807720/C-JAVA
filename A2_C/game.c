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

int getInteger(int* integer, char* type) {
	/* flag to end loop */
	int finished = FALSE;
	char input[NAMELEN + EXTRACHARS];
	int int_result = 0;
	char* end;
	do {
		normal_print("Please enter the %s:\n", type);
		/* send FALSE when empty/eof detected */
		if (fgets(input, NAMELEN + EXTRACHARS, stdin) == NULL
				|| *input == '\n') {
			return FALSE;
		}
		/* if input is too long, clear buffer  and try again*/
		if (input[strlen(input) - 1] != '\n') {
			normal_print("Input was too long.\n");
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
	int errorCheck;
	errorCheck = createPlayer(thegame);
	if (errorCheck == FALSE) {
		return FALSE;
	}
	int width, height;
	struct board nboard;
	errorCheck = getInteger(&width, "width");
	errorCheck = getInteger(&height, "height");
	if (errorCheck == FALSE) {
		return FALSE;
	}
	printf("%d %d\n", width, height);
	nboard = *new_board(width, height);
	/* this isn't working, not being set, fix tmrw */
	*thegame->theboard = nboard;
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
	struct game thegame;
	int errorCheck;
	errorCheck = game_init(&thegame);
	if (errorCheck == FALSE) {
		normal_print("Input was cancelled. Exiting game. Thanks for playing. :)");
		exit(0);
	}
	struct score_list scoreList;
	printf("about to load score la\n");
	scoreList = *load_scores(scoresfile);
	printf(" %c | ", scoreList.scores[25].letter);
	printf(" %d | ", scoreList.scores[25].score);
	printf(" %d | \n", scoreList.scores[25].count);
}

/**
 * Code provided by Paul Miller for use in "Programming in C",
 * From start-up code in Assignment 1, study period 4, 2020.
 **/
int error_print(const char *format, ...) {
	va_list arglist;
	int count = 0;
	/* print out error preamble */
	count += fprintf(stderr, "Error: ");
	/* print out the rest of the args */
	va_start(arglist, format);
	count += vfprintf(stderr, format, arglist);
	va_end(arglist);
	return count;
}
int normal_print(const char *format, ...) {
	int count = 0;
	/* prepare the argument list for output */
	va_list arglist;
	va_start(arglist, format);
	/* output the args */
	count += vprintf(format, arglist);
	/* cleanup */
	va_end(arglist);
	return count;
}
