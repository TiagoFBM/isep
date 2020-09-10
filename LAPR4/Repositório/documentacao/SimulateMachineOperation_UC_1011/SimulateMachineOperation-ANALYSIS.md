# UC 1011 - Simulate Machine Operation #

## Pré-Condições ##

* Já devem existir máquinas associadas no sistema.
* Deve existir um ficheiro de texto com mensagens.

## Pós-Condições ##

* Simulador de máquinas.

### ANÁLISE ###

* Corresponde a uma máquina industrial real ou a um software que simula o funcionamento geral de uma máquina industrial.
* Esta funcionalidade basear-se-á no envio de mensagens para o sistema central.
* O envio de uma mensagem inclui sempre o envio de um pedido (request) e a receção de uma resposta (response).
* As máquinas industriais são identificadas através de um número de identificação único (unique identification number), que corresponde a um número inteiro positivo entre 1 e 65535.
* As mensagens a enviar são lidas de um ficheiro de texto. Para causar um efeito de simulação, os linhas do ficheiro são lidas uma de cada vez com um intervalo de tempo estipulado nos parametros aquando da execução do programa.
* Para fazer a comunicação com o sistema de receção de mensagens será utilizado o protocolo TCP.

## REGRAS DE NEGÓCIO ##

* Esta funcionalidade será desenvolvida em c (utilizando threads) e através de um protocolo.
* A identificação da máquina, o ip, o nome do ficheiro e a cadência de envio são estipuladas por parametro.
* Deve contemplar cenários de erro que permitam aferir a resiliência do SCM.

## CONTEXTO DE UTILIZAÇÃO ##

* Este caso de uso será utilizado para simular o funcionamento de uma máquina, nomeadamente no envio de mensagens geradas por estas.

### **NOTAS** ###

* Quando o modo de operação é sobre TCP, o envio de uma mensagem implica o estabelecimento de uma conexão TCP sobre a qual é enviado o pedido e assim que a resposta seja obtida a conexão é fechada.
