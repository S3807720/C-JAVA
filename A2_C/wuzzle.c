/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 2, study period 4, 2020.
 *****************************************************************************/

#include "game.h"
#include <unistd.h>

int main(int argc, char* argv[]) {
	/* check the command line args and then pass the appropriate arg to
	 * play_game()
	 **/
	FILE *fpRead;
	char *scoreFile = argv[1];
	char *wordFile = argv[2];
	/* error and buh-bye if invalid argument */
	if(argc != NUM_ARGS) {
		fprintf(stderr, "Error: invalid arguments passed in.\n");
		return EXIT_FAILURE;
	}
	/* read only mode to ensure file exists */
	if ((fpRead = fopen(scoreFile, "r")) == NULL) {
		fprintf(stderr, "Error: score file does not exist. Exiting game.\n");
		return EXIT_FAILURE;
	}
	fclose(fpRead);
	if ((fpRead = fopen(wordFile, "r")) == NULL) {
		fprintf(stderr, "Error: word file does not exist. Exiting game.\n");
		return EXIT_FAILURE;
	}
	/* gots to close the file read :) */
	fclose(fpRead);
	play_game(scoreFile, wordFile);
	return EXIT_SUCCESS;
}
