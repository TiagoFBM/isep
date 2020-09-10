# UC 5001 - Process Available Messages

## Pre-Condições

- O sistema deve possuir uma lista com mensagens válidas para processamento.

## Pós-Condições

- Depois de serem processadas, o estado das mensagens será atualizado para processado.

## ANÁLISE

- Será criada uma thread para processar mensagens de cada linha de produção.
- As mensagens existentes devem ser enriquecidas. (e.g. Ordem de Produção Correspondente e Linha de Produção onde a máquina se encontra)
- É necessário validar se as mensagens possuem congruência no domínio (e.g. se estornar uma quantidade inferior à quantidade consumida). Caso contrário, um erro deve ser gerado.
- O relatório da Ordem de Produção deve ser criado e preenchido com as informações provenientes do processamento das mensagens.

## REGRAS DE NEGÓCIO

- As mensagens devem ser enriquecidas, validadas e processadas.
- O processamento das mensagens deve ser feito por linha de produção de forma independente e em paralelo.
- As mensagens são processadas em bloco para um intervalo de tempo (e.g. das 11h45 às 12h00) especificado. Processo o bloco e termina.
- As mensagens são processadas em bloco de forma recorrente em intervalos de tempo (e.g. 15m) a contar de uma dado momento (e.g. 11h00). Depois de processar um bloco, aguarda até ser oportuno processar o bloco seguinte.
- A recorrência deve ser implementada através da periodicidade de um thread.
- Ao executar o SPM, o utilizador indica:
  - O intervalo de tempo (obrigatoriamente)
  - A(s) linha(s) de produção que pretende processar. Se esta informação for omitida, pode-se considerar todas as linhas de produção.
- Erros ocorridos durante este processo devem gerar notificações que informem adequadamente os utilizadores sobre o erro ocorrido e, dessa forma, facilite a realização de operações de retificação.
