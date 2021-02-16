/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include "player.h"
#include "score_list.h"
#include "word_list.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <stdarg.h>

#ifndef GAME_H
#define GAME_H
#define MAX_PLAYERS 2
#define NUM_ARGS 3

struct game {
    struct player players[MAX_PLAYERS];
    int curr_player_num;
    struct board* theboard;
    struct score_list* score_list;
    struct word_list* word_list;
};

BOOLEAN game_init(struct game*);
void play_game(const char*, const char*);
void createHand(struct score_list *, struct game*);
BOOLEAN file_exists (char *filename);
int error_print(const char *format, ...);
int normal_print(const char *format, ...);
void clear_buffer(void);
int randomNumber(int max);
int getInteger(int* integer, char* type);
void clearMemory(struct game *);

#endif
