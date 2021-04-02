# UC 5 - Add Driver Type #

## TEST PLAN ##

* testAddDriverTypeAlreadyRegisted
* testAddDriverTypeWithCodeBiggerThan20
* testAddDriverTypeWithDescriptionBiggerThan250

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Driver Type Already Registed ####
1. Send a POST to the Driver Type route with an already existing Driver Type information.
2. The request must be answered with an error related to an already existing Driver Type.

#### CASE #2 : Add Driver Type With a Code bigger than 20 characters ####
1. Send a POST to the Driver Type route with a code bigger than 20 characters.
2. The request must be answered with an error related to the invalid code.

#### CASE #3 : Add Driver Type With a Description bigger than 250 characters ####
1. Send a POST to the Driver Type route with a description bigger than 250 characters.
2. The request must be answered with an error related to the invalid description.