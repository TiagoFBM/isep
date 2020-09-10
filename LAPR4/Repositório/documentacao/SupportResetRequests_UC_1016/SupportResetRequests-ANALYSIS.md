# UC 1016 - Support Reset Request #

## Pre-Condições ##
* A Máquina deve receber um pedido *RESET* do Sistema Central.

## Pós-Condições ##
* O Sistema Simulador da Máquina deve suportar pedidos de outros sistemas e responder a estes.

## ANÁLISE ##
* A Máquina deve possuir a capacidade de comunicação baseada em *UDP* para comunicar com o Sistema de Monitorização e *TCP* para comunicar com o Sistema de Comunicação Central.
* Quando a máquina recebe um pedido de *RESET* deve enviar um pedido *HELLO* para o Sistema Central e a resposta correspondente deve ser encaminhada como resposta ao pedido de *RESET*.
* O pedido de *RESET* deve ser executado independentemente da resposta recebida ao pedido de *HELLO* do Sistema Central.
* Quando uma máquina recebe um pedido de reinicialização, esta começa por interromper (em "segurança" / adequadamente) as atividades/processos em curso (e.g. envio de mensagens) e reinicia-se (i.e. desliga-se e volta a ligar-se). 
* Após reiniciar, a máquina envia um pedido *HELLO* baseado em TCP para o Sistema Central e retoma as atividades/processos que tem pendentes (e.g. os que foram interrompidos). 
* Entre desligar-se e voltar a ligar-se passa algum tempo (e.g. 20 segundos).

## REGRAS DE NEGÓCIO ##
* A funcionalidade deve ser concorrente (em C) com o resto da simulação da máquina e o estado deve ser partilhado entre threads.
* A comunicação entre o Sistema de Monitorização e as Máquinas é **baseada em UDP**.
* A comunicação entre o Sistema de Comunicação Central e as Máquinas é **baseada em TCP**.
* O tamanho máximo de pedidos e respostas é de 512 bytes.
* As máquinas industriais apenas se tornam conhecidas pelo Sistema de Monitorização aquando da resposta a pedidos do tipo *HELLO* remetidos por este.
* As máquinas industriais são identificadas através de um número de identificação único (unique identification number), que corresponde a um número inteiro positivo entre 1 e 65535.
* Cada máquina industrial conhece o seu próprio número de identificação único.