package lapr.project.controller;

import lapr.project.data.PathDB;

import java.util.List;
import java.util.Map;

public class AddWindSupportInformationController {

    private PathDB pdb;

    public AddWindSupportInformationController() {
        pdb = new PathDB();
    }

    public void setPathDB(PathDB db){
        this.pdb = db;
    }

    public Map<String, List<String>> getActivePaths() {
        return pdb.getActivePaths();
    }

    public boolean addWindInformationToPath(int descriptionPath, double windDirectionDegrees, double averageWindSpeed) {
        return pdb.addWindInformationToPath(descriptionPath, windDirectionDegrees, averageWindSpeed);
    }
}
