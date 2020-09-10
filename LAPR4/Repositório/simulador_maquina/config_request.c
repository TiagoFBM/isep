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

#define SERVER_C_SSL_CERT_FILE "SSL/serverC.pem"
#define SERVER_SSL_KEY_FILE "SSL/serverC.key"
#define AUTH_CLIENTS_SSL_CERTS_FILE "SSL/server_J.pem"



void* config_request(void* argv){

    machineData *ptr = (machineData *)argv;

    struct sockaddr_storage from;
    struct addrinfo req, *list;
    int newSock, sock, err;
    unsigned int adl;
    char buffer[BUF_SIZE];
    fd_set fdset;
    char fileName[80];
    char sufixFileName[] = "_ConfigFile.txt";
    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
    int i;

    bzero((char *)&req,sizeof(req));
	// requesting a IPv6 local address will allow both IPv4 and IPv6 clients to use it
	req.ai_family = AF_INET;
	req.ai_socktype = SOCK_STREAM;
	req.ai_flags = AI_PASSIVE;      // local address

	err=getaddrinfo(NULL, "30901" , &req, &list);

	if(err) {
        	printf("Failed to get local address, error: %s\n",gai_strerror(err)); exit(1); }

	sock=socket(list->ai_family,list->ai_socktype,list->ai_protocol);
	if(sock==-1) {
        	perror("Failed to open socket"); freeaddrinfo(list); exit(1);}

	if(bind(sock,(struct sockaddr *)list->ai_addr, list->ai_addrlen)==-1) {
        	perror("Bind failed");close(sock);freeaddrinfo(list);exit(1);}

	freeaddrinfo(list);

	listen(sock,SOMAXCONN);

	puts("Accepting TCP connections (both over IPv6 or IPv4). Use CTRL+C to terminate the server");

    const SSL_METHOD *method;
    SSL_CTX *ctx;

    method = SSLv23_server_method();
    ctx = SSL_CTX_new(method);

    // Load the server's certificate and key
    SSL_CTX_use_certificate_file(ctx, SERVER_C_SSL_CERT_FILE, SSL_FILETYPE_PEM);
    SSL_CTX_use_PrivateKey_file(ctx, SERVER_SSL_KEY_FILE, SSL_FILETYPE_PEM);

    if (!SSL_CTX_check_private_key(ctx)) {
        puts("Error loading server's certificate/key");
		close(sock);
        exit(1);
    }

    // THE CLIENTS' CERTIFICATES ARE TRUSTED
    SSL_CTX_load_verify_locations(ctx,AUTH_CLIENTS_SSL_CERTS_FILE,NULL);

    // Restrict TLS version and cypher suite
    SSL_CTX_set_min_proto_version(ctx,TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

    // The client must provide a certificate and it must be trusted, the handshake will fail otherwise

    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER|SSL_VERIFY_FAIL_IF_NO_PEER_CERT, NULL);


	adl=sizeof(from);


    while (1){
        newSock = accept(sock, (struct sockaddr *)&from, &adl); 
        
        getnameinfo((struct sockaddr *)&from,adl,cliIPtext,BUF_SIZE,cliPortText,BUF_SIZE,NI_NUMERICHOST|NI_NUMERICSERV);
        printf("New connection from node %s, port number %s\n", cliIPtext, cliPortText);

        SSL *sslConn = SSL_new(ctx);
        SSL_set_fd(sslConn, newSock);
		if(SSL_accept(sslConn)!=1) {
			puts("TLS handshake error: client not authorized");
			SSL_free(sslConn);
        	close(newSock);
		    exit(1);
		}
        printf("TLS version: %s\nCypher suite: %s\n",
		SSL_get_cipher_version(sslConn),SSL_get_cipher(sslConn));
		X509* cert=SSL_get_peer_certificate(sslConn);
        X509_free(cert);
		X509_NAME_oneline(X509_get_subject_name(cert),cliIPtext,BUF_SIZE);
        printf("Client's certificate subject: %s\n",cliIPtext);
                	

        FD_ZERO(&fdset);

        // monitorizar o socket
        FD_SET(newSock, &fdset);

        if (select(newSock + 1, &fdset, NULL, NULL, NULL) < 0){
            printf("Select error\n");
            exit(1);
        }

        // le o conteudo do buffer
        SSL_read(sslConn, &buffer, sizeof(buffer));

        unsigned char receivedCode = (unsigned char)buffer[1];
        
        if (receivedCode == CONFIG_REQUEST_CODE){
            printf("-- Received Config Request --\n");

            unsigned short machineID = ((unsigned char)buffer[MACHINE_ID_OFFSET] | (unsigned char)buffer[MACHINE_ID_OFFSET+1] << 8);
            if(machineID == ptr->idMachine){
                int dataSize = (buffer[DATA_SIZE_OFFSET] | buffer[DATA_SIZE_OFFSET+1] << 8) & 0xffff;
                
                char str[dataSize+1];

                for (i = 0; i < dataSize; i++){
                    str[i] = buffer[DATA_OFFSET + i];
                }
                
                snprintf(fileName, sizeof(fileName), "%hu%s", ptr->idMachine, sufixFileName);
                printf("Generated file: %s\n", fileName);
                FILE *fp = fopen(fileName,"w");

                fwrite(str,1,dataSize,fp);

                fclose(fp);

                buffer[1] = ACK_CODE;

            } else {
                char errorMsg[] = "The Machine ID in the Message doesn't match with the Machine ID.";
                int size = strlen(errorMsg);

                unsigned char lsb = (unsigned)size & 0xff;
                unsigned char msb = (unsigned)size >> 8;

                buffer[DATA_SIZE_OFFSET] = lsb;
                buffer[DATA_SIZE_OFFSET + 1] = msb;

                for (i = 0; i < size; i++){
                    buffer[DATA_OFFSET + i] = errorMsg[i];
                }

            }

            if(ptr->reset==1){
                pthread_cond_wait(&condReset,&mutex);
                printf("The config_request function has stopped.");
            }

            SSL_write(sslConn, buffer, BUF_SIZE);
        }
        SSL_free(sslConn);
        close(newSock);

    }

    close(sock);
    pthread_exit((void *)NULL);
}