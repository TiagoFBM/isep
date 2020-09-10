# UC 2007 - Export Information To XML #

## Pre-Condições ##

* O utilizador que acede ao sistema deve possuir a *role* de Gestor de Produção.
* Deve existir no sistema todos os objetos que o utilizador pretende exportar para o ficheiro XML.

## Pós-Condições ##

* Irá ser exportado para um ficheiro XML toda a informação requerida pelo utilizador.

## ANÁLISE ##

* O utilizador irá selecionar de uma lista os dados a ser importados para o ficheiro XML.
* Não será permitido selecionar 2 vezes a mesma opção da lista.
  
## REGRAS DE NEGÓCIO ##

* Deve ser possível aplicar filtros temporais sobre os dados a exportar(e.g. ordens de produção, consumos, desvios). 
* O sistema deve ainda perguntar que informação e (tipos de) resultados (e.g. desvios, tempos) devem ser considerados.
* A exportação deve ser validada pelo XSD.