# UC 5 - Add Driver Type #

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.
* The Driver Type to be added must not exist in the system.

## Post Conditions ##
* A new Driver Type will be added to the system.

## Analysis ##
* Already existing Driver types must not be added to the system.
* The Driver Type only can be added to the system if its parameters meet the requirements.
* The maximum size of the code is 20 characters.
* The maximum size of the description is 250 characters.

## Business Rules ##
* A Driver Type is represented by a free (not cataloged) description of characteristics.
* Driver Type is characterized by an unique code and a description, both alphanumeric and mandatory.