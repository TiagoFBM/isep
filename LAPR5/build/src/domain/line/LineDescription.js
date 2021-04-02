"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.LineDescription = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class LineDescription extends ValueObject_1.ValueObject {
    get value() {
        return this.props.value;
    }
    constructor(props) {
        super(props);
    }
    static create(lineDescription) {
        const descriptionIsSmallerThan250 = Guard_1.Guard.hasLessThanMaxStringSize(lineDescription, 250, 'lineDescription');
        if (!descriptionIsSmallerThan250.succeeded) {
            return Result_1.Result.fail(descriptionIsSmallerThan250.message);
        }
        return Result_1.Result.ok(new LineDescription({ value: lineDescription }));
    }
}
exports.LineDescription = LineDescription;
//# sourceMappingURL=LineDescription.js.map