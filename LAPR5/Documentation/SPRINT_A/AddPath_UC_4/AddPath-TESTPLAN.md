# UC 4 - Add Path #

## TEST PLAN ##

* testAddPathAlreadyRegisted
* testAddPathWithNoSegments
* testAddPathWithInvalidNodes
* testAddPathWithNegativeDistanceOnSegment
* testAddPathWithNegativeTravelTimeOnSegment

### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Path Already Registed ####
1. Send a POST to the Path route with an already existing Path information.
2. The request must be answered with an error related to an already existing Path.

#### CASE #2 : Add Path With No Segments ####
1. Send a POST to the Path route with no segments.
2. The request must be answered with an error related to the invalid path.

#### CASE #3 : Add Path With Invalid Nodes ####
1. Send a POST to the Path route with segments referencing non existent nodes.
2. The request must be answered with an error related to the invalid path.

#### CASE #4 : Add Path With Negative Distance On Segment ####
1. Send a POST to the Path route with a segment that has a negative distance.
2. The request must be answered with an error related to the invalid path.

#### CASE #5 : Add Path With Negative Travel Time On Segment ####
1. Send a POST to the Path route with a segment that has a negative travel time.
2. The request must be answered with an error related invalid to the path.