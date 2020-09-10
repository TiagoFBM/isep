# UC 1014 - Support Configuration File Reception #

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Estabelecer Conexão entre o Sistema de Monitorização e as Máquinas ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Monitorização de máquinas.
3. Enviar (apartir do Sistema de Monitorização) um pedido de CONFIG.
4. Imprimir, no simulador da máquina o seu IP.
5. Imprimir, no Sistema de Monitorização, o IP recebido.
6. Comparar os dois IPs, devem ser iguais.

#### CASO #2 : Obter e Exportar um Ficheiro de Configuração ####
1. Executar o Simulador da Máquina.
2. Executar o Sistema de Comunicação com as Máquinas.
3. Enviar (apartir do Sistema de Comunicação com as Máquinas) um pedido de CONFIG.
4. Exportar o ficheiro recebido para um diretório local.
5. Validar se o ficheiro exportado é igual ao ficheiro enviado pelo SCM.