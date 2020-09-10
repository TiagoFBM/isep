RCOMP 2018-2019 LAPR4 - 1181011
============================================================
(Este ficheiro deve ser editado apenas pelo membro 1181011)

## **Visão Geral do Caso de Uso:** ##

#### [Main Page - Support Config Requests](https://bitbucket.org/TiagoMoreiraISEP/lei_isep_2019_20_sem4_2di_1181011_1181012_1181047_1181061_1150/src/909e2252826ad7d4ef04581f52a6e90bacf214d8/documentacao/SupportConfigurationFileReception_UC_1014/SupportConfigurationFileReception.md)

Este caso de uso permite ao Sistema de Comunicação Central com as Máquinas (SCM) enviar ficheiros de configuração para a máquina.

A máquina é conhecida pelo SCM aquando da troca de mensagens entre o SCM e a Máquina (UC 1011). Depois de conhecida pelo SCM, a Máquina fica apta para receber pedidos de configuração.

Quando o pedidos de configuração são recebidos, no campo *RAW DATA* é recebido o conteúdo do ficheiro de configuração que será "implementado" pela máquina. Para simular a associação do ficheiro de configuração há máquina, esta exporta o ficheiro para um diretório local.

Caso o ficheiro seja recebido corretamente então é enviado como resposta uma mensagem com código ACK. Caso o contrário aconteça então o campo do código da mensagem será preenchido com NACK.

#### Interação com o utilizador ###

1. Executar o Sistema de Comunicação Central com as Máquinas (SCM).
2. Executar a Máquina.
3. Escolher um dos ficheiros de configuração da máquina para enviar para o simulador.
4. Observar a mensagem recebida pela máquina.
5. Aceder no diretório local para ver o conteúdo do ficheiro exportado.

#### [Main Page - Support Reset Requests](https://bitbucket.org/TiagoMoreiraISEP/lei_isep_2019_20_sem4_2di_1181011_1181012_1181047_1181061_1150/src/909e2252826ad7d4ef04581f52a6e90bacf214d8/documentacao/SupportResetRequests_UC_1016/SupportResetRequests.md)

Este caso de uso permite ao Sistema de Monitorização das Máquinas efetuar a reinicialização da máquina.

Depois da máquina receber o pedido de monitorização passa a ser conhecida pelo SMM. Assim sendo, é possível enviar um pedido de reinicialização.
Quando esse pedido é recebido a máquina pára (em segurança) todas as operações em funcionamento e reinincia. Depois de voltar a ligar retoma o ponto onde tinha sido parada.

#### Interação com o utilizador ###

1. Executar o Sistema de Monitorização com as Máquinas.
2. Executar o Simulador da Máquina.
3. Enviar um pedido de monitorização para a máquina.
4. Observar a receção do pedido na máquina.
5. No Sistema de Monitorização enviar o pedido de *RESET* para a máquina.
6. Observar a receção do pedido na máquina.
7. Observar o reinincio da máquina.
8. Observar a retoma tas operações anteriores.