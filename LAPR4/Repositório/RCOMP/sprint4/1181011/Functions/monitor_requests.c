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

void* monitor_requests(void* args){

    machineData *ptr = (machineData*) args;

    unsigned short id = ptr->idMachine;
    struct sockaddr_storage client;
    
    int sock, res, i, err;
    unsigned int adl;
    char linha[BUF_SIZE]="";
    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];

    sock = createSock();
    adl = sizeof(client);

    while (1){

        res = recvfrom(sock, linha, BUF_SIZE, 0, (struct sockaddr *)&client, &adl);

        if(res == 0){
            perror("Received empty message.\n");
            exit(1);
        }

        if (!getnameinfo((struct sockaddr *)&client, adl, cliIPtext, BUF_SIZE, cliPortText, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV)){
            printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);
        } else {
            printf("Got request, but failed to get client address.\n");
        }

        if (linha[MESSAGE_CODE_OFFSET] != 0){
            char errorMsg[] = "Code unknown. Please insert a valid code.";
            int size = strlen(errorMsg);

            unsigned char lsb = (unsigned)size & 0xff;
            unsigned char msb = (unsigned)size >> 8;

            linha[DATA_SIZE_OFFSET] = lsb;
            linha[DATA_SIZE_OFFSET + 1] = msb;

            for (i = 0; i < strlen(errorMsg); i++){
                linha[DATA_OFFSET + i] = errorMsg[i];
            }
        } else {
            printf("-- Received Hello Request --\n");

            if (ptr->lastAnswerFromSCM == 0){

                char errorMsg[] = "There is no connection to the SCM.";
                int size = strlen(errorMsg);

                unsigned char lsb = (unsigned)size & 0xff;
                unsigned char msb = (unsigned)size >> 8;

                linha[DATA_SIZE_OFFSET] = lsb;
                linha[DATA_SIZE_OFFSET + 1] = msb;

                for (i = 0; i < strlen(errorMsg); i++){
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
        }
        
        unsigned char lsb = (unsigned)id & 0xff;
        unsigned char msb = (unsigned)id >> 8;

        linha[MACHINE_ID_OFFSET] = lsb;
        linha[MACHINE_ID_OFFSET+1] = msb;
               
        err = sendto(sock, linha, BUF_SIZE, 0, (struct sockaddr *)&client, adl);

        if (err < 0){
            printf("Error in sending a message.\n");
            exit(1);
        }
    }

    pthread_exit((void *)NULL);
    close(sock);
    exit(0);
}