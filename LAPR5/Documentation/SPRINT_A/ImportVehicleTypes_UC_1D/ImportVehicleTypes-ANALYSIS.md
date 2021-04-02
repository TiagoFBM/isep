# UC 1D - Import Vehicle Types

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.
* The filled file must be in the local computer in a specified folder.

## Analysis ##
* Lines that not fullfill the specified format will be ignored.
* At the end of the importation process, the user should receive a summary of the operation.
* If the Type of Vehicle to be imported already exists in the system, it will not be imported.

## Business Rules ##
* The file used to import types of vehicles will be of the GLX type.
* The .glx file format respects the format provided as an [example](https://moodle.isep.ipp.pt/pluginfile.php/47572/mod_resource/content/1/demo-lapr5.glx).
