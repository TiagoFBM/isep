create or replace FUNCTION getVehiclesFromPark(descriptionPoint Park.DESCRIPTION_POINT%TYPE) RETURN SYS_REFCURSOR AS
    vehiclesCursor SYS_REFCURSOR;
BEGIN
    OPEN vehiclesCursor FOR SELECT v.DESCRIPTION_VEHICLE FROM Vehicle v
        inner join VEHICLES_LOCATION vl ON v.DESCRIPTION_VEHICLE = vl.DESCRIPTION_VEHICLE where vl.DESCRIPTION_POINT = descriptionPoint;
    return vehiclesCursor;
END;
/

