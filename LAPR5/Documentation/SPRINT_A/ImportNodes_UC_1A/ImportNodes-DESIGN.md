# UC 1 A - Import Nodes #

## DESIGN ##

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

### Domain ###
* **Node**
* NodeID
* NodeName
* NodeShortName
* NodeCoordinates
    * Latitude
    * Longitude

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
* ImportNodesController

### Routes ###
* NodeRoutes
    * POST ```/api/import```