"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PathSegmentDistance = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class PathSegmentDistance extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(distance) {
        const distanceIsPositive = Guard_1.Guard.isNegativeOrZero(distance, "distance");
        if (distanceIsPositive.succeeded) {
            return Result_1.Result.fail(distance);
        }
        return Result_1.Result.ok(new PathSegmentDistance({ value: distance }));
    }
}
exports.PathSegmentDistance = PathSegmentDistance;
//# sourceMappingURL=PathSegmentDistance.js.map