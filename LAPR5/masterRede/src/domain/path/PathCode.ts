import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface PathCodeProps {
    value: string;
}

export class PathCode extends ValueObject<PathCodeProps> {
    private constructor(props: PathCodeProps) {
        super(props);
    }

    get value() : string {
        return this.props.value;
    }

    public static create(code: string) : Result<PathCode> {
        const codeIsAlphanumeric = Guard.isAlphaNumericString(code, "code");
        if (!codeIsAlphanumeric.succeeded) {
            return Result.fail<PathCode>(codeIsAlphanumeric.message);
        }

        const codeIsSmallerThan20 = Guard.hasLessThanMaxStringSize(code, 20, "code");
        if (!codeIsSmallerThan20.succeeded) {
            return Result.fail<PathCode>(codeIsSmallerThan20.message);
        }

        return Result.ok<PathCode>(new PathCode({ value: code }));
    }

    public toString(): string {
        return this.props.value;
    }
}