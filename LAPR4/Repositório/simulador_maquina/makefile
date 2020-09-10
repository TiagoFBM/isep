server: monitor_requests.o send_messages.o machine.o createSock.o config_request.o send_hello_messages.o reset_requests.o udpHandler.o
	gcc monitor_requests.o send_messages.o machine.o createSock.o config_request.o send_hello_messages.o reset_requests.o udpHandler.o -o server -lpthread -lssl -lcrypto
monitor_requests.o: monitor_requests.c
	gcc -g -Wall -c monitor_requests.c -o monitor_requests.o -lpthread
send_messages.o: send_messages.c
	gcc -g -Wall -c send_messages.c -o send_messages.o -lpthread -lssl -lcrypto
config_request.o: config_request.c
	gcc -g -Wall -c config_request.c -o config_request.o -lpthread -lssl -lcrypto
reset_requests.o: reset_requests.c
	gcc -g -Wall -c reset_requests.c -o reset_requests.o -lpthread -lssl -lcrypto
send_hello_messages.o: send_hello_messages.c
	gcc -g -Wall -c send_hello_messages.c -o send_hello_messages.o -lpthread -lssl -lcrypto
udpHandler.o: udpHandler.c
	gcc -g -Wall -c udpHandler.c -o udpHandler.o -lpthread -lssl -lcrypto
machine.o: machine.c
	gcc -g -Wall -c machine.c -o machine.o -lssl -lcrypto
clean:
	rm -f *.o server
run: server
	./server 10 10.8.213.118 Mensagens.txt 2
