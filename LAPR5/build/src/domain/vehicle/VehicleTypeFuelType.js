"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleTypeFuelType = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
const EnumFuelType_1 = require("./EnumFuelType");
class VehicleTypeFuelType extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(fuelType) {
        const fuelTypeNotNull = Guard_1.Guard.againstNullOrUndefined(fuelType, "Fuel Type");
        if (!fuelTypeNotNull.succeeded) {
            return Result_1.Result.fail(fuelTypeNotNull.message);
        }
        if (EnumFuelType_1.EnumFuelType[fuelType] == null) {
            return Result_1.Result.fail("Fuel Type doesnt exists in DataBase");
        }
        return Result_1.Result.ok(new VehicleTypeFuelType({ value: EnumFuelType_1.EnumFuelType[fuelType] }));
    }
}
exports.VehicleTypeFuelType = VehicleTypeFuelType;
//# sourceMappingURL=VehicleTypeFuelType.js.map