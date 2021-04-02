"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PathCode = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class PathCode extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(code) {
        const codeIsAlphanumeric = Guard_1.Guard.isAlphaNumericString(code, "code");
        if (!codeIsAlphanumeric.succeeded) {
            return Result_1.Result.fail(codeIsAlphanumeric.message);
        }
        const codeIsSmallerThan20 = Guard_1.Guard.hasLessThanMaxStringSize(code, 20, "code");
        if (!codeIsSmallerThan20.succeeded) {
            return Result_1.Result.fail(codeIsSmallerThan20.message);
        }
        return Result_1.Result.ok(new PathCode({ value: code }));
    }
}
exports.PathCode = PathCode;
//# sourceMappingURL=PathCode.js.map