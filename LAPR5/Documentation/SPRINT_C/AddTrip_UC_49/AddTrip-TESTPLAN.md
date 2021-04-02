# Use Case 49 - Add Trip #

## TEST PLAN ##

* testAddTripToLineNotRegisted
* testAddTripToLineWithoutPaths
* testAddTripInvalidDepartTime

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Trip to a not registed Line ####
1. Send a POST to the Trip route with an not registed line identification.
2. The request must be answered with an error mentioning invalid Line.

#### CASE #2 : Add Trip to a Line without Paths ####
1. Send a POST to the Trip route with a Line without Paths.
2. The request must be answered with an error mentioning invalid Line.

#### CASE #3 : Add Trip to a Line with invalid Depart Time ####
1. Send a POST to the Trip route with an invalid depart time.
2. The request must be answered with an error mentioning invalid depart time.