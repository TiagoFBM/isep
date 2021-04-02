import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface DriverTypeCodeProps {
    value: string;
}

export class DriverTypeCode extends ValueObject<DriverTypeCodeProps> {

    private constructor(props: DriverTypeCodeProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(code: string): Result<DriverTypeCode> {
        const codeIsAlphanumeric = Guard.isAlphaNumericString(code, 'code');
        if (!codeIsAlphanumeric.succeeded) {
            return Result.fail<DriverTypeCode>(codeIsAlphanumeric.message);
        }

        const codeIsSmallerThan20 = Guard.hasLessThanMaxStringSize(code, 20, 'code');
        if (!codeIsSmallerThan20.succeeded) {
            return Result.fail<DriverTypeCode>(codeIsSmallerThan20.message);
        }

        return Result.ok<DriverTypeCode>(new DriverTypeCode({ value: code }))
    }

}