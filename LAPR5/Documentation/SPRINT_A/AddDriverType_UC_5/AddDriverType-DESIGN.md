# UC 5 - Add Driver Type #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **DriverType**
* DriverTypeCode
* DriverTypeDescription

### DTO ###
* IDriverTypeDTO

### Schema ###
* DriverTypeSchema

### Mappers ###
* DriverTypeMapper

### Repository ###
* IDriverTypeRepository
* DriverTypeRepository

### Services ###
* IDriverTypeService
* DriverTypeService

### Controllers ###
* AddDriverTypesController

### Routes ###
* DriverTypeRoutes
    * POST ```/api/driverType```