/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "shared.h"
#include "rules-b.h"


#ifndef PLAYER_H
#define PLAYER_H
/* the maximum length of a player's name */
#define NAMELEN 40
#define EXTRACHARS 2
#define MAXHAND 5

/**
 * enumeration that defines the colours that can be used for players
 **/
enum color {
    COL_RED,
    COL_GREEN,
    COL_YELLOW,
    COL_BLUE,
    COL_MAGENTA,
    COL_CYAN,
    COL_RESET
};
/* global variable that holds the colour codes that can be used for formatting
 * strings */
extern char *color_strings[];

/**
 * definition of the player structure.
 **/
struct player {
    char name[NAMELEN + EXTRACHARS];
    enum color color;
    struct game *curgame;
    struct score_list *hand;
    int score;
};

BOOLEAN player_init(int count,const char *name,struct game *thegame);
BOOLEAN createPlayer(struct game *thegame);
int getMoveInput(char input[]);
BOOLEAN getString(char input[], int length, char* type);
void executeMove(struct player* theplayer, int orient, struct coord * coords, char * word);

enum move_result player_turn(struct player *);
#endif
