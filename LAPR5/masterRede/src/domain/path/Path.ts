import { AggregateRoot } from "../../core/domain/AggregateRoot";
import { UniqueEntityID } from "../../core/domain/UniqueEntityID";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";
import IPathDTO from "../../dto/IPathDTO";
import IPathSegmentDTO from "../../dto/IPathSegmentDTO";
import { PathCode } from "./PathCode";
import { PathSegment } from "./PathSegment";
import Logger from './../../loaders/logger';

interface PathProps {
    code: PathCode;
    isEmpty: boolean;
    segments: Array<PathSegment>;
}

export class Path extends AggregateRoot<PathProps> {
    private constructor (props: PathProps) {
        super(props);
    }

    get pathCode() : PathCode {
        return this.props.code;
    }

    get pathIsEmpty() : boolean {
        return this.props.isEmpty;
    }

    get pathSegments() : PathSegment[] {
        return this.props.segments;
    }

    public static create(pathDTO: IPathDTO): Result<Path> {
        if (Guard.isNull(pathDTO, "pathDTO").succeeded) {
            return Result.fail<Path>("PathDTO can\'t be null.");
        }

        if (Guard.isNull(pathDTO.code, "pathDTO.code").succeeded) {
            return Result.fail<Path>("PathDTO code can\'t be null.");
        }

        if (Guard.isNull(pathDTO.isEmpty, "pathDTO.isEmpty").succeeded) {
            return Result.fail<Path>("PathDTO isEmpty can\'t be null.");
        }

        const pathCode = PathCode.create(pathDTO.code);

        if (pathCode.isFailure) {
            return Result.fail<Path>(pathCode.error);
        }
        
        return Result.ok<Path>(new Path({ code: pathCode.getValue(), isEmpty: pathDTO.isEmpty, segments: [] }))
    }

    public addSegment(segm : PathSegment) : boolean {
        if (Guard.isNull(segm, "segment").succeeded) {
            return false;
        }

        if (Guard.isNull(segm.firstNodeCode, "firstNodeCode").succeeded) {
            return false;
        }

        if (Guard.isNull(segm.secondNodeCode, "secondNodeCode").succeeded) {
            return false;
        }
        
        this.props.segments.push(segm);
    }
}