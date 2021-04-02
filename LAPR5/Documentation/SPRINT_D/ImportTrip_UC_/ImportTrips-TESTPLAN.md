# Use Case 72 - Import Trips #

## TEST PLAN ##

* testAddTripsWithInvalidFile
* testAddTripsWithValidFile

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Trips With Invalid File ####
1. Send a POST to the import trip route with a file with an invalid trip.
2. The request must be answered with an error mentioning invalid file.

#### CASE #1 : Add Trips With Valid File ####
1. Send a POST to the import trip route with a file with a valid file.
2. The trips must be added sucessfuly to the system.