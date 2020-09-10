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

#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>

#define HELLO_REQUEST 0
#define NACK 151
#define ACK 150


void* send_hello_messages(void* argv){

    machineData *ptr = (machineData *)argv;
    int size;
    int err, sock;
    char buffer[BUF_SIZE];
    char line[BUF_SIZE];
    struct addrinfo req, *list;
    fd_set fdset;
    struct timeval timeout;

    bzero((char *)&req, sizeof(req));

    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;

    err = getaddrinfo(ptr->tcpIp, SERVER_PORT, &req, &list);
    if (err){
        printf("Failed to get server address, error: %s\n", gai_strerror(err));
        pthread_exit((void *)NULL);
    }

    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1){
        perror("Failed to open socket");
        freeaddrinfo(list);
        pthread_exit((void *)NULL);
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

    if(SSL_connect(sslConn)!=1) {
		puts("TLS handshake error");
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
    unsigned char code = HELLO_REQUEST;

    // machine id
    unsigned short id = ptr->idMachine;

    // divisão do id em bytes
    unsigned char idFirstByte =(unsigned)((id >> 8) & 0xff);
    unsigned char idSecondByte =  (unsigned)(id & 0xff);

    // size do hello request -> conteudo + \0
    size = 5;
    

    // guardar a informação no buffer
    snprintf(buffer, sizeof(buffer), "%c%c%c%c", 0, code, idFirstByte, idSecondByte);

    SSL_write(sslConn, buffer, size);

    //timeout
    timeout.tv_sec = 5;

    printf("A Hello request has been sent to the server\n\n");

    //WAITING FOR THE ANSWER

    printf("Waiting for the server message: \n\n");

    FD_ZERO(&fdset);

    // monitorizar o socket
    FD_SET(sock, &fdset);

    int time = select(sock + 1, &fdset, NULL, NULL, &timeout);
    if (time < 0)
    {
        printf("Select error\n");
        pthread_exit((void *)NULL);
    }   

    while(time == 0){
            // Tenta reconectar-se 3 vezes (Pequena simulação) -> Servidor em baixo
            if(connect(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==106) {
                perror("Connection still established\n"); 
            }

            // tenta reconectar-se
            FD_ZERO(&fdset);
            FD_SET(sock, &fdset);
            time = select(sock+1, &fdset, NULL, NULL, &timeout);
        }

    // le o conteudo do buffer

    SSL_read(sslConn, &buffer, sizeof(buffer));
    unsigned char  receivedCode = (unsigned char)buffer[1];

    if (receivedCode == NACK){
        printf("The server responds with NACK\n");
        printf("The request has been refused and ignored.\n");
        ptr->lastAnswerFromSCM = receivedCode;
        ptr->hasStatus = 0;
        SSL_free(sslConn);
        close(sock);
        pthread_exit((void *)NACK);
    }

    else{
        printf("The server responds with ACK\n");
        ptr->lastAnswerFromSCM = receivedCode;
        ptr->hasStatus = 0;
    
    }

    SSL_free(sslConn);
    close(sock);
    pthread_exit((void *)ACK);
}