#include <strings.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <string.h>
#include <pthread.h>
#include "monitor_requests.h"

int main(int argc, char **argv){

    if(argc!=5){
        printf("Expected input: idMachine - ip - nameFile - simultationTime");
        exit(1);
    }

    int nThreads = 2;
    pthread_t threads[nThreads];

    machineData * ptr = (machineData*)malloc(sizeof(machineData));

    ptr->idMachine = (unsigned short) atoi(argv[1]);
    ptr->tcpIp = argv[2];
    int simulationTime = atoi(argv[4]); 

    ptr->lastAnswerFromSCM=0;
    ptr->hasStatus=0;
    strcpy(ptr->nameFile,  argv[3]);
    ptr->simulationTime = simulationTime;

    // SEND MESSAGES
    if (pthread_create(&threads[0], NULL, send_messages,(void*)ptr)==-1){
        perror("Error creating the thread.\n");
        exit(1);
    }
    
    //SUPORT MONITOR REQUESTS
    if (pthread_create(&threads[1], NULL, monitor_requests,(void*)ptr)==-1){
        perror("Error creating the thread.\n");
        exit(1);
    }
    
    

    pthread_join(threads[0],NULL);
    pthread_join(threads[1],NULL);

    return 0;
}