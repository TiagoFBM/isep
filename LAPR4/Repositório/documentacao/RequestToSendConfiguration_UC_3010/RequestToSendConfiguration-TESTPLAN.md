### UC 3010 - Request to send a configuration to the machine ###

#### TEST PLAN ####

#### CASO #1 : Enviar ficheiro de configuração para a máquina ####

1. São apresentadas ao utilizador todas as máquinas presentes no sistema ao utilizador.
2. O utilizador escolhe uma máquina.
3. O sistema verifica se já existe um ficheiro de configuração na fila de espera para ser enviado para a máquina.
4. O sistema prossegue com a operação.
5. O sistema verifica se a máquina escolhida tem algum ficheiro de configuração.
6. O sistema prossegue com a operação.
7. São apresentados todos os ficheiros de configuração da máquina ao utilizador.
8. O utilizador escolhe um ficheiro de configuração.
9. O pedido de configuração fica registado na base de dados.
10. O sistema faz pulling a todos os pedidos da base de dados.
11. O sistema verifica se a máquina correspondente ao pedido está ligada.
12. O sistema verifica que a máquina está ligada.
13. O sistema envia o pedido de configuração para a máquina.
14. O pedido fica registado como enviado.

#### CASO #2 : Enviar ficheiro de configuração para a máquina (Máquina com ficheiro de configuração ainda não enviado) ####

1. São apresentadas ao utilizador todas as máquinas presentes no sistema ao utilizador.
2. O utilizador escolhe uma máquina.
3. O sistema verifica se já existe um ficheiro de configuração na fila de espera para ser enviado para a máquina.
4. O sistema verifica que já existe um ficheiro de configuração na fila de espera para ser enviado para a máquina.
5. O pedido de configuração não é adicionado.

#### CASO #3 : Enviar ficheiro de configuração para a máquina (Máquina sem ficheiro de configuração) ####

1. São apresentadas ao utilizador todas as máquinas presentes no sistema ao utilizador.
2. O utilizador escolhe uma máquina.
3. O sistema verifica se já existe um ficheiro de configuração na fila de espera para ser enviado para a máquina.
4. O sistema prossegue com a operação.
5. O sistema verifica se a máquina escolhida tem algum ficheiro de configuração.
6. O sistema verifica que a máquina escolhida não tem nenhum ficheiro de configuração.
7. O pedido de configuração não é adicionado.

#### CASO #4 : Enviar ficheiro de configuração para uma máquina desligada ####

1. São apresentadas ao utilizador todas as máquinas presentes no sistema ao utilizador.
2. O utilizador escolhe uma máquina.
3. O sistema verifica se já existe um ficheiro de configuração na fila de espera para ser enviado para a máquina.
4. O sistema prossegue com a operação.
5. O sistema verifica se a máquina escolhida tem algum ficheiro de configuração.
6. O sistema prossegue com a operação.
7. São apresentados todos os ficheiros de configuração da máquina ao utilizador.
8. O utilizador escolhe um ficheiro de configuração.
9. O pedido de configuração fica registado na base de dados.
10. O sistema faz pulling a todos os pedidos da base de dados.
11. O sistema verifica se a máquina correspondente ao pedido está ligada.
12. O sistema verifica que a máquina não se encontra ligada.
13. O pedido não é enviado para a máquina.
