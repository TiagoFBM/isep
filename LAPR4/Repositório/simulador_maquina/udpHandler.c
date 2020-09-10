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

void *udpHandler(void *argv){

    machineData *ptr = (machineData*) argv;

    int sock, res, err;
    unsigned int adl;
    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
    struct sockaddr_storage client;

    adl = sizeof(client);

    while (1){
        int isReset=0;
        unsigned char linha[BUF_SIZE] = "";
        sock = createSock();
        if (sock > 0){
            printf("UDP Port Openned.\n");
        } else {
            exit(1);
        }

        res = recvfrom(sock, linha, BUF_SIZE, 0, (struct sockaddr *)&client, &adl);
        printf("Received UDP connection.\n");

        if (res == 0){
            perror("Received empty message.\n");
            exit(1);
        }

        if (!getnameinfo((struct sockaddr *)&client, adl, cliIPtext, BUF_SIZE, cliPortText, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV)){
            printf("Request from node %s, port number %s\n", cliIPtext, cliPortText);
        } else {
            printf("Got request, but failed to get client address.\n");
        }

        if (linha[MESSAGE_CODE_OFFSET] == HELLO_REQUEST_CODE){
            
            memcpy(linha,monitor_requests(ptr),BUF_SIZE);

        } else if (linha[MESSAGE_CODE_OFFSET] == RESET_REQUEST_CODE){

            memcpy(linha,reset_requests(ptr),BUF_SIZE);
            isReset=1;

        }

        unsigned char lsb = (unsigned)ptr->idMachine & 0xff;
        unsigned char msb = (unsigned)ptr->idMachine >> 8;

        linha[MACHINE_ID_OFFSET] = lsb;
        linha[MACHINE_ID_OFFSET + 1] = msb;

        err = sendto(sock, linha, BUF_SIZE, 0, (struct sockaddr *)&client, adl);
        printf("Answer Sent.\n");

        if (err < 0){
            printf("Error in sending a message.\n");
            exit(1);
        }

        if(isReset==1){
            close(sock);
        }
    }

}