#define BUF_SIZE 512
#define TCP_BUF_SIZE 65535 
#define SERVER_PORT "30901"
#define VERSION_OFFSET 0
#define MESSAGE_CODE_OFFSET 1
#define MACHINE_ID_OFFSET 2
#define DATA_SIZE_OFFSET 4
#define DATA_OFFSET 6
#define HELLO_REQUEST_CODE 0
#define MSG_REQUEST_CODE 1
#define CONFIG_REQUEST_CODE 2
#define RESET_REQUEST_CODE 3
#define ACK_CODE 150
#define NACK_CODE 151

#define CLIENT_SSL_CERT_FILE "SSL/client1"
#define CLIENT_SSL_KEY_FILE "SSL/client1"
#define SERVER_SSL_CERT_FILE "SSL/server_J.pem"

pthread_mutex_t mutex;
pthread_cond_t condReset;

typedef struct {
    char lastStatusReceived[506]; 
    char nameFile[50];
    char* tcpIp;
    unsigned int hasStatus;
    unsigned short idMachine;
    unsigned char lastAnswerFromSCM;
    unsigned char simulationTime;
    int reset;
} machineData;

void* udpHandler(void* args);

char* monitor_requests(machineData *ptr);

unsigned char* reset_requests(machineData *ptr);

void* config_request(void* args);

void* send_hello_messages(void* args);

void* send_messages(void* args);

int createSock();