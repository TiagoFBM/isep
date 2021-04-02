import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface VehicleTypeAverageConsuptionProps {
    value: number;
}


export class VehicleTypeAverageConsuption extends ValueObject<VehicleTypeAverageConsuptionProps> {

    private constructor(props: VehicleTypeAverageConsuptionProps) {
        super(props);
    }

    get value(): number {
        return this.props.value;
    }

    public static create(avgConsuption: number): Result<VehicleTypeAverageConsuption> {

        const costIsPositive = Guard.isPositiveOrZero(avgConsuption, 'Average Consuption');
        if (!costIsPositive.succeeded) {
            return Result.fail<VehicleTypeAverageConsuption>(costIsPositive.message);
        }

        return Result.ok<VehicleTypeAverageConsuption>(new VehicleTypeAverageConsuption({ value: avgConsuption }))
    }

}