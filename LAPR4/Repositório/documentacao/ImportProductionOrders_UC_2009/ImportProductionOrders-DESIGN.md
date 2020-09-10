# UC 2009 - Import Production Orders #

## DESIGN ##

Utilizar a estrutura base standard da aplicação baseada em camadas.

Além da especificação abaixo deve de ser considerada a especificação do [design de UC 2010 - Add Production Orders](link) visto que o processo de adição de ordens de produção (depois de tratar o ficheiro de importação) é semelhante.

### CLASSES DA APLICAÇÃO ###
* **ErrorWritter** é reponsável por escrever para um ficheiro as ordens de produção que possuem erros de formatação.

### CLASSES DE SERVIÇOS ###
* **ProductionOrdersImporterFactory** é utilizada para criar um determinado importador de acordo com o tipo de ficheiro utilizado.
* **ProductionOrdersImporter** representa uma interface que é utilizada para estabelecer a ligação entre diversos importadores.
* **ProductionOrdersImporterCSV** é criado pela **ProductionOrdersImporterFactory** e utilizado pelo **ProductionOrdersImporterService** para importar o ficheiro.
* **ProductionOrdersImporterService** de acordo com o importador especificado trata o ficheiro e adiciona ao sistema caso a ordem de produção seja válida.

### CONTROLADOR ###
* ImportProductionOrderController

### PADRÕES ###
* Foi utilizado o padrão *Strategy* visto que pode ser necessário futuramente importar ordens de produção a partir de outro tipo de ficheiros.

### DIAGRAMA DE SEQUÊNCIA ###

![SD_ImportProductionOrders.png](SD_ImportProductionOrders.png)