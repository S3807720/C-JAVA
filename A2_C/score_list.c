/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/

#include "score_list.h"
#include "shared.h"
#include "game.h"
#define delim ", \n"
#define MAX_HAND 5
/**
 * returns a malloced list of the scores loaded in from disk. As this is file io
 * you need to validate file data to ensure it is valid. Do not however validate
 *the file name.
 **/


/* need to check and verify inputs for some stupid reason..
 * You should validate that each line is formatted correctly with 2 commas and that the second and
third elements are integers and the first element is a single letter in the range A-Z. Each letter should
be unique and all letters must appear so by the end of the function your score list should have 26
elements.
 * */

struct score_list *load_scores(const char *filename) {
	FILE *fpRead;
	int i;
	i = 0;
	char line[NUM_SCORES], *ltr, *score, *cnt, *end, *ptr;
	ltr = NULL, score = NULL, cnt = NULL, ptr = NULL;
	struct score_list *scoreBoard;
	scoreBoard = malloc(sizeof(struct score_list));
	scoreBoard->num_scores = 0;
	scoreBoard->total_count = 0;
	/* already verified file exists, no need to do anything */
	if ((fpRead = fopen(filename, "r")) == NULL) {
		return EXIT_FAILURE;
	}
    /* loop through each line in file */
    while(fgets(line, sizeof(line), fpRead) != NULL){
//    	printf("%s", line);
        /*no need to convert the char to a long as we want the int value of the char */
    	ltr = strtok_r(line, delim, &ptr);
//    	if ((*ltr >= 'a' && *ltr <= 'z') || (*ltr >= 'A' && *ltr <= 'Z')) {
//    		error_print("Invalid character detected in file.");
//    		exit(0);
//    	}
		scoreBoard->scores[i].letter = *ltr;
//		printf("count: %d = %c | ", i, scoreBoard->scores[i].letter);
    	score = strtok_r(NULL, delim, &ptr);
    	scoreBoard->scores[i].score = (int) strtol(score, &end, 0);
//	    printf("%d | ", scoreBoard->scores[i].score);
    	cnt = strtok_r(NULL, delim, &ptr);
    	scoreBoard->scores[i].count = (int) strtol(cnt, &end, 0);
//		printf("%d\n\n", scoreBoard->scores[i].count);
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
	while (MAX_HAND > player_hand->total_count && score_list->total_count > 0) {
		/* get array position to place new letter */
		for (index = 0; player_hand->scores[index].letter > EOF; index++);
		random = randomNumber(score_list->num_scores);
		if (score_list->scores[random].count == 0) {
			continue;
		}
		score_list->scores[random].count--;
		score_list->total_count--;
		player_hand->scores[index] = score_list->scores[random];
		player_hand->scores[index].count = 1;
		player_hand->total_count++;
		player_hand->num_scores++;
//		printf("player hand:  %c & %d | random tile: %c & %d| \n", player_hand->scores[index].letter, player_hand->scores[index].count, score_list->scores[random].letter, score_list->scores[random].count);
	}

	if (score_list->total_count == 0){
		printf("Final tiles have been dealt. \n");
	}
}
