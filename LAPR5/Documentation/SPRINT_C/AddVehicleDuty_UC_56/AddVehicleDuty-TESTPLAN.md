# Use Case 56 - Add Vehicle Service #

## TEST PLAN ##

* testAddVehicleDutyToVehicleTypeNotRegistedTrip
* testAddVehicleInvalidVehicleDutyCode


### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Vehicle Duty to a not registed Trip ####
1. Send a POST to the Vehicle Duty route with an not registed trip identification.
2. The request must be answered with an error mentioning invalid Line.

#### CASE #2 : Add Vehicle Duty with invalid Code ####
1. Send a POST to the Vehicle Duty route with an invalid code.
2. The request must be answered with an error mentioning invalid code.

