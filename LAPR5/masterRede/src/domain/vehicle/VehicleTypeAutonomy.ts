import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface VehicleTypeAutonomyProps {
    value: number;
}

export class VehicleTypeAutonomy extends ValueObject<VehicleTypeAutonomyProps> {
    private constructor(props: VehicleTypeAutonomyProps) {
        super(props);
    }

    get value(): number {
        return this.props.value;
    }

    public static create(autonomy: number): Result<VehicleTypeAutonomy> {

        const autonomyIsPositive = Guard.isPositiveOrZero(autonomy, "autonomy");

        if (!autonomyIsPositive.succeeded) {
            return Result.fail<VehicleTypeAutonomy>(autonomyIsPositive.message);
        }

        return Result.ok<VehicleTypeAutonomy>(new VehicleTypeAutonomy({ value: autonomy }))
    }
}