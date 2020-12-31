/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : s3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 1, study period 4, 2020.
 *****************************************************************************/
#include <stdlib.h>

#include "imp.h"
#include "io.h"
#include "shared.h"

int main(void) {
	BOOLEAN quit = FALSE;
	/*
	 * have placed welcome message here to stop the welcome spam with failed input.
	 */
	normal_print("Welcome to CPT220 Menu System\n");
	normal_print("-----------------------------\n");
	/* grab input and work with it here */
	while (quit == FALSE) {
		int input;
		input = display_menu();
		switch (input) {
		case MC_REVERSE:
			reverseString();
			break;
		case MC_MAGSQ:
			magicSquare();
			break;
		case MC_GR_KNAP:
			greedyKnapsack();
			break;
		case MC_BF_KNAP:
			recursiveKnapsack();
			break;
			/* break loop and exit */
		case MC_QUIT:
			quit = TRUE;
			break;
		default:
			error_print("Invalid number. Please select from the menu.\n");
			quit = FALSE;
			break;
		}
	}
	normal_print(
			"Goodbye! Thank you for using the CPT220 menu system.\n");
	return EXIT_SUCCESS;
}
