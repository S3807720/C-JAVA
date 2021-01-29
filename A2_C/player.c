/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "player.h"
#include "game.h"
#include "board.h"

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
		if (fgets(input, length, stdin) == NULL || *input == '\n') {
			return FALSE;
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
	return TRUE;
}
/* fix to get char inputs */
int getMoveInput(char input[]) {
	int finished = FALSE;
	do {
		normal_print("Please enter your %s(blank input cancels input):\n", type);
		/* exit back to menu when blank or ctrl d is entered */
		if (fgets(input, NAMELEN, stdin) == NULL || *input == '\n') {
			return FALSE;
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
	return TRUE;
}

BOOLEAN createPlayer(struct game *thegame) {
	BOOLEAN errorCheck;
	int count;
	count = 0;
	char playerName[NAMELEN];
	while (MAX_PLAYERS > count) {
		errorCheck = getString(playerName, NAMELEN+EXTRACHARS, "name");
		if(errorCheck == FALSE) {
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
	thegame->players[count].curgame = thegame;
	strcpy(thegame->players[count].name, name);
	/* set semi random colour */
	int randCol, i;
	randCol = randomNumber(MAX_COL);
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
	printf("player color:%d name:%s\n", thegame->players[count].color, thegame->players[count].name);
	return TRUE;
}


/**
 * play a move for this player. please see the assignment specification for the
 * details of this.
 **/
enum move_result player_turn(struct player *theplayer) {
	int moveCheck;
	char word[NAMELEN], coordinates[NAMELEN];
	int orient;
	print_board(theplayer->curgame->theboard);
	while (moveCheck != FALSE) {
		moveCheck = getString(word, NAMELEN+EXTRACHARS, "word");
		moveCheck = getString(coordinates, NAMELEN, "coordinates for word(x,y)");
		moveCheck = getInteger(&orient, "orientation for the word - 1 for horizontal or 2 for vertical")-1;
		/* turn coord into struct for int, int */
	}
	moveCheck = MOVE_SUCCESS;
	if (moveCheck == FALSE) {
		return MOVE_SKIP;
	}
	struct coord coords;
	coords.x = 1, coords.y = 2;
	if (moveCheck == MOVE_SUCCESS) {
		validate_move(theplayer, word, &coords, orient);
		//theplayer->curgame->theboard->matrix[i][coords->y];
	}

	if (moveCheck == MOVE_BOARD_FULL) {

	}

	return MOVE_QUIT;
}
