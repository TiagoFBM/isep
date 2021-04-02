# UC 4 - Add Path #

## Pre-Conditions ##
* The user who accesses the system must have the role of **Data Administrator**.
* The **Path** to be added must not exist in the system.
* There must exist **Nodes** in the system.
* There must exist at least one **Line** in the system.

## Post Conditions ##
* A new **Path** will be added to the system.

## Analysis ##
* Already existing **Paths** must not be added to the system.
* The **Path** can only be added to the system if its parameters meet the requirements.
* The user should select the nodes that represent segments of the path.
* The travel duration between nodes cannot be less than 0 seconds.
* The travel distance between nodes cannot be negative.
* There is a boolean that represents whether a path is empty or not.

## Business Rules ##
* A Driver Type is represented by a free (not cataloged) description of characteristics.
* Driver Type is characterized by an unique code and a description, both alphanumeric and mandatory.
* A **Path** is represented by segments and can be empty (without passengers). Each segment has 2 nodes and the travel time and distance between these 2 nodes. 