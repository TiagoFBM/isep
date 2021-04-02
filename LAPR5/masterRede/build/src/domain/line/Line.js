"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Line = void 0;
const AggregateRoot_1 = require("../../core/domain/AggregateRoot");
const Guard_1 = require("../../core/logic/Guard");
const Result_1 = require("../../core/logic/Result");
const LineCode_1 = require("./LineCode");
const LineDescription_1 = require("./LineDescription");
const LineColor_1 = require("./LineColor");
const LinePath_1 = require("./LinePath");
const VehicleType_1 = require("../vehicle/VehicleType");
class Line extends AggregateRoot_1.AggregateRoot {
    constructor(props) {
        super(props);
    }
    get lineCode() {
        return this.props.code;
    }
    get lineDescription() {
        return this.props.description;
    }
    get linePaths() {
        return this.props.linePaths;
    }
    get allowedVehicles() {
        return this.allowedVehicles;
    }
    get deniedVehicles() {
        return this.props.deniedVehicles;
    }
    get allowedDrivers() {
        return this.props.allowedDrivers;
    }
    get deniedDrivers() {
        return this.props.deniedDrivers;
    }
    get lineColor() {
        return this.props.lineColor;
    }
    addLinePath(linePath) {
        const newLength = this.props.linePaths.length + 1;
        if (this.props.linePaths.push(linePath) == newLength) {
            return true;
        }
        else {
            return false;
        }
    }
    addAllowedVehicleTypesID(vehicleTypeID) {
        const newLength = this.props.allowedVehicles.length + 1;
        if (this.props.allowedVehicles.push(vehicleTypeID) == newLength) {
            return true;
        }
        else {
            return false;
        }
    }
    addAllowedVehicleTypesDTO(vehicleTypeDTO) {
        if (Guard_1.Guard.isNull(vehicleTypeDTO, "firstNodeID").succeeded) {
            return;
        }
        var vehicleType = VehicleType_1.VehicleType.create(vehicleTypeDTO);
        if (vehicleType.isFailure) {
            return false;
        }
        this.allowedVehicles.push(vehicleType.getValue());
        return true;
    }
    addDeniedVehicleTypes(vehicleType) {
        const newLength = this.props.deniedVehicles.length + 1;
        if (this.props.deniedVehicles.push(vehicleType) == newLength) {
            return true;
        }
        else {
            return false;
        }
    }
    addAllowedDriverTypes(driverType) {
        const newLength = this.props.allowedDrivers.length + 1;
        if (this.props.allowedDrivers.push(driverType) == newLength) {
            return true;
        }
        else {
            return false;
        }
    }
    addDeniedDriverTypes(driverType) {
        const newLength = this.props.deniedDrivers.length + 1;
        if (this.props.deniedDrivers.push(driverType) == newLength) {
            return true;
        }
        else {
            return false;
        }
    }
    static create(LineDTO) {
        if (Guard_1.Guard.isNull(LineDTO, 'LineDTO').succeeded) {
            return Result_1.Result.fail("LineDTO can\'t be null.");
        }
        if (Guard_1.Guard.isNull(LineDTO.code, 'LineDTO.code').succeeded || Guard_1.Guard.isEmptyString(LineDTO.code, 'LineDTO.code').succeeded) {
            return Result_1.Result.fail("LineDTO Code is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.description, 'LineDTO.description').succeeded || Guard_1.Guard.isEmptyString(LineDTO.description, 'LineDTO.description').succeeded) {
            return Result_1.Result.fail("LineDTO Description is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.linePaths, 'LineDTO.description').succeeded) {
            return Result_1.Result.fail("LineDTO linePaths is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.allowedDrivers, 'LineDTO.allowedDrivers').succeeded) {
            return Result_1.Result.fail("LineDTO allowedDrivers is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.deniedDrivers, 'LineDTO.deniedDrivers').succeeded) {
            return Result_1.Result.fail("LineDTO deniedDrivers is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.allowedVehicles, 'LineDTO.allowedVehicles').succeeded) {
            return Result_1.Result.fail("LineDTO allowedVehicles is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.deniedVehicles, 'LineDTO.deniedVehicles').succeeded) {
            return Result_1.Result.fail("LineDTO deniedVehicles is invalid.");
        }
        if (Guard_1.Guard.isNull(LineDTO.lineColor, 'LineDTO.lineColor').succeeded) {
            return Result_1.Result.fail("LineDTO lineColor is invalid.");
        }
        const lineCode = LineCode_1.LineCode.create(LineDTO.code);
        if (lineCode.isFailure) {
            return Result_1.Result.fail(lineCode.error);
        }
        const lineDescription = LineDescription_1.LineDescription.create(LineDTO.description);
        if (lineDescription.isFailure) {
            return Result_1.Result.fail(lineDescription.error);
        }
        var newLinePaths = [];
        for (let i in LineDTO.linePaths) {
            const element = LineDTO.linePaths[i];
            var linePathOrFailure = LinePath_1.LinePath.create(element);
            if (linePathOrFailure.isFailure) {
                return Result_1.Result.fail("LinePath couldn't be created:" + linePathOrFailure.error);
            }
            newLinePaths.push(linePathOrFailure.getValue());
        }
        var newAllowedVehicles = [];
        var newDeniedVehicles = [];
        var newAllowedDrivers = [];
        var newDeniedDrivers = [];
        const newColorLine = LineColor_1.LineColor.create(LineDTO.lineColor);
        if (newColorLine.isFailure) {
            return Result_1.Result.fail(newColorLine.error);
        }
        return Result_1.Result.ok(new Line({
            code: lineCode.getValue(),
            description: lineDescription.getValue(),
            linePaths: newLinePaths,
            allowedVehicles: newAllowedVehicles,
            deniedVehicles: newDeniedVehicles,
            allowedDrivers: newAllowedDrivers,
            deniedDrivers: newDeniedDrivers,
            lineColor: newColorLine.getValue()
        }));
    }
}
exports.Line = Line;
//# sourceMappingURL=Line.js.map