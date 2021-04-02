# Use Case 48 - Add Driver #

## Pre-Conditions ##

* The user who accesses the system must have the role of Data Administrator.
* The MDR must be active and  have at least one driver type.

## Post Conditions ##

* A new Driver will be added to the system.

## Analysis ##

* The driver is defined by an mechanographic number, a name, the date of birth, citizen card number, nif, entrance and departure day in the company, the number of the license card and her expiration date.
* The driver must had at least one driver type.

## Business Rules ##

* The mechanographic number is alphanumeric code with 9 characters.
* The entrance day in the company must be greather than the departure day.
* The citizen card number must be valid (8 characters).
* The citizen card NIF must be valid (9 characters).
* The driver license number must be in format P-0000000 0, for example: P-1234567 8.
* The driver must be at least 18 years old.

