# UC 1012 - Support Monitoring Request #

## Pre-Condições ##
* A Máquina deve receber um pedido *HELLO* do Sistema Central.

## Pós-Condições ##
* O Sistema Simulador da Máquina deve suportar pedidos de outros sistemas e responder a estes.

## ANÁLISE ##
* A Máquina deve possuir a capacidade de comunicação baseada em *UDP*.
* A Máquina responde ao pedido de *HELLO* inicial com a última resposta recebida pelo Sistema Central (caso esta esteja acompanhada de um status, este deve também ser reencaminhado).

## REGRAS DE NEGÓCIO ##
* A funcionalidade deve ser concorrente (em C) com o resto da simulação da máquina e o estado deve ser partilhado entre threads.
* A comunicação entre o sistema de monitorização e as máquinas é **baseada em UDP**.
* O tamanho máximo de pedidos e respostas é de 512 bytes.
* As máquinas industriais apenas se tornam conhecidas pelo sistema de monitorização aquando da resposta a pedidos do tipo *HELLO* remetidos por este.
* As máquinas industriais são identificadas através de um número de identificação único (unique identification number), que corresponde a um número inteiro positivo entre 1 e 65535.
* Cada máquina industrial conhece o seu próprio número de identificação único.