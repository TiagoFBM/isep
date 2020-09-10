RCOMP 2018-2019 LAPR4 - 1181012 - UC 4002
============================================================
(Este ficheiro deve ser editado apenas pelo membro 1181012)

## **Visão Geral do Caso de Uso:** ##

![SD_ConsultProductionOrdersFromPackage.png](SD_CollectionMessagesFromProductionLine.png)

## **Explicação Geral do Caso de Uso** ##

Neste caso de uso era pretendido proceder à recolha das mensagens geradas pelas máquinas de uma determinada linha de produção. Para isso, a comunicação entre as máquinas e o Sistema Central foi baseada em TCP. Desta forma, foi criado um servidor TCP concorrente, que garante que a recolha das mensagens é efetuada de forma concorrente por linha e por máquina.

Inicialmente o sistema central conhece apenas o número de identificação único de cada máquina industrial através do seu repositório de dados e guarda esta informação num Map<ProtocolIdentificationNumber, InetAddress>. O endereço de rede fica a ser conhecido depois do envio de uma mensagem HELLO por parte da máquina, ou seja inicialmente o Map anterior inicializa cada value a null. 

As máquinas podem enviar dois tipos de mensagem, HELLO (code = 0) e MSG (code = 1).
A máquina começa por ser iniciada e envia um pedido HELLO para o sistema central. Por sua vez, o sistema central verifica se o ID da máquina é conhecido, através do Map. Se sim, este envia de volta uma resposta ACK e atualiza no seu repositório o endereço de rede da máquina industrial. Caso contrário, o sistema central retorna a mensagem de NACK.

Se a máquina enviar uma mensagem MSG, o sistema valida se o ID e o endereço de rede da máquina já são conhecidos no Map. Se forem conhecidos, retorna a mensagem de ACK, se não forem retorna uma mensagem de NACK. Seja qual for a mensagem retornada, é adicionado a essa mensagem um texto de status a explicar o resultado do pedido e o estado atual da contraparte.

### Interação com o utilizador ###

1. Fazer rebuild ao projeto.
2. Correr o bootstrap.
3. Correr a clase BaseSCM.
4. Selecionar a opção 1 (Receive Messages From Machines).
5. Introduzir o código da linha de produção pretendida.
6. Esperar que a(s) máquina(s) seja(m) iniciada(s) e envie(m) o pedido de HELLO.
7. Visuzalizar as respostas do sistema central consoante as mensagens enviadas pela máquina.