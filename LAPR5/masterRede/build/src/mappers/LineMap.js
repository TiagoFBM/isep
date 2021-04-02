"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const Mapper_1 = require("../core/infra/Mapper");
const Line_1 = require("../domain/line/Line");
const VehicleTypeMap_1 = __importDefault(require("./VehicleTypeMap"));
const DriverTypeMap_1 = __importDefault(require("./DriverTypeMap"));
const lodash_1 = require("lodash");
const PathMap_1 = __importDefault(require("./PathMap"));
class LineMapper extends Mapper_1.Mapper {
    static toDTO(line) {
        var newLinePaths = [];
        if (!lodash_1.isUndefined(line.linePaths)) {
            line.linePaths.forEach(element => {
                var tmp = {
                    path: PathMap_1.default.toDTO(element.path),
                    orientation: element.linePathOrientation.value
                };
                newLinePaths.push(tmp);
            });
        }
        var newDeniedVehicles = [];
        if (!lodash_1.isUndefined(line.deniedVehicles)) {
            line.deniedVehicles.forEach(element => {
                newDeniedVehicles.push(VehicleTypeMap_1.default.toDTO(element).code);
            });
        }
        var newAllowedDrivers = [];
        if (!lodash_1.isUndefined(line.allowedDrivers)) {
            line.allowedDrivers.forEach(element => {
                newAllowedDrivers.push(DriverTypeMap_1.default.toDTO(element).code);
            });
        }
        var newDeniedDrivers = [];
        if (!lodash_1.isUndefined(line.deniedDrivers)) {
            line.deniedDrivers.forEach(element => {
                newDeniedDrivers.push(DriverTypeMap_1.default.toDTO(element).code);
            });
        }
        var teste = {
            code: line.lineCode.value,
            description: line.lineDescription.value,
            linePaths: newLinePaths,
            allowedVehicles: line.allowedVehicles,
            deniedVehicles: newDeniedVehicles,
            allowedDrivers: newAllowedDrivers,
            deniedDrivers: newDeniedDrivers,
            lineColor: line.lineColor.value
        };
        return teste;
    }
    static toDomain(line) {
        const lineOrError = Line_1.Line.create(line);
        if (lineOrError.isFailure) {
            console.log("Error Converting LineDTO toDomain: %s", lineOrError.error);
            return null;
        }
        let newLine = lineOrError.getValue();
        line.allowedVehicles.forEach(elem => {
            newLine.addAllowedVehicleTypesDTO(elem);
        });
        return newLine;
    }
    static toPersistence(line) {
        console.log("line.allowedVehicles in toPersistence");
        console.log(line.allowedVehicles);
        var newLinePaths = [];
        line.linePaths.forEach(elem => {
            newLinePaths.push({ path: elem.props.path, orientation: elem.props.orientation.value });
        });
        if (!lodash_1.isUndefined(line.deniedVehicles)) {
            var newDeniedVehicles = [];
            line.deniedVehicles.forEach(element => {
                newDeniedVehicles.push(VehicleTypeMap_1.default.toPersistence(element));
            });
        }
        if (!lodash_1.isUndefined(line.allowedDrivers)) {
            var newAllowedDrivers = [];
            line.allowedDrivers.forEach(element => {
                newAllowedDrivers.push(DriverTypeMap_1.default.toPersistence(element));
            });
        }
        if (!lodash_1.isUndefined(line.deniedDrivers)) {
            var newDeniedDrivers = [];
            line.deniedDrivers.forEach(element => {
                newDeniedDrivers.push(DriverTypeMap_1.default.toPersistence(element));
            });
        }
        const newLine = {
            code: line.lineCode.value,
            description: line.lineDescription.value,
            linePaths: newLinePaths,
            allowedVehicles: line.allowedVehicles,
            deniedVehicles: newDeniedVehicles,
            allowedDrivers: newAllowedDrivers,
            deniedDrivers: newDeniedDrivers,
            lineColor: line.lineColor.value
        };
        console.log("newLine");
        console.log(newLine);
        return newLine;
    }
}
exports.default = LineMapper;
//# sourceMappingURL=LineMap.js.map