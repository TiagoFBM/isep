import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { VehicleType } from "../domain/vehicle/VehicleType";
import IVehicleTypeDTO from "../dto/IVehicleTypeDTO";

export default class VehicleTypeMapper extends Mapper<VehicleType> {

    public static toDTO(vehicleType: VehicleType): IVehicleTypeDTO {
        return {
            code: vehicleType.vehicleTypeCode.value,
            description: vehicleType.vehicleTypeDescription.value,
            autonomy: vehicleType.vehicleTypeAutonomy.value,
            fuelType: vehicleType.vehicleTypeFuelType.value,
            costPerKilometer: vehicleType.vehicleTypeCostPerKilometer.value,
            averageConsuption: vehicleType.vehicleTypeAverageConsuption.value,
            averageSpeed: vehicleType.vehicleTypeAverageSpeed.value,
        } as IVehicleTypeDTO;
    }

    public static toDomain(vehicleType: any | Model<IVehicleTypeDTO & Document>): VehicleType {
        const vehicleOrError = VehicleType.create(
            vehicleType
        );

        vehicleOrError.isFailure ? console.log(vehicleOrError.error) : '';

        return vehicleOrError.isSuccess ? vehicleOrError.getValue() : null;
    }

    public static toPersistence(vehicleType: VehicleType): any {
        const vehicle = {
            code: vehicleType.vehicleTypeCode.value,
            description: vehicleType.vehicleTypeDescription.value,
            autonomy: vehicleType.vehicleTypeAutonomy.value,
            fuelType: vehicleType.vehicleTypeFuelType.value,
            costPerKilometer: vehicleType.vehicleTypeCostPerKilometer.value,
            averageConsuption: vehicleType.vehicleTypeAverageConsuption.value,
            averageSpeed: vehicleType.vehicleTypeAverageSpeed.value
        }
        return vehicle;
    }
}