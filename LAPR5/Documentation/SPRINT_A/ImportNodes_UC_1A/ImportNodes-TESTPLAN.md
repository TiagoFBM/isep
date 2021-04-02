# UC 1 A - Import Nodes #

## TEST PLAN ##

* testImportNodesAlreadyRegisted
* testImportNodesWithWrongFormatFile
* testImportNodesWithError

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Import Nodes Without Error ####
1. Send a POST to the import route.
2. All the nodes should be sucessfuly imported.

#### CASE #2 : Import Nodes With Error ####
1. Change the file to have some nodes with errors.
2. Send a POST to the import route.
3. All the nodes without errors should be sucessfuly imported.

#### CASE #3 : Import Nodes Already Exist ####
1. Change the file to have two or more nodes with the same information.
2. Send a POST to the import route.
3. All the nodes that do not exist should be sucessfuly imported.