# UC 1C - ImportLineFromFile #

## TEST PLAN ##

* testImportLinesWithoutErrors.
* testImportLinesAlreadyRegisted.
* testImportLinesWithWrongFormatFile.

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Import Lines Without Error ####

1. Send a POST to the import route.
2. All the lines should be sucessfuly imported.

#### CASE #2 : Import Lines With Error ####

1. Change the file to have some lines with errors.
2. Send a POST to the import route.
3. All the lines without errors should be sucessfuly imported.

#### CASE #3 : Import Lines Already Exist ####

1. Change the file to have two or more lines with the same information.
2. Send a POST to the import route.
3. All the lines that do not exist should be sucessfuly imported.
