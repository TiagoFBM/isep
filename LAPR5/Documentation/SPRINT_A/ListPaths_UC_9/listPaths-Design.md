# UC 9 - List Paths #

## DESIGN ##

In this implementation the [GET diagram](../GET.jpg) was taken in account.

## DOMAIN ##

* Path
* PathCode
* PathSegment
    * PathSegmentDistance
    * PathSegmentTravelTime
* Line
* LineCode

## DTO ##

* IPathDTO
* ILinePathDTO

## Schema ##

* LineSchema

## Mappers ##

* LineMapper
* PathMapper

## Repository ##

* ILineRepository
* LineRepository

## Services ##

* ILineService
* LineService

## Controllers ##

* ILineController
* LineController

## Routes ##

* LineRoute
    * GET /api/path
