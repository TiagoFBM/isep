-- CASCADE CONSTRAINTS para eliminar as restri��es de integridade das chaves prim�rias e chaves �nicas
DROP TABLE bicycle CASCADE CONSTRAINTS;

DROP TABLE client_registration CASCADE CONSTRAINTS;

DROP TABLE credit_card CASCADE CONSTRAINTS;

DROP TABLE delivery CASCADE CONSTRAINTS;

DROP TABLE invoice CASCADE CONSTRAINTS;

DROP TABLE park CASCADE CONSTRAINTS;

DROP TABLE paths CASCADE CONSTRAINTS;

DROP TABLE points CASCADE CONSTRAINTS;

DROP TABLE receipt CASCADE CONSTRAINTS;

DROP TABLE rent CASCADE CONSTRAINTS;

DROP TABLE scooter CASCADE CONSTRAINTS;

DROP TABLE system_user CASCADE CONSTRAINTS;

DROP TABLE travel_costs CASCADE CONSTRAINTS;

DROP TABLE vehicle CASCADE CONSTRAINTS;

DROP TABLE vehicles_location CASCADE CONSTRAINTS;

CREATE TABLE bicycle (      
    description_vehicle   VARCHAR(100)
        CONSTRAINT pk_bicycle_description_vehicle PRIMARY KEY,
    wheel_size            NUMBER(2) NOT NULL
);

CREATE TABLE client_registration (
    username            VARCHAR(100)
        CONSTRAINT pk_client_registration_username PRIMARY KEY,
    password            VARCHAR(100) NOT NULL,
    date_registration   DATE DEFAULT SYSDATE,
    cost                NUMBER(4, 2) DEFAULT 2.50
);

CREATE TABLE credit_card (
    visa         VARCHAR(16) Default 'VISA'
        CONSTRAINT pk_credit_card_visa PRIMARY KEY,
    shelf_life   DATE DEFAULT SYSDATE, 
    cvv          NUMBER(3) DEFAULT 0
);

CREATE TABLE delivery (
    description_delivery      NUMBER(3)
        CONSTRAINT pk_delivery_description_delivery PRIMARY KEY,
    description_rent          NUMBER(3) NOT NULL,
    description_cost          NUMBER(10) NOT NULL,
    date_time_end             DATE NOT NULL,
    description_point_final   VARCHAR(100) NOT NULL,
    cost_final                NUMBER(6, 2) NOT NULL,
    earned_points             NUMBER(2) DEFAULT 0
);

CREATE TABLE invoice (
    description_invoice   NUMBER(10)
        GENERATED AS IDENTITY,
    description_rent      NUMBER(3),
   CONSTRAINT pk_invoice_description_rent_description_invoice PRIMARY KEY ( description_invoice,
                  description_rent )
);

CREATE TABLE park (
    description_point   VARCHAR(100)
        CONSTRAINT pk_park_description_point PRIMARY KEY,
    status              NUMBER(1) DEFAULT 1,
    max_bicycles        NUMBER(5) NOT NULL,
    max_scooters        NUMBER(5) NOT NULL,
    input_voltage       NUMBER(5) NOT NULL,
    input_current       NUMBER(5) NOT NULL
);

CREATE TABLE paths (
    description_path      NUMBER(3)
        CONSTRAINT pk_paths_description_path PRIMARY KEY,
    description_point_origin            VARCHAR(100) NOT NULL,
    description_point_destination       VARCHAR(100) NOT NULL,
    kinetic_coefficient                 NUMBER(5,3) DEFAULT 0.000,
    wind_direction                      NUMBER(5, 2) DEFAULT 0,
    wind_speed                          NUMBER(5, 2) DEFAULT 0,
    status                NUMBER(1) DEFAULT 1
);

CREATE TABLE points (
    description_point   VARCHAR(100)
        CONSTRAINT pk_points_description_point PRIMARY KEY,
    text_description    VARCHAR(100) NOT NULL,
    latitude            NUMBER(8, 6) NOT NULL,
    longitude           NUMBER(8, 6) NOT NULL,
    elevation           NUMBER(5) DEFAULT 0
);

CREATE TABLE receipt (
    description_receipt   NUMBER(10)
        GENERATED AS IDENTITY
        CONSTRAINT pk_receipt_description_receipt PRIMARY KEY,        
    description_rent      NUMBER(3) NOT NULL,
    description_invoice   NUMBER(10) NOT NULL,
    date_receipt DATE DEFAULT SYSDATE
);

CREATE TABLE rent (
    description_rent      NUMBER(3)
        CONSTRAINT pk_rent_description_rent PRIMARY KEY,
    description_vehicle   VARCHAR(100) NOT NULL,
    description_point     VARCHAR(100) NOT NULL,
    lock_date_time       DATE NOT NULL,
    username              VARCHAR(100) NOT NULL,
    date_time_begin       DATE NOT NULL,
    description_point_destination   VARCHAR(100)
);

CREATE TABLE scooter (
    description_vehicle   VARCHAR(100)
        CONSTRAINT pk_scooter_description_scooter PRIMARY KEY,
    scooter_type          VARCHAR(100) NOT NULL,
    max_batery            NUMBER(3) NOT NULL,
    actual_batery         NUMBER(3) NOT NULL,
    motor_power             Number(10)  NOT NULL
);

CREATE TABLE system_user (
    username                VARCHAR(100)
        CONSTRAINT pk_system_user_username PRIMARY KEY,
    visa                    VARCHAR(16) DEFAULT 'VISA',
    gender                  VARCHAR(100)NOT NULL,
    email                   VARCHAR(100)NOT NULL,
    password                VARCHAR(100)NOT NULL,
    weight                  NUMBER(3)NOT NULL,
    height                  NUMBER(3)NOT NULL,
    cycling_average_speed   NUMBER(4, 2) NOT NULL,
    system_permission       NUMBER(1) Default 0,
    travel_points           NUMBER(10) default 0
);

CREATE TABLE travel_costs (
    description_cost    NUMBER(10)
        GENERATED AS IDENTITY
        CONSTRAINT pk_travel_costs_description_cost PRIMARY KEY,
    date_time_initial   DATE DEFAULT SYSDATE,
    date_time_final     DATE DEFAULT SYSDATE,
    price_hour          NUMBER(4, 2) DEFAULT 1.50,
    parking_penalty     NUMBER(4, 2) DEFAULT 10.00,
    travel_points_25m   NUMBER(5) DEFAULT 5,
    travel_points_50m   NUMBER(5) DEFAULT 15,
    month_cost          NUMBER(5, 2) DEFAULT 0.25,
    swap_points_money   NUMBER(5) DEFAULT 10
);

CREATE TABLE vehicle (
    description_vehicle   VARCHAR(100)
        CONSTRAINT pk_vehicle_description_vehicle PRIMARY KEY,
    status                NUMBER(1) DEFAULT 1,
    weight                NUMBER(5,2) NOT NULL,
    aerodynamic           NUMBER(3, 2) NOT NULL,
    frontal_area          NUMBER(2, 1) NOT NULL
);


CREATE TABLE vehicles_location (
    description_point     VARCHAR(100),
    description_vehicle   VARCHAR(100),
    lock_date_time       DATE,
    unlock_date_time                 DATE DEFAULT NULL,
    CONSTRAINT pk_vehicles_location_description_point_description_vehicle_lock_date_time PRIMARY KEY ( description_point,
                  description_vehicle,
                  lock_date_time )
);

-------------------------------------------------------ALTER TABLE------------------------------------------------------
ALTER TABLE vehicles_location
    ADD CONSTRAINT fk_vehicles_location_description_point FOREIGN KEY ( description_point )
        REFERENCES points ( description_point );

ALTER TABLE vehicles_location
    ADD CONSTRAINT fk_vehicles_location_description_vehicle FOREIGN KEY ( description_vehicle )
        REFERENCES vehicle ( description_vehicle );

ALTER TABLE system_user
    ADD CONSTRAINT fk_system_user_visa FOREIGN KEY ( visa )
        REFERENCES credit_card ( visa );

ALTER TABLE system_user
    ADD CONSTRAINT fk_system_user_username FOREIGN KEY ( username )
        REFERENCES client_registration ( username );

ALTER TABLE bicycle
    ADD CONSTRAINT fk_bicycle_description_vehicle FOREIGN KEY ( description_vehicle )
        REFERENCES vehicle ( description_vehicle );

ALTER TABLE scooter
    ADD CONSTRAINT fk_scooter_description_vehicle FOREIGN KEY ( description_vehicle )
        REFERENCES vehicle ( description_vehicle );

ALTER TABLE park
    ADD CONSTRAINT fk_park_description_point FOREIGN KEY ( description_point )
        REFERENCES points ( description_point );

ALTER TABLE paths
    ADD CONSTRAINT fk_paths_description_point_origin FOREIGN KEY ( description_point_origin )
        REFERENCES points ( description_point );
        
ALTER TABLE paths
    ADD CONSTRAINT fk_paths_description_point_destination FOREIGN KEY ( description_point_destination )
        REFERENCES points ( description_point );

ALTER TABLE rent
    ADD CONSTRAINT fk_rent_description_point_vehicle_lock_data_time FOREIGN KEY ( description_point,
                                                                                   description_vehicle,
                                                                                   lock_date_time )
        REFERENCES vehicles_location ( description_point,
                                       description_vehicle,
                                       lock_date_time );

ALTER TABLE rent
    ADD CONSTRAINT fk_rent_usarname FOREIGN KEY ( username )
        REFERENCES system_user ( username );

ALTER TABLE delivery
    ADD CONSTRAINT fk_delivery_description_rent FOREIGN KEY ( description_rent )
        REFERENCES rent ( description_rent );

ALTER TABLE delivery
    ADD CONSTRAINT fk_delivery_description_cost FOREIGN KEY ( description_cost )
        REFERENCES travel_costs ( description_cost );

ALTER TABLE invoice
    ADD CONSTRAINT fk_invoice_line_description_rent FOREIGN KEY ( description_rent )
        REFERENCES rent ( description_rent );
        
ALTER TABLE receipt
    ADD CONSTRAINT fk_receipt_description_invoice_description_rent FOREIGN KEY ( description_invoice, description_rent )
        REFERENCES invoice ( description_invoice, description_rent );

INSERT INTO TRAVEL_COSTS(date_time_initial, date_time_final, price_hour, parking_penalty, travel_points_25m, travel_points_50m, month_cost, swap_points_money) VALUES
('2019/12/01', '2040/12/01', 10, 20, 5, 15, 3, 10);