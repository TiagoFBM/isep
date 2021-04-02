# Use Case 47 - Add Vehicle #

## TEST PLAN ##

* testAddVehicleToVehicleTypeNotRegisted
* testAddVehicleInvalidVehicleRegistration
* testAddVehicleInvalidVehicleVIN
* testAddVehicleInvalidVehicleDate

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Vehicle to a not registed Vehicle Type ####
1. Send a POST to the Vehicle route with an not registed vehicle type identification.
2. The request must be answered with an error mentioning invalid Line.

#### CASE #2 : Add Vehicle with invalid Registration ####
1. Send a POST to the Vehicle route with an invalid registration.
2. The request must be answered with an error mentioning invalid registration.

#### CASE #3 : Add Vehicle with invalid VIN ####
1. Send a POST to the Vehicle route with an invalid VIN.
2. The request must be answered with an error mentioning invalid VIN.

#### CASE #4 : Add Vehicle with invalid Date ####
1. Send a POST to the Vehicle route with an invalid date.
2. The request must be answered with an error mentioning invalid date.