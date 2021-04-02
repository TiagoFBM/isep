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
const config_1 = __importDefault(require("../../config"));
const Node_1 = require("../domain/node/Node");
const Result_1 = require("../core/logic/Result");
const NodeMap_1 = __importDefault(require("../mappers/NodeMap"));
let NodeService = class NodeService {
    constructor(NodeRepo) {
        this.NodeRepo = NodeRepo;
    }
    async createNode(NodeDTO) {
        try {
            const NodeOrError = await Node_1.Node.create(NodeDTO);
            if (NodeOrError.isFailure) {
                return Result_1.Result.fail(NodeOrError.errorValue());
            }
            const NodeResult = NodeOrError.getValue();
            await this.NodeRepo.save(NodeResult);
            const NodeDTOResult = NodeMap_1.default.toDTO(NodeResult);
            return Result_1.Result.ok(NodeDTOResult);
        }
        catch (e) {
            throw e;
        }
    }
    async findAllNodes() {
        try {
            const recs = await this.NodeRepo.findAll();
            var arr = [];
            recs.forEach(elem => {
                arr.push(NodeMap_1.default.toDTO(elem));
            });
            return arr;
        }
        catch (err) {
            throw err;
        }
    }
};
NodeService = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject(config_1.default.repos.node.name)),
    __metadata("design:paramtypes", [Object])
], NodeService);
exports.default = NodeService;
//# sourceMappingURL=NodeService.js.map