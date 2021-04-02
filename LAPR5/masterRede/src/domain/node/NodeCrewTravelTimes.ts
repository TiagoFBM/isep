import { ValueObject } from "../../core/domain/ValueObject";
import { Result } from "../../core/logic/Result";
import { Guard } from "../../core/logic/Guard";
import ICrewTravelTimeDTO from "../../dto/ICrewTravelTimeDTO";
import { Entity } from "../../core/domain/Entity";

interface NodeCrewTravelTimesProps {
    node: string;
    duration: number;
}

export class NodeCrewTravelTimes extends Entity<NodeCrewTravelTimesProps> {

    private constructor(props: NodeCrewTravelTimesProps) {
        super(props);
    }

    get node(): string {
        return this.props.node;
    }

    get duration(): number {
        return this.props.duration;
    }


    public static create(crewTravelTimeDTO: ICrewTravelTimeDTO): Result<NodeCrewTravelTimes> {

        const nodeIsNull = Guard.isNull(crewTravelTimeDTO.node, 'node');

        if (nodeIsNull.succeeded) {
            return Result.fail<NodeCrewTravelTimes>(nodeIsNull.message);
        }

        const durationIsValid = Guard.isNegativeOrZero(crewTravelTimeDTO.duration, 'duration');

        if (durationIsValid.succeeded) {
            return Result.fail<NodeCrewTravelTimes>(durationIsValid.message);
        }

        return Result.ok<NodeCrewTravelTimes>(new NodeCrewTravelTimes({ node: crewTravelTimeDTO.node, duration: crewTravelTimeDTO.duration }))
    }

}