/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/

#include "score_list.h"
#include "shared.h"
#include "game.h"
#include "player.h"
#include "board.h"
#include <ctype.h>
#include <string.h>

#define delim ", \n"
#define MAX_HAND 5
/**
 * returns a malloced list of the scores loaded in from disk. As this is file io
 * you need to validate file data to ensure it is valid. Do not however validate
 *the file name.
 **/

struct score_list *load_scores(const char *filename) {
	FILE *fpRead;
	int i;
	/* pointers for strtok & line write var */
	char line[NUM_SCORES], *ltr, *score, *cnt, *end;
	struct score_list *scoreBoard;
	struct score_list *error_list;
	i = 0;
	ltr = NULL, score = NULL, cnt = NULL;
	error_list = malloc(sizeof(struct score_list));
	error_list->total_count = EOF;
	scoreBoard = malloc(sizeof(struct score_list));
	scoreBoard->num_scores = 0;
	scoreBoard->total_count = 0;
	/* double check file exists, this should never run, if it does - return a false list */
	if ((fpRead = fopen(filename, "r")) == NULL) {
		error_print("File cannot be opened.\n");
		return error_list;
	}
    /* loop through each line in file */
    while(fgets(line, sizeof(line), fpRead) != NULL){
    	/* error if too many characters */
		if (i == NUM_SCORES) {
			error_print("There are too many letters in the file.\n");
			return error_list;
		}
        /*no need to convert the char to a long as we want the int value of the char */
    	ltr = strtok(line, delim);
		scoreBoard->scores[i].letter = *ltr;
		/* make sure it's alphabetical, else fail and quit out */
    	if (isalpha(scoreBoard->scores[i].letter) == 0) {
    		error_print("Invalid character detected in file.");
    		return error_list;
    	}
		/*convert to upper if not */
		scoreBoard->scores[i].letter = toupper(scoreBoard->scores[i].letter);
    	score = strtok(NULL, delim);
    	scoreBoard->scores[i].score = (int) strtol(score, &end, 0);
    	cnt = strtok(NULL, delim);
    	scoreBoard->scores[i].count = (int) strtol(cnt, &end, 0);
		scoreBoard->total_count += scoreBoard->scores[i].count;
		scoreBoard->num_scores += 1;
        /*go to next line */
		++i;
    }
	/* check for dupes, return error if found */
	if(detectDuplicateLetters(scoreBoard) == FALSE) {
		error_print("Duplicate letters found.\n");
		return error_list;
	}
	/* free error list as there's no errors and close file */
	free(error_list);
	error_list = NULL;
	fclose(fpRead);
    return scoreBoard;
}

/* brute force every combination to ensure no duplicate letters are found */
int detectDuplicateLetters(struct score_list *score_list) {
	int i, j;
	/* use i as the constant and compare against j scores, whilst skipping equal numbers */
	for (i = 0; NUM_SCORES > i; ++i) {
		for (j = 0; NUM_SCORES > j; ++j) {
			if (j == i) {
				continue;
			}
			/* false if dupe */
			if(score_list->scores[i].letter == score_list->scores[j].letter) {
				return FALSE;
			}
		}
	}
	return TRUE;
}

/**
 * deal letters from the score list in random order, so that the player hand has
 *five letters at the end.
 **/
void deal_letters(struct score_list *score_list,
                  struct score_list *player_hand) {
	int random, index;
	random = 0;
	/* deal out letters until hand is 5 and total count is not 0*/
	while (MAX_HAND > player_hand->total_count && score_list->total_count > 0) {
		/* get array position to place new letter */
		for (index = 0; player_hand->scores[index].letter > EOF; index++);
		random = randomNumber(score_list->num_scores);
		/* skip score if all are used */
		if (score_list->scores[random].count == 0) {
			continue;
		}
		/* transfer from scorelist to hand */
		--score_list->scores[random].count;
		--score_list->total_count;
		player_hand->scores[index].letter = score_list->scores[random].letter;
		player_hand->scores[index].score = score_list->scores[random].score;
		player_hand->scores[index].count = 1;
		++player_hand->total_count;
	}
	/* if there are no scores left, alert the players */
	if (score_list->total_count == 0){
		normal_print("Final tiles have been dealt. \n");
	}
}
/* get random positions, and a random letter and place it for the player provided */
void place_start_letters(struct player* theplayer) {
	int randomWid, randomHeight, randomLetter;
	randomWid = randomNumber(theplayer->curgame->theboard->width);
	randomHeight = randomNumber(theplayer->curgame->theboard->height);
	randomLetter = randomNumber(theplayer->curgame->score_list->num_scores);
	/* if the same node, recurse until it's unique */
	if (theplayer->curgame->theboard->matrix[randomWid][randomHeight].owner != NULL) {
		place_start_letters(theplayer);
	}
	theplayer->curgame->theboard->matrix[randomWid][randomHeight].letter = theplayer->curgame->score_list->scores[randomLetter].letter;
	theplayer->curgame->theboard->matrix[randomWid][randomHeight].owner = theplayer;
	theplayer->curgame->theboard->matrix[randomWid][randomHeight].score = theplayer->curgame->score_list->scores[randomLetter].score;
}

