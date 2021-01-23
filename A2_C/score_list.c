/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/

#include "score_list.h"
#include "shared.h"
/**
 * returns a malloced list of the scores loaded in from disk. As this is file io
 * you need to validate file data to ensure it is valid. Do not however validate
 *the file name.
 **/
struct score_list *load_scores(const char *filename) {
	FILE *fpRead;
	int i;
	char line[NUM_SCORES], *delim, *value;
	delim = ",";
	struct score_list scoreboard;
	if ((fpRead = fopen(filename, "r")) == NULL) {
		return EXIT_FAILURE;
	}
    // loop through each line in file
    while(fgets(line, sizeof(line), fpRead)){
        //need to fix this to convert the string to integer
        value = strtok(line, delim);
		value = (int) strtol(line, delim, 0);
        scoreboard.scores[i].letter = value;
        printf("%i 0 %s", i, value);

        //load first time
        value = strtok(NULL, delim);
        scoreboard.scores[i].score = value;
        printf("%i 1 %s", i, value);

        // load gender
        value = strtok(NULL, delim);
        scoreboard.scores[i].count = value;
        printf("%i 2 %s", i, value);

        //go to next line
        i++;
    }

	fclose(fpRead);
    return NULL;
}
/* struct score {
    int letter, score, count;
};

struct score_list {
    struct score scores[NUM_SCORES];
    int num_scores;
    int total_count;
}; */
/**
 * deal letters from the score list in random order, so that the player hand has
 *five letters at the end.
 **/
void deal_letters(struct score_list *score_list,
                  struct score_list *player_hand) {

}
