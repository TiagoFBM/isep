"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const typedi_1 = require("typedi");
const NodeMap_1 = __importDefault(require("../mappers/NodeMap"));
const lodash_1 = require("lodash");
const mongoose_1 = require("mongoose");
const config_1 = __importDefault(require("../../config"));
let NodeRepo = class NodeRepo {
    constructor(NodeSchema) {
        this.NodeSchema = NodeSchema;
    }
    createBaseQuery() {
        return {
            where: {},
        };
    }
    async exists(node) {
        const codeX = node.props.code.value;
        const query = { code: codeX };
        const nodeFound = await this.NodeSchema.findOne(query);
        return !!nodeFound === true;
    }
    async save(node) {
        const query = { code: node.id.toString() };
        const NodeDocument = await this.NodeSchema.findOne(query);
        try {
            if (NodeDocument === null) {
                const rawNode = NodeMap_1.default.toPersistence(node);
                const NodeCreated = await this.NodeSchema.create(rawNode);
                return NodeMap_1.default.toDomain(NodeCreated);
            }
            else {
                NodeDocument.name = node.props.name.value;
                NodeDocument.latitude = node.props.coordinates.latitude;
                NodeDocument.longitude = node.props.coordinates.longitude;
                NodeDocument.shortName = node.props.shortName.value;
                NodeDocument.isDepot = node.props.isDepot;
                NodeDocument.isReliefPoint = node.props.isReliefPoint;
                var newCrewTravelTime = [];
                if (!lodash_1.isUndefined(node.crewTravelTimes)) {
                    node.crewTravelTimes.forEach(element => {
                        newCrewTravelTime.push({
                            node: element.node,
                            duration: element.duration
                        });
                    });
                }
                NodeDocument.crewTravelTimes = newCrewTravelTime;
                await NodeDocument.save();
                return node;
            }
        }
        catch (err) {
            throw err;
        }
    }
    async findByCode(nodeCode) {
        const query = { code: nodeCode.toString() };
        const NodeRecord = await this.NodeSchema.findOne(query);
        if (NodeRecord != null) {
            return NodeMap_1.default.toDomain(NodeRecord);
        }
        else
            return null;
    }
    async findAll() {
        const records = await this.NodeSchema.find({});
        var arr = [];
        records.forEach(elem => {
            arr.push(NodeMap_1.default.toDomain(elem));
        });
        return arr;
    }
    async findByCodeDB(Code) {
        const query = { code: Code.toString() };
        const NodeRecord = await this.NodeSchema.findOne(query);
        if (NodeRecord != null) {
            return NodeRecord;
        }
        else {
            return null;
        }
    }
};
NodeRepo = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject(config_1.default.schemas.node.name)),
    __metadata("design:paramtypes", [mongoose_1.Model])
], NodeRepo);
exports.default = NodeRepo;
//# sourceMappingURL=NodeRepo.js.map