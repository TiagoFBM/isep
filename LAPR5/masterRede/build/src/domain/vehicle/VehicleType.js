"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleType = void 0;
const AggregateRoot_1 = require("../../core/domain/AggregateRoot");
const Guard_1 = require("../../core/logic/Guard");
const Result_1 = require("../../core/logic/Result");
const VehicleTypeCode_1 = require("./VehicleTypeCode");
const VehicleTypeDescription_1 = require("./VehicleTypeDescription");
const VehicleTypeAutonomy_1 = require("./VehicleTypeAutonomy");
const VehicleTypeFuelType_1 = require("./VehicleTypeFuelType");
const VehicleTypeCostPerKilometer_1 = require("./VehicleTypeCostPerKilometer");
const VehicleTypeAverageConsuption_1 = require("./VehicleTypeAverageConsuption");
const VehicleTypeAverageSpeed_1 = require("./VehicleTypeAverageSpeed");
class VehicleType extends AggregateRoot_1.AggregateRoot {
    constructor(props) {
        super(props);
    }
    get vehicleTypeId() {
        return VehicleTypeCode_1.VehicleTypeCode.create(this.vehicleTypeCode.value).getValue();
    }
    get vehicleTypeDescription() {
        return this.props.description;
    }
    get vehicleTypeCode() {
        return this.props.code;
    }
    get vehicleTypeAutonomy() {
        return this.props.autonomy;
    }
    get vehicleTypeFuelType() {
        return this.props.fuelType;
    }
    get vehicleTypeCostPerKilometer() {
        return this.props.costPerKilometer;
    }
    get vehicleTypeAverageConsuption() {
        return this.props.averageConsuption;
    }
    get vehicleTypeAverageSpeed() {
        return this.props.averageSpeed;
    }
    set vehicleTypeCode(code) {
        this.props.code = code;
    }
    set vehicleTypeDescription(description) {
        this.props.description = description;
    }
    set vehicleTypeAutonomy(autonomy) {
        this.props.autonomy = autonomy;
    }
    set vehicleTypeFuelType(fuelType) {
        this.props.fuelType = fuelType;
    }
    set vehicleTypeCostPerKilometer(costPerKM) {
        this.props.costPerKilometer = costPerKM;
    }
    set vehicleTypeAverageConsuption(avgConsuption) {
        this.props.averageConsuption = avgConsuption;
    }
    set vehicleTypeAverageSpeed(avgSpeed) {
        this.props.averageSpeed = avgSpeed;
    }
    static create(vehicleTypeDTO) {
        if (Guard_1.Guard.isNull(vehicleTypeDTO, 'vehicleTypeDTO').succeeded) {
            return Result_1.Result.fail("vehicleTypeDTO cant be null.");
        }
        if (!Guard_1.Guard.againstNullOrUndefined(vehicleTypeDTO.code, 'code').succeeded || Guard_1.Guard.isEmptyString(vehicleTypeDTO.code, 'code').succeeded) {
            return Result_1.Result.fail("VehicleTypeDTO code is invalid");
        }
        if (!Guard_1.Guard.againstNullOrUndefined(vehicleTypeDTO.description, 'description').succeeded || Guard_1.Guard.isEmptyString(vehicleTypeDTO.description, 'description').succeeded) {
            return Result_1.Result.fail("VehicleTypeDTO description is invalid");
        }
        if (!Guard_1.Guard.againstNullOrUndefined(vehicleTypeDTO.autonomy, 'autonomy').succeeded) {
            return Result_1.Result.fail("VehicleTypeDTO autonomy is invalid");
        }
        if (!Guard_1.Guard.againstNullOrUndefined(vehicleTypeDTO.fuelType, 'fuel type').succeeded || Guard_1.Guard.isEmptyString(vehicleTypeDTO.fuelType, 'fuel type').succeeded) {
            return Result_1.Result.fail("VehicleTypeDTO fuel type is invalid");
        }
        if (!Guard_1.Guard.againstNullOrUndefined(vehicleTypeDTO.costPerKilometer, 'cost per kilometer').succeeded) {
            return Result_1.Result.fail("VehicleTypeDTO cost per kilometer is invalid");
        }
        if (!Guard_1.Guard.againstNullOrUndefined(vehicleTypeDTO.averageConsuption, 'average consuption').succeeded) {
            return Result_1.Result.fail("VehicleTypeDTO average consuption is invalid");
        }
        if (!Guard_1.Guard.againstNullOrUndefined(vehicleTypeDTO.averageSpeed, 'average speed').succeeded) {
            return Result_1.Result.fail("VehicleTypeDTO average speed is invalid");
        }
        const code = VehicleTypeCode_1.VehicleTypeCode.create(vehicleTypeDTO.code);
        if (code.isFailure) {
            return Result_1.Result.fail(code.error);
        }
        const description = VehicleTypeDescription_1.VehicleTypeDescription.create(vehicleTypeDTO.description);
        if (description.isFailure) {
            return Result_1.Result.fail(description.error);
        }
        const autonomy = VehicleTypeAutonomy_1.VehicleTypeAutonomy.create(vehicleTypeDTO.autonomy);
        if (autonomy.isFailure) {
            return Result_1.Result.fail(autonomy.error);
        }
        const fuelType = VehicleTypeFuelType_1.VehicleTypeFuelType.create(vehicleTypeDTO.fuelType);
        if (fuelType.isFailure) {
            return Result_1.Result.fail(fuelType.error);
        }
        const costPerKM = VehicleTypeCostPerKilometer_1.VehicleTypeCostPerKilometer.create(vehicleTypeDTO.costPerKilometer);
        if (costPerKM.isFailure) {
            return Result_1.Result.fail(costPerKM.error);
        }
        const averageConsuption = VehicleTypeAverageConsuption_1.VehicleTypeAverageConsuption.create(vehicleTypeDTO.averageConsuption);
        if (averageConsuption.isFailure) {
            return Result_1.Result.fail(averageConsuption.error);
        }
        const averageSpeed = VehicleTypeAverageSpeed_1.VehicleTypeAverageSpeed.create(vehicleTypeDTO.averageSpeed);
        if (averageSpeed.isFailure) {
            return Result_1.Result.fail(averageSpeed.error);
        }
        return Result_1.Result.ok(new VehicleType({ code: code.getValue(), description: description.getValue(), autonomy: autonomy.getValue(), fuelType: fuelType.getValue(), costPerKilometer: costPerKM.getValue(), averageConsuption: averageConsuption.getValue(), averageSpeed: averageSpeed.getValue() }));
    }
}
exports.VehicleType = VehicleType;
//# sourceMappingURL=VehicleType.js.map