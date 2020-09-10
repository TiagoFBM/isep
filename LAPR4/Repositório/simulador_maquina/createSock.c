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

int createSock() {
    
    int err, sock;

    struct addrinfo req, *list;
    bzero((char *)&req, sizeof(req));

    req.ai_family = AF_INET;
    req.ai_socktype = SOCK_DGRAM;
    req.ai_flags = AI_PASSIVE; // local address

    err = getaddrinfo(NULL, SERVER_PORT, &req, &list);

    if (err){
        printf("Failed to get local address, error: %s\n", gai_strerror(err));
        exit(1);
    }

    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);

    if (sock == -1){
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }

    int one = 1;
    setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &one, sizeof(one));

    if (bind(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1){
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list);
        exit(1);
    }

    freeaddrinfo(list);

    return sock;

}