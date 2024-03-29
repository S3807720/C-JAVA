/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "player.h"

#ifndef SCORE_LIST_H
#define SCORE_LIST_H

/**
 * a letter score is a combination of the letter itself, its score and its count
 **/
struct score {
    int letter, score, count;
};

#define NUM_SCORES 26
/**
 * the scores list that contains the scores for all letters and doubles as the
 * 'deck' from which letters are dealt. This is also the structure used by each
 * player to represent their hand.
 **/
struct score_list {
    struct score scores[NUM_SCORES];
    int num_scores;
    int total_count;
};

struct score_list *load_scores(const char *);
void deal_letters(struct score_list *, struct score_list *);
void place_start_letters(struct player *);
int detectDuplicateLetters(struct score_list *);
#endif
