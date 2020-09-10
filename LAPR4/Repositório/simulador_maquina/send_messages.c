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
#include "monitor_requests.h"


#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>


#define HELLO_REQUEST 0
#define MESSAGE_REQUEST 1
#define ACK 150
#define NACK 151

void *send_messages(void *argv)
{

    machineData *ptr = (machineData *)argv;

    int err, sock;
    char buffer[BUF_SIZE], msg[BUF_SIZE];
    struct addrinfo req, *list;
    fd_set fdset;
    struct timeval timeout;
    char fileName[50];
    char line[100];
    int size,i;

    bzero((char *)&req, sizeof(req));

    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;

    err = getaddrinfo(ptr->tcpIp, SERVER_PORT, &req, &list);
    if (err)
    {
        printf("Failed to get server address, error: %s\n", gai_strerror(err));
        pthread_exit((void *)NULL);
    }
    
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);

    if (sock == -1){
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }

    if (connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1){
        perror("Failed connect TCP");
        freeaddrinfo(list);
        close(sock);
        pthread_exit((void *)NULL);
    }

    freeaddrinfo(list);

    printf("Connected to Server.\n");

    const SSL_METHOD *method=SSLv23_client_method();
    SSL_CTX *ctx = SSL_CTX_new(method);

    // Load client's certificate and key
    strcpy(line, CLIENT_SSL_CERT_FILE);
    strcat(line, ".pem");
    puts(line);

    SSL_CTX_use_certificate_file(ctx, line, SSL_FILETYPE_PEM);
	strcpy(line, CLIENT_SSL_KEY_FILE);
	strcat(line, ".key");
	puts(line);

    SSL_CTX_use_PrivateKey_file(ctx, line, SSL_FILETYPE_PEM);

    if (!SSL_CTX_check_private_key(ctx)){
		perror("Error loading client's certificate/key");
		close(sock);
		exit(1);
	}

    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER,NULL);

    // THE SERVER'S CERTIFICATE IS TRUSTED
    SSL_CTX_load_verify_locations(ctx,SERVER_SSL_CERT_FILE,NULL);

    // Restrict TLS version and cypher suites
    SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");


    SSL *sslConn = SSL_new(ctx);
    SSL_set_fd(sslConn, sock);
    
    int erro = SSL_connect(sslConn);

    if(erro!=1) {
		perror("TLS handshake error\n");
        SSL_free(sslConn);
        close(sock);
        exit(1);
	}

    if(SSL_get_verify_result(sslConn)!=X509_V_OK) {
		puts("Sorry: invalid server certificate");
        SSL_free(sslConn);
        close(sock);
        exit(1);
    }

    X509* cert=SSL_get_peer_certificate(sslConn);
    X509_free(cert);


    if(cert==NULL) {
        puts("Sorry: no certificate provided by the server");
        SSL_free(sslConn);
        close(sock);
        exit(1);
    }


    // code of hello request
    unsigned char code;

    // machine id
    unsigned short id = ptr->idMachine;

    // divisão do id em bytes
    unsigned char idFirstByte =(unsigned)((id >> 8) & 0xff);
    unsigned char idSecondByte =  (unsigned)(id & 0xff);

    // size do hello request -> conteudo + \0
 
    unsigned char  receivedCode;

    FILE *fptr;

    strcpy(fileName, ptr->nameFile);

    fptr = fopen(fileName, "r");

    if (!fptr)
    {
        perror("Can't open file\n");
        pthread_exit((void *)NULL);
    }

    i = 0;

    // Read line from file till end of file.
    while ((fgets(msg, sizeof(msg), fptr)) != NULL)
    {   
        

        code = MESSAGE_REQUEST;

        printf("Generating a message...\n\n");

        // sleep para simular o processamento da mensagem
        sleep(ptr->simulationTime);

        // 6 -> version (1 byte) + code (1 byte) + id (2 bytes) + datalength (2 bytes)
        size = 6 + strlen(msg) + 1;

        // transformar os dois bytes que representam o tamanho data length em dois bytes independentes
        
        unsigned char sizeFirstByte = (char)((size >> 8) & 0xff);
        unsigned char sizeSecondByte = (char)(size & 0xff);

        snprintf(buffer, sizeof(buffer), "%c%c%c%c%c%c%s", 0, code, idFirstByte, idSecondByte, sizeFirstByte, sizeSecondByte, msg);

        SSL_write(sslConn, buffer, size);


        //timeout
        timeout.tv_sec = 5;

        printf("The message %s was sent to the server\n\n", msg);

        // waiting for the server message
        FD_ZERO(&fdset);
        FD_SET(sock, &fdset);

        int time = select(sock + 1, &fdset, NULL, NULL, &timeout);

        if (time < 0){
            printf("Select error\n");
            pthread_exit((void *)NULL);
        }
        int cont = 0;
        // Ocorreu um timeout. Tenta reconectar-se ao servidor
        while(time == 0){
            // Tenta reconectar-se 3 vezes (Pequena simulação) -> Servidor em baixo

            if(cont == 3){
                printf("The server is down.\n");
                SSL_free(sslConn);
                close(sock);
                pthread_exit((void *)NULL);
            }

            if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==106) {
                perror("Connection still established\n"); 
            }

            // tenta reconectar-se
            FD_ZERO(&fdset);
            FD_SET(sock, &fdset);
            time = select(sock+1, &fdset, NULL, NULL, &timeout);
            cont++;

        }
        
        // lê a resposta do server
        SSL_read(sslConn, &buffer, sizeof(buffer));
        char lastMessage[sizeof(buffer)] ;
        receivedCode = (unsigned char)buffer[1];

        if (receivedCode == NACK){
            ptr->lastAnswerFromSCM = receivedCode;
            if (sizeof(buffer) - 6 < 0){
                ptr->hasStatus = 0;
            }

            for (i = 6; i < sizeof(buffer); i++){
                lastMessage[i - 6] = buffer[i];
            }
            ptr->hasStatus = 1;
            strcpy(ptr->lastStatusReceived, lastMessage);
            printf("The server responds with ACK\n");
            printf("The request has been refused and ignored.\n");
            SSL_free(sslConn);
            close(sock);
            pthread_exit((void *)NULL);
        }   

        else{

            printf("The server responds with ACK\n");

            ptr->lastAnswerFromSCM = receivedCode;
        
            if (sizeof(buffer) - 6 < 0){
                ptr->hasStatus = 0;
            }

            for (i = 6; i < sizeof(buffer); i++){
                lastMessage[i - 6] = buffer[i];
            }
            
            
            ptr->hasStatus = 1;
            strcpy(ptr->lastStatusReceived, lastMessage);
        }

        puts(buffer);

        pthread_mutex_lock(&mutex);
        while(ptr->reset == 1){
            printf("The Send Messages Function has been stopped.\n");
            pthread_cond_wait(&condReset,&mutex);
        }
        pthread_mutex_unlock(&mutex);

    }

    SSL_free(sslConn);
    close(sock);
    pthread_exit((void *)NULL);
    exit(0);
}
