# UC 1E - Import Driver Type #

## TEST PLAN ##

* testImportDriverTypesAlreadyRegisted
* testImportDriverTypesWithWrongFormatFile
* testImportDriverTypesWithError

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Import Driver Types Without Error ####
1. Send a POST to the import route.
2. All the driver types should be sucessfuly imported.

#### CASE #2 : Import Driver Types With Error ####
1. Change the file to have some driver types with errors.
2. Send a POST to the import route.
3. All the driver types without errors should be sucessfuly imported.

#### CASE #3 : Import Driver Types Already Exist ####
1. Change the file to have two or more driver types with the same information.
2. Send a POST to the import route.
3. All the driver types that do not exist should be sucessfuly imported.