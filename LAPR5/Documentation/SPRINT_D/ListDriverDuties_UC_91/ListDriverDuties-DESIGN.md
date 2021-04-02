# UC 91 - List Driver Duties

## DESIGN ##

In the implementation of this use case the [GET diagram](../GET.png) was taken into account.

### Domain ###

* **WorkBlock**
* **DriverDuty**
  
### DTO ###
* DriverDutyDTO
* WorkBlockDTO

### Mappers ###
* WorkBlockMapper
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
* DriverDutyRoute
    * GET ```/api/driverDutys```