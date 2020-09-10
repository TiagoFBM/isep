# UC 2009 - Import Production Orders #

## **PLANO DE TESTES** ##

### PLANO DE TESTES UNITÁRIOS ###
Visto que o domínio a testar é o mesmo a ser considerada na especificação do [plano de testes de UC 2010 - Add Production Orders](link), o plano de testes será igual.

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Importar Ordens de Produção Sem Erro ####
1. Login com prodman/prodmanA1.
2. Selecionar a opção Importar Ordens de Produção.
3. Introduzir o nome de ficheiro para importação: Ordens.csv.
4. Introduzir o nome de ficheiro para armazenamento de erros: Erros.
5. O sistema deve apresentar uma mensagem de operação realizada com sucesso.

#### CASO #2 : Importar Ordens de Produção Repetidas ####
1. Login com prodman/prodmanA1.
2. Selecionar a opção Importar Ordens de Produção.
3. Introduzir o nome de ficheiro para importação: Ordens.csv.
4. Introduzir o nome de ficheiro para armazenamento de erros: Erros.
5. O Sistema deve questionar o utilizador se pretende atualizar as ordens de produção ou mantê-las.

#### CASO #3 : Importar Ordens de Produção com Produtos inexistentes ####
1. Login com prodman/prodmanA1.
2. Selecionar a opção Importar Ordens de Produção.
3. Introduzir o nome de ficheiro para importação: OrdensProdutoInexistente.csv.
4. Introduzir o nome de ficheiro para armazenamento de erros: Erros.
5. O Sistema deve apresentar uma mensagem de erro sugestiva e voltar para o menu inicial.

#### CASO #4 : Importar Ordens de Produção com Código de Ordem repetido - 1 ####
1. Login com prodman/prodmanA1.
2. Selecionar a opção Importar Ordens de Produção.
3. Introduzir o nome de ficheiro para importação: OrdensCodigoOrdemRepetido.csv.
4. Introduzir o nome de ficheiro para armazenamento de erros: Erros.
5. O Sistema deve apresentar opções ao utilizador para atualizar a ordem de produção existente com os novos dados ou descartar os dados introduzidos.
6. Selecionar a opção que permite atualizar a ordem de produção já existente.
7. O sistema deve apresentar uma mensagem de operação executada com sucesso.

#### CASO #5 : Importar Ordens de Produção com Código de Ordem repetido - 2 ####
1. Login com prodman/prodmanA1.
2. Selecionar a opção Importar Ordens de Produção.
3. Introduzir o nome de ficheiro para importação: OrdensCodigoOrdemRepetido.csv.
4. Introduzir o nome de ficheiro para armazenamento de erros: Erros.
5. O Sistema deve apresentar opções ao utilizador para atualizar a ordem de produção existente com os novos dados ou descartar os dados introduzidos.
6. Selecionar a opção que permite manter a ordem de produção já existente.
7. O sistema deve apresentar uma mensagem de operação executada com sucesso.

#### CASO #6 : Importar Ordens de Produção com Erros no Ficheiro ####
1. Login com prodman/prodmanA1.
2. Selecionar a opção Importar Ordens de Produção.
3. Introduzir o nome de ficheiro para importação: OrdensComErros.csv.
4. Introduzir o nome de ficheiro para armazenamento de erros: Erros.
5. O Sistema deve apresentar uma mensagem de erro sugestiva e a ordem de produção com o erro deve ser escrita no ficheiro de erros.