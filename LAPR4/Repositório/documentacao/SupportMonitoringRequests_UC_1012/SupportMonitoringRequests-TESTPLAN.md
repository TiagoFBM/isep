# UC 1012 - Support Monitoring Request #

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Estabelecer conexão entre o Sistema de Monitorização e as Máquinas ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Monitorização de máquinas.
3. Enviar (apartir do Sistema de Monitorização) um pedido Broadcast para a porta 1010.
4. Imprimir, no simulador da máquina o seu IP.
5. Imprimir, no Sistema de Monitorização, o IP recebido.
6. Comparar os dois IPs, devem ser iguais.

#### CASO #2 : Inquirir a máquina sobre o seu estado não tendo esta conexão com o Sistema de Comunicação Central  ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Monitorização de máquinas.
3. Enviar (apartir do Sistema de Monitorização) um pedido Broadcast para a porta 1010.
4. Estabelecer conexão com uma máquina.
5. Inquirir a máquina sobre o seu estado (garantindo que a comunicação entre esta e o sistema central está inativo).
6. O resultado obtido deve ser um estado "Desligado".

#### CASO #3 : Inquirir a máquina sobre o seu estado tendo esta conexão com o Sistema de Comunicação Central ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Monitorização de máquinas.
3. Enviar (apartir do Sistema de Monitorização) um pedido Broadcast para a porta 1010.
4. Estabelecer conexão com uma máquina.
5. Inquirir a máquina sobre o seu estado (garantindo que a comunicação entre esta e o sistema central está ativo).
6. O resultado obtido deve ser um estado "Ligado".