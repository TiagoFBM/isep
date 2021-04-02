"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.LineColor = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class LineColor extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(newValue) {
        const valueEmpty = Guard_1.Guard.isEmptyString(newValue, 'Color Value');
        if (valueEmpty.succeeded) {
            return Result_1.Result.fail("Color Value must not be empty.");
        }
        const validColor = Guard_1.Guard.isValidColor(newValue, 'Color Value');
        if (!validColor.succeeded) {
            return Result_1.Result.fail("Color Value must be valid.");
        }
        return Result_1.Result.ok(new LineColor({ value: newValue }));
    }
}
exports.LineColor = LineColor;
//# sourceMappingURL=LineColor.js.map