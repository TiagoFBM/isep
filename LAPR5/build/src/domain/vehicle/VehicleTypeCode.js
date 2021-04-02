"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.VehicleTypeCode = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class VehicleTypeCode extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(code) {
        const codeIsAlphanumeric = Guard_1.Guard.isAlphaNumericString(code, 'code');
        if (!codeIsAlphanumeric.succeeded) {
            return Result_1.Result.fail(codeIsAlphanumeric.message);
        }
        const codeHasCorrectSize = Guard_1.Guard.hasCorrectStringSize(code, 20, 'code');
        if (!codeHasCorrectSize.succeeded) {
            return Result_1.Result.fail(codeHasCorrectSize.message);
        }
        return Result_1.Result.ok(new VehicleTypeCode({ value: code }));
    }
}
exports.VehicleTypeCode = VehicleTypeCode;
//# sourceMappingURL=VehicleTypeCode.js.map