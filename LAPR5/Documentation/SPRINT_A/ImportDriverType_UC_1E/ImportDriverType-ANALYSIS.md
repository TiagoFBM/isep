# UC 1E - Import Driver Type

In addition to the information bellow, an analysis of the [UC_5_AddDriverType - Analysis](../AddDriverType_UC_5/AddDriverType-ANALYSIS.md) should be considered as the process of adding driver types (after handling the import file) is similar.

## Pre-Conditions ##
* The user who accesses the system must have the role of Data Administrator.

## Analysis ##
* Lines that not fullfill the specified format will be ignored.
* At the end of the importation process, the user should receive a summary of the operation.
* If the Driver Type to be imported already exists in the system, it will not be imported.
* The file must be passed in the post request.

## Business Rules ##
* The file used to import driver types will be of the GLX type.
* The .glx file format respects the format provided as an [example](https://moodle.isep.ipp.pt/pluginfile.php/47572/mod_resource/content/1/demo-lapr5.glx).
