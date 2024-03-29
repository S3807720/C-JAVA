/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "shared.h"
#ifndef RULES_B_H
#define RULES_B_H
struct player;
enum move_result {
    MOVE_QUIT,
    MOVE_SKIP,
    MOVE_SUCCESS,
    MOVE_BOARD_FULL,
	/* add value for try again */
	MOVE_INVALID,
	/* filler value for word list action */
	MOVE_WL_ACTION
};
struct coord {
    int x, y;
};

enum orientation {
    HORIZ,
    VERT
};

BOOLEAN validate_move(struct player* theplayer, const char* word,
                      const struct coord* coords, enum orientation orient);
int calculate_score(struct player* theplayer);
#endif
