# UC 2010 - Add New Production Order #

## PLANO DE TESTES ##

## TESTES UNITÁRIO ##

* testCreateProductionOrderWithoutCode
* testCreateProductionOrderWithoutIssueDate
* testCreateProductionOrderWithoutForecastDate
* testCreateProductionOrderWithoutOrderCode
* testCreateProductionOrderWithoutProduct
* testCreateProductionOrderWithoutQuantity
* testCreateProductionOrderWithoutUnit
* testCreateProductionOrderWithInvalidCode
* testCreateProductionOrderWithoutInvalidOrderIDCode

## TESTES FUNCIONAIS ##

#### CASO #1 : Adicionar ordem de produção ####

1. Login com fabman/fabmanA1.
2. Selecionar a opção Adicionar a ordem de produção.
3. Escolher um dos produtos apresentados.
4. Inserir o código OP100.
5. Inserir data de emissão 01-01-2020.
6. Inserir data de previsão 06-06-2020.
7. Inserir uma quantidade de 100.
8. Inserir a unidade KILOGRAMS.
9. Inserir uma encomenda: EC1.
10. Inserir uma encomenda: EC2.
11. O sistema deve apresentar uma mensagem com o sucesso da operação.

#### CASO #2 : Adicionar ordem de produção sem o produto ####

1. Login com fabman/fabmanA1.
2. Selecionar a opção Adicionar ordem de produção.
3. Não escolher nennhum dos produtos presentes.
4. O sistema termina o caso de uso avisando o utilizador que não conseguirá proceder com a ação se não selecionar nenhum produto.

#### CASO #3 : Adicionar ordem de produção sem produtos criados ####

1. Login com fabman/fabmanA1.
2. Selecionar a opção Adicionar ordem de produção.
3. O sistema informa que não existem produtos disponiveís levando à conclusão da funcionalidade.
