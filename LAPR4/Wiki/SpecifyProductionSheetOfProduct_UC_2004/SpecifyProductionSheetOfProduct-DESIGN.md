# UC 2004 - Specify Production Sheet of Product

## DESIGN ##

Utilizar a estrutura base standard da aplicação baseada em camadas.

### CLASSES DO DOMÍNIO ###
* É identificada e atualizada uma instância de **Product**.
* É efetuada a criação de **ProductionSheetOfProduct** a partir de **Product** (*Information Expert*).
* A partir de **ProductionSheetOfProduct** são criadas várias instâncias de **LineProductionSheetOfProduct** que podem referenciar **Product** ou **RawMaterial** dependendo da escolha do utilizador.

### CLASSES DTO ###
* **DTOLineProductionSheetOfProduct** é utilizada como estrutura auxiliar para passar a informação selecionada na UI até a **ProductionSheetOfProduct** onde de facto as linhas podem ser criadas (*Information Expert*).

### CLASSES DE SERVIÇOS ###
* Utiliza-se o serviço **ListRawMaterialService** para obter uma lista de matérias-primas registadas no sistema.
* O serviço **ListProductService** é utilizado para obter uma lista de produtos registados no sistema.

### CONTROLADOR ###
* SpecifyProductionSheetOfProductController

### REPOSITÓRIOS ###
* Utiliza-se **ProductRepository** para identificar **Product** selecionado.
* **RawMaterialRepository** é utilizado para obter uma lista de todas as matérias-primas.

### DIAGRAMA DE SEQUÊNCIA ###

![SD_SpecifyProductionSheetofProduct.png](SD_SpecifyProductionSheetofProduct.png)