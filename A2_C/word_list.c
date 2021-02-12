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
#include "game.h"

void list_print(struct word_list * list) {
    struct word_node * current = (struct word_node *)malloc(sizeof(struct word_node));;
    current = list->head;
    printf("\n ---Word List---- \n");
    /* iterate over the list */
    while(current) {
        printf("%s\n", current->word);
        current = current->next;
    }
    printf("\n");
}

/* read in words from file */
struct word_list * readFile(const char* fileName) {
	FILE *fpRead;
	char line[NAMELEN];
	int length, i;
	/* create word list, try initialize */
	struct word_list *word_list;
	struct word_list * error_list;
	word_list = malloc(sizeof(struct word_list));
	error_list = malloc(sizeof(struct word_list));
	error_list->head = NULL;
	if (!list_init(word_list, word_cmp, word_free) ) {
		error_print("Could not initialize list.\n");
		return error_list;
	}

	/* double check file exists, this should never run, if it does - return false */
	if ((fpRead = fopen(fileName, "r")) == NULL) {
		error_print("File cannot be opened.\n");
		return error_list;
	}
    /* loop through each line in file and pass to list */
    while(fgets(line, sizeof(line), fpRead) != NULL){
    	/* remove new line from file read */
    	line[strcspn(line, "\n")] = 0;
    	/* capitalize all words if they aren't already */
    	length = strlen(line);
    	for(i = 0; length > i; ++i) {
    		line[i] = toupper(line[i]);
        	if (isalpha(line[i]) == 0) {
        		error_print("Invalid character detected in file.\n");
        		return error_list;
        	}
    	}
    	printf("line read: %s\n", line);
    	list_add(word_list, line);
        printf("wordlist: %s\n\n", word_list->head->word);
        list_print(word_list);
    }
    fclose(fpRead);
    free(error_list);
    /* return true(or prob the list once done) once all is said and done! */
	return word_list;
}

/* deal with list management here */
void listMenu( struct word_list * list, char * choice) {
	if (strcmp(choice,":help") == 0){
		normal_print("To add a word enter ':add', then you will be prompted to enter a word and it will be added to the list.\n");
		normal_print("To delete a word enter ':delete', then you will be prompted to enter a word and it will be removed from the list.\n");
		normal_print("Or to save your list, enter ':save'.\n\n");
	}
	else if (strcmp(choice,":add") == 0) {
		addWord(list, TRUE);
	}
	else if (strcmp(choice,":delete") == 0) {
		addWord(list, FALSE);
	}
	else if (strcmp(choice,":save") == 0) {
		/* saveWordList(list); */
	}
	else if (strcmp(choice,":print") == 0) {
		list_print(list);
	}
	else {
		error_print("Invalid function.\n");
		listMenu(list, ":help");
	}
}

/* get word and do action based on if true or false */
int addWord(struct word_list * list, int choice) {
	int length, i;
	char word[NAMELEN];
	/* get string from user */
	getString(word, NAMELEN+EXTRACHARS, "word to remove or add");
	/* use length to capitalize */
	length = strlen(word);
	for(i = 0; length > i; ++i) {
		word[i] = toupper(word[i]);
	}
	if (choice == TRUE) {
		list_add(list, word);
	}
	else if (choice == FALSE) {
		list_remove(list, word);
	}
	return TRUE;
}

/* remove word */
int list_remove(struct word_list * list, char * word) {
	struct word_node *temp, *prev;
	prev = NULL, temp = list->head;
	/* if first node is a match, work is done :) */
	if (temp != NULL && strcmp(temp->word, word) == 0) {
		/* re-assign list head to the 2nd entry */
		list->head = temp->next;
		/* and free memory */
		free(temp);
		normal_print("%s has been removed from the list.\n", word);
		list_print(list);
		return TRUE;
	}
	/* keep looping until a match is found */
	while (temp != NULL && strcmp(temp->word, word) != 0) {
		prev = temp;
		temp = temp->next;
	}
	/* if there is no match */
	if (temp == NULL) {
		error_print("That word was not in the list.\n");
		return FALSE;
	}
	/* again, re-assign previous words next to the one infront of the removed */
	prev->next = temp->next;
	free(temp);
	normal_print("%s has been removed from the list.\n", word);
	list_print(list);
	return TRUE;
}

/* add the words read to the list */
BOOLEAN list_add(struct word_list * list, char * nuWord) {
    struct word_node * newWord;
    struct word_node * current, * prev;
    prev = NULL;
    newWord = (struct word_node *)malloc(sizeof(struct word_node));
    if(!newWord) {
    	error_print("Could not allocate memory for the word.\n");
    	return FALSE;
    }
    /* assign the next pointer to NULL to minimise the work if this gets
     * added to the tail of the list
     */
    newWord->next = NULL;
    strcpy(newWord->word, nuWord);
    current = list->head;
    /* if the list is empty, simply assign the new node to the head and
     * job done
     */
    if(!list->head) {
        list->head = newWord;
        ++list->num_words;
        return TRUE;
    }
    /* search for the insertion point */
    while(current != NULL && list->cmp(current->word, nuWord) < 0) {
        prev = current;
        current = current->next;
    }
    /* insertion at the head */
    if(!prev) {
        newWord->next = list->head;
        list->head=newWord;
    } else {
        /* insertion elsewhere in the list */
        newWord->next = current;
        prev->next = newWord;
    }
    ++list->num_words;
    return TRUE;
}

BOOLEAN list_init(struct word_list * list, int(*cmp)(const char*,const char*), void (*myFree)(void*)) {
    /* initialise data */
    list->head = NULL;
    list->num_words=0;
     /*initalise function pointers */
    list->cmp=cmp;
    list->data_free = myFree;
    /* all good */
    return TRUE;
}

void word_free(void* w) {
	free(w);
}

/* ensure everythings alphabetical */
int word_cmp(const char* first, const char* second) {
    int cmp;
    /* document our assumption that first and second must be valid pointers
     **/
    const char *a = first;
    const char* b = second;
    assert(first);
    assert(second);

    /* compare the words */
    cmp = strcmp(a, b);
	/* deal with duplicate words here .. */
    if (cmp == 0) {
    	return -1;
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

