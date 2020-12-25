/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : s3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 1, study period 4, 2020.
 *****************************************************************************/
#include "io.h"

#include <ctype.h>
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>

/**
 * function used to clear the buffer when you detect buffer overflow.
 * You may notice you cannot call this function from outside this module and
 * that's by design (that's why it's static). As you should be doing all input
 * and output code in this module, there is no need to have access to this
 * function outside of the io module.
 **/
static void clear_buffer(void) {
	int ch;
	while (ch = getc(stdin), ch != EOF && ch != '\n')
		;
	clearerr(stdin);
}

int getInteger(int* integer) {
	/* flag to end loop */
	int finished = FALSE;
	char input[LINELEN + EXTRACHARS];
	int int_result = 0;
	char* end;
	do {
		normal_print("Please enter your choice:\n");
		/* send EOF flag when empty/eof detected */
		if (fgets(input, LINELEN + EXTRACHARS, stdin) == NULL
				|| *input == '\n') {
			return IR_EOF;
		}
		/* if input is too long, clear buffer  and try again*/
		if (input[strlen(input) - 1] != '\n') {
			error_print("Input was too long.\n");
			clear_buffer();
		} else {
			/* replace \n with \0. */
			input[strlen(input) - 1] = '\0';
			int_result = (int) strtol(input, &end, 0);

			/* verify result. */
			if (*end != 0) {
				error_print("Input was not numeric.\n");
			}
			/* change flag to end loop if everything's A-OK */
			else {
				finished = TRUE;
			}
		}
	} while (finished == FALSE);
	/* set menu choice thru pointer */
	*integer = int_result;
	return IR_SUCCESS;
}

/* grab the display, then grab the input w/ fgets.
 * use the first non 0 number for menu and return an appropriate value */
enum menu_choice display_menu(void) {
	normal_print("1) Reverse a string\n");
	normal_print("2) Almost magic square\n");
	normal_print("3) Greedy knapsack solution\n");
	normal_print("4) Recursive bruteforce knapsack solution\n");
	normal_print("5) Exit the program\n");
	int input = 0;
	/* check for any errors, EOF. otherwise send to switch */
	int errorCheck = getInteger(&input);
	if (errorCheck == IR_EOF) {
		normal_print(
				"Wowee you managed to quit through the alternate way. I'm proud of you.\n");
		return MC_QUIT;
	}
	/* return menu choice with appropriate value */
	switch (input) {
	case 1:
		return MC_REVERSE;
		break;
	case 2:
		return MC_MAGSQ;
		break;
	case 3:
		return MC_GR_KNAP;
		break;
	case 4:
		return MC_BF_KNAP;
		break;
	case 5:
		return MC_QUIT;
		break;
	}
	return 7;
}

/**
 * for each of the requirements in part C you will need to create a function
 * here to handle user input. All user input should be handled from this module
 * and not from other modules. This makes it easier if we wish to change the
 * user interface.
 * temporary types/etc, will change to appropriate during implementation of stage C
 **/
void reverseString() {
	char string[LINELEN+EXTRACHARS];
	printf("Input a string: ");
	fgets(string, LINELEN + EXTRACHARS, stdin);
	reverse(&string);
}
void magicSquare() {
	normal_print("Almost a magic square. Good job!\n");
}
void greedyKnapsack() {
	normal_print("A greedy knapsack problem. Good job!\n");
}
void recursiveKnapsack() {
	normal_print("Brute forced a recursive knapsack solution. Good job!\n");
}

/**
 * this function is a drop-in replacement for fprintf with stderr. You should
 * use this and not use fprintf in your code. The idea is that while this
 * does the same thing as printf, we could replace this such as with a gui
 * function and you would not need to change any other code for it to work.
 **/
int error_print(const char *format, ...) {
	va_list arglist;
	int count = 0;
	/* print out error preamble */
	count += fprintf(stderr, "Error: ");
	/* print out the rest of the args */
	va_start(arglist, format);
	count += vfprintf(stderr, format, arglist);
	va_end(arglist);
	return count;
}

/**
 * this function is a stand-in for printf. You should use this function rather
 * than printf. The idea is that we could replace this function with one
 * targeting another output and we would not have to change any other code for
 * this to work.
 **/
int normal_print(const char *format, ...) {
	int count = 0;
	/* prepare the argument list for output */
	va_list arglist;
	va_start(arglist, format);
	/* output the args */
	count += vprintf(format, arglist);
	/* cleanup */
	va_end(arglist);
	return count;
}

/**
 * function provided to you to make it easy to print out the item list from the
 * knapsack problem
 **/
void itemlist_print(const struct item_list *items) {
	int count;
	normal_print("The items in the list are: \n");
	for (count = 0; count < items->num_items; ++count) {
		const struct item *curitem = items->items + count;
		printf("item name: %s, cost: %d, weight: %d, count: %d\n",
				curitem->name, curitem->cost, curitem->weight, curitem->count);
	}
}
