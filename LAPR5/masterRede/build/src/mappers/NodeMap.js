"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Mapper_1 = require("../core/infra/Mapper");
const Node_1 = require("../domain/node/Node");
const lodash_1 = require("lodash");
class NodeMapper extends Mapper_1.Mapper {
    static toDTO(node) {
        var list = [];
        if (!lodash_1.isUndefined(node.crewTravelTimes)) {
            node.crewTravelTimes.forEach(element => {
                var crewTravelTimeDTO = element;
                list.push(crewTravelTimeDTO);
            });
        }
        return {
            code: node.code.value,
            latitude: node.coordinates.latitude,
            longitude: node.coordinates.longitude,
            name: node.name.value,
            shortName: node.shortName.value,
            isDepot: node.isDepot,
            isReliefPoint: node.isReliefPoint,
            crewTravelTimes: list,
        };
    }
    static toDomain(node) {
        const nodeOrError = Node_1.Node.create(node);
        nodeOrError.isFailure ? console.log(nodeOrError.error) : '';
        return nodeOrError.isSuccess ? nodeOrError.getValue() : null;
    }
    static toPersistence(node) {
        var newCrewTravelTime = [];
        if (!lodash_1.isUndefined(node.crewTravelTimes)) {
            node.crewTravelTimes.forEach(element => {
                newCrewTravelTime.push({
                    node: element.node,
                    duration: element.duration,
                });
            });
        }
        const node1 = {
            code: node.code.value,
            latitude: node.coordinates.latitude,
            longitude: node.coordinates.longitude,
            name: node.name.value,
            shortName: node.shortName.value,
            isDepot: node.isDepot,
            isReliefPoint: node.isReliefPoint,
            crewTravelTimes: newCrewTravelTime,
        };
        return node1;
    }
}
exports.default = NodeMapper;
//# sourceMappingURL=NodeMap.js.map