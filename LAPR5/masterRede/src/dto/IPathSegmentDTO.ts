import { Node } from "../domain/node/Node";
import INodeDTO from "./INodeDTO";

export default interface IPathSegmentDTO {
    firstNodeID: string | INodeDTO,
    secondNodeID: string | INodeDTO,
    travelTimeBetweenNodes: number,
    distanceBetweenNodes: number
}