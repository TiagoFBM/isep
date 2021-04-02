"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Mapper_1 = require("../core/infra/Mapper");
const DriverType_1 = require("../domain/driver/DriverType");
class DriverTypeMapper extends Mapper_1.Mapper {
    static toDTO(driverType) {
        return {
            code: driverType.driverTypeCode.value,
            description: driverType.driverTypeDescription.value,
        };
    }
    static toDomain(driverType) {
        const roleOrError = DriverType_1.DriverType.create(driverType);
        if (roleOrError.isFailure) {
            console.log("Error Converting toDomain: %s", roleOrError.error);
            return null;
        }
        return roleOrError.getValue();
    }
    static toPersistence(driverType) {
        const driver = {
            code: driverType.driverTypeCode.value,
            description: driverType.driverTypeDescription.value
        };
        return driver;
    }
}
exports.default = DriverTypeMapper;
//# sourceMappingURL=DriverTypeMap.js.map