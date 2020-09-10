# RCOMP 2019-2020 LAPR4 - 1181061 - UC 6001

(Este ficheiro deve ser editado apenas pelo membro 1181061)

## **Visão Geral do Caso de Uso:**

![Main page - Monitor Machines From Production Line](https://bitbucket.org/TiagoMoreiraISEP/lei_isep_2019_20_sem4_2di_1181011_1181012_1181047_1181061_1150/src/master/documentacao/MonitorMachinesFromProductionLine_UC_6001/MonitorMachinesFromProductionLine.md)

## **Explicação Geral**

O caso de uso permite que o sistema de monitorização conheça as ultimas respostas e o respetivo estado enviadas pela máquina.
Inicialmente, este envia um pedido de broadcast para todos os servidores que estão à escuta na porta 30901 na rede local e, quando estes enviam uma respota, o sistema verifica se o ID de protocolo da máquina está contido na linha de produção que este está a monitorar e se isto acontecer, o sistema guarda o IP da máquina para enviar pedidos unicast.
Depois de enviar o pedido de broadcast para todas as máquinas, o sistema envia um pedido unicast para cada IP guardado de 30 em 30 segundos. Este dá timeout se durante 60 segundos não houver uma resposta da máquina.
Com a resposta recebida, o sistema constrói uma lista com os estados recebidos e, quando é interrompido o sistema ou quando todas as máquinas dão timeout, este cria um ficheiro de texto com os estados recebidos de cada máquina.

### Interação com o utilizador

1. Executar o Simulador da Máquina.
2. Executar o Sistema de Comunicação Central.
3. Executar o Sistema de Monitorização com as Máquinas.
4. Observar a interação entre o SMM e a Máquina.
