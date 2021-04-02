"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.NodeShortName = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class NodeShortName extends ValueObject_1.ValueObject {
    get value() {
        return this.props.value;
    }
    constructor(props) {
        super(props);
    }
    static create(name) {
        const nameIsValid = Guard_1.Guard.isShortName(name, 'name');
        if (!nameIsValid.succeeded) {
            return Result_1.Result.fail(nameIsValid.message);
        }
        return Result_1.Result.ok(new NodeShortName({ value: name }));
    }
}
exports.NodeShortName = NodeShortName;
//# sourceMappingURL=NodeShortName.js.map