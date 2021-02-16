/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : S3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 3, study period 4, 2020.
 *****************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <ctype.h>
#include "word_list.h"
#include "game.h"

/* max length in files is 45 */
#define FILELEN 45

/* part of the code has been adapted from tutorial code, courtesy of teach, Paul Miller */

/* super secret print method */
void list_print(struct word_list * list) {
	struct word_node * current;
	current = list->head;
	normal_print("\n ---Word List---- \n");
	/* iterate over the list */
	while(current) {
		normal_print("%s\n", current->word);
		current = current->next;
	}
	normal_print("\n");
}

/* read in words from file */
struct word_list * readFile(const char* fileName) {
	clock_t start, end;
	double elapsed;
	FILE *fpRead;
	char line[FILELEN+EXTRACHARS];
	int length, i;
	/* create word list, try initialize */
	struct word_list *word_list;
	struct word_list * error_list;
	start = clock();
	/* allocate mem */
	error_list = malloc(sizeof(struct word_list));
	if(!error_list) {
		error_print("Could not allocate memory for the list.\n");
		return error_list;
	}
	word_list = malloc(sizeof(struct word_list));
	if(!word_list) {
		error_print("Could not allocate memory for the list.\n");
		return error_list;
	}
	error_list->head = NULL;
	/* if error :O */
	if (!list_init(word_list, word_cmp) ) {
		error_print("Could not initialize list.\n");
		free(word_list);
		return error_list;
	}

	/* double check file exists, this should never run, if it does - return false */
	if ((fpRead = fopen(fileName, "r")) == NULL) {
		error_print("File cannot be opened.\n");
		free(word_list);
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
		list_add(word_list, line);
	}
	/* close file */
	fclose(fpRead);
	/* free error list */
	free(error_list);
	normal_print("%s was loaded successfully.\n", fileName);
	end = clock();
	elapsed = ((double)(end-start))/CLOCKS_PER_SEC;
	printf("Time taken to read word list was %.7f seconds.\n", elapsed);
	/* return true(or prob the list once done) once all is said and done! */
	return word_list;
}

/* deal with list management here */
int listMenu( struct word_list * list, char * input) {
	char *ptr, *choice;
	int i;
	/* read until space */
	choice = strtok(input, " ");
	/* if add/del/save */
	if (strcmp(choice, ":add") == 0|| strcmp(choice, ":delete") == 0 || strcmp(choice, ":save") == 0) {
		/* ignores any spaces past the first */
		ptr = strtok(NULL, " \n");
		/* check if alphabetical */
		for(i = 0; ptr[i] != 0; ++i) {
			if (isalpha(ptr[i]) == 0 ) {
				error_print("The word must be alphabetical.\n");
				return FALSE;
			}
		}
	}
	/* help msg and so on..*/
	if (strcmp(choice,":help") == 0){
		normal_print("To add a word enter ':add', then you will be prompted to enter a word and it will be added to the list.\n");
		normal_print("To delete a word enter ':delete', then you will be prompted to enter a word and it will be removed from the list.\n");
		normal_print("Or to save your list, enter ':save'.\n");
		normal_print("It is also possible to show the list of words with ':print', however this is not recommended with a long list.\n\n");
	}
	else if (strcmp(choice,":add") == 0) {
		addWord(list, TRUE, ptr);
	}
	else if (strcmp(choice,":delete") == 0) {
		addWord(list, FALSE, ptr);
	}
	else if (strcmp(choice,":save") == 0) {
		save_list(list, ptr);
	}
	/* secret print to check deletion/save works :O */
	else if (strcmp(choice,":print") == 0) {
		list_print(list);
	}
	/* if an invalid : command is entered, show help and return to game */
	else {
		error_print("Invalid function.\n");
		listMenu(list, ":help");
	}
	return TRUE;
}

/* get word and do action based on if true or false */
int addWord(struct word_list * list, int choice, char * word) {
	int length, i;
	/* use length to capitalize */
	length = strlen(word);
	for(i = 0; length > i; ++i) {
		word[i] = toupper(word[i]);
	}
	/* if adding */
	if (choice == TRUE) {
		if(list_add(list, word)){
			normal_print("%s successfully added to the list.\n", word);
		}
		else {
			error_print("%s could not be added to the list.\n", word);
		}
	}
	/* otherwise remove */
	else if (choice == FALSE) {
		list_remove(list, word);
	}
	return TRUE;
}

/* remove word */
int list_remove(struct word_list * list, char * word) {
	struct word_node *temp, *prev;
	clock_t start, clockEnd;
	double elapsed;
	start = clock();
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
	clockEnd = clock();
	elapsed = ((double) (clockEnd-start)) / CLOCKS_PER_SEC;
	normal_print("Time taken to remove a word was %.9f seconds.\n", elapsed);
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

BOOLEAN list_init(struct word_list * list, int(*cmp)(const char*,const char*)) {
	/* initialise data */
	list->head = NULL;
	list->num_words=0;
	/*initalise function pointers */
	list->cmp=cmp;
	/* all good */
	return TRUE;
}

/* free list */
void list_free(struct word_list *list) {
	struct word_node *curr, *next;
	clock_t start, clockEnd;
	double elapsed;
	start = clock();
	curr = list->head;
	while (curr) {
		next = curr->next;
		free(curr);
		curr = next;
	}
	clockEnd = clock();
	elapsed = ((double) (clockEnd-start)) / CLOCKS_PER_SEC;
	normal_print("Time taken to free score list was %.9f seconds.\n", elapsed);
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
	/* deal with duplicate words here ..
	 * return -1 as duplicates are fine, I guess */
	if (cmp == 0) {
		return -1;
	}
	return cmp;
}

/* save file to the passed in file name */
int save_list(struct word_list * list, const char * fileName) {
	struct word_node * pointer;
	FILE *fWrite;
	clock_t start, clockEnd;
	double elapsed;
	start = clock();
	pointer = list->head;
	/* test file */
	if (fileName == NULL) {
		error_print("No file was entered.\n");
		return FALSE;
	}
	/* can open file? */
	if ((fWrite = fopen(fileName, "w")) == NULL) {
		error_print("Cannot write to this file.\n");
		fclose(fWrite);
		return FALSE;
	}
	/* now loop through each word and save to file */
	while(pointer != NULL) {
		fputs(pointer->word, fWrite);
		fputs("\n", fWrite);
		pointer = pointer->next;
	}
	/* close and return success \o/ */
	fclose(fWrite);
	normal_print("File successfully saved to %s.\n", fileName);
	clockEnd = clock();
	elapsed = ((double) (clockEnd-start)) / CLOCKS_PER_SEC;
	normal_print("Time taken to save score list was %.9f seconds.\n", elapsed);
	return TRUE;
}

/* loop through list and find matching word */
int find_word(struct word_list * list, const char * word) {
	struct word_node *finder;
	finder = list->head;
	while (finder != NULL && strcmp(finder->word, word) != 0) {
		finder = finder->next;
	}
	/* no word found :*( */
	if (finder == NULL) {
		error_print("That word was not in the list.\n");
		return FALSE;
	}
	return MOVE_SUCCESS;
}

