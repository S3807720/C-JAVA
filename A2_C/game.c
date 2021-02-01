/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "game.h"
#include "player.h"

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
				printf("Input was not numeric.\n");
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
	int errorCheck;
	errorCheck = createPlayer(thegame);
	if (errorCheck == FALSE) {
		return FALSE;
	}
	int width, height;
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
	printf("height: %d width: %d random node: %d\n", thegame->theboard->height, thegame->theboard->width, thegame->theboard->matrix[0][1].letter);
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
	int errorCheck, i, turnCount, quitFlag;
	turnCount = 0, quitFlag = FALSE;
	srand(time(NULL));
	errorCheck = game_init(&thegame);
	if (errorCheck == FALSE) {
		normal_print("Input was cancelled. Exiting game. Thanks for playing. :)");
		exit(0);
	}
	struct score_list scoreList;
	scoreList = *load_scores(scoresfile);
	/* flip coin for player turn */
	thegame.curr_player_num = randomNumber(MAX_PLAYERS);
	normal_print("Player %s will take the first turn.\n", thegame.players[thegame.curr_player_num].name);
	/* game loop */
	thegame.theboard->matrix[1][2].letter = 'F';
	thegame.theboard->matrix[1][2].owner = &thegame.players[1];
	thegame.theboard->matrix[1][0].letter = 'B';
	thegame.theboard->matrix[1][0].owner = &thegame.players[0];
	thegame.theboard->matrix[0][1].letter = 'Z';
	thegame.theboard->matrix[0][1].owner = &thegame.players[1];
	thegame.theboard->matrix[0][2].letter = 'X';
	thegame.theboard->matrix[0][2].owner = &thegame.players[0];
	while (quitFlag == FALSE) {
		int moveCheck;
		moveCheck = MOVE_INVALID;
		deal_letters(&scoreList, thegame.players[thegame.curr_player_num].hand);
		normal_print("\n");
		normal_print("Player %s's turn.\nTheir hand contains: %c", thegame.players[thegame.curr_player_num].name);
		normal_print("\n");
		/* retry turn while invalid */
		while (moveCheck == MOVE_INVALID ) {
			for(i = 0; 5 > i; i++) {
				normal_print("%c | ", thegame.players[thegame.curr_player_num].hand->scores[i].letter);
			}
			normal_print("\n\n");
			moveCheck = player_turn(&thegame.players[thegame.curr_player_num]);
		}
		switch(moveCheck) {
		/* determine score and winner here */
		case(MOVE_BOARD_FULL) :
			normal_print("Board is full. The winner is..\n");
			quitFlag = TRUE;
			break;
		/* quit on player choice */
		case(MOVE_QUIT) :
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
