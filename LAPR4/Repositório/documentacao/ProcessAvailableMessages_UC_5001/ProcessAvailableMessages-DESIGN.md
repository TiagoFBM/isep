# UC 5001 - Process Available Messages

## DESIGN

Utilizar a estrutura base standard da aplicação baseada em camadas.

### CLASSES DO DOMÍNIO

- Message
- ActivityEndMessage
- ActivityResumptionMessage
- ActivityStartMessage
- ChargebackMessage
- ConsumptionMessage
- ForcedStopMessage
- ProductionDeliveryMessage
- ProductionMessage

### CONTROLADOR

- ProcessAvailableMessagesController
- MessageHandlerService

### REPOSITÓRIOS

- ProductionLineRepository
- MessageRepository

### DIAGRAMA DE SEQUÊNCIA

![SD_ProcessAvailableMessages.svg](SD_ProcessAvailableMessages.svg)
