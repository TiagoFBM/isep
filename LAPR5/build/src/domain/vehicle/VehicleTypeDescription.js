"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleTypeDescription = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class VehicleTypeDescription extends ValueObject_1.ValueObject {
    get value() {
        return this.props.value;
    }
    constructor(props) {
        super(props);
    }
    static create(description) {
        const descriptionIsSmallerThan250 = Guard_1.Guard.hasLessThanMaxStringSize(description, 250, 'description');
        if (!descriptionIsSmallerThan250.succeeded) {
            return Result_1.Result.fail(descriptionIsSmallerThan250.message);
        }
        return Result_1.Result.ok(new VehicleTypeDescription({ value: description }));
    }
}
exports.VehicleTypeDescription = VehicleTypeDescription;
//# sourceMappingURL=VehicleTypeDescription.js.map