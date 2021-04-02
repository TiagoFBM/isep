"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const Mapper_1 = require("../core/infra/Mapper");
const Path_1 = require("../domain/path/Path");
const PathSegment_1 = require("../domain/path/PathSegment");
const NodeMap_1 = __importDefault(require("./NodeMap"));
class PathMapper extends Mapper_1.Mapper {
    static toDTO(path) {
        const segments = [];
        path.pathSegments.forEach(elem => {
            const firstNodeID = NodeMap_1.default.toDTO(elem.firstNodeCode);
            const secondNodeID = NodeMap_1.default.toDTO(elem.secondNodeCode);
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
        };
    }
    static toDomain(path) {
        const pathOrError = Path_1.Path.create(path);
        if (pathOrError.isFailure) {
            console.log("Error converting toDomain: " + pathOrError.error);
            return null;
        }
        path.segments.forEach(elem => {
            var pathSegment = PathSegment_1.PathSegment.create({
                firstNodeID: "",
                secondNodeID: "",
                travelTimeBetweenNodes: elem.travelTimeBetweenNodes,
                distanceBetweenNodes: elem.distanceBetweenNodes
            }).getValue();
            pathSegment.addFirstNodeCode(elem.firstNodeID);
            pathSegment.addSecondNodeCode(elem.secondNodeID);
            pathOrError.getValue().addSegment(pathSegment);
        });
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