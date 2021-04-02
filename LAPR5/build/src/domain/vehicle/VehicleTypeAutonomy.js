"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleTypeAutonomy = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class VehicleTypeAutonomy extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(autonomy) {
        const autonomyIsPositive = Guard_1.Guard.isPositiveOrZero(autonomy, "autonomy");
        if (!autonomyIsPositive.succeeded) {
            return Result_1.Result.fail(autonomyIsPositive.message);
        }
        return Result_1.Result.ok(new VehicleTypeAutonomy({ value: autonomy }));
    }
}
exports.VehicleTypeAutonomy = VehicleTypeAutonomy;
//# sourceMappingURL=VehicleTypeAutonomy.js.map