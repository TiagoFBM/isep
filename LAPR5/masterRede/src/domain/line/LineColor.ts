import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface LineColorProps {
    value: string;
}

export class LineColor extends ValueObject<LineColorProps> {

    private constructor(props: LineColorProps) {
        super(props);
    }

    get value(): string {
        return this.props.value;
    }

    public static create(newValue: string): Result<LineColor> {
        const valueEmpty = Guard.isEmptyString(newValue, 'Color Value');
        if (valueEmpty.succeeded) {
            return Result.fail<LineColor>("Color Value must not be empty.");
        }

        const validColor = Guard.isValidColor(newValue, 'Color Value');
        if (!validColor.succeeded) {
            return Result.fail<LineColor>("Color Value must be valid.");
        }

        return Result.ok<LineColor>(new LineColor({ value: newValue }))
    }

}