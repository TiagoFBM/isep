# UC 6 - Create Vehicle Type #

As a Data Administrator, I want to create a vehicle type.

## Pre-Requisites ##

- The user who accesses the system must have the role of Data Administrator.
- At least, one Fuel Type must be registered in the system.

## Post-Requisites ##

- A new vehicle type is registered in the system.

## Analyze ##

- The vehicle type is defined by a code, a description, the fuel type, an autonomy, the cost per kilometer, the average consuption and the average speed.
- At the end of the process, the user should received a message informing about the success of the operation.
- If the vehicle type already exists in the system, it will not be registered.

## Business Rules ##

- The vehicle type is featured by an alphanumeric code (20 character) and a alphanumeric description (max. 250 characters).
- The fuel types are fixed and only those indicated in the specifications.
- The autonomy and the average speed are positive numbers.
- Cost per kilometer is a non-negative currency (we can always assume in Euro but also can leave a possibility of different currencies open).
- Average consumption is number with 3 decimal places whose unit will depend on the type of fuel.
