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
#include <string.h>

#include "helpers.h"
#include "io.h"

/**
 * reverse a string inplace so that the order of letters is the opposite to
 * what we started with. Be careful to keep the nul terminator in the right
 * spot!
 **/
void reverse(char *string) {

}

/**
 * the first of the two functions you need to do for the magic square
 * requirement. You will accept as arguments a 2d array of integers that
 * we have typedefed to "square" - it's just an alias that means the
 * same thing.
 **/
BOOLEAN square_init(square newsquare, const char *square_string,
                    int side_width) {
    /* delete this return statement and substitute with your own code and
     * comments */
    return FALSE;
}

/**
 * validate that the square passed in is an (almost) magic square in that
 * the totals for all columns, all rows and all diagonals are the same.
 **/
BOOLEAN magicsquare_validate(square thesquare, int sidewidth) {
    /* delete this comment and return statement and substitute with your own
     * code and return statements */
    return FALSE;
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
    /* declare any variables you need up here */
    /* error list I have defined that can be returned if an error occurs in
     * this function and then you can check that the num_items field is EOF */
    struct item_list error_list;
    error_list.num_items = EOF;
    knapsack_sort(thelist, thecategory);
    /* implement the algorithm to always take the largest item given the
     * criterion specified */
    /* you need to create your own list and add items to it according to the
     * greedy algorithm
     */
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
