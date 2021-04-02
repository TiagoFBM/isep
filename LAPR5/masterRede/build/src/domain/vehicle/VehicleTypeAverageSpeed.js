"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleTypeAverageSpeed = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class VehicleTypeAverageSpeed extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(avgSpeed) {
        const speedIsPositive = Guard_1.Guard.isPositiveOrZero(avgSpeed, 'Average Speed');
        if (!speedIsPositive.succeeded) {
            return Result_1.Result.fail(speedIsPositive.message);
        }
        return Result_1.Result.ok(new VehicleTypeAverageSpeed({ value: avgSpeed }));
    }
}
exports.VehicleTypeAverageSpeed = VehicleTypeAverageSpeed;
//# sourceMappingURL=VehicleTypeAverageSpeed.js.map