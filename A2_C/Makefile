###############################################################################
# Student Name    : Luke Smith
# RMIT Student ID : S3807720
#
# Startup code provided by Paul Miller for use in "Programming in C",
# Assignment 2, study period 4, 2020.
###############################################################################
#flags / object variable
CC=gcc
CFLAGS=-ansi -Wall -pedantic
OBJECTS = game.o player.o board.o rules-b.o \
	score_list.o word_list.o wuzzle.o

#file
all: wuzzle-b
#compile each .c file w/ headers
game.o : game.h board.h player.h score_list.h word_list.h shared.h
	$(CC) $(CFLAGS) -c game.c
	
player.o : player.h game.h board.h word_list.h rules-b.h shared.h
	$(CC) $(CFLAGS) -c player.c
	
board.o : board.h game.h rules-b.h shared.h
	$(CC) $(CFLAGS) -c board.c
	
rules-b.o : rules-b.h game.h player.h board.h word_list.h shared.h
	$(CC) $(CFLAGS) -c rules-b.c
	
score_list.o : score_list.h game.h player.h board.h shared.h
	$(CC) $(CFLAGS) -c score_list.c
	
word_list.o : word_list.h game.h player.h shared.h
	$(CC) $(CFLAGS) -c word_list.c
	
wuzzle.o : game.h
	$(CC) $(CFLAGS) -c wuzzle.c
#final exe
wuzzle-b: $(OBJECTS)
	$(CC) $^ -o $@
#remove all files / blacklist from default run
.PHONY: clean all
clean: 
	rm -f wuzzle-b 
	rm -f $(OBJECTS)