# Use Case 56 - Add Vehicle Service #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **Vehicle Duty**
* VehicleDutyCode
* Trip


### DTO ###
* IVehicleDutyDTO

### Schema ###
* VehicleDutySchema

### Mappers ###
* VehicleDutyMapper

### Repository ###
* IVehicleDutyRepository
* VehicleDutyRepository

### Services ###
* IVehicleDutyService
* VehicleDutyService

### Controllers ###
* AddVehicleDutyController

### Routes ###
* VehicleDutyRoutes
    * POST ```/api/vehicleDuty```