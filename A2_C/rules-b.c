/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "rules-b.h"
#include <string.h>
#include "shared.h"
#include "game.h"

/* use details to check every possibility for errors */
BOOLEAN validate_move(struct player* theplayer, const char* word,
		const struct coord* coords, enum orientation orient) {
	int moveConfirm, i, length, ownedCells, wordIndex, handCheck, handCounter;
	wordIndex = 0;
	moveConfirm = MOVE_INVALID;
	/* count of nodes to ensure non empty spot */
	ownedCells = 0;
	/* get length of word */
	length = strlen(word);
	if (orient == HORIZ) {
		for (i = coords->x; theplayer->curgame->theboard->height > i; ++i){
			handCheck = FALSE;
			--length;
			/* increment counter for nodes occupied & check if word choice is valid */
			if (theplayer->curgame->theboard->matrix[i][coords->y].owner != NULL) {
				if (theplayer->curgame->theboard->matrix[i][coords->y].letter != word[wordIndex]) {
					return MOVE_INVALID;
				}
				++ownedCells;
				handCheck = TRUE;
			}
			/* check if player actually has the tile in hand */
			else if (theplayer->curgame->theboard->matrix[i][coords->y].owner == NULL) {
				for(handCounter = 0; theplayer->hand->total_count > handCounter; ++handCounter) {
					if (word[wordIndex] == theplayer->hand->scores[handCounter].letter) {
						handCheck = TRUE;
					}
				}
			}
			if (handCheck == FALSE) {
				error_print("You do not have that letter in your hand.\n");
				return MOVE_INVALID;
			}
			++wordIndex;
		}
		if (length < 1) {
			moveConfirm = MOVE_SUCCESS;
		}
	}
	else if (orient == VERT) {
		for (i = coords->y; theplayer->curgame->theboard->width > i; ++i){
			--length;
			/* increment counter for nodes occupied & check if word choice is valid */
			if (theplayer->curgame->theboard->matrix[coords->x][i].owner != NULL) {
				if (theplayer->curgame->theboard->matrix[coords->x][i].letter != word[wordIndex]) {
					return MOVE_INVALID;
				}
				++ownedCells;
			}
			/* check if player actually has the tile in hand */
			else if (theplayer->curgame->theboard->matrix[coords->x][i].owner == NULL) {
				for(handCounter = 0; theplayer->hand->total_count > handCounter; ++handCounter) {
					if (word[wordIndex] == theplayer->hand->scores[handCounter].letter) {
						handCheck = TRUE;
					}
				}
			}
			if (handCheck == FALSE) {
				error_print("You do not have that letter in your hand.\n");
				return MOVE_INVALID;
			}
			++wordIndex;
		}
		if (length < 1) {
			moveConfirm = MOVE_SUCCESS;
		}
	}
	if (moveConfirm != MOVE_SUCCESS || length > 0 || ownedCells == 0) {
		return MOVE_INVALID;
	}
	return MOVE_SUCCESS;
}
/* as there is a score already in the player struct, there's no real need to calculate it. easier to keep it up to date */
int calculate_score(struct player* theplayer) {
	if (!theplayer->name) {
		error_print("This player doesn't exist.\n");
		return FALSE;
	}
	normal_print("The score for player %s is: %d\n", theplayer->name, theplayer->score);
	return TRUE;
}
