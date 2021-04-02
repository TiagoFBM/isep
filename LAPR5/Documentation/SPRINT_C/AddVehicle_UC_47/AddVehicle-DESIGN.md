# Use Case 47 - Add Vehicle #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **Vehicle**
* VehicleRegistration
* VehicelVIN
* VehicleDate

### DTO ###
* IVehicleDTO

### Schema ###
* VehicleSchema

### Mappers ###
* VehicleMapper

### Repository ###
* IVehicleRepository
* VehicleRepository

### Services ###
* IVehicleService
* VehicleService

### Controllers ###
* AddVehicleController

### Routes ###
* VehicleRoutes
    * POST ```/api/vehicle```