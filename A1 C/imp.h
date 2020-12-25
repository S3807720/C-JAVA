/******************************************************************************
 * Student Name    : Luke Smith
 * RMIT Student ID : s3807720
 *
 * Startup code provided by Paul Miller for use in "Programming in C",
 * Assignment 1, study period 4, 2020.
 *****************************************************************************/
#include "shared.h"
#ifndef IMP_H
#define IMP_H
#define SQUARE_SIDE 40
#define NAMELEN 40
#define MAX_ITEMS 100

typedef int square[SQUARE_SIDE][SQUARE_SIDE];
enum category { NONE, WEIGHT, COST };
struct item {
    char name[NAMELEN + 1];
    int weight;
    int cost;
    int count;
};

struct item_list {
    int num_items;
    int total_items;
    struct item items[MAX_ITEMS];
};

void reverse(char *);
BOOLEAN square_init(square, const char *, int);
BOOLEAN magicsquare_validate(square, const int);
void itemlist_init(struct item_list *);
BOOLEAN itemlist_add(struct item_list *, const char *, int, int, int);
struct item_list knapsack_greedy(struct item_list *, int, enum category);
struct item_list knapsack_bruteforce(const struct item_list *, int,
                                     enum category);
#endif
