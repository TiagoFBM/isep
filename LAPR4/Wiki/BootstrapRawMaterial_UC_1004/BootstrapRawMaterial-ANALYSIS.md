### UC 1004 - BOOTSTRAP RAW MATERIAL ###

## Pré-condições ##

* É necessário que existam categorias de matérias-primas.

## Pós-conditições ##

* Existe mais uma matéria-prima que poderá ser utilizada para produção de produtos.

## Análise ##

* Uma matéria-prima caracteriza-se essencialmente por um código interno, uma descrição, uma categoria e uma ficha técnica.
* Uma matéria-prima é sempre de uma categoria.
* Uma matéria-prima é utilizada para a produção de certos produtos.
* Uma matéria-prima corresponde a um material e/ou produto usado no processo de fabrico de um ou mais produtos.

## Regras de negócio ##

* O código interno de uma matéria-prima tem que ser único no sistema.
* O código interno é alfanumérico (15 carateres no máximo) e é introduzido pelo utilizador
* São necessários todos os atributos já descritos em cima para o registo de uma matéria-prima.
* A ficha técnica é indicada pelo utilizador (nome e path) e o sistema deve guardar o ficheiro de forma a, se for necessário, posteriormente indicar qual é e mostra-lo ao utilizador.

## Meetings com o cliente ##

* Foram adotadas algumas medidas de acordo com o projeto base que foi disponibilizado. Sendo assim, para a inicialização de matérias-primas, a respetiva categoria é identificada pelo ID e caso a opção fosse válida, o programa segue. Caso contrário, é lançada uma exceção.
