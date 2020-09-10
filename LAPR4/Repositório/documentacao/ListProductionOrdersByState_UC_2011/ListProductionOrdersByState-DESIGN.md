# UC 2011 - List Production Orders By State #

## DESIGN ##

* Utilizar a estrutura base standard da aplicação baseada em camadas

### SERVIÇOS ###
* **ListProductionOrdersPerStateService** -> Listagem de todas as ordens de produçao por estado.

### Classes de DOMÍNIO ###
* ProductionOrder

### REPOSITÓRIOS ###
* **ProductionOrderRepository** que está responsável pelas operações com base de dados relacionadas com o dominio de classes correspondente, criado pelo **FactoryRepository**.

### CONTROLADOR ###
* ListProductionOrderPerStateController

### DIAGRAMA DE SEQUÊNCIA ###
![SD_ListProductionOrdersByState.jpg](SD_ListProductionOrdersByState.jpg)