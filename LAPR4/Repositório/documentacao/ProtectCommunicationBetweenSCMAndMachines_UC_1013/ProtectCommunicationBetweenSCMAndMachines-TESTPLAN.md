# UC 1013 - Protect Communication Between SCM And Machines #

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Receber Mensagens Hello Com Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido HELLO.
3. O ID da máquina é conhecido e é enviada como resposta uma mensagem de ACK.
4. O sistema atualiza no repositório da máquina o seu endereço de rede.

#### CASO #2 : Receber Mensagens Hello Sem Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido HELLO.
3. O ID da máquina não é conhecido pelo sistema.
4. É retornada uma mensagem de NACK.

#### CASO #3 : Receber Mensagens Hello Sem Sucesso (Certificado inválido) ####

1. Gerar os certificados através da execução do comando ./make_certs no gitbash.
2. Executar o servidor do Sistema Central de Comunicação com as máquinas.
3. Executar o simulador da máquina (por exemplo, se a linha de produção escolhida for a *LP_002*, executar a máquina 10 cujo certificado seja client50.pem/ client50.key).
4. O utilizador será notificada que a tentativa de conexão foi recusada devido à máquina que está a ser executada não ter um certificado válido.


#### CASO #4 : Receber Mensagens MSG Com Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido MSG.
3. O ID e o endereço de rede da máquina coincidem com a informação disponível no sistema central.
4. É retornada uma mensagem de ACK.

#### CASO #5 : Receber Mensagens MSG Sem Sucesso ####

1. Executar o Sistema de Comunicação com as Máquinas com o código da linha de produção passado como parâmetro.
2. O sistema espera que uma máquina industrial envie um pedido MSG.
3. O ID e o endereço de rede da máquina não coincidem com a informação disponível no sistema central.
4. O pedido é ignorado e a resposta NACK é retornada.

