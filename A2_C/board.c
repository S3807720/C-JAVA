/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "board.h"
#include "game.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct board* new_board(int w, int h) {
	struct board* newBoard;
	int i;
	newBoard = malloc(sizeof(struct board));
	newBoard->width = w;
	newBoard->height = h;
	/* allocate width/columns then go through each row and allocate rows */
	newBoard->matrix = malloc(w * sizeof(struct cell));
	for(i = 0; i < w; ++i) {
		newBoard->matrix[i] = malloc(h * sizeof(struct cell));
	}
	/* initialize each cell */
	init_cell(newBoard,w, h);
	return newBoard;
}

/* and free allocated mem */
void free_cell(struct board* newBoard) {
	int i;
	i = 0;
	while (newBoard->width > i) {
		free(newBoard->matrix[i]);
		++i;
	}
	free(newBoard->matrix);
	free(newBoard);
}

/* print current board */
int print_board(struct board* newBoard) {
	int i, j, nodeCount;
	nodeCount = 0;
	normal_print("\n%3c|", ' ');
	for (i = 0; newBoard->width > i; ++i) {
		normal_print("%3d|", i+1);
	}
	normal_print("\n");
	/* go through each row, left to right */
	for (i = 0; newBoard->height > i; ++i) {
		/* adjust the amount of --- for each width */
		for(j = 0; newBoard->width > j; ++j) {
			normal_print("-----");
		}
		normal_print("\n%3d|", i+1);
		for (j = 0; newBoard->width > j; ++j) {
			/* if has a owner, print the char */
			if (newBoard->matrix[j][i].owner != NULL) {
				normal_print("%s%3c%s|", color_strings[newBoard->matrix[j][i].owner->color],
						newBoard->matrix[j][i].letter, color_strings[COL_RESET]);
			/*	normal_print("%3c|", newBoard->matrix[j][i].letter);*/
				++nodeCount;
			}
			/* otherwise empty spot */
			else {
				normal_print("%3c|", ' ');
			}
		}
		normal_print("\n");
	}
	normal_print("\n");
	if(nodeCount == (newBoard->height * newBoard->width)) {
		return MOVE_BOARD_FULL;
	}
	return MOVE_SUCCESS;
}

/* initialize each cell to default values */
void init_cell(struct board* newBoard,int width, int height) {
	int i, j;
	i = 0;
	while (width > i) {
		j = 0;
		while (height > j) {
			newBoard->matrix[i][j].owner = NULL;
			newBoard->matrix[i][j].letter = EOF;
			newBoard->matrix[i][j].score = EOF;
			++j;
		}
		++i;
	}
}
