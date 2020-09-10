# UC 2002 - Add New Raw Material Category #

## Pre-Condições ##

* O utilizador que acede ao sistema deve possuir a *role* de Gestor de Produção.
* A categoria que está a ser criada não pode existir no sistema.
  
## Pós-Condições ##

* Passa a existir mais uma categoria no sistema que poderá vir a ser associada a uma matéria prima.

## ANÁLISE ##

* Categorias que já existem no sistema não devem ser adicionadas novamente.
* Pode vir a ser necessário inquirir o utilizador em relação à criação de uma categoria à matéria prima que está a ser registada (caso a matéria prima ainda não exista no sistema).


## REGRAS DE NEGÓCIO ##

* Uma categoria relativa a uma matéria prima caracteriza-se por um id e por uma descrição breve.
* A descrição de uma categoria tem no máximo 30 e no mínimo 3 caracteres, não pode ter números e é constituída por apenas 1 palavra.
* O id da categoria pode conter números e letras. Tem no máximo 10 e no mínimo 2 caracteres.