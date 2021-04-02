"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Mapper_1 = require("../core/infra/Mapper");
const VehicleType_1 = require("../domain/vehicle/VehicleType");
class VehicleTypeMapper extends Mapper_1.Mapper {
    static toDTO(vehicleType) {
        return {
            code: vehicleType.vehicleTypeCode.value,
            description: vehicleType.vehicleTypeDescription.value,
            autonomy: vehicleType.vehicleTypeAutonomy.value,
            fuelType: vehicleType.vehicleTypeFuelType.value,
            costPerKilometer: vehicleType.vehicleTypeCostPerKilometer.value,
            averageConsuption: vehicleType.vehicleTypeAverageConsuption.value,
            averageSpeed: vehicleType.vehicleTypeAverageSpeed.value,
        };
    }
    static toDomain(vehicleType) {
        const vehicleOrError = VehicleType_1.VehicleType.create(vehicleType);
        vehicleOrError.isFailure ? console.log(vehicleOrError.error) : '';
        return vehicleOrError.isSuccess ? vehicleOrError.getValue() : null;
    }
    static toPersistence(vehicleType) {
        const vehicle = {
            code: vehicleType.vehicleTypeCode.value,
            description: vehicleType.vehicleTypeDescription.value,
            autonomy: vehicleType.vehicleTypeAutonomy.value,
            fuelType: vehicleType.vehicleTypeFuelType.value,
            costPerKilometer: vehicleType.vehicleTypeCostPerKilometer.value,
            averageConsuption: vehicleType.vehicleTypeAverageConsuption.value,
            averageSpeed: vehicleType.vehicleTypeAverageSpeed.value
        };
        return vehicle;
    }
}
exports.default = VehicleTypeMapper;
//# sourceMappingURL=VehicleTypeMap.js.map