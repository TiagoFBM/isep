import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface VehicleTypeAverageSpeedProps {
    value: number;
}


export class VehicleTypeAverageSpeed extends ValueObject<VehicleTypeAverageSpeedProps> {

    private constructor(props: VehicleTypeAverageSpeedProps) {
        super(props);
    }

    get value(): number {
        return this.props.value;
    }

    public static create(avgSpeed: number): Result<VehicleTypeAverageSpeed> {

        const speedIsPositive = Guard.isPositiveOrZero(avgSpeed, 'Average Speed');
        if (!speedIsPositive.succeeded) {
            return Result.fail<VehicleTypeAverageSpeed>(speedIsPositive.message);
        }

        return Result.ok<VehicleTypeAverageSpeed>(new VehicleTypeAverageSpeed({ value: avgSpeed }))
    }

}