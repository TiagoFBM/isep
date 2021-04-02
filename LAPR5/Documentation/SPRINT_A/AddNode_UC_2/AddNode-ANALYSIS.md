# UC 2 - Add Node #

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.

## Post Conditions ##
* A new Node should be added to the system.

## Analysis ##
* By default there are no restrictions on the node.
* It must be possible to indicate if a node is a collection station or a relief point.
* By default a node is created not being a collection station neither a relief point.

## Business Rules ##
* A Node is created with an ID, name, latitude, longitude and if it is a collection station or a relief point.
* The name must be smaller than 200 characters and alphanumeric and mandatory.
* The shortName must be smaller than 20 characters, alpahnumeric and mandatory.
  
