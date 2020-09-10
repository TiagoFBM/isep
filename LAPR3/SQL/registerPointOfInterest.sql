create or replace PROCEDURE registerPointOfInterest(pointDescription points.DESCRIPTION_POINT%Type,pointTextDescription points.TEXT_DESCRIPTION%Type,
pointLatitude points.LATITUDE%TYPE,pointLongitude points.LONGITUDE%TYPE, pointElevation points.ELEVATION%TYPE)
IS
    invalidLatitude EXCEPTION;
    invalidLongitude EXCEPTION;
    invalidElevation EXCEPTION;

BEGIN
    IF (pointLatitude NOT BETWEEN -90 AND 90) THEN
        RAISE invalidLatitude;
    end if;

    IF (pointLongitude NOT BETWEEN -180 AND 180) THEN
        RAISE invalidLongitude;
    end if;

    IF (pointElevation<0) THEN
        RAISE invalidElevation;
    end if;

    INSERT INTO POINTS (DESCRIPTION_POINT, TEXT_DESCRIPTION, LATITUDE, LONGITUDE, ELEVATION)
    VALUES (pointDescription,pointTextDescription,pointLatitude,pointLongitude,pointElevation);

    EXCEPTION
        WHEN invalidLatitude THEN
            raise_application_error(-20001, 'The latitude must be between -90 and 90.');
        WHEN invalidLongitude THEN
            raise_application_error(-20002, 'The latitude must be between -180 and 180.');
        WHEN invalidElevation THEN
            raise_application_error(-20003, 'The elevation must be a positive value.');
END;