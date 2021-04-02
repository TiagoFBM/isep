# UC 3 - Add Line #

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.
* At least two nodes must be registered in the system (terminal nodes).

## Post Conditions ##
* A new Line should be added to the system.

## Analysis ##
* By default there are no restrictions on the line.
* It must be possible to indicate which types of crew or vehicles are eligible (by their codes).
* The maximum size of the line code is 20 characters.
* The maximum size of the line description is 250 characters.
* The terminal nodes must be identified with their short name.
* At the time of the creation the line paths are empty.
* Each line has a descriptive color defined by RGB code.

## Business Rules ##
* A Line is created with a code, name, its terminal nodes and any restrictions on the type of vehicle and type of crew.
* The code must be mandatory, unique and alphanumeric with at least one character.
* The name is an alphanumeric string.

