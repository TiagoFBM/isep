# UC 1B - Import Paths #

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
* LineRoutes
    * POST ```/api/import```