#define BUF_SIZE 512
#define SERVER_PORT "30901"
#define VERSION_OFFSET 0
#define MESSAGE_CODE_OFFSET 1
#define MACHINE_ID_OFFSET 2
#define DATA_SIZE_OFFSET 4
#define DATA_OFFSET 6
#define ACK_CODE 150
#define NACK_CODE 151

typedef struct {
    char lastStatusReceived[506]; 
    char nameFile[50];
    char* tcpIp;
    unsigned int hasStatus;
    unsigned short idMachine;
    unsigned char lastAnswerFromSCM;
    unsigned char simulationTime;
} machineData;

void* monitor_requests(void* args);

void* send_messages(void* args);

int createSock();