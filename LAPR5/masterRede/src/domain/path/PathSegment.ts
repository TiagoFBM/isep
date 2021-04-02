import { UniqueEntityID } from "../../core/domain/UniqueEntityID";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";
import { NodeID } from "../../domain/node/NodeID";
import { PathSegmentTravelTime } from "./PathSegmentTravelTime";
import { PathSegmentDistance } from "./PathSegmentDistance";
import { Entity } from "../../core/domain/Entity";
import IPathSegmentDTO from "../../dto/IPathSegmentDTO";
import { Node } from "../node/Node";
import INodeDTO from "../../dto/INodeDTO";

interface PathSegmentProps {
    firstNodeCode: Node | string;
    secondNodeCode: Node | string;
    travelTimeBetweenNodes: PathSegmentTravelTime;
    distanceBetweenNodes: PathSegmentDistance;
}

export class PathSegment extends Entity<PathSegmentProps> {
    private constructor (props: PathSegmentProps) {
        super(props);
    }

    get firstNodeCode() : Node | string{
        return this.props.firstNodeCode;
    }

    get secondNodeCode() : Node | string {
        return this.props.secondNodeCode;
    }

    get travelTimeBetweenNodes() : PathSegmentTravelTime {
        return this.props.travelTimeBetweenNodes;
    }

    get distanceBetweenNodes() : PathSegmentDistance {
        return this.props.distanceBetweenNodes;
    }

    public static create (pathSegmentDTO : IPathSegmentDTO) : Result<PathSegment> {
        if (Guard.isNull(pathSegmentDTO, "pathSegmentDTO").succeeded) {
            return Result.fail<PathSegment>("PathSegmentDTO can\'t be null.");
        }

        if (Guard.isNull(pathSegmentDTO.travelTimeBetweenNodes, "travelTimeBetweenNodes").succeeded) {
            return Result.fail<PathSegment>("PathSegmentDTO travelTimeBetweenNodes can\'t be null.");
        }

        if (Guard.isNull(pathSegmentDTO.distanceBetweenNodes, "distanceBetweenNodes").succeeded) {
            return Result.fail<PathSegment>("PathSegmentDTO distanceBetweenNodes can\'t be null.");
        }

        const travelTimeBetweenNodes = PathSegmentTravelTime.create(pathSegmentDTO.travelTimeBetweenNodes);

        if (travelTimeBetweenNodes.isFailure) {
            return Result.fail<PathSegment>(travelTimeBetweenNodes.error);
        }

        const distanceBetweenNodes = PathSegmentDistance.create(pathSegmentDTO.distanceBetweenNodes);

        if (distanceBetweenNodes.isFailure) {
            return Result.fail<PathSegment>(distanceBetweenNodes.error);
        }

        return Result.ok<PathSegment>(new PathSegment({ firstNodeCode: null, secondNodeCode: null, travelTimeBetweenNodes: travelTimeBetweenNodes.getValue(), distanceBetweenNodes: distanceBetweenNodes.getValue() }));
    }
    
    public addFirstNodeID(nodeID: string) {
        this.props.firstNodeCode = nodeID;
    }

    public addSecondNodeID(nodeID: string) {
        this.props.secondNodeCode = nodeID;
    }

    public addFirstNodeCode(node: INodeDTO) {
        if (Guard.isNull(node, "firstNodeID").succeeded) {
            return;
        }
        var nodeCode = Node.create(node);
        if (nodeCode.isFailure) {
            return false;
        }
        this.props.firstNodeCode = nodeCode.getValue();
        return true;
    }

    public addSecondNodeCode(node: INodeDTO) {
        if (Guard.isNull(node, "secondNodeID").succeeded) {
            return;
        }
        var nodeCode = Node.create(node);
        if (nodeCode.isFailure) {
            return false;
        }
        this.props.secondNodeCode = nodeCode.getValue();
        return true;
    }
}