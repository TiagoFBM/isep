# UC 1B - Import Path #

## TEST PLAN ##

* testImportPathsAlreadyRegisted
* testImportPathsWithWrongFormatFile
* testImportPathsWithError

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Import Paths Without Error ####
1. Send a POST to the import route.
2. All the paths should be sucessfuly imported.

#### CASE #2 : Import Paths With Error ####
1. Change the file to have some paths with errors.
2. Send a POST to the import route.
3. All the paths without errors should be sucessfuly imported.

#### CASE #3 : Import Paths Already Exist ####
1. Change the file to have two or more Path with the same information.
2. Send a POST to the import route.
3. All the paths that do not exist should be sucessfuly imported.