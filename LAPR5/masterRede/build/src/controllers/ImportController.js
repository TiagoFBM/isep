"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const typedi_1 = require("typedi");
const config_1 = __importDefault(require("../../config"));
let ImportController = class ImportController {
    constructor(driverTypeServiceInstance, nodeServiceInstance, vehicleTypeServiceInstance, importerServiceInstance, lineServiceInstance, PathServiceInstance) {
        this.driverTypeServiceInstance = driverTypeServiceInstance;
        this.nodeServiceInstance = nodeServiceInstance;
        this.vehicleTypeServiceInstance = vehicleTypeServiceInstance;
        this.importerServiceInstance = importerServiceInstance;
        this.lineServiceInstance = lineServiceInstance;
        this.PathServiceInstance = PathServiceInstance;
    }
    async import(req, res, next) {
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
    }
    ;
    async importPath(pathJSON, res, next) {
        if (pathJSON["Paths"]["Path"] !== undefined) {
            if (Array.isArray(pathJSON["Paths"]["Path"])) {
                for (const pathElement of pathJSON["Paths"]["Path"]) {
                    var PathNodes = [];
                    var segmento;
                    const pathNode = pathElement["PathNodes"]["PathNode"];
                    for (let i = 0; i < pathNode.length - 1; i++) {
                        var firstNode = pathNode[i];
                        var secondNode = pathNode[i + 1];
                        segmento = {
                            firstNodeID: firstNode.Node,
                            secondNodeID: secondNode.Node,
                            travelTimeBetweenNodes: secondNode.Duration,
                            distanceBetweenNodes: secondNode.Distance
                        };
                        PathNodes.push(segmento);
                    }
                    var path = {
                        code: pathElement.key,
                        isEmpty: pathElement.IsEmpty,
                        segments: PathNodes
                    };
                    const pathOrError = await this.PathServiceInstance.createPath(path);
                    if ((pathOrError).isFailure) {
                        return res.status(402).send(pathOrError);
                    }
                }
            }
            else {
                const pathElement = pathJSON["Paths"]["Path"]["PathNodes"]["PathNode"];
                var PathNodes = [];
                var segmento;
                for (let i = 0; i < pathElement.length - 1; i++) {
                    var firstNode = pathElement[i];
                    var secondNode = pathElement[i + 1];
                    segmento = {
                        firstNodeID: firstNode.Node,
                        secondNodeID: secondNode.Node,
                        travelTimeBetweenNodes: secondNode.Duration,
                        distanceBetweenNodes: secondNode.Distance
                    };
                    PathNodes.push(segmento);
                }
                var path = {
                    code: pathJSON["Paths"]["Path"].key,
                    isEmpty: pathJSON["Paths"]["Path"].IsEmpty,
                    segments: PathNodes
                };
                const pathOrError = await this.PathServiceInstance.createPath(path);
                if ((pathOrError).isFailure) {
                    return res.status(402).send(pathOrError);
                }
            }
        }
    }
    async importLines(pathJSON, res, next) {
        if (pathJSON["Lines"]["Line"] !== undefined) {
            for (const lineElement of pathJSON["Lines"]["Line"]) {
                var linePathsArray = [];
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
                        lineAllowedVehiclesArray.push(allowedVehicle.Name);
                    });
                }
                var lineDeniedVehiclesArray = [];
                if (lineElement["DeniedVehicles"]["DeniedVehicle"] !== undefined) {
                    lineElement["DeniedVehicles"]["DeniedVehicle"].forEach(deniedVehicle => {
                        lineDeniedVehiclesArray.push(deniedVehicle.Name);
                    });
                }
                var lineAllowedDriversArray = [];
                if (lineElement["AllowedDrivers"]["AllowedDriver"] !== undefined) {
                    lineElement["AllowedDrivers"]["AllowedDriver"].forEach(allowedDriver => {
                        lineAllowedDriversArray.push(allowedDriver.Name);
                    });
                }
                var lineDeniedDriversArray = [];
                if (lineElement["DeniedDrivers"]["DeniedDriver"] !== undefined) {
                    lineElement["DeniedDrivers"]["DeniedDriver"].forEach(deniedDriver => {
                        lineDeniedDriversArray.push(deniedDriver.Name);
                    });
                }
                var line = {
                    code: lineElement.key,
                    description: lineElement.Name,
                    linePaths: linePathsArray,
                    allowedVehicles: lineAllowedVehiclesArray,
                    deniedVehicles: lineDeniedVehiclesArray,
                    allowedDrivers: lineAllowedDriversArray,
                    deniedDrivers: lineDeniedDriversArray,
                    lineColor: lineElement.Color,
                };
                const lineOrError = await this.lineServiceInstance.createLine(line);
                if ((lineOrError).isFailure) {
                    return res.status(402).send(lineOrError.error);
                }
            }
            ;
        }
    }
    async importNodes(pathJSON, res, next) {
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
                        }
                        else {
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
                    };
                    const nodeOrError = await this.nodeServiceInstance.createNode(nodeDTO);
                    if (nodeOrError.isFailure) {
                        return res.status(402).send(nodeOrError.error);
                    }
                }
                catch (e) {
                    return next(e);
                }
            }
            ;
        }
    }
    async importVehicleTypes(pathJSON, res, next) {
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
                    };
                    const vehicleTypeOrError = await this.vehicleTypeServiceInstance.createVehicleType(vehicleDTO);
                    if (vehicleTypeOrError.isFailure) {
                        return res.status(402).send(vehicleTypeOrError.error);
                    }
                }
                catch (e) {
                    return next(e);
                }
            }
            ;
        }
    }
    async importDriverTypes(pathJSON, res, next) {
        if (pathJSON["DriverTypes"]["DriverType"] !== undefined) {
            for (const elementDriver of pathJSON["DriverTypes"]["DriverType"]) {
                try {
                    var driverTypeDTO = {
                        code: elementDriver.Name,
                        description: elementDriver.Description
                    };
                    const driverTypeOrError = await this.driverTypeServiceInstance.createDriverType(driverTypeDTO);
                    if (driverTypeOrError.isFailure) {
                        return res.status(402).send(driverTypeOrError.error);
                    }
                }
                catch (e) {
                    return next(e);
                }
            }
            ;
        }
    }
};
ImportController = __decorate([
    __param(0, typedi_1.Inject(config_1.default.services.driverType.name)),
    __param(1, typedi_1.Inject(config_1.default.services.node.name)),
    __param(2, typedi_1.Inject(config_1.default.services.vehicleType.name)),
    __param(3, typedi_1.Inject(config_1.default.services.import.name)),
    __param(4, typedi_1.Inject(config_1.default.services.line.name)),
    __param(5, typedi_1.Inject(config_1.default.services.path.name)),
    __metadata("design:paramtypes", [Object, Object, Object, Object, Object, Object])
], ImportController);
exports.default = ImportController;
//# sourceMappingURL=ImportController.js.map