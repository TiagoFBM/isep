# Use Case 73 - Import Vehicle Duty #

## TEST PLAN ##

* testAddVehicleDutiesWithInvalidFile
* testAddVehicleDutiesWithValidFile

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Vehicle Duties With Invalid File ####
1. Send a POST to the import vehicle duty route with a file with an invalid vehicle duty.
2. The request must be answered with an error mentioning invalid file.

#### CASE #2 : Add Vehicle Duties With Valid File ####
1. Send a POST to the import vehicle duty route with a file with a valid file.
2. The vehicle duties must be added sucessfuly to the system.