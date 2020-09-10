### UC 2001 - ADD RAW MATERIAL ###

## Pré-Condições ##

* O utilizador que acede ao sistema deve possuir a *role* de Gestor de Produção.
* É necessário que existam categorias de matérias-primas.

## Pos-condições ##

* Existe mais uma matéria-prima que poderá ser utilizada para produção de produtos.

## Análise ##

* Uma matéria-prima caracteriza-se essencialmente por um código interno, uma descrição, uma categoria e uma ficha técnica.
* Uma matéria-prima é sempre de uma categoria.
* Uma matéria-prima é utilizada para a produção de certos produtos.
* Uma matéria-prima corresponde a um material e/ou produto usado no processo de fabrico de um ou mais produtos.
* Caso não existam categorias de matérias-primas, o utilizador será advertido e posteriormente redirecionado para a funcionalidade de adição de categorias de matérias-primas.

## Regras de negócio ##

* O código interno de uma matéria-prima tem que ser único no sistema.
* O código interno é alfanumérico (15 carateres no máximo) e é introduzido pelo utilizador
* São necessários todos os atributos já descritos em cima para o registo de uma matéria-prima.
* A ficha técnica de uma matéria-prima é um documento PDF.

## Meetings com o cliente ##

* Após reunião com o cliente, foi decidido que a melhor maneira de apresentar as categorias ao utilizador para que este pudesse escolher, seria a apresentação destas numa lista em que a escolha seria feita através do código da categoria selecionada.
* O caminho e o nome do ficheiro são digitados pelo utilizador. De seguida o contéudo do ficheiro deve ser armazenado na base de dados como campo binário.
* Para eventuais casos de uso futuros, foi adicionado um atributo à ficha técnica correspondente à sua extensão para facilitar a visualização do seu conteúdo.

## Padrões Utilizados ##

* Builder.

## Contexto de utilização ##
Este caso de uso será utilizado para a adição de matérias-primas na base de dados
