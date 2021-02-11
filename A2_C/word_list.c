/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 3, study period 4, 2020.
 *****************************************************************************/
#include "word_list.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <ctype.h>
#include "shared.h"

/* read in words from file */
int readFile(const char* fileName) {
	FILE *fpRead;
	int i;
	char line[LTR_LIMIT];
	/* create word list, try initialize */
	struct word_list *word_list;
	if (!list_init(word_list, word_list(), word_free())) {
		error_print("Could not initialize list.\n");
		return FALSE;
	}

	/* double check file exists, this should never run, if it does - return false */
	if ((fpRead = fopen(fileName, "r")) == NULL) {
		error_print("File cannot be opened.\n");
		return FALSE;
	}
    /* loop through each line in file and pass to list */
    while(fgets(line, sizeof(line), fpRead) != NULL){
    	list_add(word_list, line);
    }
    /* return true(or prob the list once done) once all is said and done! */
	return TRUE;
}

/* create the functions for the management of the linked list in this file */
BOOLEAN list_add(struct word_list * list, void * word)
{
    /* pointers required for iteration */
    struct word_node * current, * prev = NULL;
    /* our new data */
    struct word_node * newWord = (struct node *)malloc(sizeof(struct node));
    if(!newWord) {
    	error_print("Could not allocate memory for the word.\n");
    	return FALSE;
    }
    /* assign the next pointer to NULL to minimise the work if this gets
     * added to the tail of the list
     */
    newWord->next = NULL;
    newWord->word=word;
    current = list->head;
    /* if the list is empty, simply assign the new node to the head and
     * job done
     */
    if(!list->head)
    {
        list->head = newWord;
        ++list->num_words;
        return TRUE;
    }
    /* search for the insertion point */
    while(current != NULL && list->cmp(current->word, word) < 0)
    {
        prev = current;
        current = current->next;
    }
    /* insertion at the head */
    if(!prev)
    {
        newWord->next = list->head;
        list->head=newWord;
    }
    else
        /* insertion elsewhere in the list */
    {
        newWord->next = current;
        prev->next = newWord;
    }
    ++list->num_words;
    return TRUE;
}

BOOLEAN list_init(struct word_list * list, int(*cmp)(const void*,const void*), void (*data_free)(void*)) {
    /* initialise data */
    list->head = NULL;
    list->num_words=0;

     /*initalise function pointers */
    list->cmp=cmp;
    /*list->data_free = myfree;*/

    /* all good */
    return TRUE;
}

void word_free(void* w) {
	free(w);
}

/* ensure everythings alphabetical */
int word_cmp(const void* first, const void* second) {
    int cmp, i;
    const struct word_node* fWord = first;
    const struct word_node* sWord = second;
    i = 1;
    /* document our assumption that first and second must be valid pointers
     **/
    assert(first);
    assert(second);

    /* compare the words */
    cmp = strcmp(fWord->word[0], sWord->word[0]);
    /* same first letter go to 2nd, 3rd... */
    while (cmp == 0 || strlen(fWord->word[i]) > i) {
    	cmp = strcmp(fWord->word[i], sWord->word[i]);
    	++i;
    }
    if (cmp == 0) {
    	/* deal with duplicate words here .. */
    	return cmp;
    }
    return cmp;
}

/**
 * tests whether the word specified is in the word_list and therefore a valid
 * spelling. Please see the assignment specification for further details.
 **/
BOOLEAN is_in_dictionary(struct word_list *thelist, const char *word) {
    return FALSE;
}

