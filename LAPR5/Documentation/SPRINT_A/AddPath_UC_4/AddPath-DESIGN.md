# UC 4 - Add Path #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **Path**
* PathCode
* PathSegment
    * PathSegmentDistance
    * PathSegmentTravelTime

### DTO ###
* IPathDTO

### Schema ###
* PathSchema

### Mappers ###
* PathMapper

### Repository ###
* IPathRepository
* PathRepository

### Services ###
* IPathService
* PathService

### Controllers ###
* AddPathController

### Routes ###
* PathRoutes
    * POST ```/api/path```