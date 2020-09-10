#include <strings.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <string.h>
#include <pthread.h>
#include "monitor_requests.h"

unsigned char* reset_requests(machineData *ptr){
    
    int i;
    unsigned char *linha = (unsigned char*)malloc(BUF_SIZE);
    int resetTime = 10;

    printf("-- Received Reset Request --\n");
    
    pthread_t thread;
    char answer;

    pthread_mutex_lock(&mutex);
    ptr->reset = 1;

    printf("Reseting the Machine...\n");
    sleep(2);
    printf("The Machine was Turned Off...\n");

    for(i = 0;i<resetTime;i++){
        printf("Booting up the Machine... %ds Remaining\n",resetTime-i);
        sleep(1);
    }

    printf("Resuming Previous Processes...\n");
    sleep(2);

    ptr->reset = 0;
    pthread_cond_broadcast(&condReset);
    
    pthread_mutex_unlock(&mutex);

    //TCP
    if (pthread_create(&thread, NULL, send_hello_messages,(void*)ptr)==-1){
        perror("Error creating the thread.\n");
        pthread_exit((void *)NULL);
    } else {
        pthread_join(thread,(void*)&answer);
    }

    linha[MESSAGE_CODE_OFFSET] = answer;

    printf("Answer From SCM: %04x\n",answer);

    if(linha[MESSAGE_CODE_OFFSET] == ACK_CODE){
        printf("All the processes were sucessfuly resumed.\n");

        char errorMsg[] = "All the processes were sucessfuly resumed.";
        int size = strlen(errorMsg);

        unsigned char lsb = (unsigned)size & 0xff;
        unsigned char msb = (unsigned)size >> 8;

        linha[DATA_SIZE_OFFSET] = lsb;
        linha[DATA_SIZE_OFFSET + 1] = msb;

        for (i = 0; i < strlen(errorMsg); i++){
            linha[DATA_OFFSET + i] = errorMsg[i];
        }

    } else {
        printf("The Machine couldn't establish Comunication with the Central System.\n");

        char errorMsg[] = "The Machine couldn't establish Comunication with the Central System.";
        int size = strlen(errorMsg);

        unsigned char lsb = (unsigned)size & 0xff;
        unsigned char msb = (unsigned)size >> 8;

        linha[DATA_SIZE_OFFSET] = lsb;
        linha[DATA_SIZE_OFFSET + 1] = msb;

        for (i = 0; i < strlen(errorMsg); i++){
            linha[DATA_OFFSET + i] = errorMsg[i];
        }

    }

    return linha;
}
