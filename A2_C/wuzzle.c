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
	printf("%d args \n", argc);
	    if(argc != NUM_ARGS) {
	        fprintf(stderr, "Error: invalid arguments passed in.\n");
	        return EXIT_FAILURE;
	    }
	/* read only mode to ensure file exists */
	FILE *fpRead;
	char *fileName = argv[1];
	/*char *fileName = "C:\\Users\\l\\Documents\\eclipse-workspace\\A2_C\\Debug\\word_scores.txt";*/
    if (!(access(argv[1], R_OK)) == 0 ) {
		fprintf(stderr, "Error: file does not exist.\n");
        return EXIT_FAILURE;
    }
	if ((fpRead = fopen(argv[1], "r")) == NULL) {
				fprintf(stderr, "Error: file does not exist.\n");
		        return EXIT_FAILURE;
	}
	if ((fpRead = fopen(fileName, "r")) == NULL) {
				fprintf(stderr, "Error: file does not exist. Exiting game.\n");
		        return EXIT_FAILURE;
	}
	fclose(fpRead);
    play_game(fileName);
	return EXIT_SUCCESS;
}
