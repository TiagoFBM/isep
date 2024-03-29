create or replace FUNCTION getActiveParks RETURN SYS_REFCURSOR AS

    parksCursor SYS_REFCURSOR;
    ACTIVE_PARK_NUMBER INTEGER;
BEGIN
    ACTIVE_PARK_NUMBER:=1;

    OPEN parksCursor FOR SELECT P.DESCRIPTION_POINT,PO.LATITUDE,PO.LONGITUDE,PO.ELEVATION,PO.TEXT_DESCRIPTION,P.MAX_BICYCLES,P.MAX_SCOOTERS,P.INPUT_VOLTAGE,P.INPUT_CURRENT FROM PARK P
    INNER JOIN POINTS PO on P.DESCRIPTION_POINT = PO.DESCRIPTION_POINT
    where P.STATUS = ACTIVE_PARK_NUMBER;
    return parksCursor;
END;
/
