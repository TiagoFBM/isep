# UC 2009 - Import Production Orders #

## ANÁLISE ##
Além da especificação acima deve de ser considerada a especificação da [análise de UC 2010 - Add Production Orders](link) visto que o processo de adição da ordem de produção (depois de tratar o ficheiro de importação) é semelhante.

* O sistema deve estar preparado  para possíveis alterações no tipo de ficheiros a utilizar.
* Caso o ID do produto seja desconhecido, a ordem de produção a ele associada deve ser ignorada e o sistema deve colocar essa informação num ficheiro de erros.
* No final da importação utilizador deve receber um relatório onde consta a quantidade de ordens de produção 
adicionadas e a quantidade de ordens de produção com erros.

### REGRAS DE NEGÓCIO ###
* O ficheiro utilizado para importar produtos será do tipo CSV.
* Para cada linha de produção o ficheiro possui o ID do produto a produzir, o ID da encomenda relativa a esse produto, a quantidade de produto a produzir, a data de emissão da ordem de produção e a data prevista de produção.
* Todas as datas introduzidas devem ser válidas (não podem existir datas no passado).
* Os códigos de identificação são do tipo alfanumérico.
* Uma ordem de produção é executada numa única linha de produção.
* Caso o ficheiro contenha linhas com um número errado de parâmetros, estas devem ser escritas num ficheiro de erros e as restantes ordens de produção devem de ser adicionadas ao sistema.
* Todas as Ordens de Produção que possuam parâmetros inválidos ou número incorreto de parâmetros devem ser enviados para um ficheiro de erros (do mesmo tipo do ficheiro de importação).