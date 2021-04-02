# Use Case 48 - Add Driver #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###

* **Driver**
* DriverId
* MechanographicNumber
* EntranceDate
* DepartureDate

* **CitizenCard**
* CitizenCardId
* Name
* CitizenCardNumber
* BirthDate
* DriverNif

* **DriverLicense**
* DriverLicenseId
* DriverLicenseDate
* NumberDriverLicense

* **DriverType**

### DTO ###

* DriverDTO
* CreatingDriverDTO
* CitizenCardDTO
* CreatingCitizenCardDTO
* DriverLicenseDTO
* CreatingDriverLicenseDTO
* DriverTypeDTO

### Mappers ###

* DriverMapper

### Repository ###

* IDriverRepository
* DriverRepository

### Services ###

* DriverTypeService
* DriverService
* HttpRequest

### Controllers ###

* AddDriverController

### Routes ###

* DriverRoutes
* POST ```/driver```
