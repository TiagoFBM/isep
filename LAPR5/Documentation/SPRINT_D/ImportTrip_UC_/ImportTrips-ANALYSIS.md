# Use Case 72 - Import Trips #

In addition to this document, [Add Trip - UC 49 - Analysis](../../SPRINT_C/AddTrip_UC_49/AddTrip-ANALYSIS.md) must also be considered, since the domain entities are the same.

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.

## Analysis ##
* When there is no line identified in the xml, the system must request the line of the given path to the MDR.
* The trip depart time is the time of the first node passage.

## Post Conditions ##
* All the trips that have no errors must be added to the system.