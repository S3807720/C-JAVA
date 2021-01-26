/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/

#include "score_list.h"
#include "shared.h"
#define delim ", \n"
/**
 * returns a malloced list of the scores loaded in from disk. As this is file io
 * you need to validate file data to ensure it is valid. Do not however validate
 *the file name.
 **/
//void scoreInit(struct score_list* scoreBoard) {
//	int i;
//	scoreBoard = malloc(sizeof(struct score_list));
//}

struct score_list *load_scores(const char *filename) {
	FILE *fpRead;
	int i;
	i = 0;
	char line[NUM_SCORES], *ltr, *score, *cnt, *end, *ptr;
	ltr = NULL, score = NULL, cnt = NULL, ptr = NULL;
	struct score_list *scoreBoard;
	scoreBoard = malloc(sizeof(struct score_list));
//	scoreInit(scoreBoard);
	if ((fpRead = fopen(filename, "r")) == NULL) {
		return EXIT_FAILURE;
	}
    /* loop through each line in file */
    while(fgets(line, sizeof(line), fpRead) != NULL){
    	printf("%s", line);
        /*no need to convert the char to a long as we want the int value of the char */
    	ltr = strtok_r(line, delim, &ptr);
		scoreBoard->scores[i].letter = *ltr;
		printf("count: %d = %c | ", i, scoreBoard->scores[i].letter);
    	score = strtok_r(NULL, delim, &ptr);
    	scoreBoard->scores[i].score = (int) strtol(score, &end, 0);
	    printf("%d | ", scoreBoard->scores[i].score);
    	cnt = strtok_r(NULL, delim, &ptr);
    	scoreBoard->scores[i].count = (int) strtol(cnt, &end, 0);
		printf("%d\n\n", scoreBoard->scores[i].count);
        /*go to next line */
        i++;
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

}
