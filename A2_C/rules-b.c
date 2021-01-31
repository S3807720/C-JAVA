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
	moveConfirm = MOVE_INVALID;
	/* count of nodes to ensure non empty spot */
	ownedCells = 0;
	/* get length of word */
	length = strlen(word);
	printf("length of word: %s %d\n", word, length);
	if (orient == HORIZ) {
		for (i = coords->x; theplayer->curgame->theboard->height > i; ++i){
			--length;
			printf("%s \n", theplayer->curgame->theboard->matrix[i][coords->y].owner->name);
			if (theplayer->curgame->theboard->matrix[i][coords->y].owner != NULL) {
				++ownedCells;
			}
		}
		if (length < 1) {
			moveConfirm = MOVE_SUCCESS;
		}
	}
	else if (orient == VERT) {
		for (i = coords->y; theplayer->curgame->theboard->width > i; ++i){
			--length;
			printf("%s \n", theplayer->curgame->theboard->matrix[coords->x][i].owner->name);
			if (theplayer->curgame->theboard->matrix[coords->x][i].owner != NULL) {
				++ownedCells;
			}
		}
		if (length < 1) {
			moveConfirm = MOVE_SUCCESS;
		}
	}
	printf("length: %d\n", length);
	if (moveConfirm != MOVE_SUCCESS || length > 0 || ownedCells == 0) {
		error_print("That was not a valid move. Please try again.\n");
		return MOVE_INVALID;
	}
	printf("move successfully verified\n");
	return MOVE_SUCCESS;
}

int calculate_score(struct player* theplayer) {
	return 0;
}
