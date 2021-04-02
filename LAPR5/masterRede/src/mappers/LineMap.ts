import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { Line } from "../domain/line/Line";
import ILineDTO from "../dto/ILineDTO";
import ILinePathDTO from "../dto/ILinePathDTO";
import IVehicleTypeDTO from "../dto/IVehicleTypeDTO";
import VehicleTypeMap from "./VehicleTypeMap";
import DriverTypeMap from "./DriverTypeMap";
import { isUndefined } from "lodash";
import PathMapper from "./PathMap";
import { Path } from "../domain/path/Path";
import { VehicleType } from "../domain/vehicle/VehicleType";
import { DriverType } from "../domain/driver/DriverType";
import IDriverTypeDTO from "../dto/IDriverTypeDTO";

export default class LineMapper extends Mapper<Line> {

    public static toDTO(line: Line): ILineDTO {
        var newLinePaths: Array<ILinePathDTO> = [];

        if (!isUndefined(line.linePaths)) {
            line.linePaths.forEach(element => {
                var tmp = {
                    path: PathMapper.toDTO(element.path as Path),
                    orientation: element.linePathOrientation.value
                };
                newLinePaths.push(tmp);
            });
        }

        var newAllowedVehicles: Array<IVehicleTypeDTO> = []
        if (!isUndefined(line.allowedVehicles)) {
            line.allowedVehicles.forEach(element => {
                var tmp = VehicleTypeMap.toDTO(element as VehicleType);
                newAllowedVehicles.push(tmp);
            });
        }

        var newDeniedVehicles: Array<IVehicleTypeDTO> = []
        if (!isUndefined(line.deniedVehicles)) {
            line.deniedVehicles.forEach(element => {
                var tmp = VehicleTypeMap.toDTO(element as VehicleType);
                newDeniedVehicles.push(tmp);
            });
        }

        var newAllowedDrivers: Array<IDriverTypeDTO> = [];
        if (!isUndefined(line.allowedDrivers)) {
            line.allowedDrivers.forEach(element => {
                var tmp = DriverTypeMap.toDTO(element as DriverType);
                newAllowedDrivers.push(tmp);
            });
        }

        var newDeniedDrivers: Array<IDriverTypeDTO> = [];
        if (!isUndefined(line.deniedDrivers)) {
            line.deniedDrivers.forEach(element => {
                var tmp = DriverTypeMap.toDTO(element as DriverType);
                newDeniedDrivers.push(tmp);
            });
        }

        var teste = {
            code: line.lineCode.value,
            description: line.lineDescription.value,
            linePaths: newLinePaths,
            allowedVehicles: newAllowedVehicles,
            deniedVehicles: newDeniedVehicles,
            allowedDrivers: newAllowedDrivers,
            deniedDrivers: newDeniedDrivers,
            lineColor: line.lineColor.value
        } as ILineDTO;

        return teste;
    }

    public static toDomain(line: any | Model<ILineDTO & Document>): Line {
        const lineOrError = Line.create(
            line
        );

        if (lineOrError.isFailure) {
            console.log("Error Converting LineDTO toDomain: %s", lineOrError.error);
            return null;
        }

        let newLine = lineOrError.getValue();

        return newLine;
    }

    public static toPersistence(line: Line): any {
        var newLinePaths = [];
        line.linePaths.forEach(elem => {
            newLinePaths.push({ path: elem.props.path, orientation: elem.props.orientation.value });
        })

        if (!isUndefined(line.allowedVehicles)) {
            var newAllowedVehicles = [];
            line.allowedVehicles.forEach(elem => {
                newAllowedVehicles.push(elem);
            })
        }

        if (!isUndefined(line.deniedVehicles)) {
            var newDeniedVehicles = [];
            line.deniedVehicles.forEach(elem => {
                newDeniedVehicles.push(elem);
            })
        }

        if (!isUndefined(line.allowedDrivers)) {
            var newAllowedDrivers = [];
            line.allowedDrivers.forEach(elem => {
                newAllowedDrivers.push(elem);
            })
        }

        if (!isUndefined(line.deniedDrivers)) {
            var newDeniedDrivers = [];
            line.deniedDrivers.forEach(elem => {
                newDeniedDrivers.push(elem);
            })

        }

        return {
            code: line.lineCode.value,
            description: line.lineDescription.value,
            linePaths: newLinePaths,
            allowedVehicles: newAllowedVehicles,
            deniedVehicles: newDeniedVehicles,
            allowedDrivers: newAllowedDrivers,
            deniedDrivers: newDeniedDrivers,
            lineColor: line.lineColor.value
        };
    }

}