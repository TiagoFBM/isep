package lapr.project.utils;

public class Utils {

    private Utils() {
    }

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 0;

    public static final int USER_PERMISSION = 0;
    public static final int ADMIN_PERMISSION = 1;

    public static final int NOT_SUCCESS = 0;
    public static final int SUCCESS = 1;

    public static final int START_CYCLING_AVERAGE_SPEED = 0;

    public static final int AVERAGE_SPEED_SCOOTER_CITY = 25;
    public static final int AVERAGE_SPEED_SCOOTER_OFFROAD = 40;
    public static final double AVERAGE_SPEED_SCOOTER = 33.5;
    public static final double AVERAGE_SPEED_BYCICLE = 15.5;

    public static final double AVERAGE_WEIGHT_SCOOTER = 20.0;
    public static final double AVERAGE_WEIGHT_BYCICLE = 8.5;

    public static final double EFFICIENCY_PERCENTAGE = 0.7;
    public static final int RADIUS_NEARBY_PARKS = 1000;
    public static final double GRAVITY_FORCE = 9.8;
    public static final double MECHANICAL_FRICTION = 0.0053;
    public static final double STANDARD_AREA = 0.5;
    public static final double AERODYNAMIC_DRAG_COEFFICIENT = 0.5;

    public static final double ADDITIONAL_BATTERY = 0.10d;

    public static final String FILES_SPLITTER = ";";
    public static final String CHAR_TO_IGNORE = "#";

    public static final int DAY_TO_GENERATE_INVOICES = 5;
    public static final int HOURS_TO_GENERATE_INVOCES = 2;
    public static final int MINUTES_TO_GENERATE_INVOCES = 0;

    public static final int VALUES_TO_SWAP_POINTS = 10;

    public static final double AVERAGE_CYCLING_SPEED = 6.7;

    public static final double JOULES_TO_CALORIES_RATIO = 4.1855;

    public static double calculateDistanceBetweenCoordinates(double lat1, double lat2, double lon1, double lon2) {
        final double R = 6371e3; // Raio da terra
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (R * c); // em m
    }

}
