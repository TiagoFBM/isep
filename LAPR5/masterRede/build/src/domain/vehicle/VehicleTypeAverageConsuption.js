"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleTypeAverageConsuption = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class VehicleTypeAverageConsuption extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(avgConsuption) {
        const costIsPositive = Guard_1.Guard.isPositiveOrZero(avgConsuption, 'Average Consuption');
        if (!costIsPositive.succeeded) {
            return Result_1.Result.fail(costIsPositive.message);
        }
        return Result_1.Result.ok(new VehicleTypeAverageConsuption({ value: avgConsuption }));
    }
}
exports.VehicleTypeAverageConsuption = VehicleTypeAverageConsuption;
//# sourceMappingURL=VehicleTypeAverageConsuption.js.map