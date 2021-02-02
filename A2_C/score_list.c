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
#include <ctype.h>

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
	i = 0;
	char line[NUM_SCORES], *ltr, *score, *cnt, *end, *ptr;
	ltr = NULL, score = NULL, cnt = NULL, ptr = NULL;
	struct score_list *scoreBoard;
	struct score_list *error_list;
	error_list = malloc(sizeof(struct score_list));
	error_list->total_count = EOF;
	scoreBoard = malloc(sizeof(struct score_list));
	scoreBoard->num_scores = 0;
	scoreBoard->total_count = 0;
	/* already verified file exists, no need to do anything */
	if ((fpRead = fopen(filename, "r")) == NULL) {
		error_print("File cannot be opened.\n");
		return error_list;
	}
    /* loop through each line in file */
    while(fgets(line, sizeof(line), fpRead) != NULL){
		if (i == NUM_SCORES) {
			error_print("There are too many letters in the file.\n");
			return error_list;
		}
        /*no need to convert the char to a long as we want the int value of the char */
    	ltr = strtok_r(line, delim, &ptr);
//    	if ((*ltr >= 'a' && *ltr <= 'z') || (*ltr >= 'A' && *ltr <= 'Z')) {
//    		error_print("Invalid character detected in file.");
//    		exit(0);
//    	}
		scoreBoard->scores[i].letter = *ltr;
		/*convert to upper if not */
		scoreBoard->scores[i].letter = toupper(scoreBoard->scores[i].letter);
    	score = strtok_r(NULL, delim, &ptr);
    	scoreBoard->scores[i].score = (int) strtol(score, &end, 0);
    	cnt = strtok_r(NULL, delim, &ptr);
    	scoreBoard->scores[i].count = (int) strtol(cnt, &end, 0);
		scoreBoard->total_count += scoreBoard->scores[i].count;
		scoreBoard->num_scores += 1;
        /*go to next line */
		++i;
    }
	/* and close file */
	fclose(fpRead);
    return scoreBoard;
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
		score_list->scores[random].count--;
		score_list->total_count--;
		player_hand->scores[index] = score_list->scores[random];
		player_hand->scores[index].count = 1;
		player_hand->total_count++;
		player_hand->num_scores++;
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
	while (theplayer->curgame->theboard->matrix[randomWid][randomHeight].owner != NULL) {
		randomWid = randomNumber(theplayer->curgame->theboard->width);
		randomHeight = randomNumber(theplayer->curgame->theboard->height);
		randomLetter = randomNumber(theplayer->curgame->score_list->num_scores);
	}
	theplayer->curgame->theboard->matrix[randomWid][randomHeight].letter = theplayer->curgame->score_list->scores[randomLetter].letter;
	theplayer->curgame->theboard->matrix[randomWid][randomHeight].owner = theplayer;
	theplayer->curgame->theboard->matrix[randomWid][randomHeight].score = theplayer->curgame->score_list->scores[randomLetter].score;
}

