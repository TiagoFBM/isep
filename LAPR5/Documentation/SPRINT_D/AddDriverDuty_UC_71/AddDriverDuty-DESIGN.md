# Use Case 71 - Add Driver Duty #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **Vehicle Duty**
* DriverDutyCode
* Workblock


### DTO ###
* IDriverDutyDTO

### Mappers ###
* DriverDutyMapper

### Repository ###
* IDriverDutyRepository
* DriverDutyRepository

### Services ###
* IDriverDutyService
* DriverDutyService

### Controllers ###
* AddDriverDutyController

### Routes ###
* DriverDutyRoutes
    * POST ```/api/driverDuty```