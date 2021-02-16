/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include <string.h>
#include "rules-b.h"
#include "game.h"
#include "player.h"
#include "board.h"
#include "word_list.h"

/* use details to check every possibility for errors */
BOOLEAN validate_move(struct player* theplayer, const char* word,
		const struct coord* coords, enum orientation orient) {
	int moveConfirm, i, length, ownedCells, wordIndex, handCheck, handCounter, position;
	wordIndex = 0;
	moveConfirm = MOVE_INVALID;
	/* count of nodes to ensure non empty spot */
	ownedCells = 0;
	/* get length of word */
	length = strlen(word);
	if (orient == HORIZ) {
		/* set position to same val as coords and increment each loop */
		position = coords->x;
		/* loop until the difference between max width and coords input and length of string */
		for (i = 0; (theplayer->curgame->theboard->width - coords->x) > i && strlen(word) > i;
				++i, ++wordIndex, ++position){
			handCheck = FALSE;
			--length;
			/* increment counter for nodes occupied & check if word choice is valid */
			if (theplayer->curgame->theboard->matrix[position][coords->y].owner != NULL) {
				if (theplayer->curgame->theboard->matrix[position][coords->y].letter != word[wordIndex]) {
					return MOVE_INVALID;
				}
				++ownedCells;
				handCheck = TRUE;
			}
			/* otherwise check if player actually has the tile in hand */
			else if (theplayer->curgame->theboard->matrix[position][coords->y].owner == NULL) {
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
		}
		if (length < 1 && ownedCells < strlen(word)) {
			moveConfirm = MOVE_SUCCESS;
		}
	}
	else if (orient == VERT) {
		position = coords->y;
		/* loop until the difference between max height and coords input */
		for (i = 0; (theplayer->curgame->theboard->height - coords->y) > i && strlen(word) > i;
				++i, ++wordIndex, ++position){
			handCheck = FALSE;
			--length;
			/* increment counter for nodes occupied & check if word choice is valid */
			if (theplayer->curgame->theboard->matrix[coords->x][position].owner != NULL) {
				if (theplayer->curgame->theboard->matrix[coords->x][position].letter != word[wordIndex]) {
					return MOVE_INVALID;
				}
				++ownedCells;
				handCheck = TRUE;
			}
			/* otherwise check if player actually has the tile in hand */
			else if (theplayer->curgame->theboard->matrix[coords->x][position].owner == NULL) {
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
		}
		/* if length is below 1 then there's enough space */
		if (length < 1 && ownedCells < strlen(word)) {
			moveConfirm = MOVE_SUCCESS;
		}
	}
	/* ensure words in the list */
	moveConfirm = find_word(theplayer->curgame->word_list, word);
	/* final confirmation that nothing went wrong :) */
	if (moveConfirm != MOVE_SUCCESS || length > 0 || ownedCells == 0) {
		return MOVE_INVALID;
	}
	/* and wow, made it to the finish line! */
	return moveConfirm;
}
/* as there is a score already in the player struct, there's no real need to calculate it. easier to keep it up to date,
 * this will just display the results or if the player somehow doesn't exist, error? */
int calculate_score(struct player* theplayer) {
	if (!theplayer->name) {
		error_print("This player doesn't exist.\n");
		return FALSE;
	}
	normal_print("The score for player %s is: %d\n", theplayer->name, theplayer->score);
	return TRUE;
}
