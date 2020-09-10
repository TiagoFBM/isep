# UC 1014 - Support Configuration File Reception #

## Pre-Condições ##
* A Máquina deve receber um pedido *CONFIG* do Sistema Central.

## Pós-Condições ##
* O Sistema Simulador da Máquina deve exportar o ficheiro recebido para um diretório local.

## ANÁLISE ##
* A Máquina deve possuir a capacidade de comunicação baseada em *TCP* para comunicar com o Sistema de Comunicação Central.
* Quando a máquina recebe um pedido de *CONFIG* deve exportar o ficheiro recebido para um diretório local.
* O ficheiro de configuração será enviado na mensagem *CONFIG* no campo **RAW DATA**.

## REGRAS DE NEGÓCIO ##
* A funcionalidade deve ser concorrente (em C) com o resto da simulação da máquina e o estado deve ser partilhado entre threads.
* A comunicação entre o sistema de comunicação central e as máquinas é **baseada em TCP**.
* O tamanho máximo de mensagens é 512 bytes.
* As máquinas industriais apenas se tornam conhecidas pelo Sistema de Monitorização aquando da resposta a pedidos do tipo *HELLO* remetidos por este.
* As máquinas industriais são identificadas através de um número de identificação único (unique identification number), que corresponde a um número inteiro positivo entre 1 e 65535.
* Cada máquina industrial conhece o seu próprio número de identificação único.
* Para demonstrar que recebeu corretamente esse ficheiro a máquina pode persisti-lo localmente numa pasta.
* Caso o ID constante no pedido corresponda ao ID da máquina industrial deve ser retornado uma resposta ACK. Caso contrário, o pedido deve ser recusado e uma resposta NACK é retornada.