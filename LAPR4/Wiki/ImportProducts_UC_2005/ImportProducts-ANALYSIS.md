# UC 2005 - Import Products #

### ANÁLISE ###
* O sistema deve estar preparado  para possíveis alterações no tipo de ficheiros a utilizar.

## REGRAS DE NEGÓCIO ##
* O ficheiro utilizado para importar produtos será do tipo CSV.
* Caso o ficheiro contenha linhas com um número errado de parâmetros, estas devem ser escritas num ficheiro de erros e os restantes produtos devem de ser adicionadas ao sistema.
* Todos os Produtos que possuam parâmetros inválidos ou número incorreto de parâmetros devem ser enviados para um ficheiro de erros (do mesmo tipo do ficheiro de importação).
* No final da importação o utilizador deve receber um resumo da importação.
* Caso o Produto a ser importado já exista no sistema deve ser dada a opção ao utilizador de decidir se pretende ou não atualizar o Produto já existente com os novos dados.

* Além da especificação acima deve de ser considerada a especificação da [análise de UC 2006 - Add New Product](https://bitbucket.org/TiagoMoreiraISEP/lei_isep_2019_20_sem4_2di_1181011_1181012_1181047_1181061_1150/wiki/AddNewProduct_UC_2006/AddNewProduct-ANALYSIS.md) visto que o processo de adição de produtos (depois de tratar o ficheiro de importação) é semelhante.
