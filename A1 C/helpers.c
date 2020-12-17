/******************************************************************************
 * Student Name    :
 * RMIT Student ID :
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 1, study period 4, 2020.
 *****************************************************************************/
#include "helpers.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/**
 * a convenience function we provide to you to make it easier to copy strings.
 * Please note that this does some memory allocation but you are not required
 * to free that memory as that's out of the scope of this assignment.
 **/
char *strdup(const char *orig) {
    /* make space for the string copy */
    char *copy = malloc(sizeof(char) * (strlen(orig) + 1));
    if (!copy) {
        perror("strdup");
        return NULL;
    }
    /* copy the string */
    strcpy(copy, orig);
    return copy;
}
