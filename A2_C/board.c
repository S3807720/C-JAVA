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

#define EOF -1

struct board* new_board(int w, int h) {
	struct board newBoard;
	printf("new board method\n");
	newBoard.width = w;
	newBoard.height = h;
	/* allocate width/rows then go through each row and allocate columns */
	newBoard.matrix = malloc(w * sizeof(int));
	for(int i = 0; i < h; i++) {
		newBoard.matrix[i] = malloc(h * sizeof(int));
	}
	/* initialize each cell */
	init_cell(&newBoard,w, h);
	return TRUE;
}

void init_cell(struct board* newBoard,int w, int h) {
	int i, j;
	printf("starting to init\n%d %d\n",w,h);
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
