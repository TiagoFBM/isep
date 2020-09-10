# UC 3003 - Specify New Deposit #

## DESIGN ##

Utilizar a estrutura base standard da aplicação baseada em camadas

### CLASSES DO DOMÍNIO ###

* É efetuada a criação de **Deposit** através da classe *DepositRepository*
* É efetuada a criação e persistência de objetos da classe *Deposit*.

### CONTROLADOR ###
* SpecifyNewDepositController

### REPOSITÓRIOS ###
* Utiliza-se o *DepositRepository* para persistir o objeto criado.


### DIAGRAMA DE SEQUÊNCIA ###
![SD-SpecifyNewDepositSD.png](SD-SpecifyNewDepositSD.jpg)