# UC 2010 - Add New Production Order #

## Pre-Condições ##

* O utilizador que acede ao sistema deve possuir a *role* de Gestor de Produção.
* A ordem de produção deve fazer referência a um produto existente no sistema, preferencialmente que já tenha ficha de produção.

## Pós-Condições ##

* Existirá uma nova ordem de produção.

## ANÁLISE ##

* Documento em que se autoriza/solicita a produção de um produto numa determinada quantidade (a pretendida) através de um conjunto de matérias-primas e respetivas quantidades (de referência).
* Uma ordem de produção é constituída por um código, uma data de emissão, uma data de previsão, um identificador do produto, uma quantidade e a respetiva unidade do produto e o id das encomendas.
* Por omissão, uma ordem de produção assume o estado de “pendente”
* Uma ordem de produção posse assumir outros estados:
    1. “Em Execução” ou “Execução Parada Temporariamente” ou “Concluída”. Estes estados são atribuídos automaticamente com base em informação de mensagens enviadas pelas máquinas
    2. “Suspensa”. Atribuído manualmente por um utilizador
* Caso não existam produtos adicionados na base de dados, não será possível a criação de uma ordem de produção.

## REGRAS DE NEGÓCIO ##

* Os códigos presentes nas ordens de produção são alfanuméricos.
* O identificador do produto corresponde ao respetivo código de fabrico.
* Não deve ser necessário especificar a linha de produção, visto que esta será conhecida pelo processamento das mensagens enviadas pelas máquinas (numa dessas mensagens, uma máquina dirá que começou a operar no contexto de uma determinada ordem de produção).
* Ao introduzir manualmente uma ordem de produção:
    1. Se o produto não existe, então não deve permitir a criação da ordem da produção;
    2. Se o produto ainda não dispõe de uma Ficha de Produção, o sistema deve avisar o utilizador mas deve permitir criar
    ordem de produção.
* Caso o utilizador introduza um código de uma ordem de produção já existente, o sistema deve permite uma possível atualização.
