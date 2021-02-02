/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "shared.h"
#ifndef BOARD_H
#define BOARD_H

struct player;

struct cell {
    struct player* owner;
    int letter;
    int score;
};

struct board {
    struct cell** matrix;
    int width;
    int height;
};

void init_cell(struct board* newBoard,int w, int h);
int print_board(struct board* newBoard);
void free_cell(struct board* newBoard);
struct board* new_board(int, int);
#endif
