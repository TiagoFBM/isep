package lapr.project.model;

import lapr.project.utils.Utils;

public class Park extends PointOfInterest {

    private int maxBicycles;
    private int maxScooters;
    private int inputVoltage;
    private int inputCurrent;
    private int status;


    public Park(String descriptionPark, double latitude, double longitude, int elevation, String textDescription, int maxBicycles, int maxScooters, int inputVoltage, int inputCurrent) {
        super(descriptionPark, textDescription, latitude, longitude, elevation);
        this.maxBicycles = maxBicycles;
        this.maxScooters = maxScooters;
        this.inputVoltage = inputVoltage;
        this.inputCurrent = inputCurrent;
        this.status = Utils.STATUS_ACTIVE;
    }


    public String getDescriptionPark() {
        return super.getPoiDescription();
    }

    public void setDescriptionPark(String descriptionPark) {
        super.setPoiDescription(descriptionPark);
    }

    @Override
    public double getLatitude() {
        return super.getLatitude();
    }

    @Override
    public void setLatitude(double latitude) {
        super.setLatitude(latitude);
    }

    @Override
    public double getLongitude() {
        return super.getLongitude();
    }

    @Override
    public void setLongitude(double longitude) {
        super.setLongitude(longitude);
    }

    @Override
    public int getElevation() {
        return super.getElevation();
    }

    @Override
    public void setElevation(int elevation) {
        super.setElevation(elevation);
    }

    public String getTextDescription() {
        return super.getPoiTextDescription();
    }

    public void setTextDescription(String textDescription) {
        super.setPoiTextDescription(textDescription);
    }

    public int getMaxBicycles() {
        return maxBicycles;
    }

    public void setMaxBicycles(int maxBicycles) {
        this.maxBicycles = maxBicycles;
    }

    public int getMaxScooters() {
        return maxScooters;
    }

    public void setMaxScooters(int maxScooters) {
        this.maxScooters = maxScooters;
    }

    public int getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(int inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public int getInputCurrent() {
        return inputCurrent;
    }

    public void setInputCurrent(int inputCurrent) {
        this.inputCurrent = inputCurrent;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Parque\n" +
                super.toString() +
                "MaxBicycles = " + maxBicycles + "\n" +
                "MaxScooters = " + maxScooters + "\n" +
                "InputVoltage = " + inputVoltage + "\n" +
                "InputCurrent = " + inputCurrent + '\n';
    }

}
