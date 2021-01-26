/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "board.h"

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
