# UC 3003 - Specify New Deposit #

## Pre-Condições ##
* O utilizador que acede ao sistema deve possuir a *role* de Gestor de chão de fábrica.
* O depósito que está a ser criado não pode existir no sistema.

## Pós-Condições ##

* Passa a existir mais um depósito no sistema que poderá vir a ser associado a um movimento.

## ANÁLISE ##

* Depósitos só podem ser adicionados ao sistema se não tiverem parâmetros vazios ou inválidos.
* Produtos que já existem no sistema não devem ser adicionados novamente.

## REGRAS DE NEGÓCIO ##

*O id do depósito é único.
*Depósitos guardam produtos/matérias-primas.
