# UC 1C - ImportLineFromFile #

As a Data Administrator, I want to import lines to the system.
In addition to the information bellow, an analysis of the AddLine_UC_3 - Analysis should be considered as the process of adding Lines (after handling the import file) is similar.

## Pre-Requisites ##

- The user who accesses the system must have the role of Data Administrator.
- The filled file must be in the local computer in a specified folder.

## Post-Requisites ##

- A new line is registered in the system.

## Analyze ##

- Lines that not fullfill the specified format will be ignored.
- At the end of the importation process, the user should receive a summary of the operation.
- If the Line to be imported already exists in the system, it will not be imported.

## Business Rules ##

- The file used to import nodes will be of the GLX type.
- The .glx file format respects the format provided as an example.
