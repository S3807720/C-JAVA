/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
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
};

struct board {
    struct cell** matrix;
    int width;
    int height;
};

void init_cell(struct board* newBoard,int w, int h);
void print_board(struct board* newBoard);
struct board* new_board(int, int);
#endif
