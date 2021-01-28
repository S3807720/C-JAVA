/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
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
	for(i = 0; i < w; i++) {
		newBoard->matrix[i] = malloc(h * sizeof(struct cell));
	}
	/* initialize each cell */
	init_cell(newBoard,w, h);
	return newBoard;
}

/* print current board */
void print_board(struct board* newBoard) {
	int i, j;
//	newBoard->matrix[1][2].letter = 'F';
//	newBoard->matrix[j][i].owner =
//	newBoard->matrix[1][0].letter = 'B';
//	newBoard->matrix[0][1].letter = 'Z';
//	newBoard->matrix[0][2].letter = 'X';
	normal_print("  | ");
	for (i = 0; newBoard->width > i; ++i) {
		normal_print("%d | ", i+1);
	}
	normal_print("<X\n");
	/* go through each row, left to right */
	for (i = 0; newBoard->height > i; ++i) {
		/* adjust the amount of --- for each width to keep symmetry*/
		for(j = 0; newBoard->width > j; ++j) {
			normal_print("-----");
		}
		normal_print("\n%d | ", i+1);
		for (j = 0; newBoard->width > j; ++j) {
			if (newBoard->matrix[j][i].owner != NULL) {
				normal_print("%s%c%s | ", color_strings[newBoard->matrix[j][i].owner->color],
						newBoard->matrix[j][i].letter, color_strings[COL_RESET]);
			}
		}
		normal_print("\n");
	}
	normal_print("Y\n");
}

void init_cell(struct board* newBoard,int w, int h) {
	int i, j;
	i = 0;
	while (w > i) {
		j = 0;
		while (h > j) {
			newBoard->matrix[i][j].owner = NULL;
			newBoard->matrix[i][j].letter = EOF;
			printf("+");
			++j;
		}
		printf("\n");
		++i;
	}
}

void free_cell(struct board* newBoard) {
	int i, j;
	printf("starting to wipe board\n");
	i = 0;
	while (newBoard->width > i) {
		j = 0;
		while (newBoard->height > j) {
			free(newBoard->matrix[i]);
			printf("+");
			++j;
		}
		printf("\n");
		++i;
	}
	free(newBoard);
}
