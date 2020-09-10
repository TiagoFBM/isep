# UC 6001 - Monitor Machines From Production Line #

## PLANO DE TESTES ##

#### CASO #1 : Enviar mensagem Hello a todas as máquinas ####
1. Aceder ao sistema.
2. Enviar mensagem de broadcast a todas as máquinas.
3. Esperar mensagem ACK ou NACK de cada máquina.
4. Adicionar as máquinas desejadas à lista de IPs.
5. Enviar mensagens de Hello de 30 em 30 segundos.

#### CASO #2 : Validar se lista de IPs está vazia ####
1. Aceder ao sistema.
2. Enviar mensagem de broadcast a todas as máquinas.
3. Nenhuma máquina responde, por consequência a lista fica vazia.
4. Enviar mensagens de broadcast de 30 em 30 segundos.
  
#### CASO #3 : Enviar um pedido de reset a uma máquina ####
1. Aceder ao sistema.
2. Enviar mensagem de reset à máquina.
3. Esperar que a máquina envie um pedido HELLO para o sistema central.
4. A resposta correspondente deve ser encaminhada como resposta ao pedido de RESET.

#### CASO #4 : Detetar respostas com o mesmo número de identificação único ####
1. Aceder ao sistema.
2. Enviar mensagem de broadcast a todas as máquinas.
3. Esperar mensagem ACK ou NACK de cada máquina.
4. Receção de 2 mensagens com o mesmo número de identificação único.
5. Registar num ficheiro de texto as máquinas que enviaram essas mensagens.
  