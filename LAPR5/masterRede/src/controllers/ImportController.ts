import { Inject } from "typedi";
import config from "../../config";
import { Result } from "../core/logic/Result";
import IDriverTypeService from "../services/IServices/IDriverTypeService";
import INodeService from "../services/IServices/INodeService";
import IVehicleTypeService from "../services/IServices/IVehicleTypeService";
import IImportService from "../services/IServices/IImportService";
import IImportController from "./IControllers/IImportController";
import INodeDTO from "../dto/INodeDTO";
import { NextFunction, Response } from "express";
import ILineService from "../services/IServices/ILineService";
import { Request } from "multer";
import IPathService from "../services/IServices/IPathService";
import IPathDTO from "../dto/IPathDTO";
import IPathSegmentDTO from "../dto/IPathSegmentDTO";
import ILinePathDTO from "../dto/ILinePathDTO";
import ILineDTO from "../dto/ILineDTO";

export default class ImportController implements IImportController /* TODO: extends ../core/infra/BaseController */ {
    constructor(
        @Inject(config.services.driverType.name) private driverTypeServiceInstance: IDriverTypeService,
        @Inject(config.services.node.name) private nodeServiceInstance: INodeService,
        @Inject(config.services.vehicleType.name) private vehicleTypeServiceInstance: IVehicleTypeService,
        @Inject(config.services.import.name) private importerServiceInstance: IImportService,
        @Inject(config.services.line.name) private lineServiceInstance: ILineService,
        @Inject(config.services.path.name) private PathServiceInstance: IPathService
    ) { }

    public async import(req: Request, res: Response, next: NextFunction) {
        try {
            const importerData = await this.importerServiceInstance.toJSON(req.file);
            const pathJSON = importerData["GlDocumentInfo"]["world"]["GlDocument"]["GlDocumentNetwork"]["Network"];

            await this.importNodes(pathJSON, res, next);
            await this.importVehicleTypes(pathJSON, res, next);
            await this.importDriverTypes(pathJSON, res, next);
            await this.importPath(pathJSON, res, next);
            await this.importLines(pathJSON, res, next);
            return res.status(201).send("Imported File with success!");

        }
        catch (e) {
            return next(e);
        }
    };


    private async importPath(pathJSON, res: Response, next: NextFunction) {
        if (pathJSON["Paths"]["Path"] !== undefined) {
            if (Array.isArray(pathJSON["Paths"]["Path"])) {
                for (const pathElement of pathJSON["Paths"]["Path"]) {
                    var PathNodes: Array<IPathSegmentDTO> = [];
                    var segmento: IPathSegmentDTO;
                    const pathNode = pathElement["PathNodes"]["PathNode"];

                    for (let i = 0; i < pathNode.length - 1; i++) {
                        var firstNode = pathNode[i];
                        var secondNode = pathNode[i + 1];

                        segmento = {
                            firstNodeID: firstNode.Node,
                            secondNodeID: secondNode.Node,
                            travelTimeBetweenNodes: secondNode.Duration,
                            distanceBetweenNodes: secondNode.Distance
                        }
                        PathNodes.push(segmento);
                    }
                    var path: IPathDTO = {
                        code: pathElement.key,
                        isEmpty: pathElement.IsEmpty,
                        segments: PathNodes
                    }
                    const pathOrError = await this.PathServiceInstance.createPath(path as IPathDTO) as Result<IPathDTO>;

                    if ((pathOrError).isFailure) {
                        return res.status(402).send(pathOrError);
                    }
                }
            } else {
                const pathElement = pathJSON["Paths"]["Path"]["PathNodes"]["PathNode"];
                var PathNodes: Array<IPathSegmentDTO> = [];
                var segmento: IPathSegmentDTO;
                for (let i = 0; i < pathElement.length - 1; i++) {
                    var firstNode = pathElement[i];
                    var secondNode = pathElement[i + 1];

                    segmento = {
                        firstNodeID: firstNode.Node,
                        secondNodeID: secondNode.Node,
                        travelTimeBetweenNodes: secondNode.Duration,
                        distanceBetweenNodes: secondNode.Distance
                    }
                    PathNodes.push(segmento);
                }
                var path: IPathDTO = {
                    code: pathJSON["Paths"]["Path"].key,
                    isEmpty: pathJSON["Paths"]["Path"].IsEmpty,
                    segments: PathNodes
                }
                const pathOrError = await this.PathServiceInstance.createPath(path as IPathDTO) as Result<IPathDTO>;

                if ((pathOrError).isFailure) {
                    return res.status(402).send(pathOrError);
                }
            }
        }
    }

    private async importLines(pathJSON, res: Response, next: NextFunction) {
        if (pathJSON["Lines"]["Line"] !== undefined) {
            for (const lineElement of pathJSON["Lines"]["Line"]) {
                var linePathsArray:Array<ILinePathDTO>= [];
                if (lineElement["LinePaths"]["LinePath"] !== undefined) {
                    lineElement["LinePaths"]["LinePath"].forEach(elementLinePath => {
                        linePathsArray.push({
                            path: elementLinePath.Path,
                            orientation: elementLinePath.Orientation
                        });
                    });
                }

                var lineAllowedVehiclesArray = [];
                if (lineElement["AllowedVehicles"]["AllowedVehicle"] !== undefined) {
                    lineElement["AllowedVehicles"]["AllowedVehicle"].forEach(allowedVehicle => {
                        lineAllowedVehiclesArray.push(
                            allowedVehicle.Name
                        );
                    });
                }

                var lineDeniedVehiclesArray = [];
                if (lineElement["DeniedVehicles"]["DeniedVehicle"] !== undefined) {
                    lineElement["DeniedVehicles"]["DeniedVehicle"].forEach(deniedVehicle => {
                        lineDeniedVehiclesArray.push(
                            deniedVehicle.Name
                        );
                    });
                }

                var lineAllowedDriversArray = [];
                if (lineElement["AllowedDrivers"]["AllowedDriver"] !== undefined) {
                    lineElement["AllowedDrivers"]["AllowedDriver"].forEach(allowedDriver => {
                        lineAllowedDriversArray.push(
                            allowedDriver.Name
                        );
                    });
                }

                var lineDeniedDriversArray = [];
                if (lineElement["DeniedDrivers"]["DeniedDriver"] !== undefined) {
                    lineElement["DeniedDrivers"]["DeniedDriver"].forEach(deniedDriver => {
                        lineDeniedDriversArray.push(
                            deniedDriver.Name
                        );
                    });
                }

                var line: ILineDTO = {
                    code: lineElement.key,
                    description: lineElement.Name,
                    linePaths: linePathsArray,
                    allowedVehicles: lineAllowedVehiclesArray,
                    deniedVehicles: lineDeniedVehiclesArray,
                    allowedDrivers: lineAllowedDriversArray,
                    deniedDrivers: lineDeniedDriversArray,
                    lineColor: lineElement.Color,
                }

                const lineOrError = await this.lineServiceInstance.createLine(line);

                if ((lineOrError).isFailure) {
                    return res.status(402).send(lineOrError.error);
                }

            };
        }
    }

    private async importNodes(pathJSON, res: Response, next: NextFunction) {
        if (pathJSON["Nodes"]["Node"] !== undefined) {
            for (const elementNode of pathJSON["Nodes"]["Node"]) {
                try {
                    var arrayCrewTravelTimes = [];
                    if (elementNode["CrewTravelTimes"]["CrewTravelTime"] !== undefined) {
                        if (Array.isArray(elementNode["CrewTravelTimes"]["CrewTravelTime"])) {
                            (elementNode["CrewTravelTimes"]["CrewTravelTime"]);

                            elementNode["CrewTravelTimes"]["CrewTravelTime"].forEach(elementCrew => {
                                var crewTraveltimesDTO = {
                                    node: elementCrew.Node,
                                    duration: elementCrew.Duration
                                };
                                arrayCrewTravelTimes.push(crewTraveltimesDTO);
                            });
                        } else {
                            var crewTraveltimesDTO = {
                                node: elementNode["CrewTravelTimes"]["CrewTravelTime"].Node,
                                duration: elementNode["CrewTravelTimes"]["CrewTravelTime"].Duration
                            };
                            arrayCrewTravelTimes.push(crewTraveltimesDTO);
                        }
                    }

                    var nodeDTO = {
                        code: elementNode.key,
                        name: elementNode.Name,
                        shortName: elementNode.ShortName,
                        latitude: elementNode.Latitude,
                        longitude: elementNode.Longitude,
                        isReliefPoint: elementNode.IsReliefPoint,
                        isDepot: elementNode.IsDepot,
                        crewTravelTimes: arrayCrewTravelTimes
                    }
                    const nodeOrError = await this.nodeServiceInstance.createNode(nodeDTO as INodeDTO) as Result<INodeDTO>;
                    if (nodeOrError.isFailure) {
                        return res.status(402).send(nodeOrError.error);
                    }
                }
                catch (e) {
                    return next(e);
                }

            };
        }

    }

    private async importVehicleTypes(pathJSON, res: Response, next: NextFunction) {
        if (pathJSON["VehicleTypes"]["VehicleType"] !== undefined) {
            for (const elementVehicle of pathJSON["VehicleTypes"]["VehicleType"]) {
                try {
                    var vehicleDTO = {
                        code: elementVehicle.Code,
                        description: elementVehicle.Name,
                        fuelType: elementVehicle.FuelType,
                        autonomy: elementVehicle.Autonomy,
                        costPerKilometer: elementVehicle.Cost,
                        averageConsuption: elementVehicle.Consumption,
                        averageSpeed: elementVehicle.AverageSpeed
                    }
                    const vehicleTypeOrError = await this.vehicleTypeServiceInstance.createVehicleType(vehicleDTO);
                    if (vehicleTypeOrError.isFailure) {
                        return res.status(402).send(vehicleTypeOrError.error);
                    }
                }
                catch (e) {
                    return next(e);
                }
            };
        }
    }

    private async importDriverTypes(pathJSON, res: Response, next: NextFunction) {
        if (pathJSON["DriverTypes"]["DriverType"] !== undefined) {
            for (const elementDriver of pathJSON["DriverTypes"]["DriverType"]) {
                try {
                    var driverTypeDTO = {
                        code: elementDriver.Name,
                        description: elementDriver.Description
                    }
                    const driverTypeOrError = await this.driverTypeServiceInstance.createDriverType(driverTypeDTO);

                    if (driverTypeOrError.isFailure) {
                        return res.status(402).send(driverTypeOrError.error);
                    }

                }
                catch (e) {
                    return next(e);
                }
            };
        }
    }

}