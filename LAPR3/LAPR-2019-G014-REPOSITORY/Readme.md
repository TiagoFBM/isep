LAPR3 PROJECT REPORT
====================

**GROUP 14 (2di and 2dJ):**

Caio Maciel Sousa (1181103); Daniel Baptista Lourenço (1181047)

Inês de Castro Lopes (1181101); João Miguel Almeida Ferreira da Silva (1181061)

Tiago Filipe Brandão Moreira (1181011); Tiago Miguel Santos Alves Moreira
(1181012)

**DATA DE EXECUÇÃO E DE ENTREGA:**

23 de dezembro de 2019 – 08 de janeiro de 2020

**TEACHERS:**

Margarida Afonso (AFO); Nuno Morgado (NVM)

Nuno Malheiro (NFM); Paulo Fernandes (PAF)

**CLIENT:**

Nuno Bettencourt (NMB)

**COURSE UNIT:**

Laboratory/Project III (LAPR3)

ABSTRACT
========

The present document reports all the work developed during the development of an
application for a software company that needs to develop a product that supports
ride-sharing businesses. From the problem that was proposed to the solution that
was implemented, this report also talks about the methodology of work
implemented by the team. We reinforce our solution by developing software
engineering rationales and diagrams. In this way, we find the reading of this
document very interesting because it envisions a simple and effective solution
for the development of this application.

1. INTRODUCTION
===============

This report aims to describe the whole process of project development,
elaborated within the scope of the Laboratory/Project II, integrated in the
Degree in Informatics Engineering of the Instituto Superior de Engenharia do
Porto. This chair allows a familiarization with the professional world and with
the responsibility and posture to assume before a client.

That being said, this report is not only aimed at reporting the development of
the product but also on the working methods adopted and the results achieved.

Thus, in addition to a brief contextualization of the project, we also undertake
to criticize the creative process, that is, the challenges encountered and the
manipulation/resolution of problems. That way, the report is divided into three
key parts beyond the introduction and conclusion:

-   *Chapter 2 – Problem:* presentation of the application and its use in the
    daily life of the users, as well as brief explanation of each use case for a
    better understanding of the functioning of the application;

-   *Chapter 3 – Work organization, planning and methodology:* this chapter
    summarizes the work methodology adopted by the team, the planning and
    distribution of all the tasks, as well as a reflection of the operation of
    the team over the three weeks;

-   *Chapter 4 – Solution:* description of the application development process,
    in relation to software engineering.

2. PROBLEM
==========

A transport company needs an application to manage its vehicles, bicycles and
electric scooters, users and parks, scattered throughout the city to park
bicycles and electric scooters when not in use.

An user can rent three-speed manual bikes and electric scooters (e.g. city and
off-road).

To use a vehicle, the user must unlock a vehicle from the park. Upon delivery,
the user must receive a notification of success. If vehicles are not blocked in
parks, users will receive a penalty.

There is also a system for adding points for certain user actions, for example,
when a user parks the bike in a higher altitude park, among others.

User’s monthly cost is issued to the user, by using invoices. Users’ points may
be used to partially pay the invoice, and after that, users should get a
receipt.

From now on, it is envisaged that the application will be accessed by several
users (personas) with different roles, such as:

>   • User: person who registered in the application in order to make service
>   requests;

>   • Unregistered User: person who want registered in the application;

>   • Administrator: people responsible for carrying out various activities in
>   the system to support the company's activity.

All of these carry out various functions, defined in the different use cases,
all preceded by an authentication process.

The following use case diagram shows the personas with their respective use
cases.

Use Case Diagram

Use Case Diagram

2.1. UC01 – Add Parks to the System
-----------------------------------

>   This first use case allows an administrator to add parks to the system. Each
>   park has a geographical location using Decimal Degrees (DD).

>   So, for this use if we define the following user story:

>   **As** an Administrator

>   **I want** to be able to add parks to the system

>   **So that** I can have more parks to park vehicles.

>   **--------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I add a park to the system

>   **Then** present an updated list with all the parks.

2.2. UC02 – Remove parks from the system
----------------------------------------

>   Remove parks from the system allows an administrator to remove parks to the
>   system, as the name implies.

>   The defined user story is:

>   **As** an Administrator

>   **I want** to be able to remove parks to the system

>   **So that** I can update the number of active parks.

>   **----------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I remove a park to the system

>   **Then** I am presented with a confirmation option.

2.3. UC03 – Update Parks in The System
--------------------------------------

>   Through this use case, an administrator needs to update a system park.

>   User Story for the use case in question:

>   **As** an Administrator

>   **I want** to be able to change a park

>   **So that** I can update information about it.

>   **----------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I update a park to the system

>   **Then** I am presented with a confirmation option.

2.4. UC04 – Add Vehicle to a Park
---------------------------------

>   In this use case, an administrator wants to add vehicles to a park.

>   So, for this use if we define the following user story:

>   **As** an Administrator

>   **I want** to be able to add vehicles to a park

>   **So that** I can have more vehicles to rent in a park.

>   **--------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I add vehicles to a park

>   **Then** I am presented with a confirmation option.

2.5. UC05 – Remove Vehicle from a Park
--------------------------------------

>   Remove vehicles from parks allows an administrator, as the name implies, to
>   remove vehicles from parks to the system.

>   The defined user story is:

>   **As** an Administrator

>   **I want** to be able to remove vehicles from a park

>   **So that** I can update the number of vehicles in a park.

>   **-----------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I remove a vehicle from a park

>   **Then** I am presented with a confirmation option.

2.6. UC06 – Update Vehicle from a Park
--------------------------------------

>   Through this use case, an administrator needs to update a system park.

>   User Story for the use case in question:

>   **As** an Administrator

>   **I want** to be able to upgrade vehicles in a park

>   **So that** I can update information about it.

>   **--------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I upgrade a park vehicle

>   **Then** I am presented with a confirmation option.

2.7. UC07 – Add Vehicles to the System
--------------------------------------

>   The administrator needs to be able to add vehicles to the system, bicycles,
>   with different sizes (e.g. 15”, 17”), and scooters, city and off-road. Each
>   vehicle is identified by a unique number and has a maximum capacity for the
>   different types of vehicles. With this, we define the following user story:

>   **As** an Administrator

>   **I want** to be able to add vehicles to the system

>   **So that** I can have more vehicles to rent.

>   **----------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I add a vehicle to the system

>   **Then** present an updated list with all the vehicles.

2.8. UC08 – Update Vehicle in the System
----------------------------------------

>   The administrator needs to be able to update vehicles to the system. So, we
>   define the following user story:

>   **As** an Administrator

>   **I want** to be able to upgrade vehicles in the system

>   **So that** I can have vehicles with the latest features.

>   **--------------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I upgrade a vehicle to the system

>   **Then** displays a message with the updated vehicle parameters.

2.9. UC09 – Application Registration
------------------------------------

>   Unregistered users must register themselves on the application, and each
>   registration has a credit card and an initial charge, as well as the height,
>   weight and gender of the user. The following user story represents the use
>   case.

>   **As** an Unregistered User

>   **I want** to register in the app

>   **So that** I can enjoy the services of the company.

>   **------------------------------------------------------------**

>   **Given** I'm an unregistered user

>   **When** I register

>   **Then** I get a confirmation email.

2.10. UC10 – Get a List of Nearest Parks
----------------------------------------

>   By sharing your location, the user gets a list of nearby parks sorted in
>   order of distance.

>   So, for this use if we define the following user story:

>   **As** a user

>   **I want** to get a list of nearby parks

>   **So that** I can know where is the best place to deliver/pick up a vehicle.

>   **------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I get a list of nearby parks

>   **Then** a list is displayed in ascending order of distance.

2.11. UC11 – Get a List of Vehicles from a Park
-----------------------------------------------

>   Users can check on the application for available vehicles at a given park.

>   The defined user story is:

>   **As** a user

>   **I want** to get a list of vehicles from a park

>   **So that** I can find out which vehicles are available in a specific park.

>   **---------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I get a list of park vehicles

>   **Then** a list of all park vehicles, sorted by type, is displayed.

2.12. UC12 – Determine Distance to the Park
-------------------------------------------

>   Users may use the application to check how far another park is. User Story
>   for the use case in question:

>   **As** a user

>   **I want** to determine how far another park is

>   **So that** I can find out which park is nearest.

>   **-------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I get the distance between my location and another park

>   **Then** the system asks if I want to perform another search.

2.13. UC13 – Lifting a Vehicle
------------------------------

>   This use case allows users to request a vehicle at a given park. When
>   requesting electric scooters and no destination is provided the system
>   recommend scooters that have the highest battery charge level.

>   Furthermore, it should be given more choices about the routes that may be
>   chosen by the user, for example:

-   The shortest route between two parks;

-   The most energetically efficient route between two parks;

-   The shortest route between two parks that goes by at least a certain number
    of interest points, which can be specified by the user;

-   The more energy efficient route between two parks, that goes by at least a
    certain number of interest points, which can be specified by the user.

>   When a user requests a route between two locations, and provides a certain
>   number of interest points in-between, more than one route can be suggested
>   to the user. He also specifies if results should be sorted in ascending or
>   descending order, for example, for total distance.

>   So, we define the following user story:

>   **As** a user

>   **I want** to lift a vehicle

>   **So that** I can be able to use this vehicle.

>   **------------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I request to pick up a vehicle

>   **Then** I should be presented with a message informing me of this value.

2.14. UC14 – Calculate the energy required to travel between parks
------------------------------------------------------------------

>   The application should be able to calculate the amount of electrical energy
>   required to travel from one park to another when using scooters.

>   User Story for the use case in question:

>   **As** a user

>   **I want** to calculate the energy required to travel from one park to
>   another

>   **So that** I can find out if the scooter I am using has the battery needed
>   for travel.

>   **------------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I have an estimate of the amount of energy spent on a trip

>   **Then** I should be presented with a message informing me of this value.

2.15. UC15 – Check available parking lots in a park
---------------------------------------------------

>   Users may use the application to check if a destination park has any free
>   parking places for their currently loaned vehicle.

>   So, for this use if we define the following user story:

>   **As** a user

>   **I want** to check if a park has seats available

>   **So that** I can park my vehicle there.

>   **------------------------------------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I check if a park has available parking lots

>   **Then** I should be presented with a message telling me if there are
>   available parking lots.

2.16. UC16 – Park Vehicle
-------------------------

>   When parking a vehicle user must receive an e-mail stating that the vehicle
>   is correctly locked and for how long it was taken. So, we define the
>   following user story:

>   **As** a user

>   **I want** to receive a confirmation email

>   **So that** I can know that my vehicle was parked correctly and the duration
>   of the trip.

>   **----------------------------------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I park the vehicle

>   **Then** I should receive a confirmation email.

2.17. UC17 – Calculate Estimate for Calories Expended
-----------------------------------------------------

>   The application should be able to return a projection for the total amount
>   of calories burnt between two parks considering the altitude/elevation
>   between the initial and final point.

>   User Story for the use case in question:

>   **As** a user

>   **I want** to calculate an estimate for the number of calories spent

>   **So that** I can find out how many trips I need to make to lose weight.

>   **-------------------------------------------------------------------------------------------------------------------------------**

>   **Given** I'm a user

>   **When** I have an estimate of the amount of calories spent on a trip

>   **Then** I should be presented with a message that tells me the amount of
>   calories spent traveling.

2.18. UC18 – Request Up to Date History of Vehicles
---------------------------------------------------

>   A user can request an up to date locking/unlocking history of vehicles, with
>   information concerning locking and unlocking date and time, vehicle and
>   parks. With this, we define the following user story:

>   **As** a user  
>   **I want** to access the rental history of a vehicle  
>   **So that** know in more detail if the vehicle meets my needs.

>   **---------------------------------------------------------------------------------------------------------**

>   **Since** I'm a user  
>   **When** I access the history of car rentals  
>   **Then** I am shown information about the vehicle, the park and the road
>   traveled.

2.19. UC19 – Add Touristic Points of Interest
---------------------------------------------

>   The administrator should be allowed to load touristic points of interest to
>   the application, to better handle routing between parks.

>   So, for this use if we define the following user story:

>   **As** an administrator  
>   **I want** to add tourist spots to the system  
>   **So that** I can be able to cover more areas of interest.

>   **-------------------------------------------------------------------------**

>   **Since** I'm an administrator  
>   **When** I add touristic Points of Interest to the system  
>   **Then** I get a success message.

2.20. UC20 – Add Wind Support Information
-----------------------------------------

>   Wind must be considered as a factor when traveling between two locations.
>   Administrators must be allowed to add wind support information between two
>   different locations (average wind speed between two locations and the wind's
>   direction, in degrees).

>   It is necessary to calculate the course between location A and location B to
>   verify that wind is facing forward, backward or sideways. When no wind
>   information is provided between two locations, you should assume that there
>   is no wind resistance between these two locations.

>   User Story for the use case in question:

>   **As** an administrator

>   **I want** to add the corresponding wind information to a path

>   **So that** I can calculate the energy used during a trip with more
>   precision.

>   **-------------------------------------------------------------------------------------------------**

>   **Given** I'm an administrator

>   **When** I add the wind information to a path

>   **Then** I get a confirmation about the information added.

2.21. UC21 – Generate Invoice
-----------------------------

>   Invoices should be generated on the 5th of each month, but before issuing
>   invoices, the system should check if users have any accumulated bonus
>   points, where every 10 points can be exchanged for one euro (no negative or
>   fractional points are allowed). So, we define the following user story:

>   **As** a user

>   **I want** to get the invoice 

>   **So that** I can get the total coast of the trip.

>   **---------------------------------------------------------**

>   **Given** I'm a user

>   **When** I park a vehicle

>   **Then** an invoice is showed to me.

2.22. UC22 – Get Report for Specific Park 
------------------------------------------

>   An administrator should be able to get a report for a specific park, stating
>   the charging status for each vehicle and an estimate projection for how long
>   it would take for each vehicle to reach 100% charge under the existing
>   circumstances. User Story for the use case in question:

>   **As** a user

>   **I want** to get the invoice 

>   **So that** I can get the total coast of the trip.

>   **---------------------------------------------------------**

>   **Given** I'm a user

>   **When** I park a vehicle

>   **Then** an invoice is showed to me.

2.23. UC23 – Get Report Ability Vehicles Flat Trip Greater Than X Km
--------------------------------------------------------------------

>   An administrator should be able to get a report, stating which electric
>   vehicles don’t have enough capacity to perform an estimated trip of over X
>   Km (rounded to the unit) over a flat road.

>   **As** an administrator

>   **I want** to get a report stating which electric vehicles do not have
>   enough capacity to perform an estimated trip of over X Km

>   **So that** I can know which vehicle better fits my needs.

>   **-----------------------------------------------------------------------------------------------------------------------------------------------**

>   **Given** I'm an administrator

>   **When** I get a report stating which electric vehicles do not have enough
>   capacity to perform an estimated trip of over X Km

>   **Then** I get a message with all the information 

2.24. UC24 – Get Report Vehicles Unlocked
-----------------------------------------

>   An administrator should be able to get a report stating which vehicles are
>   unlocked at a given moment and by whom.

>   **As** an Administrator  
>   **I want** to get a report stating which vehicles are unlocked at a given
>   moment and by whom  
>   **So that** I can know which vehicles are available

>   **-------------------------------------------------------------------------------------------------------------------------**

>   **Given** I'm an administrator  
>   **When** I request a report stating which vehicles are unlocked   
>   **Then **I am presented with a list with the information

3. WORK ORGANIZATION, PLANNING AND METHODOLOGY
==============================================

3.1. EDUSCRUM
-------------

In the EduScrum methodology there is no defined leader, but a Scrum Master. This
element has the function of speeding up the communication between the team
members and achieving a better distribution of tasks between them, as well as,
he's a link between the client of the project and the team that develops it, in
order to be more effective in carrying out the work .

During the entire project, the Scrum Master and the entire team divided the
various tasks into 2 sprints (small parts of the project to be performed with a
defined delivery date). In sprint 1 we put the most important tasks (must), such
as adding, removing, and upgrading parks and vehicles use cases, for example.
For sprint 2, we made the user suggestion use cases and not basic to how the app
works, as well as some small tasks left over from first sprint.

3.2. PLANNING AND DISTRIBUTION OF TASKS
---------------------------------------

After analyzing the job requirements, the team divided them into two sprints, as
previously mentioned, for better organization and time management.

Every day, ten minutes before we finish working, we made a point of situation to
perceive what was done and what was left to do, where a new distribution of
tasks, new deadlines and new priorities occurred.

For a continuous record of the tasks and deadlines to be met, the team used
[jira](https://trello.com/?truid=tr3ce27c-f5f2-41df-171c-9fb3d1404681) as a
support, updated daily, which helped us throughout this process, since it
allowed a better organization of the tasks and the deadlines to be fulfilled,
being able each member can have its tasks well defined.

In addition to this [Bitbucket](https://bitbucket.org/product),
[Sourcetree](https://www.sourcetreeapp.com/),
[SonarQube](https://www.sonarqube.org/) and [Jenkins](https://jenkins.io/) were
also included in the work methodology.
[Bitbucket](https://bitbucket.org/product) and
[Sourcetree](https://www.sourcetreeapp.com/) were both very used to the project
and the final report, allowing us to work online and offline and make comments
with the changes made day by day in the files placed there.

3.3. CRITICAL REFLECTION ON GROUP DYNAMICS AND SELF-ASSESSMENT
--------------------------------------------------------------

The group didn’t feel imbalances in the amount of work of each one, since there
was a good communication and organization of tasks done from the beginning. In
some methods, we find difficulty in meeting deadlines, like physics’ methods,
but among all members, almost all tasks were performed within the timeframe
initially defined. In addition, we improved our research method and improved
communication skills among the group, being that when a member of the group felt
more difficult to understand some step, we all contributed to the understanding
of the problem, which resulted in a remarkable work efficiency.

4. SOLUTION
===========

In all use cases the Facade class integrates the application through input
files, which communicate with the Controller classes, and these with all the
others.

![Domain_Model](/Wiki/UseCases/MD.png)

Through the use cases defined above and, with the help of the files provided by
the client, we establish this relational model in the third normal form.

![Relational_Model](/Wiki/Design/Relational_Model.png)

4.1. UC01 – Add Parks to the System
-----------------------------------

>   In this use case, we insert a new park in DataBase.

>   We create a new Park in ParkController using the method newPark(all
>   parameters), accessing class Park and after this,

>   if the created park was diferent of null, we insert the new Park in the
>   DataBase using the method addPark(Park park).

>   This method access the method addPark in the class ParkDb (package Data).
>   This method access our DataBase and insert the new park in the DataBase.

![SSD_UC01](/Wiki/UseCases/SSD_UC01.png)

![SD_UC01](/Wiki/Design/SD_UC01.png)

![CD_UC01](/Wiki/Design/CD_UC01.png)

4.2. UC02 – Remove parks from the system
----------------------------------------

In this method we delete a park from our database.
--------------------------------------------------

Technically, we do not remove information from the given park from the database.
--------------------------------------------------------------------------------

That is, we have a park class parameter called Status. This status is 1 when the park is active. Otherwise it means the park is down.
-------------------------------------------------------------------------------------------------------------------------------------

In short, given a park ID in the ParkController class, using the removePark method, which takes a park id as a parameter, redirects it to the removePark method of the ParkDB (Database) class. 
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

This method goes to the database, checks if the park is active.
---------------------------------------------------------------

If so, we set the status to 0.
------------------------------

![SSD_UC02](/Wiki/UseCases/SSD_UC02.png)

![SD_UC02](/Wiki/Design/SD_UC02.png)

![CD_UC02](/Wiki/Design/CD_UC02.png)

 4.3. UC03 – Update Parks in The System
---------------------------------------

In this method, we update a given park.
---------------------------------------

We received as a parameter all data relating to a park.
-------------------------------------------------------

In the ParkController class we have a method called updatePark, which redirects to the ParkDB class. 
-----------------------------------------------------------------------------------------------------

In this class, we have a method that looks for a database park according to its ID. 
------------------------------------------------------------------------------------

Once you find it, it will change all the parameters for a park, regardless of whether they were the same or not.
----------------------------------------------------------------------------------------------------------------

![SSD_UC03](/Wiki/UseCases/SSD_UC03.png)

![SD_UC03](/Wiki/Design/SD_UC03.png)

![CD_UC03](/Wiki/Design/CD_UC03.png)

4.4. UC04 – Add Vehicle to a Park
---------------------------------

>   In this use case, an administrator wants to add vehicles to a park.

>   So, for this use if we define the following user story:

>   **As** an Administrator

>   **I want** to be able to add vehicles to a park

>   **So that** I can have more vehicles to rent in a park.

>   **--------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I add vehicles to a park

>   **Then** I am presented with a confirmation option.

4.5. UC05 – Remove Vehicle from a Park
--------------------------------------

>   Remove vehicles from parks allows an administrator, as the name implies, to
>   remove vehicles from parks to the system.

>   The defined user story is:

>   **As** an Administrator

>   **I want** to be able to remove vehicles from a park

>   **So that** I can update the number of vehicles in a park.

>   **-----------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I remove a vehicle from a park

>   **Then** I am presented with a confirmation option.

4.6. UC06 – Update Vehicle from a Park
--------------------------------------

>   Through this use case, an administrator needs to update a system park.

>   User Story for the use case in question:

>   **As** an Administrator

>   **I want** to be able to upgrade vehicles in a park

>   **So that** I can update information about it.

>   **--------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I upgrade a park vehicle

>   **Then** I am presented with a confirmation option.

4.7. UC07 – Add Vehicles to the System
--------------------------------------

>   The administrator needs to be able to add vehicles to the system, bicycles,
>   with different sizes (e.g. 15”, 17”), and scooters, city and off-road. Each
>   vehicle is identified by a unique number and has a maximum capacity for the
>   different types of vehicles. With this, we define the following user story:

>   **As** an Administrator

>   **I want** to be able to add vehicles to the system

>   **So that** I can have more vehicles to rent.

>   **----------------------------------------------------------------**

>   **Given** I'm an Administrator

>   **When** I add a vehicle to the system

>   **Then** present an updated list with all the vehicles.

4.8. UC08 – Update Vehicle in the System
----------------------------------------

![SSD_UC08](/Wiki/UseCases/SSD_UC08.png)

![SD_UC08](/Wiki/Design/SD_UC08.png)

![CD_UC08](/Wiki/Design/CD_UC08.png)

4.9. UC09 – Application Registration
------------------------------------

>   When this operation is requested, if the non-registered user would like to
>   become a client the system requested to insert his email, name, login,
>   password, weight, heeight, gender and his cycling average speed.

![SSD_UC09](/Wiki/UseCases/SSD_UC09.png)

![SD_UC09](/Wiki/Design/SD_UC09.png)

![CD_UC09](/Wiki/Design/CD_UC09.png)

4.10. UC10 – Get a List of Nearest Parks
----------------------------------------

>   The getNearestPark method receives two coordinates, a user's location, and
>   may or may not receive a radius away. If you do not receive the radius, it
>   is considered 1km. Then go to the database to access the available parks and
>   from these will calculate the distance and see which one is within the
>   defined radius. Returns a list of parks sorted in ascending order of
>   distance.

![SSD_UC10](/Wiki/UseCases/SSD_UC10.png)

![SD_UC10](/Wiki/Design/SD_UC10.png)

![CD_UC10](/Wiki/Design/CD_UC10.png)

4.11. UC11 – Get a List of Vehicles from a Park
-----------------------------------------------

>   When the user wants to get a list of vehicles from a park, selecting this
>   operation, the system shows all the parks actives in the system and requires
>   to the user to select one, after that shows all the vehicles that are in the
>   park.

![SSD_UC11](/Wiki/UseCases/SSD\_ UC11.png)

![SD\_ UC11](/Wiki/Design/SD\_ UC11.png)

![CD\_ UC11](/Wiki/Design/CD\_ UC11.png)

4.12. UC12 – Determine Distance to the Park
-------------------------------------------

>   When this operation is requested, the system asks the introduction of the
>   user's location and selection of destine park. He introduces his location
>   and selects the desired park. The system informs of the total distance.

![SSD_UC12](/Wiki/UseCases/SSD\_ UC12.png)

![SD\_ UC12](/Wiki/Design/SD\_ UC12.png)

![CD\_ UC12](/Wiki/Design/CD\_ UC12.png)

4.13. UC13 – Lifting a Vehicle
------------------------------

![SSD_UC13](/Wiki/UseCases/SSD\_ UC13.png)

![SD\_ UC13](/Wiki/Design/SD\_ UC13.png)

![CD\_ UC13](/Wiki/Design/CD\_ UC13.png)

2.14. UC14 – Calculate the energy required to travel between parks
------------------------------------------------------------------

The method for calculating spent energy receives four coordinates, from the two
locations to be calculated and the customer id. Through various average
variables defined in Utils, and accessing the database, we calculated the engine
power (P = g m Vg (K1+s) + K2 Va\^2 Vg) . From here, we get the total energy.

![SSD_UC14](/Wiki/UseCases/SSD_UC14.png)

![SD_UC14](/Wiki/Design/SD_UC14.png)

![CD_UC14](/Wiki/Design/CD_UC14.png)

2.15. UC15 – Check available parking lots in a park
---------------------------------------------------

>   Given a specific park, the user can check the number of available parking
>   lots in the respective park, even if they are bicycle parking spots or
>   scooter parking spots.

![SSD_UC15](/Wiki/UseCases/SSD\_ UC15.png)

![SD\_ UC15(/Wiki/Design/SD\_ UC15.png)

![CD\_ UC15](/Wiki/Design/CD\_ UC15.png)

2.16. UC16 – Park Vehicle
-------------------------

The LockVehicle use case was intended to return a vehicle. As requested in the statement, several conditions not only to generate the cost of the trip but also to upgrade points - if the altitude was greater than 25 meters (the equivalent of 5 points) or greater than 50 meters (the equivalent of 15 points). Regarding payments, each trip is assigned a cost for each hour of travel, being the first hour always free and a extra fine if the vehicle is parked in a place which isn´t a park. Taking all of this into consideration, the use case was mostly developed in sql language due to the fact that various database information is required in a very organized manner
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

![SSD_UC16](/Wiki/UseCases/SSD\_ UC16.png)

![SD\_ UC16](/Wiki/Design/SD\_ UC16.png)

![CD\_ UC16](/Wiki/Design/CD\_ UC16.png)

2.17. UC17 – Calculate Estimate for Calories Expended
-----------------------------------------------------

>   In this feature, the system calculates an estimated amount of calories based
>   on the bicycle that the user is going to rent and on the initial park the
>   user is going to ride off and the park where the user is going to lock the
>   bicycle.

![SSD_UC17](/Wiki/UseCases/SSD\_ UC17.png)

![SD\_ UC17](/Wiki/Design/SD\_ UC17.png)

![CD\_ UC17](/Wiki/Design/CD\_ UC17.png)

2.18. UC18 – Request Up to Date History of Vehicles
---------------------------------------------------

>   In this function, the system displays a list with the information about
>   every single vehicle and informs the sucess of the operation.

![SSD_UC18](/Wiki/UseCases/SSD\_ UC18.png)

![SD\_ UC18](/Wiki/Design/SD\_ UC18.png)

![CD\_ UC18](/Wiki/Design/CD\_ UC18.png)

2.19. UC19 – Add Touristic Points of Interest
---------------------------------------------

>   The system asks for the required information for the registration of the
>   point of interest. The administrator inserts the information, the system
>   asks for the confirmation, the administrator confirms and the system
>   confirms the action and return to the main menu.

![SSD_UC19](/Wiki/UseCases/SSD\_ UC19.png)

![SD\_ UC19](/Wiki/Design/SD\_ UC19.png)

![CD\_ UC19](/Wiki/Design/CD\_ UC19.png)

2.20. UC20 – Add Wind Support Information
-----------------------------------------

>   The system asks an administrator to input the wind informations regarding a
>   certain path, which are used to calculate the calories and the energy used
>   by a scooter more precisely.

![SSD_UC20](/Wiki/UseCases/SSD\_ UC20.png)

![SD\_ UC20](/Wiki/Design/SD\_ UC20.png)

![CD\_ UC20](/Wiki/Design/CD\_ UC20.png)

2.21. UC21 – Generate Invoice
-----------------------------

>   The Generate Invoice use case was somewhat more complex to develop due to
>   the fact that it implemented the functionality of sending a mail. In order
>   to organize this information, so that we had everything we needed, we used
>   information structures called Maps that contained for each user all monthly
>   travel invoices. Regarding the idea of generating this feature
>   automatically, after consulting with the database teacher we decided to use
>   the triggers that allowed the choice of one day of the month, a certain time
>   for everything to be generated and send an email with all information to
>   each user.

![SSD_UC21](/Wiki/UseCases/SSD\_ UC21.png)

![SD\_ UC21](/Wiki/Design/SD\_ UC21.png)

![CD\_ UC21](/Wiki/Design/CD\_ UC21.png)

2.22. UC22 – Get Report for Specific Park 
------------------------------------------

In this method, we get one report about the charging status of all scooters of a given park.
--------------------------------------------------------------------------------------------

We started this method by getting a list of all vehicles in a given park using the getAllVehiclesOfPark method.
---------------------------------------------------------------------------------------------------------------

In this method we will fetch all vehicles from a park, but using instance off, we get a list with only the scooters of a given park.
------------------------------------------------------------------------------------------------------------------------------------

Next, using the getChargingTimeLeft method, we will fetch the remaining time to finish loading each scooter. However, we had a small setback. The current scooter battery is the battery the scooter had after the last delivery. Then, using a sql method, we fetched the time that passed between the current date (for example, when the administrator requests the report) and the last delivery.
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

So we calculate the battery at the exact moment and using the getChargingTimeLeft method, we give back the time left to fully charge a scooter.
-----------------------------------------------------------------------------------------------------------------------------------------------

Since we were also asked to return vehicles that are not 100% loaded, we made a method called getVehiclesWithout100perBatery. 
------------------------------------------------------------------------------------------------------------------------------

In this method, after calculating the current battery, we check if the battery of each scooter is less than 100%. 
------------------------------------------------------------------------------------------------------------------

On success, we incremented a counter. 
--------------------------------------

This method returns an integer, which is the counter (number of scooters that are not 100% loaded)
--------------------------------------------------------------------------------------------------

![SSD_UC22](/Wiki/UseCases/SSD_UC22.png)

![SD_UC22](/Wiki/Design/SD_UC22.png)

![CD_UC22](/Wiki/Design/CD_UC22.png)

2.23. UC23 – Get Report Ability Vehicles Flat Trip Greater Than X Km
--------------------------------------------------------------------

>   When the administrator wants to get a report about the ability of vehicles
>   to travel certain distance, the system requests the distance and after
>   getting all the active vehicles, computes the distance that each can make
>   and shows the one that have the ability to travel X km.

![SSD_UC23](/Wiki/UseCases/SSD\_ UC23.png)

![SD\_ UC23](/Wiki/Design/SD\_ UC23.png)

![CD\_ UC23](/Wiki/Design/CD\_ UC23.png)

2.24. UC24 – Get Report Vehicles Unlocked
-----------------------------------------

This method accesses the database and does just about everything in it. Go to
the rent and delivery tables and see the delivery dates that are null. Returns a
list of unlocked vehicles.

![SSD_UC24](/Wiki/UseCases/SSD_UC24.png)

![SD_UC24](/Wiki/Design/SD_UC24.png)

![CD_UC24](/Wiki/Design/CD_UC24.png)

CONCLUSION
==========

This application is a very useful and well formulated application, able to serve
its users through specific requests. This report covers all the topics required
for the problem vehicle renting problem and it is explained here how we adapt
our knowledge of the theme to a Java application. However, we are aware that
some of the use-case diagrams have some flaws, since when passing them to code,
we changed certain things that we did not update in the diagrams. Just like the
test class and JavaDocs in main app, we know they could be improved. In
addition, through this curricular unit, it was possible to live the world of
work a little more closely, which proved to be immensely enriching, since it
facilitated an understanding of the usefulness of what was discussed in other
classes and how it is possible to apply it in the day-to-day. Thus, this project
culminated in a well-structured application whose functionalities fulfilled the
initial objective. Although we have encountered some difficulties in working,
for example, manipulating the data base correctly (mainly because it was down
during a lot of important hours), because it is an application that we have more
recent knowledge, the team managed to overcome them and adapt so that there is a
fair division of tasks, finishing the work within the stipulated time.

REFERENCES
==========

Atlassian, A. (2018). The Git solution for professional teams. Retrieved
December/January, 2019/2020, from <https://bitbucket.org/product>

Atlassian, A. (2018). Free Git GUI for Mac and Windows. Retrieved
December/January, 2019/2020, from <https://www.sourcetreeapp.com/>

S., © S.A. (n.d.). ContinuousCode Quality. Retrieved December/January,
2019/2020, from <https://www.sonarqube.org/>

Jenkins. (n.d.). Retrieved December/January, 2019/2020 , from
<https://jenkins.io/>
