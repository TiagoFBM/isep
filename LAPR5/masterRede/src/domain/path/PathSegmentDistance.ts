import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface PathSegmentDistanceProps {
    value: number;
}

export class PathSegmentDistance extends ValueObject<PathSegmentDistanceProps> {
    private constructor(props: PathSegmentDistanceProps) {
        super(props);
    }
    
    get value() : number {
        return this.props.value;
    }

    public static create(distance: number) : Result<PathSegmentDistance> {
        const distanceIsPositive = Guard.isNegativeOrZero(distance, "distance");

        if (distanceIsPositive.succeeded) {
            return Result.fail<PathSegmentDistance>(distance);
        }

        return Result.ok<PathSegmentDistance>(new PathSegmentDistance({ value: distance }));
    }
}