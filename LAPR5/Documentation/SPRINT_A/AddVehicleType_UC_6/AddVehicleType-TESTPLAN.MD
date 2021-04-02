# UC 6 - Create Vehicle Type #

# TEST PLAN #

* testAddVehicleTypeAlreadyRegisted
* testAddVehicleTypeWithCodeBiggerThan20
* testAddVehicleTypeWithDescriptionBiggerThan250
* testAddVehicleTypeWithNegativaAverageConsuption
* testAddVehicleTypeWithNegativaAverageSpeed
* testAddVehicleTypeWithNegativeCostPerKilometer
* testAddVehicleTypeWithWrongFuelType

# FUNCTIONAL TEST PLAN #

CASE #1 : Add Vehicle Type Without Error
Send a POST to the vehicle type route.
The vehicle type is successfully registered.

CASE #2 : Add Vehicle Type With a Code bigger than 20 characters.
Send a POST to the Vehicle Type route with a code bigger than 20 characters.
The request must be answered with an error related to the invalid code.

CASE #3 : Add Vehicle Type With a Description bigger than 250 characters.
Send a POST to the Vehicle Type route with a description bigger than 250 characters.
The request must be answered with an error related to the invalid description.

CASE #4 : Add Vehicle Type With a Negative Average Consuption.
Send a POST to the Vehicle Type route with a average consuption less than 0.
The request must be answered with an error related to the invalid average consuption.

CASE #4 : Add Vehicle Type With a Negative Average Speed.
Send a POST to the Vehicle Type route with a average speed less than 0.
The request must be answered with an error related to the invalid average speed.

CASE #6 : Add Vehicle Type With a Negative Cost Per Kilometer.
Send a POST to the Vehicle Type route with a Cost Per Kilometer lower less than 0.
The request must be answered with an error related to the invalid cost per kilometer.

CASE #7 : Add Vehicle Type With a Fuel Type that doesn´t exist.
Send a POST to the Vehicle Type route with a Fuel Type that doesn´t exists.
The request must be answered with an error related to the invalid Fuel type.
