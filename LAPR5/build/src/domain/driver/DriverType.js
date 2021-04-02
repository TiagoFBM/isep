"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.DriverType = void 0;
const AggregateRoot_1 = require("../../core/domain/AggregateRoot");
const Guard_1 = require("../../core/logic/Guard");
const Result_1 = require("../../core/logic/Result");
const DriverTypeCode_1 = require("./DriverTypeCode");
const DriverTypeDescription_1 = require("./DriverTypeDescription");
class DriverType extends AggregateRoot_1.AggregateRoot {
    constructor(props, id) {
        super(props, id);
    }
    get driverId() {
        return DriverTypeCode_1.DriverTypeCode.create(this.id.toString()).getValue();
    }
    get driverTypeDescription() {
        return this.props.description;
    }
    get driverTypeCode() {
        return this.props.code;
    }
    static create(driverTypeDTO) {
        if (Guard_1.Guard.isNull(driverTypeDTO, 'driverTypeDTO').succeeded) {
            return Result_1.Result.fail("DriverTypeDTO can\'t be null.");
        }
        if (Guard_1.Guard.isNull(driverTypeDTO.code, 'driverTypeDTO.code').succeeded || Guard_1.Guard.isEmptyString(driverTypeDTO.code, 'driverTypeDTO.code').succeeded) {
            return Result_1.Result.fail("DriverTypeDTO Code is invalid.");
        }
        if (Guard_1.Guard.isNull(driverTypeDTO.description, 'driverTypeDTO.description').succeeded || Guard_1.Guard.isEmptyString(driverTypeDTO.description, 'driverTypeDTO.description').succeeded) {
            return Result_1.Result.fail("DriverTypeDTO Description is invalid.");
        }
        const driverTypeCode = DriverTypeCode_1.DriverTypeCode.create(driverTypeDTO.code);
        if (driverTypeCode.isFailure) {
            return Result_1.Result.fail(driverTypeCode.error);
        }
        const driverTypeDescription = DriverTypeDescription_1.DriverTypeDescription.create(driverTypeDTO.description);
        if (driverTypeDescription.isFailure) {
            return Result_1.Result.fail(driverTypeDescription.error);
        }
        return Result_1.Result.ok(new DriverType({ code: driverTypeCode.getValue(), description: driverTypeDescription.getValue() }));
    }
}
exports.DriverType = DriverType;
//# sourceMappingURL=DriverType.js.map