import { Document, Model } from "mongoose";
import { Mapper } from "../core/infra/Mapper";
import { Node } from "../domain/node/Node";
import { NodeID } from "../domain/node/NodeID";
import { Path } from "../domain/path/Path";
import { PathSegment } from "../domain/path/PathSegment";
import { PathSegmentDistance } from "../domain/path/PathSegmentDistance";
import { PathSegmentTravelTime } from "../domain/path/PathSegmentTravelTime";
import IPathDTO from "../dto/IPathDTO";
import IPathSegmentDTO from "../dto/IPathSegmentDTO";
import NodeMapper from "./NodeMap";

export default class PathMapper extends Mapper<Path> {
    public static toDTO(path: Path): IPathDTO {
        
        
        const segments: Array<IPathSegmentDTO> = [];
        path.pathSegments.forEach(elem => {
            const firstNodeID = NodeMapper.toDTO(elem.firstNodeCode as Node);
            const secondNodeID = NodeMapper.toDTO(elem.secondNodeCode as Node);
            
            segments.push({
                firstNodeID: firstNodeID,
                secondNodeID: secondNodeID,
                travelTimeBetweenNodes: elem.travelTimeBetweenNodes.value,
                distanceBetweenNodes: elem.distanceBetweenNodes.value
            });
        });
        return {
            code: path.pathCode.value,
            isEmpty: path.pathIsEmpty,
            segments: segments
        }
    }

    public static toDomain(path: any | Model<IPathDTO & Document>): Path {
        const pathOrError = Path.create(path);
        
        if (pathOrError.isFailure) {
            console.log("Error converting toDomain: " + pathOrError.error);
            return null;
        }

        path.segments.forEach(elem => {
            
            var pathSegment = PathSegment.create({
                firstNodeID: "",
                secondNodeID: "",
                travelTimeBetweenNodes: elem.travelTimeBetweenNodes,
                distanceBetweenNodes: elem.distanceBetweenNodes
            }).getValue();
            
            pathSegment.addFirstNodeCode(elem.firstNodeID);
            pathSegment.addSecondNodeCode(elem.secondNodeID);

            pathOrError.getValue().addSegment(pathSegment)
        });
        
        return pathOrError.getValue();
    }

    public static toPersistence(path: Path): any {
        const segms: Array<any> = [];
        path.pathSegments.forEach(elem => {
            segms.push({
                firstNodeID: elem.firstNodeCode,
                secondNodeID: elem.secondNodeCode,
                travelTimeBetweenNodes: elem.travelTimeBetweenNodes.value,
                distanceBetweenNodes: elem.distanceBetweenNodes.value
            });
        });

        const pathO = {
            code: path.pathCode.value,
            isEmpty: path.pathIsEmpty,
            segments: segms
        }
        return pathO;
    }
}