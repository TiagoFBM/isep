# Use Case 49 - Add Trip #

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.
* The MDR must be active and have at least one line with outward and return path.

## Post Conditions ##
* A new Trip will be added to the system.

## Analysis ##
* The Trip only can be added to the system if its parameters meet the requirements.
* The departure time must be a valid time - from 00:00 to 23:59.
* When Trips are created with the second method the depart time for the return path must be estimated by the travel time between nodes of each segment.
* There should be no trips that start at the same time for a given line/path.
* A trip is characterized by the time of passage at each of the nodes on the path of the line.

## Business Rules ##
* (1) A Trip is created for a given line along with the departure time and the path.
* (2) A Trip can also be created for a given line with the the departure time, frequency, number of trips, the outward and the return path.