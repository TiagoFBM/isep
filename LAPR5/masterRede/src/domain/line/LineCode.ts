import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface LineCodeProps {
    value: string;
}

export class LineCode extends ValueObject<LineCodeProps> {

    private constructor(props: LineCodeProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(lineCode: string): Result<LineCode> {
        const codeIsAlphanumeric = Guard.isAlphaNumericString(lineCode, 'lineCode');
        if (!codeIsAlphanumeric.succeeded) {
            return Result.fail<LineCode>(codeIsAlphanumeric.message);
        }

        const codeBiggerThan1 = Guard.isBiggerThanSize(lineCode, 1, 'lineCode');
        if (!codeBiggerThan1.succeeded) {
            return Result.fail<LineCode>(codeBiggerThan1.message);
        }

        return Result.ok<LineCode>(new LineCode({ value: lineCode }))
    }

    public toString(): string {
        return this.props.value;
    }

}