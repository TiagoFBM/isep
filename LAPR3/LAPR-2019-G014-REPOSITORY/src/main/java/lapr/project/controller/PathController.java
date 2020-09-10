package lapr.project.controller;

import lapr.project.data.PathDB;

public class PathController {
    private PathDB pathDB;

    public PathController(){
        pathDB = new PathDB();
    }

    public void setPathDB(PathDB pdb){
        this.pathDB = pdb;
    }

    public boolean registerPath(double latitudeA, double longitudeA, double latitudeB, double longitudeB, double kineticCoefficient,
                            double windDirection, double windSpeed){
        return pathDB.registerPath( latitudeA, longitudeA,  latitudeB,  longitudeB,  kineticCoefficient, windDirection, windSpeed);
    }
}
