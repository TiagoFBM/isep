import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";

interface CoordinatesProps {
    latitude: string,
    longitude: string;
}

export class Coordinates extends ValueObject<CoordinatesProps> {
    get latitude(): string {
        return this.props.latitude;
    }

    get longitude(): string {
        return this.props.longitude;
    }

    private constructor(props: CoordinatesProps) {
        super(props);
    }

    public static create(latitude: string, longitude: string): Result<Coordinates> {
        const guardResult = Guard.againstNullOrUndefined(latitude, 'latitude');
        if (!guardResult.succeeded) {
            return Result.fail<Coordinates>(guardResult.message);
        }
        const guardResult1 = Guard.againstInvalidLatitude(latitude, 'latitude');
        if (!guardResult1.succeeded) {
            return Result.fail<Coordinates>(guardResult1.message);
        }
        const guardResult2 = Guard.againstNullOrUndefined(longitude, 'longitude');
        if (!guardResult2.succeeded) {
            return Result.fail<Coordinates>(guardResult2.message);
        }
        const guardResult3 = Guard.againstInvalidLongitude(longitude, 'longitude');
        if (!guardResult3.succeeded) {
            return Result.fail<Coordinates>(guardResult3.message);
        }
        else {
            return Result.ok<Coordinates>(new Coordinates({ latitude: latitude, longitude: longitude }))
        }
    }
}