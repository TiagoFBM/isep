# Use Case 47 - Add Vehicle #

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.
* The MDR must be active and have at least one vehicle type.

## Post Conditions ##
* A new Vehicle will be added to the system.

## Analysis ##
* The Vehicle only can be added to the system if its parameters meet the requirements.
* The Vehicle Identification Number (VIN) must be unique.
* At the end of the process, the user should received a message informing about the success of the operation.

## Business Rules ##

* A Vehicle is created for a given vehicle type.
* A vehicle is characterized by the registration, VIN, is type and the date of entry into service.
