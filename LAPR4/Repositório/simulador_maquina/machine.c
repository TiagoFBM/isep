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

    if (argc != 5){
        printf("Expected input: idMachine - ip - nameFile - simultationTime");
        exit(1);
    }

    int nThreads = 6;
    pthread_t threads[nThreads];
    unsigned char answerTCP;
    char verif1 = 0, verif2 = 0;

    machineData *ptr = (machineData *)malloc(sizeof(machineData));

    ptr->idMachine = (unsigned short)atoi(argv[1]);
    ptr->tcpIp = argv[2];
    int simulationTime = atoi(argv[4]);

    ptr->lastAnswerFromSCM = 0;
    ptr->hasStatus = 0;
    strcpy(ptr->nameFile, argv[3]);
    ptr->simulationTime = simulationTime;

    ptr->reset = 0;

    pthread_mutex_init(&mutex, NULL);
    pthread_cond_init(&condReset, NULL);

    if (pthread_create(&threads[3], NULL, udpHandler, (void *)ptr) == -1){
        perror("Error creating the thread.\n");
        exit(1);
    }
    
    //SEND HELLO MESSAGE
    if (pthread_create(&threads[0], NULL, send_hello_messages, (void *)ptr) == -1){
        perror("Error creating the thread.\n");
    } else {
        pthread_join(threads[0], (void *)&answerTCP);

        if (answerTCP == ACK_CODE){

            // SEND MESSAGES
            if (pthread_create(&threads[1], NULL, send_messages, (void *)ptr) == -1){
                perror("Error creating the thread.\n");
            } else {
                verif1 = 1;
            }

            // CONFIG REQUEST
            if (pthread_create(&threads[2], NULL, config_request, (void *)ptr) == -1){
                perror("Error creating the thread.\n");
            } else {
                verif2 = 1;
            }

        }
    }


    if (verif1 == 1){
        pthread_join(threads[1], NULL);
    }

    if (verif2 == 1){
        pthread_join(threads[2], NULL);
    }

    pthread_join(threads[3], NULL);

    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&condReset);

    return 0;
}