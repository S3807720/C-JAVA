/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "player.h"
#include "game.h"
#include "board.h"
#include <ctype.h>
#define MAX_COL 7
/* the color_strings array that defines the color codes for the printing
 * out colour in the terminal. The order of the values in this array is the
 * same as the color enum
 **/
char *color_strings[] = {"\033[0;31m", "\033[0;32m", "\033[0;33m", "\033[0;34m",
		"\033[0;35m", "\033[0;36m", "\033[0m"};
/**
 * prompt the user for their name and deal the letters for the player to start
 * with. Also assign the game pointer so we can access it later.
 **/

BOOLEAN getString(char input[], int length, char* type) {
	int finished = FALSE;
	do {
		normal_print("Please enter your %s(blank input cancels input):\n", type);
		/* exit back to menu when blank or ctrl d is entered */
		if (fgets(input, length, stdin) == NULL) {
			return MOVE_QUIT;
		}
		if (*input == '\n') {
			return MOVE_SKIP;
		}
		/* if input is too long, clear buffer  and try again*/
		if (input[strlen(input) - 1] != '\n') {
			error_print("Input was too long.\n");
			clear_buffer();
		} else {
			/* replace \n with \0. */
			input[strlen(input) - 1] = '\0';
			/* change flag to end loop if everything's A-OK */
			finished = TRUE;
		}
	} while (finished == FALSE);
	return MOVE_SUCCESS;
}

BOOLEAN createPlayer(struct game *thegame) {
	char playerName[NAMELEN];
	int errorCheck;
	int count;
	count = 0;
	while (MAX_PLAYERS > count) {
		errorCheck = getString(playerName, NAMELEN+EXTRACHARS, "name");
		if(errorCheck == MOVE_SKIP || errorCheck == MOVE_QUIT) {
			return FALSE;
		}
		player_init(count, playerName, thegame);
		/* increase curr players */
		thegame->curr_player_num++;
		++count;
	}

	return TRUE;
}

BOOLEAN player_init(int count,const char *name,
		struct game *thegame) {
	int randCol, i;
	randCol = randomNumber(MAX_COL);
	thegame->players[count].curgame = thegame;
	strcpy(thegame->players[count].name, name);
	/* set semi random colour */
	thegame->players[count].color = randCol;
	thegame->players[count].score = 0;
	thegame->players[count].hand = malloc(sizeof(struct score_list));
	thegame->players[count].hand->num_scores = 0;
	thegame->players[count].hand->total_count = 0;
	for (i = 0; MAXHAND > i; i++) {
		thegame->players[count].hand->scores[i].count = EOF;
		thegame->players[count].hand->scores[i].letter = EOF;
		thegame->players[count].hand->scores[i].score = EOF;
	}
	return TRUE;
}


/**
 * play a move for this player. please see the assignment specification for the
 * details of this.
 **/
enum move_result player_turn(struct player *theplayer) {
	int moveCheck, orient, x, y, i;
	char word[NAMELEN];
	struct coord coords;
	/* eof as default */
	x = EOF, y = EOF, moveCheck = 0, orient = 0;
	moveCheck = print_board(theplayer->curgame->theboard);
	if (moveCheck == MOVE_BOARD_FULL) {
		return MOVE_BOARD_FULL;
	}
	moveCheck = getString(word, NAMELEN+EXTRACHARS, "word");
	/* convert to upper case */
	for(i = 0; word[i] != '\0'; ++i) {
		word[i] = toupper(word[i]);
	}

	if (moveCheck == MOVE_SKIP) {
		return MOVE_SKIP;
	}
	if (moveCheck == MOVE_QUIT) {
		return MOVE_QUIT;
	}
	while (x < 0 || x > theplayer->curgame->theboard->width
			|| y < 0 || y > theplayer->curgame->theboard->height) {
		moveCheck = getInteger(&x, "x(left to right) coordinate for word");
		if (moveCheck == MOVE_SKIP) {
			return MOVE_SKIP;
		}
		if (moveCheck == MOVE_QUIT) {
			return MOVE_QUIT;
		}
		moveCheck = getInteger(&y, "y(top to bottom) coordinate for word");
		if (moveCheck == MOVE_SKIP) {
			return MOVE_SKIP;
		}
		if (moveCheck == MOVE_QUIT) {
			return MOVE_QUIT;
		}
		/* if invalid width */
		if (x < 0 || x > theplayer->curgame->theboard->width
				|| y < 0 || y > theplayer->curgame->theboard->height) {
			error_print("Invalid input. Must be positive numbers below boards "
					"maximum width(%d) and height(%d).\n",theplayer->curgame->theboard->width, theplayer->curgame->theboard->height );
		}
	}
	while(orient < 1 || orient > 2) {
		moveCheck = getInteger(&orient, "orientation for the word - 1 for horizontal or 2 for vertical");
		if (moveCheck == MOVE_SKIP) {
			return MOVE_SKIP;
		}
		if (moveCheck == MOVE_QUIT) {
			return MOVE_QUIT;
		}
		if (orient < 1 || orient > 2) {
			error_print("Invalid input. Must be 1 or 2.\n");
		}
	}
	/* set orient based on input */
	if (orient == 1) {
		orient = HORIZ;
	}
	else {
		orient = VERT;
	}
	/*set coords to proper array vals */
	coords.x = x-1, coords.y = y-1;
	moveCheck = validate_move(theplayer, word, &coords, orient);
	if (moveCheck == MOVE_INVALID) {
		return MOVE_INVALID;
	}
	/* if validated, execute move and change variables */
	if (moveCheck == MOVE_SUCCESS) {
		executeMove(theplayer, orient, &coords, word);
		return MOVE_SUCCESS;
	}

	if (moveCheck == MOVE_BOARD_FULL) {
		return MOVE_BOARD_FULL;
	}

	return MOVE_QUIT;
}

/* execute action */
void executeMove(struct player* theplayer, int orient, struct coord * coords, char * word) {
	/* generic i, index for position of found hand, placedTiles to keep track of how much to decrement hand */
	int i, index, placedTiles, wordIndex, hand, handLen, found, length, position;
	length = strlen(word);
	placedTiles = 0, wordIndex = 0, handLen = theplayer->hand->total_count;
	if (orient == HORIZ) {
		position = coords->x;
		for (i = 0; length > i; ++i, ++wordIndex, ++position) {
			found = FALSE;
			/* if letter already on board */
			if (theplayer->curgame->theboard->matrix[position][coords->y].owner != NULL) {
				/* remove score from owner */
				theplayer->curgame->theboard->matrix[position][coords->y].owner->score -= theplayer->curgame->theboard->matrix[position][coords->y].score;
				/* re-assign owner and add score to new owner */
				theplayer->curgame->theboard->matrix[position][coords->y].owner = theplayer;
				theplayer->curgame->theboard->matrix[position][coords->y].owner->score += theplayer->curgame->theboard->matrix[position][coords->y].score;
			}
			else {
				/* variable to store position of found card in hand */
				index = 0;
				for(hand = 0; handLen > hand; ++hand) {
					if (word[wordIndex] == theplayer->hand->scores[hand].letter) {
						index = hand;
						found = TRUE;
						/* reduce count of hand */
						++placedTiles;
					}
				}
				/* change values of indexed letter and place on board */
				if(found == TRUE) {
					theplayer->curgame->theboard->matrix[position][coords->y].letter = theplayer->hand->scores[index].letter;
					theplayer->curgame->theboard->matrix[position][coords->y].owner = theplayer;
					/* update scores for node and player */
					theplayer->curgame->theboard->matrix[position][coords->y].score = theplayer->hand->scores[index].score;
					theplayer->curgame->theboard->matrix[position][coords->y].owner->score += theplayer->hand->scores[index].score;
					theplayer->hand->scores[index].letter = EOF;
					--theplayer->hand->scores[index].count;
				}
			}

		}
	}
	/* if vertical, do same w/ diff part of array */
	else if (orient == VERT) {
		position = coords->y;
		for (i = 0; length > i; ++i, ++wordIndex, ++position) {
			found = FALSE;
			/* if letter already on board */
			if (theplayer->curgame->theboard->matrix[coords->x][position].owner != NULL) {
				theplayer->curgame->theboard->matrix[coords->x][position].owner->score -= theplayer->curgame->theboard->matrix[coords->x][position].score;
				theplayer->curgame->theboard->matrix[coords->x][position].owner = theplayer;
				theplayer->curgame->theboard->matrix[coords->x][position].owner->score += theplayer->curgame->theboard->matrix[coords->x][position].score;
				continue;
			}
			else {
				index = 0;
				for(hand = 0; handLen > hand; ++hand) {
					if (word[wordIndex] == theplayer->hand->scores[hand].letter) {
						index = hand;
						found = TRUE;
						/* reduce count of hand */
						++placedTiles;
					}
				}
				/* change values of indexed letter and place on board */
				if(found == TRUE) {
					theplayer->curgame->theboard->matrix[coords->x][position].letter = theplayer->hand->scores[index].letter;
					theplayer->curgame->theboard->matrix[coords->x][position].owner = theplayer;
					/* update scores for node and player */
					theplayer->curgame->theboard->matrix[coords->x][position].score = theplayer->hand->scores[index].score;
					theplayer->curgame->theboard->matrix[coords->x][position].owner->score += theplayer->curgame->theboard->matrix[coords->x][position].score;
					theplayer->hand->scores[index].letter = EOF;
					--theplayer->hand->scores[index].count;
				}
			}
		}
	}
	theplayer->hand->total_count -= placedTiles;
}
