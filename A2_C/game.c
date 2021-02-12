/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "game.h"
#include "player.h"
#include "board.h"
#include "word_list.h"

/* courtesy of teach, Paul Miller */
void clear_buffer(void) {
	int ch;
	while (ch = getc(stdin), ch != EOF && ch != '\n')
		;
	clearerr(stdin);
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
		if (fgets(input, NAMELEN + EXTRACHARS, stdin) == NULL) {
			return MOVE_QUIT;
		}
		else if (*input == '\n') {
			return MOVE_SKIP;
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
				normal_print("Input was not numeric.\n");
			}
			/* change flag to end loop if everything's A-OK */
			else {
				finished = MOVE_SUCCESS;
			}
		}
	} while (finished == FALSE);
	/* set menu choice thru pointer */
	*integer = int_result;
	return MOVE_SUCCESS;
}

/**
 * initialise the game. Please see the assignment specification for details of
 * what is required.
 **/
BOOLEAN game_init(struct game* thegame) {
	int errorCheck, width, height;
	/* grab inputs and if an error is returned, do appropriate action */
	errorCheck = createPlayer(thegame);
	if (errorCheck == FALSE) {
		return FALSE;
	}
	/* if any values are empty, return exit function */
	errorCheck = getInteger(&width, "width");
	if (errorCheck == MOVE_SKIP || MOVE_QUIT) {
		return FALSE;
	}
	errorCheck = getInteger(&height, "height");
	if (errorCheck == MOVE_SKIP ||  MOVE_QUIT) {
		return FALSE;
	}
	/* allocate memory for pointer */
	thegame->theboard = malloc(sizeof(struct board));
	/* create board, initialize and assign to board pointer in game */
	*thegame->theboard = *new_board(width, height);
	return TRUE;
}

/**
 * the core function for the management of the game.
 *
 * call game_init() to initialise the game and then manage whose player turn
 * it is and handle cleaning up and quitting the program when a player quits the
 * game.
 **/
void play_game(const char* scoresfile, const char* wordFile) {
	struct game thegame;
	int errorCheck, i, turnCount, quitFlag;
	turnCount = 0, quitFlag = FALSE;
	/* seed timer, seems to be more random when placed here */
	srand(time(NULL));
	thegame.word_list = malloc(sizeof(struct word_list));
	thegame.word_list = readFile(wordFile);
	if (thegame.word_list->head == NULL) {
		error_print("Fatal error detected in reading file. Exiting game.\n");
		exit(0);
	}
	errorCheck = game_init(&thegame);
	if (errorCheck == FALSE) {
		normal_print("Input was cancelled. Exiting game. Thanks for playing. :)");
		exit(0);
	}
	thegame.score_list = malloc(sizeof(struct score_list));
	*thegame.score_list = *load_scores(scoresfile);
	/* exit game if error in scores */
	if (thegame.score_list->total_count == EOF) {
		clearMemory(&thegame);
		quitFlag = TRUE;
	}
	/* "flip coin" for player turn */
	thegame.curr_player_num = randomNumber(MAX_PLAYERS);
	/* place 2 starting letters */
	for (i = 0; MAX_PLAYERS > i; ++i) {
		place_start_letters(&thegame.players[i]);
	}
	/* perhaps a more elegant solution could be found, but this adds more starting letters if the board is large */
	if (thegame.theboard->height >= MAXHAND) {
		for (i = 0; MAX_PLAYERS > i; ++i) {
			place_start_letters(&thegame.players[i]);
		}
	}
	normal_print("Player %s will take the first turn.\n", thegame.players[thegame.curr_player_num].name);
	/* game loop while the flag is not triggered, end game if it is */
	while (quitFlag == FALSE) {
		int moveCheck;
		/* index for winner */
		int winningPlayer;
		winningPlayer = 0;
		moveCheck = MOVE_INVALID;
		/* deal out letters/tiles at the start of each turn */
		deal_letters(thegame.score_list, thegame.players[thegame.curr_player_num].hand);
		/* if both players have no tiles left, trigger end flag */
		if (thegame.players[thegame.curr_player_num].hand->total_count == 0
				&& thegame.players[1 - thegame.curr_player_num].hand->total_count == 0) {
			normal_print("There are no letters left. The game will end after this turn.\n");
			quitFlag = TRUE;
		}
		normal_print("\nPlayer %s's turn.\n", thegame.players[thegame.curr_player_num].name);
		/* print out score and other details */
		calculate_score(&thegame.players[thegame.curr_player_num]);
		normal_print("Their hand contains: ");
		/* retry turn while invalid */
		while (moveCheck == MOVE_INVALID ) {
			for(i = 0; MAXHAND > i; i++) {
				normal_print("%c | ", thegame.players[thegame.curr_player_num].hand->scores[i].letter);
			}
			normal_print("\n\n");
			/* return invalid if invalid and try repeat, return an alternate value for
			 * actions regarding word_list and repeat turn. redundant to check for the value
			 */
			moveCheck = player_turn(&thegame.players[thegame.curr_player_num]);
			if (moveCheck == MOVE_INVALID) {
				error_print("That was not a valid move. Please try again.\n");
			}
		}

		switch(moveCheck) {
			/* determine score and winner here */
			case(MOVE_BOARD_FULL) :
			/* quit on player choice */
			case(MOVE_QUIT) :
				for(i = 0; MAX_PLAYERS > i; i++) {
					calculate_score(&thegame.players[i]);
					if (thegame.players[i].score > thegame.players[winningPlayer].score) {
						winningPlayer = i;
					}
				}
				normal_print("The winner is %s with a score of %d!\n", thegame.players[winningPlayer].name, thegame.players[winningPlayer].score);
				normal_print("Thanks for playing. \n");
				quitFlag = TRUE;
				break;
			/* flip players turn on successful turn or skip - display msg if skip */
			/* no break as it shares behavior with success other than alerting the user */
			case(MOVE_SKIP) :
				normal_print("Player %s has skipped their turn. \n", thegame.players[thegame.curr_player_num].name);
			case(MOVE_SUCCESS) :
				thegame.curr_player_num = 1 - thegame.curr_player_num;
				++turnCount;
				continue;
		}
	}
	clearMemory(&thegame);
}
/* free mems !!!!*/
void clearMemory(struct game *thegame) {
	int i;
	for (i = 0; MAX_PLAYERS > i; ++i) {
		free(thegame->players[i].hand);
	}
	free(thegame->score_list);
	free_cell(thegame->theboard);
}

/* random number generator */
int randomNumber(int max) {
	int num;
	num = (rand() % max);
	return num;
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
