# UC 2 - Add Node #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **Node**
* **Node**
* NodeID
* NodeName
* NodeShortName
* NodeCoordinates
    * Latitude
    * Longitude
* isDepot
* isReliefPoint
* CrewTravelTime
    * Key
    * Node
    * Duration
  
### DTO ###
* INodeDTO

### Schema ###
* NodeSchema

### Mappers ###
* NodeMapper

### Repository ###
* INodeRepository
* NodeRepository

### Services ###
* INodeService
* NodeService

### Controllers ###
* AddNodesController

### Routes ###
* NodeRoutes
    * POST ```/api/node```