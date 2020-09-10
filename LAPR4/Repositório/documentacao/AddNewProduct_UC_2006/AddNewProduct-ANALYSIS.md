# UC 2006 - Add New Product #

## Pre-Condições ##
* O utilizador que acede ao sistema deve possuir a *role* de Gestor de Produção.
* O Produto que vai ser adicionado não deve existir no sistema.
* O Sistema deve possuir um conjunto de unidades possíveis.

## Pós-Condições ##
* Existirá mais um produto no sistema.

## ANÁLISE ##
* Produtos que já existem no sistema não devem ser adicionados novamente.
* Produtos só podem ser adicionados ao sistema se não tiverem parâmetros vazios ou inválidos.
* Os Produtos são registados no sistema sem ficha de produção.
* Tanto o código de fabrico como o código comercial devem ter no máximo 15 caracteres.
* A descrição breve de um produto não deve ultrapassar os 30 caracteres.
* A descrição completa de um produto pode possuir até 70 caracteres.

## REGRAS DE NEGÓCIO ##
* Um Produto caracteriza-se por um código único de fabrico, um código comercial, uma descrição breve, uma descrição completa e uma categoria.
* O produto deve possuir uma unidade associada a ser considerada para operações futuras.
