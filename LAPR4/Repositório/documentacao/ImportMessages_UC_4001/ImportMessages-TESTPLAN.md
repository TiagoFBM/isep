# UC 4001 - Import Messages #

## **PLANO DE TESTES** ##

### PLANO DE TESTES UNITÁRIOS ###
* createMessageWithNullParameters
* createMessageWithEmptyParameters

### PLANO DE TESTES FUNCIONAIS ###

#### CASO #1 : Importar um ficheiro de Menssagens com Sucesso ####
1. Executar o Sistema de Comunicação com as Máquinas.
2. Visto que apenas um ficheiro de menssagens está no diretório, esse deve ser importado.

#### CASO #2 : Importar vários ficheiros de Menssagens com Sucesso ####
1. Executar o Sistema de Comunicação com as Máquinas.
2. Todos os ficheiros de menssagens do diretório definido devem ser importadas sem erro.

#### CASO #3 : Diretório de Menssagens Vazio ####
1. Executar o Sistema de Comunicação com as Máquinas.
2. Deve aparecer uma mensage a notificar o utilizador de que o Deamon ficou ativo.

#### CASO #3 : Diretório de Menssagens Vazio e Adicionar Mensagens depois da Execução ####
1. Executar o Sistema de Comunicação com as Máquinas.
2. Deve aparecer uma mensagem a notificar o utilizador de que o Deamon ficou ativo.
3. Adicionar um ficheiro de mensagens ao diretório.
4. Deve ser mostrada uma mensagem de reconhecimento de mensagem.