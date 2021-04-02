# UC 2 - Add Node #
  
### FUNCTIONAL TEST PLAN ###

#### CASE #1 : Add Node Without Error ####
1. Send a POST to the node route.
2. All the nodes should be sucessfuly added.

#### CASE #2 : Add Node With a Code bigger than 20 characters ####
1. Send a POST to the Node route with a code bigger than 20 characters.
2. The request must be answered with an error related to the invalid code.

#### CASE #3 : Add Node With an invalid Code (Regex) ####
1. Send a POST to the Node route with a code that isnt alphanumeric.
2. The request must be answered with an error related to the invalid code.

#### CASE #4 : Add Node With an invalid Name (Regex) ####
1. Send a POST to the Node route with a name that isnt alphanumeric.
2. The request must be answered with an error related to the invalid name.

#### CASE #5 : Add Node With a name bigger than 200 characters ####
1. Send a POST to the Node route with a name bigger than 200 characters.
2. The request must be answered with an error related to the invalid name.

#### CASE #6 : Add Node With a latitude that doesnt respect wgs84 format ####
1. Send a POST to the Node route with a latitude that doesnt respect wgs84 format.
2. The request must be answered with an error related to the invalid latitude.

#### CASE #7 : Add Node With a longitude that doesnt respect wgs84 format ####
1. Send a POST to the Node route with a longitude that doesnt respect wgs84 format.
2. The request must be answered with an error related to the invalid longitude.

#### CASE #8 : Add Node With an invalid Short Name (Regex) ####
1. Send a POST to the Node route with a short name that is not in capslock.
2. The request must be answered with an error related to the invalid short name.

#### CASE #9 : Add Node With a short name bigger than 20 characters ####
1. Send a POST to the Node route with a short name bigger than 20 characters.
2. The request must be answered with an error related to the invalid short name.