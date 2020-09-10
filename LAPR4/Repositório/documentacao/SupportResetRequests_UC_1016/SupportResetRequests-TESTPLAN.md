# UC 1016 - Support Reset Request #

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Estabelecer Conexão entre o Sistema de Monitorização e as Máquinas ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Monitorização de máquinas.
3. Enviar (apartir do Sistema de Monitorização) um pedido de RESET.
4. Imprimir, no simulador da máquina o seu IP.
5. Imprimir, no Sistema de Monitorização, o IP recebido.
6. Comparar os dois IPs, devem ser iguais.

#### CASO #2 : Obter Timeout por Falta de Resposta do SCM ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Monitorização de máquinas.
3. Executar o Sistema de Comunicação com as Máquinas.
4. Enviar (apartir do Sistema de Monitorização) um pedido de RESET.
5. Enviar um pedido de HELLO ao SCM.
6. Imprimir uma mensagem explicita de Timeout.
7. O Sistema de Monitorização deve receber uma resposta com um código de erro.

#### CASO #3 : Obter uma Resposta Válida ao Pedido de Reset ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Monitorização de máquinas.
3. Executar o Sistema de Comunicação com as Máquinas.
4. Enviar (apartir do Sistema de Monitorização) um pedido de RESET.
5. Enviar um pedido de HELLO ao SCM.
6. O Sistema de Monitorização deve receber a resposta ao pedido de HELLO enviada pelo SCM.