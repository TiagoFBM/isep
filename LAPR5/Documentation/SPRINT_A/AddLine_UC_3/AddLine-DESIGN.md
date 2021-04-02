# UC 3 - Add Line #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

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
* LineRoutes
    * POST ```/api/line```