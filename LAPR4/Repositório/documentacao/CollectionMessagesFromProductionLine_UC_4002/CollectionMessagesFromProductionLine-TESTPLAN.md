# UC 4002 - Collection Messages From Production Line #

## PLANO DE TESTES ##

#### CASO #1 : Receber Menssagens Hello Com Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido HELLO.
3. O ID da máquina é conhecido e é enviada como resposta uma mensagem de ACK.
4. O sistema atualiza no repositório da máquina o seu endereço de rede.

#### CASO #2 : Receber Menssagens Hello Sem Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido HELLO.
3. O ID da máquina não é conhecido pelo sistema.
4. É retornada uma mensagem de NACK.

#### CASO #3 : Receber Menssagens MSG Com Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido MSG.
3. O ID e o endereço de rede da máquina coincidem com a informação disponível no sistema central.
4. É retornada uma mensagem de ACK.

#### CASO #4 : Receber Menssagens MSG Sem Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido MSG.
3. O ID e o endereço de rede da máquina não coincidem com a informação disponível no sistema central.
4. O pedido é ignorado e a resposta NACK é retornada.

  