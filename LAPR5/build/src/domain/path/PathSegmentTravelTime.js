"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PathSegmentTravelTime = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class PathSegmentTravelTime extends ValueObject_1.ValueObject {
    get value() {
        return this.props.value;
    }
    constructor(props) {
        super(props);
    }
    static create(travelTime) {
        const travelTimeIsPositive = Guard_1.Guard.isNegativeOrZero(travelTime, "travelTime");
        if (travelTimeIsPositive.succeeded) {
            return Result_1.Result.fail(travelTimeIsPositive.message);
        }
        return Result_1.Result.ok(new PathSegmentTravelTime({ value: travelTime }));
    }
}
exports.PathSegmentTravelTime = PathSegmentTravelTime;
//# sourceMappingURL=PathSegmentTravelTime.js.map