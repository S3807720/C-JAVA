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
#include "player.h"

struct word_node {
    struct word_node * next;
    char word[NAMELEN];
};

struct word_list {
    struct word_node * head;
    long num_words;
    int (*cmp)(const char*, const char*);
};

/**
 * List function prototypes that will be used for the management of the linked
 * list here.
 **/

struct word_list * readFile(const char*);
BOOLEAN list_add(struct word_list *, char *);
BOOLEAN list_init(struct word_list * list, int(*cmp)(const char*,const char*));
int word_cmp(const char*, const char*);
void word_free(void*);
void list_free(struct word_list*);
int listMenu( struct word_list *, char *);
int addWord(struct word_list *, int, char *);
int find_word(struct word_list *, const char *);
int list_remove(struct word_list *, char *);
int save_list(struct word_list *, const char *);

#endif
