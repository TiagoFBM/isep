# Use Case 48 - Add Driver #

## TEST PLAN ##

* testAddDriverToTheSystem
* testAddDriverWithInvalidCode
* testAddDriverWithInvalidName
* testAddDriverWithInvalidBirthDate
* testAddDriverWithInvalidCitizenCardNumber
* testAddDriverWithInvalidNIF
* testAddDriverWithDepartureDateGreatherThanEntranceDate
* testAddDriverWithInvalidEntranceDate
* testAddDriverWithInvalidDepartureDate
* testAddDriverWithInvalidDriverLicenseNumber
* testAddDriverWithInvalidDriverLicenseDate

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Driver Successfully ####

1. Send a POST to the Driver route with all information needed
2. The request must be answered with an successfull message.

#### CASE #2 : Add Driver with invalid code ####

1. Send a POST to the Driver route with invalid code.
2. The request must be answered with an error mentioning invalid code.

#### CASE #3 : Add Driver with invalid name ####

1. Send a POST to the Driver route with invalid name.
2. The request must be answered with an error mentioning invalid name.

#### CASE #4 : Add Driver with invalid date of birth ####

1. Send a POST to the Driver route with invalid date of birth.
2. The request must be answered with an error mentioning invalid date of birth.

#### CASE #5 : Add Driver with invalid driver citizen card number ####

1. Send a POST to the Driver route with invalid driver citizen card number.
2. The request must be answered with an error mentioning invalid driver citizen card number.

#### CASE #6 : Add Driver with invalid NIF ####

1. Send a POST to the Driver route with invalid NIF.
2. The request must be answered with an error mentioning invalid NIF.

#### CASE #7 : Add Driver with left date greather than entrance date ####

1. Send a POST to the Driver route with left date greather than entrance date.
2. The request must be answered with an error mentioning that the left date must be greather than the entrance date.

#### CASE #8 : Add Driver with invalid entrance date ####

1. Send a POST to the Driver route with invalid entrance date.
2. The request must be answered with an error mentioning invalid entrance date.

#### CASE #9 : Add Driver with invalid departure date ####

1. Send a POST to the Driver route with invalid departure date.
2. The request must be answered with an error mentioning invalid departure date.

#### CASE #10 : Add Driver with invalid driver license number ####

1. Send a POST to the Driver route with invalid driver license number.
2. The request must be answered with an error mentioning invalid driver license number.

#### CASE #11 : Add Driver with invalid driver license date ####

1. Send a POST to the Driver route with invalid driver license date.
2. The request must be answered with an error mentioning invalid driver license date.

#### CASE #12 : Add Driver without driver types ####

1. Send a POST to the Driver route without driver types.
2. The request must be answered with an error mentioning that there must be at least one driver type related.
