# Use Case 49 - Add Trip #

## DESIGN ##

In the implementation of this use case the [POST diagram](../..//SPRINT_A/POST.png) was taken into account.

### Domain ###
* **Trip**
* TripDepartureTime
* NodePassage

### DTO ###
* ITripDTO
* INodePassageDTO
* TripDepartureTimeDTO

### Repository ###
* ITripRepository
* TripRepository

### Services ###
* ITripService
* TripService

### Controllers ###
* TripController

### Routes ###
* TripRoutes
    * POST ```/api/trip```