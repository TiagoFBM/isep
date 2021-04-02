# UC 3 - Add Line #

## TEST PLAN ##

* testAddLineWithCodeBiggerThan20
* testAddLineWithDescriptionBiggerThan250
* testAddLineWithTerminalNodesNotRegisted
* testAddLineWithElegibleVehicleTypeNotRegisted
* testAddLineWithElegibleDriverTypeNotRegisted

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Line Without Error ####
1. Send a POST to the line route.
2. All the lines should be sucessfuly added.

#### CASE #2 : Add Line With a Code bigger than 20 characters ####
1. Send a POST to the line route with a code bigger than 20 characters.
2. The request must be answered with an error related to the invalid code.

#### CASE #3 : Add Line With a Description bigger than 250 characters ####
1. Send a POST to the line route with a description bigger than 250 characters.
2. The request must be answered with an error related to the invalid description.

#### CASE #4 : Add Line With Terminal Node's that are not registed ####
1. Send a POST to the line route with terminal Nodes that are not registed.
2. The request must be answered with an error related to the invalid nodes.

#### CASE #5 : Add Line With an Elegible Vehicle Type Not Registed  ####
1. Send a POST to the line route with an Elegible Vehicle Type Not Registed.
2. The request must be answered with an error related to the invalid vehicle type.

#### CASE #6 : Add Line With an Elegible Driver Type Not Registed  ####
1. Send a POST to the line route with an Elegible Driver Type Not Registed.
2. The request must be answered with an error related to the invalid driver type.