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

char* monitor_requests(machineData *ptr){

    int i;
    char *linha = (char*)malloc(BUF_SIZE);

    printf("-- Received Hello Request --\n");

    if (ptr->lastAnswerFromSCM == 0){

        char errorMsg[] = "There is no connection to the SCM.";
        int size = strlen(errorMsg);

        unsigned char lsb = (unsigned)size & 0xff;
        unsigned char msb = (unsigned)size >> 8;

        linha[DATA_SIZE_OFFSET] = lsb;
        linha[DATA_SIZE_OFFSET + 1] = msb;

        for (i = 0; i < strlen(errorMsg); i++)
        {
            linha[DATA_OFFSET + i] = errorMsg[i];
        }

        linha[MESSAGE_CODE_OFFSET] = NACK_CODE;

    } else {

        if (ptr->lastAnswerFromSCM == ACK_CODE){
            linha[MESSAGE_CODE_OFFSET] = ACK_CODE;
        } else {
            linha[MESSAGE_CODE_OFFSET] = NACK_CODE;
        }

        if (ptr->hasStatus == 1){

            int size = strlen(ptr->lastStatusReceived);

            unsigned char lsb = (unsigned)size & 0xff;
            unsigned char msb = (unsigned)size >> 8;

            linha[DATA_SIZE_OFFSET] = lsb;
            linha[DATA_SIZE_OFFSET + 1] = msb;

            for (i = 0; i < size; i++){
                linha[DATA_OFFSET + i] = ptr->lastStatusReceived[i];
            }
        } else {

            int size = 1;

            unsigned char lsb = (unsigned)size & 0xff;
            unsigned char msb = (unsigned)size >> 8;

            linha[DATA_SIZE_OFFSET] = lsb;
            linha[DATA_SIZE_OFFSET + 1] = msb;
        }
    }

    return linha;
    
}