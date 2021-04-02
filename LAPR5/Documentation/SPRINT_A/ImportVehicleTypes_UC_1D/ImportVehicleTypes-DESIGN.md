# UC 1 D - Import Vehicle Types #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **Vehicle Type**
* VehicleID
* VehicleName
* VehicleAutonomy
* VehicleCost
* VehicleAverageCost
* VehicleEnergySource
* VehicleConsumption
* VehicleEmissions

### DTO ###
* IVehicleTypeDTO

### Schema ###
* VehicleTypeSchema

### Mappers ###
* VehicleTypeMapper

### Repository ###
* IVehicleTypeRepository
* VehicleTypeRepository

### Services ###
* IVehicleTypeService
* VehicleTypeService

### Controllers ###
* ImportVehicleTypeController

### Routes ###
* VehicleTypeRoutes
    * POST ```/api/import```