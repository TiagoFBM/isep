"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Node = void 0;
const AggregateRoot_1 = require("../../core/domain/AggregateRoot");
const Guard_1 = require("../../core/logic/Guard");
const Result_1 = require("../../core/logic/Result");
const Coordinates_1 = require("./Coordinates");
const NodeID_1 = require("./NodeID");
const NodeName_1 = require("./NodeName");
const NodeShortName_1 = require("./NodeShortName");
const NodeCrewTravelTimes_1 = require("./NodeCrewTravelTimes");
const lodash_1 = require("lodash");
class Node extends AggregateRoot_1.AggregateRoot {
    constructor(props, id) {
        super(props, id);
    }
    get coordinates() {
        return this.props.coordinates;
    }
    get code() {
        return this.props.code;
    }
    get name() {
        return this.props.name;
    }
    get shortName() {
        return this.props.shortName;
    }
    get crewTravelTimes() {
        return this.props.crewTravelTimes;
    }
    get isDepot() {
        return this.props.isDepot;
    }
    get isReliefPoint() {
        return this.props.isReliefPoint;
    }
    static create(nodeDTO) {
        if (Guard_1.Guard.isNull(nodeDTO, 'nodeDTO').succeeded) {
            return Result_1.Result.fail("NodeDTO can\'t be null.");
        }
        if (Guard_1.Guard.isNull(nodeDTO.code, 'nodeDTO.code').succeeded || Guard_1.Guard.isEmptyString(nodeDTO.code, 'nodeDTO.code').succeeded) {
            return Result_1.Result.fail("nodeDTO Code is invalid.");
        }
        if (Guard_1.Guard.isNull(nodeDTO.name, 'nodeDTO.name').succeeded || Guard_1.Guard.isEmptyString(nodeDTO.name, 'nodeDTO.name').succeeded) {
            return Result_1.Result.fail("nodeDTO Name is invalid.");
        }
        if (Guard_1.Guard.isNull(nodeDTO.latitude, 'nodeDTO.latitude').succeeded) {
            return Result_1.Result.fail("nodeDTO Latitude is invalid.");
        }
        if (Guard_1.Guard.isNull(nodeDTO.longitude, 'nodeDTO.longitude').succeeded) {
            return Result_1.Result.fail("nodeDTO Longitude is invalid.");
        }
        if (Guard_1.Guard.isNull(nodeDTO.shortName, 'nodeDTO.shortName').succeeded || Guard_1.Guard.isEmptyString(nodeDTO.shortName, 'nodeDTO.shortName').succeeded) {
            return Result_1.Result.fail("nodeDTO Short Name is invalid.");
        }
        if (Guard_1.Guard.isNull(nodeDTO.isDepot, 'nodeDTO.isDepot').succeeded) {
            return Result_1.Result.fail("nodeDTO Depot is invalid.");
        }
        if (Guard_1.Guard.isNull(nodeDTO.isReliefPoint, 'nodeDTO.isReliefPoint').succeeded) {
            return Result_1.Result.fail("nodeDTO Relief Point  is invalid.");
        }
        const code = NodeID_1.NodeID.create(nodeDTO.code);
        if (code.isFailure) {
            return Result_1.Result.fail(code.error);
        }
        const name = NodeName_1.NodeName.create(nodeDTO.name);
        if (name.isFailure) {
            return Result_1.Result.fail(name.error);
        }
        const coordenadas = Coordinates_1.Coordinates.create(nodeDTO.latitude, nodeDTO.longitude);
        if (coordenadas.isFailure) {
            return Result_1.Result.fail(coordenadas.error);
        }
        const shortName = NodeShortName_1.NodeShortName.create(nodeDTO.shortName);
        if (shortName.isFailure) {
            return Result_1.Result.fail(shortName.error);
        }
        var crewTravelTimes = [];
        if (!lodash_1.isUndefined(nodeDTO.crewTravelTimes)) {
            nodeDTO.crewTravelTimes.forEach(element => {
                var crewTravelTimeOrFailure = NodeCrewTravelTimes_1.NodeCrewTravelTimes.create(element);
                if (crewTravelTimeOrFailure.isSuccess)
                    crewTravelTimes.push(crewTravelTimeOrFailure.getValue());
            });
        }
        return Result_1.Result.ok(new Node({
            code: code.getValue(),
            name: name.getValue(),
            coordinates: coordenadas.getValue(),
            shortName: shortName.getValue(),
            isDepot: nodeDTO.isDepot,
            isReliefPoint: nodeDTO.isReliefPoint,
            crewTravelTimes: crewTravelTimes
        }));
    }
}
exports.Node = Node;
//# sourceMappingURL=Node.js.map