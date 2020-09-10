### UC 3007 - Query Archived Errors ###

#### TEST PLAN ####

#### CASO #1 : Consultar as notificações de erros de processamento arquivadas com filtro temporal com sucesso.####
  
 1. Aceder ao sistema como gestor de chão de fábrica.
 2. Selecionar a opção de consultar notificações de erros de processamento arquivadas com o filtro temporal.
 3. Introduz data de início e data de fim.
 4. As datas são validadas (data de início é inferior à data de fim).
 5. São listadas todas as notificações de erros arquivadas entre essas 2 datas.
 
#### CASO #2 : Consultar as notificações de erros de processamento arquivadas com filtro temporal sem sucesso.####
  
 1. Aceder ao sistema como gestor de chão de fábrica.
 2. Selecionar a opção de consultar notificações de erros de processamento arquivadas com o filtro temporal.
 3. Introduz data de início e data de fim.
 4. As datas são introduzidas de forma incorreta (data de início é superior à data de fim).
 5. É enviada uma mensagem a informar que as datas foram mal introduzidas.
 
#### CASO #3 : Consultar notificações inexistentes com filtro temporal.####  

1. Aceder ao sistema como gestor de chão de fábrica.
2. Selecionar a opção de consultar notificações de erros de processamento arquivadas com o filtro temporal.
3. Introduz data de início e data de fim.
4. As datas são validadas (data de início é inferior à data de fim).
5. É enviada uma mensagem a informar que não existem notificações de erros de processamento arquivadas naquele filtro temporal.
 
#### CASO #4 : Consultar as notificações de erros de processamento arquivadas com filtro por tipo de erro com sucesso.####
  
 1. Aceder ao sistema como gestor de chão de fábrica.
 2. Selecionar a opção de consultar notificações de erros de processamento arquivadas com o filtro por tipo de erro.
 3. São listadas todas as notificações de erros arquivadas segundo o tipo de erro selecionado. 
  
#### CASO #5 : Consultar notificações inexistentes por tipo de erro.####
  
 1. Aceder ao sistema como gestor de chão de fábrica.
 2. Selecionar a opção de consultar notificações de erros de processamento arquivadas com o filtro por tipo de erro.
 3. É enviada uma mensagem a informar que não existem notificações de erros de processamento arquivadas naquele filtro. 