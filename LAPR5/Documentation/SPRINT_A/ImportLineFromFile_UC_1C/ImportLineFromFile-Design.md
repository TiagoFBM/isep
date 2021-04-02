# UC 1C - ImportLineFromFile #

# Design #

In the implementation of this use case the POST diagram was taken into account.

## Domain ##

* Line
* LineCode
* LineDescription

## DTO ##

* ILineDTO

## Schema ##

* LineSchema

## Mappers ##

* LineMapper

## Repository ##

* ILineRepository
* LineRepository

## Services ##

* ILineService
* LineService

## Controllers ##

* AddLinesController

## Routes ##

* LineRoutes
    * POST /api/line