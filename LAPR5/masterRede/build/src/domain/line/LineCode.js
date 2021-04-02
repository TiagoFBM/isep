"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.LineCode = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class LineCode extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(lineCode) {
        const codeIsAlphanumeric = Guard_1.Guard.isAlphaNumericString(lineCode, 'lineCode');
        if (!codeIsAlphanumeric.succeeded) {
            return Result_1.Result.fail(codeIsAlphanumeric.message);
        }
        const codeBiggerThan1 = Guard_1.Guard.isBiggerThanSize(lineCode, 1, 'lineCode');
        if (!codeBiggerThan1.succeeded) {
            return Result_1.Result.fail(codeBiggerThan1.message);
        }
        return Result_1.Result.ok(new LineCode({ value: lineCode }));
    }
    toString() {
        return this.props.value;
    }
}
exports.LineCode = LineCode;
//# sourceMappingURL=LineCode.js.map