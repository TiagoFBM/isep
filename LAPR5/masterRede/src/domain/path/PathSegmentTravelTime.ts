import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface PathSegmentTravelTimeProps {
    value: number;
}

export class PathSegmentTravelTime extends ValueObject<PathSegmentTravelTimeProps> {
    get value() : number {
        return this.props.value;
    }

    private constructor (props: PathSegmentTravelTimeProps) {
        super(props);
    }

    public static create(travelTime: number) : Result<PathSegmentTravelTime> {
        const travelTimeIsPositive = Guard.isNegativeOrZero(travelTime, "travelTime");

        if (travelTimeIsPositive.succeeded) {
            return Result.fail<PathSegmentTravelTime>(travelTimeIsPositive.message);
        }

        return Result.ok<PathSegmentTravelTime>(new PathSegmentTravelTime({ value: travelTime }));
    }
}
