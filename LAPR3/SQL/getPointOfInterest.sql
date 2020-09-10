create or replace FUNCTION getPointOfInterest(description POINTS.description_point%type) RETURN SYS_REFCURSOR AS
    res SYS_REFCURSOR;
BEGIN
  OPEN res FOR SELECT p1.description_point,p1.latitude, p1.longitude, p1.elevation, p1.text_description
  FROM POINTS p1 where p1.DESCRIPTION_POINT = description;
  RETURN res;
END getPointOfInterest;
/