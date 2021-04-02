"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Coordinates = void 0;
const ValueObject_1 = require("../../core/domain/ValueObject");
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
class Coordinates extends ValueObject_1.ValueObject {
    get latitude() {
        return this.props.latitude;
    }
    get longitude() {
        return this.props.longitude;
    }
    constructor(props) {
        super(props);
    }
    static create(latitude, longitude) {
        const guardResult = Guard_1.Guard.againstNullOrUndefined(latitude, 'latitude');
        if (!guardResult.succeeded) {
            return Result_1.Result.fail(guardResult.message);
        }
        const guardResult1 = Guard_1.Guard.againstInvalidLatitude(latitude, 'latitude');
        if (!guardResult1.succeeded) {
            return Result_1.Result.fail(guardResult1.message);
        }
        const guardResult2 = Guard_1.Guard.againstNullOrUndefined(longitude, 'longitude');
        if (!guardResult2.succeeded) {
            return Result_1.Result.fail(guardResult2.message);
        }
        const guardResult3 = Guard_1.Guard.againstInvalidLongitude(longitude, 'longitude');
        if (!guardResult3.succeeded) {
            return Result_1.Result.fail(guardResult3.message);
        }
        else {
            return Result_1.Result.ok(new Coordinates({ latitude: latitude, longitude: longitude }));
        }
    }
}
exports.Coordinates = Coordinates;
//# sourceMappingURL=Coordinates.js.map