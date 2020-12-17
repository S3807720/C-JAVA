/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
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
    /* hints for what to do in main() */
    /* have a loop that terminates when the user selects to exit the program */
    /* call display menu to get the user's choice of what they want to do */
    /* call the appropriate functions that handle user input in io.c */
    /* pass the user input into the appropriate function where that actual
     * option is implemented */
    /* replace this return with a correct return value and delete this comment
     */
    while (quit == FALSE) {
    	display_menu();
    	quit = TRUE;
    }
    return EXIT_FAILURE;
}
