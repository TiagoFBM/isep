# Use Case 71 - Add Driver Duty #

## Pre-Conditions ##

* The user who accesses the system must have the role of Data Administrator.
* The MDV must be active and have at least one workblock.

## Post Conditions ##

* A new Driver Duty will be defined into the system.

## Analysis ##

* The Driver Duty only can be added to the system if its parameters meet the requirements.
* At the end of the process, the user should received a message informing about the success of the operation.
* The Driver Duty is defined by a code, a name, a color and a list of workblocks

## Business Rules ##

* A driver duty will be characterized by an alphanumeric code with 10 characters.
* A driver duty will have a list of workblocks
* A driver must perform a maximum of 8 hours of daily service - there should be a break after 4 hours.
* The duration of the service corresponds to the duration of the associated blocks (which must be contiguous in each period).
* A driver can't work more than 4 hours straigth.
