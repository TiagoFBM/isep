"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleTypeCostPerKilometer = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class VehicleTypeCostPerKilometer extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(costPerKM) {
        const costIsPositive = Guard_1.Guard.isPositiveOrZero(costPerKM, 'Cost per Kilometer');
        if (!costIsPositive.succeeded) {
            return Result_1.Result.fail(costIsPositive.message);
        }
        return Result_1.Result.ok(new VehicleTypeCostPerKilometer({ value: costPerKM }));
    }
}
exports.VehicleTypeCostPerKilometer = VehicleTypeCostPerKilometer;
//# sourceMappingURL=VehicleTypeCostPerKilometer.js.map