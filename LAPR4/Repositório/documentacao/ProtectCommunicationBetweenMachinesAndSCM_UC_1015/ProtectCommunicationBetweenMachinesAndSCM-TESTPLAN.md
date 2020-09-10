# UC 1015 - Protect Communication Between Machines And SCM #

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Mandar Mensagens Hello Com Sucesso ####

1. Gerar os certificados através da execução do comando ./make_certs no gitbash.
2. Executar o servidor do Sistema Central de Comunicação com as máquinas.
3. Executar o simulador da máquina (por exemplo, se a linha de produção escolhida for a *LP_002*, executar a máquina 10).
4. O simulador da máquina envia um *hello request* ao servidor.
5. O servidor recebe o pedido e retorna um *ACK*, visto que a máquina pertence à linha de produção identificada no servidor e tem um certificado conhecido pelo SCM.
6. A máquina começa a leitura do ficheiro. Podemos confirmar isto com a mensagem ("Generating messages"). Lê as mensagens uma de cada vez e envia-as como *Message Request* para o servidor.
7. O servidor recebe o pedido de mensagem e envia uma resposta a informar do sucesso da operação.
8. A conexão é fechada aquando da finalização da leitura do ficheiro.

#### CASO #2 : Mandar Mensagens Hello Sem Sucesso (SCM desligado) ####

1. Executar o simulador da máquina.
2. O utilizador será notificada que a tentativa de conexão foi recusada devido ao servidor estar desligado.

#### CASO #3 : Mandar Mensagens Hello Sem Sucesso (Máquina não pertence à linha de produção) ####

1. Executar o servidor do Sistema Central de Comunicação com as máquinas.
2. Executar o simulador da máquina (escolher uma máquina que não esteja na linha de produção definida no SCM).
3. O utilizador será notificada que a tentativa de conexão foi recusada devido à máquina que está a ser executada não pertencer à linha de produção definida no SCM.

#### CASO #4 : Mandar Mensagens Hello Sem Sucesso (Máquina não tem certificado válido para estabelecer contacto com o SCM de maneira a enviar mensagens) ####

1. Gerar os certificados através da execução do comando ./make_certs no gitbash.
2. Executar o servidor do Sistema Central de Comunicação com as máquinas.
3. Executar o simulador da máquina (por exemplo, se a linha de produção escolhida for a *LP_002*, executar a máquina 10 cujo certificado seja client50.pem/ client50.key).
4. O utilizador será notificada que a tentativa de conexão foi recusada devido à máquina que está a ser executada não ter um certificado válido.
