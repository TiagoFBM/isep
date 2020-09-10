# UC 1011 - Simulate Machine Operation #

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Estabelecer conexão entre o Simulador de Máquinas e o Sistema Central ####

1. Executar o servidor do Sistema Central de Comunicação com as máquinas.
2. Executar o simulador da máquina.
3. O simulador da máquina envia um *hello request* ao servidor.
4. O servidor recebe o pedido e retorna um *ACK*, visto que a máquina pertence à linha de produção identificada no servidor.
5. A máquina começa a leitura do ficheiro. Podemos confirmar isto com a mensagem ("Generating messages"). Lê as mensagens uma de cada vez e envia-as como *Message Request* para o servidor.
6. O servidor recebe o pedido de mensagem e envia uma resposta a informar do sucesso da operação.
7. A conexão é fechada aquando da finalização da leitura do ficheiro.

### Caso #2 : Estabelecer conexão entre o Simulador de Máquinas e o Sistema Central ####

1. Executar o simulador da máquina.
2. O utilizador será notificada que a tentativa de conexão foi recusada devido ao servidor estar desligado.

