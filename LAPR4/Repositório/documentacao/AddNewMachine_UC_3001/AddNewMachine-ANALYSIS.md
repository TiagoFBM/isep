# UC 3001 - Add New Machine #

## Pre-Condições ##
* O utilizador que acede ao sistema deve possuir a *role* de Gestor de chão de fábrica.
* A máquina que vai ser adicionado não deve existir no sistema.

## Pós-Condições ##
* Existirá mais uma máquina no sistema.

## ANÁLISE ##

* Máquinas que já existem no sistema não devem ser adicionados novamente.
* Máquinas só podem ser adicionadas ao sistema se não tiverem parâmetros vazios ou inválidos.

## REGRAS DE NEGÓCIO ##

* Uma máquina caracteriza-se por um número de série único, um fabricante, um modelo, um ano de instalação, um ficheiro de configuração e o tipo de operação que irá realizar.
* O número de série é único.
* A data de instalação de uma máquina não pode ser posterior à data atual do sistema.