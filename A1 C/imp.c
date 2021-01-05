/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : s3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 1, study period 4, 2020.
 *****************************************************************************/
#include "imp.h"

#include <assert.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "helpers.h"
#include "io.h"
#define INT_MAX 2147483647

void reverse(char *string) {
	/* variables to swap the original string using pointers */
	char *string_copy = string;
	char *string_rev = string_copy + strlen(string_copy) - 1;
	while (string_rev > string_copy) {
		/* temp to store value where pointer is, rev to store reverse pointers*/
		char temp;
		temp = *string_rev;
		*string_rev-- = *string_copy;
		*string_copy++ = temp;
	}
}

/**
 * the first of the two functions you need to do for the magic square
 * requirement. You will accept as arguments a 2d array of integers that
 * we have typedefed to "square" - it's just an alias that means the
 * same thing.
 **/
BOOLEAN square_init(square newsquare, const char *square_string,
		int side_width) {
	/* make a copy to destroy with tokens */
	char *square_copy = strdup(square_string);
	const char *delim = ",;";
	char *token, *end;
	int i, j, num, count;
	i = 0, j = 0, num = 0, count = 0;
	/* token to split up and add to each part of the square through loop */
	token = strtok(square_copy, delim);
	while(side_width > i && token != NULL) {
		num = (int) strtol(token, &end, 10);
		if (num == INT_MAX) {
			error_print("An input entered is far too large. Must be below %d\n", INT_MAX);
			return FALSE;
		}
		/* verify result. */
		newsquare[i][j] = num;
		j++;
		count++;
		token = strtok(NULL, delim);  // next token
		/* could just use another loop but this works just as well to reset until i = sides */
		if (j == side_width) {
			j = 0;
			i++;
		}
	}
	/* test if the side count and inputs match up. if input count < side*side or not a multiple of it
	 * reject and go back. */
	if ((count % side_width) != 0 || count != side_width*side_width) {
		error_print("Side count and input does not match.\n");
		return FALSE;
	}
	i = 0;
	/* print out the square */
	normal_print("Your (hopefully magic) square: \n\n");
	while(side_width > i) {
		j = 0;
		while(side_width > j) {
			normal_print("%*d ",3, newsquare[i][j]);
			j++;
		}
		normal_print("\n");
		i++;
	}
	normal_print("\n");
	/* send to validate whether it's magic or not! */
	int mgsqCheck = magicsquare_validate(newsquare, side_width);
	if (mgsqCheck == TRUE) {
		normal_print("You have created an (almost) magic square!\n");
		return TRUE;
	}
	else {
		normal_print("Unfortunately you have failed at creating an (almost) magic square. :( \n");
		return FALSE;
	}
}

/**
 * validate that the square passed in is an (almost) magic square in that
 * the totals for all columns, all rows and all diagonals are the same.
 **/
BOOLEAN magicsquare_validate(square thesquare, int sidewidth) {
	int rowCount, colCount, sum, sumCheck, diagCheck;
	diagCheck = sidewidth -1;
	sumCheck = 0, sum = 0;
	for (rowCount = 0; sidewidth > rowCount; rowCount++) {
		sum += thesquare[rowCount][0];
	}
	/* check columns */
	/* these work by setting the loop to increment the other counter
	 * and once that counter hits side length, it'll increment the main one
	 * allowing it to loop through all rows, all columns etc.
	 */
	for (rowCount = 0, colCount = 0; sidewidth > colCount; rowCount++) {
		if(rowCount == sidewidth) {
			if (sumCheck != sum) {
				return FALSE;
			}
			sumCheck = 0;
			colCount++;
			rowCount = 0;
		}
		if (sidewidth > colCount) {
			sumCheck += thesquare[rowCount][colCount];
		}
	}
	sumCheck = 0;
	/* check rows */
	for (rowCount = 0, colCount = 0; sidewidth > rowCount; colCount++) {
		if(colCount == sidewidth) {
			if (sumCheck != sum) {
				return FALSE;
			}
			sumCheck = 0;
			rowCount++;
			colCount = 0;
		}
		if (sidewidth > rowCount) {
			sumCheck += thesquare[rowCount][colCount];
		}
	}
	sumCheck = 0;
	/* finally check diagonals
	 * start from 0 and increment each by 1. 00, 11, 22, 33...*/
	for (rowCount = 0, colCount = 0; sidewidth > colCount; rowCount++, colCount++) {
		sumCheck += thesquare[rowCount][colCount];
	}
	if (sumCheck != sum) {
		return FALSE;
	}
	sumCheck = 0;
	/* and other diagonal
	 * start from opposite end. eg 30, 21, 12, 03*/
	for (colCount = diagCheck, rowCount = 0; sidewidth > rowCount; rowCount++, colCount--) {
		sumCheck += thesquare[rowCount][colCount];
	}
	if (sumCheck != sum) {
		return FALSE;
	}
	return TRUE;
}

/**
 * provided implementation of the itemlist that you will need to use for the
 * knapsack problem. You don't need to create your own list but you need to be
 * able to use ours.
 **/

/**
 * initialise an item list to an empty state
 **/
void itemlist_init(struct item_list *thelist) { thelist->num_items = 0; }

/**
 * adds a new item to the item list given the data about the item.
 **/
BOOLEAN itemlist_add(struct item_list *thelist, const char *itemname,
		int weight, int cost, int count) {
	/* a pointer to where we will add a new item if we add it */
	struct item *newitem = thelist->items + thelist->num_items;

	/* we can't add more items than we have specified that a list can hold */
	if (thelist->num_items == MAX_ITEMS) {
		return FALSE;
	}
	/* copy in the data */
	strcpy(newitem->name, itemname);
	newitem->weight = weight;
	newitem->cost = cost;
	newitem->count = count;
	/* increase the number of items */
	thelist->num_items++;
	thelist->total_items += count;
	return TRUE;
}

/**
 * This function is provided when you want to add a new item to the
 * item list when you have already constructed an item and wish to pass that in
 **/
BOOLEAN itemlist_add_item(struct item_list *thelist, struct item theitem) {
	/* check that there is space in the list */
	if (thelist->num_items == MAX_ITEMS) {
		return FALSE;
	}
	/* insert the item at the end of the list */
	thelist->items[thelist->num_items++] = theitem;
	thelist->total_items += theitem.count;
	return TRUE;
}

/* a function pointer to the function used to do the comparison which may be
 * one of weight_cmp and cost_cmp defined below */
typedef int (*comparator)(const struct item *, const struct item *);

static int weight_cmp(const struct item *first, const struct item *second) {
	return first->weight - second->weight;
}

int cost_cmp(const struct item *first, const struct item *second) {
	return first->cost - second->cost;
}

/**
 * function that defines an item_swap that is used to make the sorting of the
 * item list easier.
 **/
static void item_swap(struct item *itema, struct item *itemb) {
	struct item temp = *itema;
	*itema = *itemb;
	*itemb = temp;
}

/**
 * implements  the sorting of the item_list so that you don't have to. Sorting
 * the list makes implementation of the greedy version of the algorithm much
 * easier.
 **/
static void knapsack_sort(struct item_list *thelist,
		enum category thecategory) {
	comparator cmp;
	int outer, inner;
	/* category must be WEIGHT OR COST */
	assert(thecategory == WEIGHT || thecategory == COST);
	/* decide how we are going to compare items */
	cmp = thecategory == COST ? cost_cmp : weight_cmp;
	/* implement the bubble sort - there are two loops */
	/* outer loop looks at each item and inner loop looks at all the subsequent
	 * items in the list. If they are larger than the current item then we
	 * swap them. The end result is sorting from largest to smallest
	 */
	for (outer = 0; outer < thelist->num_items - 1; ++outer) {
		for (inner = outer + 1; inner < thelist->num_items; ++inner) {
			if (cmp(thelist->items + outer, thelist->items + inner) > 0) {
				item_swap(thelist->items + outer, thelist->items + inner);
			}
		}
	}
}

/**
 * this function implements the greedy version of the knapsack solution
 **/
struct item_list knapsack_greedy(struct item_list *thelist, int max,
		enum category thecategory) {
	/* loop counter and value to keep track of greedy capacity */
	int i, greedyCounter;
	printf("%d max \n", max);
	greedyCounter = max;
	/* error list I have defined that can be returned if an error occurs in
	 * this function and then you can check that the num_items field is EOF */
	struct item_list error_list;
	error_list.num_items = EOF;
	knapsack_sort(thelist, thecategory);
	/* greedy bag to store priority values */
	struct item_list greedyList;
	itemlist_init(&greedyList);
	/* loop until greedy counter is 0
	 * as the bag has been sorted, can go through the
	 * general item list from position 0*/
	for (i = 0; greedyCounter > 0; i++) {
		printf("starting loop of greedy add\n total count: %d\n", thelist->total_items);
		/* or and insufficient amount of items for capacity will
		 * add all available and end counter. */
		if (greedyCounter > thelist->total_items) {
			printf("greedy loop 2\n");
			itemlist_add_item(&greedyList, thelist->items[i]);
			greedyCounter = 0;
		}
		/* otherwise we have to copy the item values over and use greedy counter
		 * also setting counter to 0 as it's lower than item count */
		else if (thelist->items[i].count > greedyCounter) {
			printf("greedy loop 3\n");
			itemlist_add(&greedyList, thelist->items[i].name, thelist->items[i].weight,
					thelist->items[i].cost, greedyCounter);
			greedyCounter = 0;
		}
		/* if greedy counter is higher, use the item count */
		else if (greedyCounter >= thelist->items[i].count) {
			printf("greedy loop 1\n");
			itemlist_add_item(&greedyList, thelist->items[i]);
			greedyCounter -= thelist->items[i].count;
		}
		else {
			normal_print("This isn't meant to execute..\n");
		}
	}
	if (thecategory == WEIGHT) {
		normal_print("The items in the knapsack are prioritized by least weight.\n");
	}
	if (thecategory == COST) {
		normal_print("The items in the knapsack are prioritized by least cost.\n");
	}
	/* print through greedy bag */
	normal_print("The items in the greedy knapsack are: \n");
	for (i = 0; greedyList.num_items > i; i++) {
		normal_print("greedy list: Item: %s, Weight: %d Cost: %d Count: %d\n", greedyList.items[i].name,
				greedyList.items[i].weight, greedyList.items[i].cost, greedyList.items[i].count);
	}
	normal_print("The complete item list is: \n");
	for (i = 0; thelist->num_items > i; i++) {
		normal_print("normal list: Item: %s, Weight: %d Cost: %d Count: %d\n", thelist->items[i].name,
				thelist->items[i].weight,thelist->items[i].cost, thelist->items[i].count);
	}
	return error_list;
}

/**
 * this is the function that implements the recursion for the algorithm. You
 * will have a separate recursive call for each possible choice at a given
 * point. You can then select which one has given the most weight/cost based on
 *the
 * and  return that list. Please be careful to subtract from
 * the count every time you select an item and also make copies of lists when
 * you pass them to successive recursive calls. Otherwise your algorithm will
 * easily break.
 **/
void knapsack_bruteforce_rec(struct item_list *select_from,
		struct item_list *selected, int max, int current,
		enum category thecategory) {
	/* select_from is the list of elements you can select from, selected is the
	 * list of items that have been selected for this branch of the recursion */
	/* please note that current is the value of the metric of weight / cost
	 * so far
	 */
}

/**
 * This is the function that kick starts the recursive process. What we wnat to
 * do is have a separate recursive call that starts at each of the possible
 *items in the knapsack and allow for any possible choice at each stage. We will
 *then select the list with the largest number of items as being the best
 *selection.
 **/
struct item_list knapsack_bruteforce(const struct item_list *thelist, int max,
		enum category thecategory) {
	/*struct item_list array_of_lists[MAX_ITEMS];*/
	/* list you should return on error */
	struct item_list error_list;
	error_list.num_items = EOF;
	/* return the best list (highest with the given category) of the lists we
	 * have found based on the recursive
	 * calls*/
	return error_list;
}
