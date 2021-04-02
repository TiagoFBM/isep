import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";
import { EnumFuelType } from "./EnumFuelType";


interface VehicleTypeFuelTypeProps {
    value: EnumFuelType;
}

export class VehicleTypeFuelType extends ValueObject<VehicleTypeFuelTypeProps> {

    private constructor(props: VehicleTypeFuelTypeProps) {
        super(props);
    }

    get value(): EnumFuelType {
        return this.props.value;
    }

    public static create(fuelType: string): Result<VehicleTypeFuelType> {

        const fuelTypeNotNull = Guard.againstNullOrUndefined(fuelType, "Fuel Type")

        if (!fuelTypeNotNull.succeeded) {
            return Result.fail<VehicleTypeFuelType>(fuelTypeNotNull.message);
        }

        if (EnumFuelType[fuelType] == null) {
            return Result.fail<VehicleTypeFuelType>("Fuel Type doesnt exists in DataBase")
        }

        return Result.ok<VehicleTypeFuelType>(new VehicleTypeFuelType({ value: EnumFuelType[fuelType] }))
    }

}