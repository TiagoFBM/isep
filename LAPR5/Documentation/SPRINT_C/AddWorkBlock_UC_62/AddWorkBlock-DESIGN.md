# Use Case 62 - Add Work Block #

## DESIGN ##

In the implementation of this use case the [POST diagram](../..//SPRINT_A/POST.png) was taken into account.

### Domain ###
* **Trip**
* WorkBlock

### DTO ###
* WorkBlockDTO

### Repository ###
* IWorkBlock
* WorkBlockRepository

### Services ###
* IVehicleDutyService
* VehicleDutyService

### Controllers ###
* VehicleDutyController

### Routes ###
* VehicleDutyRoutes
    * POST ```/api/trip```