import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface VehicleTypeCostPerKilometerProps {
    value: number;
}

export class VehicleTypeCostPerKilometer extends ValueObject<VehicleTypeCostPerKilometerProps> {

    private constructor(props: VehicleTypeCostPerKilometerProps) {
        super(props);
    }

    get value(): number {
        return this.props.value;
    }

    public static create(costPerKM: number): Result<VehicleTypeCostPerKilometer> {

        const costIsPositive = Guard.isPositiveOrZero(costPerKM, 'Cost per Kilometer');
        if (!costIsPositive.succeeded) {
            return Result.fail<VehicleTypeCostPerKilometer>(costIsPositive.message);
        }

        return Result.ok<VehicleTypeCostPerKilometer>(new VehicleTypeCostPerKilometer({ value: costPerKM }))
    }

}