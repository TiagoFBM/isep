# Use Case 49 - Add Work blocks #

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.
* There must exist a vehicle duty in the system

## Post Conditions ##
* Work blocks will be added to a vehicle duty.

## Analysis ##
* To create the work blocks, the user needs to select the vehicle duty and choose how many workblocks he wants to create and the maximum duration of each workblock.
* A work block is characterized by a starting and an ending time, as well as the trips it will affect.
* The work block duration cannot be greater than the default maximum value, normally 4 hours.

* The Trip only can be added to the system if its parameters meet the requirements.
* The departure time must be a valid time - from 00:00 to 23:59.
* When Trips are created with the second method the depart time for the return path must be estimated by the travel time between nodes of each segment.
* There should be no trips that start at the same time for a given line/path.
* A trip is characterized by the time of passage at each of the nodes on the path of the line.

## Business Rules ##
* (1) A Trip is created for a given line along with the departure time and the path.
* (2) A Trip can also be created for a given line with the the departure time, frequency, number of trips, the outward and the return path.