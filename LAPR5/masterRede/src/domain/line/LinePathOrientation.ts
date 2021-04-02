import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";
import { OrientationEnum } from "./OrientationEnum";

interface LinePathOrientationProps {
    value: OrientationEnum;
}

export class LinePathOrientation extends ValueObject<LinePathOrientationProps> {

    private constructor(props: LinePathOrientationProps) {
        super(props);
    }

    get value(): OrientationEnum {
        return this.props.value;
    }

    public static create(orientation: string): Result<LinePathOrientation> {

        const orientationOrNull = Guard.againstNullOrUndefined(orientation, "Path Orientation")

        if (!orientationOrNull.succeeded) {
            return Result.fail<LinePathOrientation>(orientationOrNull.message);
        }

        if (OrientationEnum[orientation] === null) {
            return Result.fail<LinePathOrientation>("Orientation :" + orientation + " doesnt exists in DataBase")
        }

        return Result.ok<LinePathOrientation>(new LinePathOrientation({ value: OrientationEnum[orientation] }))
    }

}