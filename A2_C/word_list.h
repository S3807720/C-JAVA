/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 3, study period 4, 2020.
 *****************************************************************************/
#include "shared.h"
#ifndef WORDLIST_H
#define WORDLIST_H
#define LTR_LIMIT 80

struct word_node {
    struct word_node * next;
    const char * word;
};

struct word_list {
    struct word_node * head;
    long num_words;
    int (*cmp)(const void*,const void*);
    void (*data_free)(void*);
};

/**
 * List function prototypes that will be used for the management of the linked
 * list here.
 **/
BOOLEAN is_in_dictionary(struct word_list *, const char *);
#endif
