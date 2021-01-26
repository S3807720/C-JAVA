/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "player.h"

#include "game.h"
#include <time.h>
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
		normal_print("Please enter your %s(blank input sends back to menu):\n", type);
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
	srand(time(NULL));
	int randCol;
	randCol = ( rand() % 7);
	thegame->players[count].color = randCol;
	thegame->players[count].score = 0;
	printf("end method\n color:%d name:%s\n", thegame->players[count].color, thegame->players[count].name);
    return TRUE;
}

/**
 * play a move for this player. please see the assignment specification for the
 * details of this.
 **/
enum move_result player_turn(struct player *theplayer) { return MOVE_QUIT; }
