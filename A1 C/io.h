/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : s3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 1, study period 4, 2020.
 *****************************************************************************/
#include <stdarg.h>
#include <string.h>

#include "imp.h"
#include "shared.h"
#ifndef IO_H
#define IO_H
/**
 * the choices that can be made from the menu. Do not hardcode the values - use
 * these constants. Hardcoding of values is poor practice and makes your code
 * very difficult to maintain.
 **/
enum menu_choice {
	MC_REVERSE,
	MC_MAGSQ,
	MC_GR_KNAP,
	MC_BF_KNAP,
	/* a default return for failed input */
	MC_DEFAULT,
	MC_QUIT = -1
};

/**
 * enumeration provided to you to return the result an input operation.
 * these could've been incorporated into menu_choice
 * to minimize the amount of redundant code...
 **/
enum input_result { IR_FAILURE, IR_SUCCESS, IR_EOF = -1 };

#define LINELEN 80
#define EXTRACHARS 2
/* 40*40 *4 should be enough to cover any inputs for the square */
#define SQUARELEN 6400

enum menu_choice display_menu(void);
int getString(char input[], int length);
int getInteger(int* integer);
int error_print(const char *format, ...);
int normal_print(const char *format, ...);

/**
 * declare functions here that handle the input for each option. For now each
 * function will just say "hello from <option_name>" (where option_name is the
 * name of the option being called) but the first thing you will want to do in
 * part 3 is to implement these function to handle the user input for each
 * function.
 **/

void reverseString();
void magicSquare();
void greedyKnapsack();
void recursiveKnapsack();

#endif
