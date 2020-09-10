### UC 3010 - Request to send a configuration to the machine ###

## Pré Condições ##

* O utilizador que acede ao sistema deverá ter a *role* de Gestor de Cháo de Fábrica.
* Devem existir máquinas ligadas no sistema.

## Pós Condições ##

* É enviado para o simulador da máquina uma configuração

## Análise ##

* O sistema envia para uma dada máquina a mensagem de configuração constante no ficheiro indicado pelo utilizador.
* A máquina que o utilizador selecionou deve estar ligada para que, desta maneira, possa ser enviado o ficheiro de configuração.
* O sistema irá comunicar com o backoffice da aplicação, através da Base de Dados, de maneira a saber quando é que poderá enviar pedidos config.
* A cada 5 segundos, o sistema faz *pulling* da base de dados para ir buscar todos os pedidos feitos pelo gestor de chão de fábrica.

## Regras de negócio ##

* Será utilizada a base de dados para a comunicação entre o gestor de chão de fábrica e o sistema de comunicação das máquinas.
* O envio de mensagens e recepção de ficheiro de configuração ocorrem em modo paralelo/concorrente.
* Apenas é possível ter um pedido para cada máquina na base de dados.

## Ocorrência do caso de uso ##

* Este caso de uso será utilizado aquando do envio de uma configuração para a máquina selecionada.
