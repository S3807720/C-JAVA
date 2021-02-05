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
	char *fileName = argv[1];
	if(argc != NUM_ARGS) {
		fprintf(stderr, "Error: invalid arguments passed in.\n");
		return EXIT_FAILURE;
	}
	/* read only mode to ensure file exists */
	/*char *fileName = "C:\\Users\\l\\Documents\\eclipse-workspace\\A2_C\\Debug\\word_scores.txt";*/
	if ((fpRead = fopen(fileName, "r")) == NULL) {
		fprintf(stderr, "Error: file does not exist. Exiting game.\n");
		return EXIT_FAILURE;
	}
	fclose(fpRead);
	play_game(fileName);
	return EXIT_SUCCESS;
}
