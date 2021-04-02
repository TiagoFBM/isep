# Use Case 56 - Add Vehicle Duty #

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.
* The MDV must be active and have at least one trip.

## Post Conditions ##
* A new Vehicle Duty will be defined into the system.

## Analysis ##
* The Vehicle Duty only can be added to the system if its parameters meet the requirements.
* At the end of the process, the user should received a message informing about the success of the operation.

## Business Rules ##

* A vehicle duty will be characterized by an alphanumeric code whit 10 characters.
* A vehicle duty will have a list of trips.
* A vehicle must perform a maximum of 16 hours of daily service.
* A vehicle must at most remain at a collection station 48 hours.
* A vehicle starts and ends its service at a collection station.

