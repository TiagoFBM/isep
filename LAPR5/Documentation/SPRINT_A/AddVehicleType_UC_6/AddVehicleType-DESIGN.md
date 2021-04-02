# UC 6 - Create Vehicle Type #

# Design #

In the implementation of this use case the [POST diagram](../POST.png) was taken into account.

## Domain ##

* VehicleType
* VehicleTypeCode
* VehicleTypeDescription
* EnumFuelType
* VehicleTypeFuelType
* Autonomy
* CostPerKM
* AvgConsuption
* AvgSpeed

## DTO ##

* IVehicleTypeDTO

## Schema ##

* VehicleTypeSchema

## Mappers ##

* VehicleTypeMapper

## Repository ##

* IVehicleTypeRepository
* VehicleTypeRepository

## Services ##

* IVehicleTypeService
* VehicleTypeService

## Controllers ##

* AddVehicleTypeController

## Routes ##

* VehicleTypeRoutes
    * POST /api/VehicleType