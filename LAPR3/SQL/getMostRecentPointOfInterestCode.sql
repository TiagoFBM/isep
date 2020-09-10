create or replace FUNCTION getMostRecentPointOfInterestCode RETURN INTEGER AS
    cod integer;
BEGIN

SELECT regexp_substr(DESCRIPTION_POINT, '[^_]+', 1, 2) INTO cod FROM POINTS ORDER BY DESCRIPTION_POINT DESC FETCH NEXT 1 ROW ONLY;

RETURN cod;

EXCEPTION WHEN NO_DATA_FOUND THEN
    return 0;
END getMostRecentPointOfInterestCode;
/

DECLARE
    BEGIN
    dbms_output.PUT_LINE(getMostRecentPointOfInterestCode());
end;