RCOMP 2018-2019 LAPR4 - 1181011 - UC 1012
============================================================
(Este ficheiro deve ser editado apenas pelo membro 1181011)

## **Visão Geral do Caso de Uso:** ##

[Main Page - Support Monitoring Request](https://bitbucket.org/TiagoMoreiraISEP/lei_isep_2019_20_sem4_2di_1181011_1181012_1181047_1181061_1150/src/d7f9d479786f659718963b5fd0019254b8d0c507/documentacao/SupportMonitoringRequests_UC_1012/SupportMonitoringRequests.md)

Este caso de uso permite ao Sistema de Monitorização das Máquinas conhecer o estado (última resposta do SCM) de cada máquina.

A máquina atua como servidor UDP, assim sendo é necessário executá-la em primeiro lugar. Depois, quando o Sistema de Monitorização envia (em broadcast) um pedido de HELLO para a porta em que a máquina se encontra, esta deteta esse pedido e responde com a última resposta recebida pelo SCM.

Quando o simulador da máquina é executado não existe conexão com o sistema central da máquina, assim sendo, se um pedido de monitorização foi efetuando não existindo essa comunicação, será enviado para o SMM apenas o ID da máquina juntamente com uma mensagem de erro armazenada em *Raw Data*.

Caso a última mensagem enviada pelo SCM para a máquina contenha um status associada, então esse será também enviado para o SMM.

As funções utilizadas foram anexadas na pasta Functions para que seja possível consultar o trabalho efetuado.

### Interação com o utilizador ###

1. Executar o Simulador da Máquina.
2. Executar o Sistema de Comunicação Central.
3. Executar o Sistema de Monitorização com as Máquinas.
4. Observar a interação entre o SMM e a Máquina.