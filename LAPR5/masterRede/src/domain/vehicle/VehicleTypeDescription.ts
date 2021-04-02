import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface VehicleTypeDescriptionProps {
    value: string;
}

export class VehicleTypeDescription extends ValueObject<VehicleTypeDescriptionProps> {

    get value(): string {
        return this.props.value;
    }

    private constructor(props: VehicleTypeDescriptionProps) {
        super(props);
    }

    public static create(description: string): Result<VehicleTypeDescription> {

        const descriptionIsSmallerThan250 = Guard.hasLessThanMaxStringSize(description, 250, 'description');
        if (!descriptionIsSmallerThan250.succeeded) {
            return Result.fail<VehicleTypeDescription>(descriptionIsSmallerThan250.message);
        }

        return Result.ok<VehicleTypeDescription>(new VehicleTypeDescription({ value: description }))

    }
}