# Use Case 71 - Add Driver Duty #

## TEST PLAN ##

* testAddDriverDutyWithInvalidCode.
* testAddDriverDutyWithInvalidWorkBlocks.

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Driver Duty With Invalid Code ####

1. Send a POST to the Driver Duty route with invalid code.
2. The request must be answered with an error mentioning invalid Code.

#### CASE #2 : Add Driver Duty With Invalid Workblocks ####

1. Send a POST to the Driver Duty route with the workblock list empty.
2. The request must be answered with an error mentioning invalid workblock list.
