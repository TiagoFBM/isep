"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.NodeName = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class NodeName extends ValueObject_1.ValueObject {
    get value() {
        return this.props.value;
    }
    constructor(props) {
        super(props);
    }
    static create(name) {
        const nameIsAlphanumeric = Guard_1.Guard.isAlphaNumericString(name, 'name');
        if (!nameIsAlphanumeric.succeeded) {
            return Result_1.Result.fail(nameIsAlphanumeric.message);
        }
        const nameIsSmallerThan200 = Guard_1.Guard.hasLessThanMaxStringSize(name, 200, 'name');
        if (!nameIsSmallerThan200.succeeded) {
            return Result_1.Result.fail(nameIsSmallerThan200.message);
        }
        return Result_1.Result.ok(new NodeName({ value: name }));
    }
}
exports.NodeName = NodeName;
//# sourceMappingURL=NodeName.js.map