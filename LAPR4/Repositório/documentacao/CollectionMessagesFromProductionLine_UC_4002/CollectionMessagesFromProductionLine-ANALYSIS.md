# UC 4002 - Collection Messages From Production Line #

## Pre-Condições ##

* Deve existir no sistema, máquinas para gerarem mensagens e essas serem recebidas.

## Pós-Condições ##

* Irá ser criado um log de todas as mensagens recebidas das máquinas.

## ANÁLISE ##

* Irá ser criada uma linha para cada mensagem recebida, com a informação do IP da máquina que enviou a mensagem e a hora de envio.
* Irá ser criado um servidor concorrente TCP para receber todas as mensagens provenientes das máquinas.
  
## REGRAS DE NEGÓCIO ##

* As mensagens recolhidas devem ser disponibilizadas para posterior processamento.
* A recolha das mensagens será feita de forma concorrente (por linha e máquina).