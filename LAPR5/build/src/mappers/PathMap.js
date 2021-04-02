"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Mapper_1 = require("../core/infra/Mapper");
const Path_1 = require("../domain/path/Path");
const PathSegment_1 = require("../domain/path/PathSegment");
class PathMapper extends Mapper_1.Mapper {
    static toDTO(path) {
        const segments = [];
        path.pathSegments.forEach(elem => {
            segments.push({
                firstNodeID: elem.firstNodeCode,
                secondNodeID: elem.secondNodeCode,
                travelTimeBetweenNodes: elem.travelTimeBetweenNodes.value,
                distanceBetweenNodes: elem.distanceBetweenNodes.value
            });
        });
        return {
            code: path.pathCode.value,
            isEmpty: path.pathIsEmpty,
            segments: segments
        };
    }
    static toDomain(path) {
        const pathOrError = Path_1.Path.create(path);
        if (pathOrError.isFailure) {
            console.log("Error converting toDomain: " + pathOrError.error);
            return null;
        }
        console.log("Path: \n" + path);
        path.segments.forEach(elem => {
            console.log("Node1 :" + elem.firstNodeID);
            console.log("Node2 :" + elem.secondNodeID);
            var pathSegment = PathSegment_1.PathSegment.create({
                firstNodeID: null,
                secondNodeID: null,
                travelTimeBetweenNodes: elem.travelTimeBetweenNodes,
                distanceBetweenNodes: elem.distanceBetweenNodes
            }).getValue();
            pathSegment.addFirstNodeID(elem.firstNodeID);
            pathSegment.addSecondNodeID(elem.secondNodeID);
            pathOrError.getValue().addSegment(pathSegment);
        });
        console.log("pathOrError: \n" + pathOrError);
        return pathOrError.getValue();
    }
    static toPersistence(path) {
        const segms = [];
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
        };
        return pathO;
    }
}
exports.default = PathMapper;
//# sourceMappingURL=PathMap.js.map