"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.NodeCrewTravelTimes = void 0;
const Result_1 = require("../../core/logic/Result");
const Guard_1 = require("../../core/logic/Guard");
const Entity_1 = require("../../core/domain/Entity");
class NodeCrewTravelTimes extends Entity_1.Entity {
    constructor(props) {
        super(props);
    }
    get node() {
        return this.props.node;
    }
    get duration() {
        return this.props.duration;
    }
    static create(crewTravelTimeDTO) {
        const nodeIsNull = Guard_1.Guard.isNull(crewTravelTimeDTO.node, 'node');
        if (nodeIsNull.succeeded) {
            return Result_1.Result.fail(nodeIsNull.message);
        }
        const durationIsValid = Guard_1.Guard.isNegativeOrZero(crewTravelTimeDTO.duration, 'duration');
        if (durationIsValid.succeeded) {
            return Result_1.Result.fail(durationIsValid.message);
        }
        return Result_1.Result.ok(new NodeCrewTravelTimes({ node: crewTravelTimeDTO.node, duration: crewTravelTimeDTO.duration }));
    }
}
exports.NodeCrewTravelTimes = NodeCrewTravelTimes;
//# sourceMappingURL=NodeCrewTravelTimes.js.map