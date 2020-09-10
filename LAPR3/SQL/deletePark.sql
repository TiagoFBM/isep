create PROCEDURE deletePark(descriptionPoint Park.DESCRIPTION_POINT%TYPE) AS

BEGIN
    DELETE FROM Park p where p.DESCRIPTION_POINT = descriptionPoint;
    DELETE FROM Points p where p.DESCRIPTION_POINT = descriptionPoint;
END;
/