/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "rules-b.h"
#include <string.h>
#include "shared.h"
#include "game.h"

/**
 *
 **/
BOOLEAN validate_move(struct player* theplayer, const char* word,
                      const struct coord* coords, enum orientation orient) {
	int moveConfirm, i, length, ownedCells;
	ownedCells = 0;
	/* get length of word */
	length = strlen(word);
	printf("length of word: %s %d", word, length);
	if (orient == HORIZ) {
		for (i = coords->x; theplayer->curgame->theboard->height > i; ++i){
			--length;
			if (theplayer->curgame->theboard->matrix[i][coords->y].owner != NULL) {
				++ownedCells;
			}
		}
		if (length > -1) {
			moveConfirm = TRUE;
		}
	}
	else if (orient == VERT) {
		for (i = coords->y; theplayer->curgame->theboard->width > i; ++i){
			--length;
			if (theplayer->curgame->theboard->matrix[coords->x][i].owner != NULL) {
				++ownedCells;
			}
		}
		if (length > -1) {
			moveConfirm = TRUE;
		}
	}
	if (moveConfirm == FALSE || length < 0 || ownedCells == 0) {
		error_print("That was not a valid move. Please try again.");
		return FALSE;
	}
	return TRUE;
}

int calculate_score(struct player* theplayer) {
	return 0;
}
