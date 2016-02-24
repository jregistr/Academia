#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/utsname.h>
#include "tinydir.h"
#include <time.h>
#include <dirent.h>
#include <limits.h>
#include <sys/types.h>
#include <dirent.h>
#include <errno.h>

typedef struct Node {
 char *key;
 char *value;
 struct Node *next;
}Node;

struct utsname unameData;

typedef int Bool;
#define True 1;
#define False 0;

Node *createNode (char *k, char *v){
   Node *temp = malloc(sizeof(Node));
   temp->key = k;
   temp->value = v;
   return temp;
}


Node *insert(Node *head, Node *toInsert){
	
   if(strcmp(toInsert->key, head->key) < 0){
		toInsert->next = head;
		head = toInsert;
   }else{
		Node *temp = head->next;
		Node *last = head;
		int biggest = 1;
		
		while(temp){
			if(strcmp(toInsert->key,temp->key) < 0){
				biggest = 0;
				toInsert->next = temp;
				last->next = toInsert;
				break;
			}else if (strcmp(toInsert->key,temp->key) > 0){
				last = temp;
				temp = temp->next;
			}else if (strcmp(toInsert->key,temp->key) == 0){
				break;
			}
		}
		
		if(biggest == 1){
			last->next = toInsert;
		}
   }
   return head;
}

void printNodes(Node *node){
   Node *temp = node;
    while(temp){
		printf("Key:%s\n",temp->key);
		temp = temp->next;
   }
}

int stringLength(char *string)
{
    int i;
    for(i=0;string[i]!='\0';i++);
    return i;
}



int main(int argc, char * argv[] ){
	
	int totalLength = 0;
	int i = 1;
	
	char tty[40];
    snprintf(tty, 40,"%s", ttyname(STDIN_FILENO));
    
    char uid[40];
    snprintf(uid, 40, "%d", getuid());
	
	for(;i<argc; i++){
		totalLength += stringLength(argv[i]);
	}
	
	char str[40];
    sprintf(str, "%d", stringLength);
	
	tinydir_dir dir;
    tinydir_open(&dir, ".");
	int fileCounter = 0;
	while (dir.has_next) {
        tinydir_file file;
        tinydir_readfile(&dir, &file);
        if(!file.is_dir) {
            fileCounter++;
        }
        tinydir_next(&dir);
    }

    tinydir_close(&dir);
	 char temp[20];
	  sprintf(temp, "%d", fileCounter);
     char *numberOfFiles = temp;
	 
	 
	 char cwd[256];
    uname(&unameData);
    
    char *ttyname(int fd);
    char * pathname;
    pathname = ttyname(0);
    
    char *ttyname(int fildes);
    time_t  time_raw_format;
    struct tm * ptr_time;
    time ( &time_raw_format);
    ptr_time = localtime ( &time_raw_format );
	
	Node *head = createNode("progname", argv[0]);
	 insert(head, createNode( "user", getlogin()));
     insert(head, createNode("host", unameData.sysname));
}


