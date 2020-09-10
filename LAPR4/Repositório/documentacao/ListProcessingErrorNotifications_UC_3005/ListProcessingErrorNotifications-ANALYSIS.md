# UC 3005 - List Processor Error Notifications #

## Pre-Condições ##

* O utilizador que acede ao sistema deve possuir a *role* de Gestor de chão de fábrica.
* Já deve ter existido um processamento de mensagens.
* As notificações devem estar classificadas em/por tipos de erro.

## Pós-Condições ##

* Lista com os resultados incorretos durante o processamento de mensagens.

## ANÁLISE ##

* A lista deve distinguir entre:
    1. notificações ativas ou não tratadas
    2. notificações arquivadas, i.e. que já foram tratadas por alguém.

* É apresentado ao utilizador duas opções (filtros) para escolher a forma como a consulta será apresentada.


## REGRAS DE NEGÓCIO ##

* O sistema deve permitir filtrar estas notificações por tipo de erro e linha de produção.
