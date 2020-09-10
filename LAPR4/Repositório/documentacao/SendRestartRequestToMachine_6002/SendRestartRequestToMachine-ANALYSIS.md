### UC 6002 - Send a restart request to a machine

## Pré Condições

- É necessário conhecer o IP da máquina a que se quer enviar o pedido.

## Pós Condições

- A máquina reinicia e retorna se teve sucesso ou não.

## Análise

- É apresentada uma lista de máquinas com IP conhecido e é solicitada a escolha da máquina pretendida.
- Quando a máquina recebe o pedido de reset, esta envia um pacote de dados UDP com o código de RESET para a máquina em específico e aguarda pela sua resposta.
- A resposta pode ser ACK ou NACK.

## Regras de negócio

- RESET tem o código 3.
- ACK tem o código 150.
- NACK tem o código 151.

### Notas

Este caso de uso está dependente do caso de uso 6001. Para ser possível enviar um pedido de reset para uma máquina é necessário haver a monitorização da máquina no caso de uso especificado anteriormente.
