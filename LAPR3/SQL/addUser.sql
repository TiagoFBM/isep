create or replace PROCEDURE addUser(userParameter system_user.username%TYPE,visaParameter system_user.visa%TYPE, genderParameter system_user.gender%TYPE, emailParameter system_user.email%TYPE,
passwordParameter SYSTEM_USER.PASSWORD%TYPE, weightParameter SYSTEM_USER.WEIGHT%TYPE, heightParameter SYSTEM_USER.HEIGHT%TYPE, cyclingAverageSpeedParametar SYSTEM_USER.CYCLING_AVERAGE_SPEED%TYPE)
IS
    invalidGenderException EXCEPTION;
BEGIN
    IF (genderParameter != 'M' or genderParameter != 'F') THEN
        RAISE invalidGenderException;
    end if;

    INSERT INTO SYSTEM_USER (USERNAME, VISA, GENDER, EMAIL, PASSWORD, WEIGHT, HEIGHT, CYCLING_AVERAGE_SPEED)
    VALUES (userParameter,visaParameter,genderParameter,emailParameter,passwordParameter,weightParameter,heightParameter,cyclingAverageSpeedParametar);

    EXCEPTION
        WHEN invalidGenderException THEN
            raise_application_error(-20001, 'The gender inserted is invalid.');
END;
/