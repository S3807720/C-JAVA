/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
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

/**
 * This function should display the menu for the system. It should validate
 * input and when there is an input error, display the error and reprompt
 * for input. It should then return a value representing what menu item was
 *selected.
 **/
enum menu_choice display_menu(void) {
	   printf("Welcome to CPT220 Menu System\n");
	   printf("-----------------------------\n");
	   int i, input;
	   for (i = 0; 5 > i; ++i) {
		   normal_print("%d. hello %s\n", i+1, "boats");
	   }
	   scanf("%d", &input);
	   switch(input) {
	   case
	   }
//	   printf("1) Reverse a string\n");
//	   printf("2) Almost magic square\n");
//	   printf("3) Greedy knapsack solution\n");
//	   printf("4) Recursive bruteforce knapsack solution\n");
//	   printf("5) Exit the program\n");
//	   printf("Please enter your choice:\n");
    /* delete this comment and return value and replace it with the proper
     * implementation of this function */
	return MC_INVALID;
}

/**
 * for each of the requirements in part C you will need to create a function
 * here to handle user input. All user input should be handled from this module
 * and not from other modules. This makes it easier if we wish to change the
 * user interface.
 **/

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
