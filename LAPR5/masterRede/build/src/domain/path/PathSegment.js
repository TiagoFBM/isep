"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PathSegment = void 0;
const Guard_1 = require("../../core/logic/Guard");
const Result_1 = require("../../core/logic/Result");
const PathSegmentTravelTime_1 = require("./PathSegmentTravelTime");
const PathSegmentDistance_1 = require("./PathSegmentDistance");
const Entity_1 = require("../../core/domain/Entity");
const Node_1 = require("../node/Node");
class PathSegment extends Entity_1.Entity {
    constructor(props) {
        super(props);
    }
    get firstNodeCode() {
        return this.props.firstNodeCode;
    }
    get secondNodeCode() {
        return this.props.secondNodeCode;
    }
    get travelTimeBetweenNodes() {
        return this.props.travelTimeBetweenNodes;
    }
    get distanceBetweenNodes() {
        return this.props.distanceBetweenNodes;
    }
    static create(pathSegmentDTO) {
        if (Guard_1.Guard.isNull(pathSegmentDTO, "pathSegmentDTO").succeeded) {
            return Result_1.Result.fail("PathSegmentDTO can\'t be null.");
        }
        if (Guard_1.Guard.isNull(pathSegmentDTO.travelTimeBetweenNodes, "travelTimeBetweenNodes").succeeded) {
            return Result_1.Result.fail("PathSegmentDTO travelTimeBetweenNodes can\'t be null.");
        }
        if (Guard_1.Guard.isNull(pathSegmentDTO.distanceBetweenNodes, "distanceBetweenNodes").succeeded) {
            return Result_1.Result.fail("PathSegmentDTO distanceBetweenNodes can\'t be null.");
        }
        const travelTimeBetweenNodes = PathSegmentTravelTime_1.PathSegmentTravelTime.create(pathSegmentDTO.travelTimeBetweenNodes);
        if (travelTimeBetweenNodes.isFailure) {
            return Result_1.Result.fail(travelTimeBetweenNodes.error);
        }
        const distanceBetweenNodes = PathSegmentDistance_1.PathSegmentDistance.create(pathSegmentDTO.distanceBetweenNodes);
        if (distanceBetweenNodes.isFailure) {
            return Result_1.Result.fail(distanceBetweenNodes.error);
        }
        return Result_1.Result.ok(new PathSegment({ firstNodeCode: null, secondNodeCode: null, travelTimeBetweenNodes: travelTimeBetweenNodes.getValue(), distanceBetweenNodes: distanceBetweenNodes.getValue() }));
    }
    addFirstNodeID(nodeID) {
        this.props.firstNodeCode = nodeID;
    }
    addSecondNodeID(nodeID) {
        this.props.secondNodeCode = nodeID;
    }
    addFirstNodeCode(node) {
        if (Guard_1.Guard.isNull(node, "firstNodeID").succeeded) {
            return;
        }
        var nodeCode = Node_1.Node.create(node);
        if (nodeCode.isFailure) {
            return false;
        }
        this.props.firstNodeCode = nodeCode.getValue();
        return true;
    }
    addSecondNodeCode(node) {
        if (Guard_1.Guard.isNull(node, "secondNodeID").succeeded) {
            return;
        }
        var nodeCode = Node_1.Node.create(node);
        if (nodeCode.isFailure) {
            return false;
        }
        this.props.secondNodeCode = nodeCode.getValue();
        return true;
    }
}
exports.PathSegment = PathSegment;
//# sourceMappingURL=PathSegment.js.map