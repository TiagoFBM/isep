import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface VehicleTypeCodeProps {
    value: string;
}

export class VehicleTypeCode extends ValueObject<VehicleTypeCodeProps> {

    private constructor(props: VehicleTypeCodeProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(code: string): Result<VehicleTypeCode> {

        const codeIsAlphanumeric = Guard.isAlphaNumericString(code, 'code');

        if (!codeIsAlphanumeric.succeeded) {
            return Result.fail<VehicleTypeCode>(codeIsAlphanumeric.message);
        }

        const codeHasCorrectSize = Guard.hasCorrectStringSize(code, 20, 'code');
        if (!codeHasCorrectSize.succeeded) {
            return Result.fail<VehicleTypeCode>(codeHasCorrectSize.message);
        }

        return Result.ok<VehicleTypeCode>(new VehicleTypeCode({ value: code }))
    }

}