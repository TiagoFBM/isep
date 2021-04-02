# UC 8 - List Lines #

## DESIGN ##

In the implementation of this use case the [GET diagram](../GET.png) was taken into account.

### Domain ###
* **Line**
* LineCode
* LineDescription
* **LinePath**
* LinePathCode
* LinePathOrientation
* **VehicleType**
* **DriverType**
* LineColor
  
### DTO ###
* ILineDTO
* ILinePathDTO

### Schema ###
* LineSchema
* LinePathSchema

### Mappers ###
* LineMapper
* LinePathMapper

### Repository ###
* ILineRepository
* LineRepository

### Services ###
* ILineService
* LineService

### Controllers ###
* AddLinesController

### Routes ###
* NodeRoutes
    * GET ```/api/line```