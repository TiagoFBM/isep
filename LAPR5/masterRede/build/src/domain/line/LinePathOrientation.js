"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.LinePathOrientation = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
const OrientationEnum_1 = require("./OrientationEnum");
class LinePathOrientation extends ValueObject_1.ValueObject {
    constructor(props) {
        super(props);
    }
    get value() {
        return this.props.value;
    }
    static create(orientation) {
        const orientationOrNull = Guard_1.Guard.againstNullOrUndefined(orientation, "Path Orientation");
        if (!orientationOrNull.succeeded) {
            return Result_1.Result.fail(orientationOrNull.message);
        }
        if (OrientationEnum_1.OrientationEnum[orientation] === null) {
            return Result_1.Result.fail("Orientation :" + orientation + " doesnt exists in DataBase");
        }
        return Result_1.Result.ok(new LinePathOrientation({ value: OrientationEnum_1.OrientationEnum[orientation] }));
    }
}
exports.LinePathOrientation = LinePathOrientation;
//# sourceMappingURL=LinePathOrientation.js.map