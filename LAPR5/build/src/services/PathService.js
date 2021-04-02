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
const Result_1 = require("../core/logic/Result");
const Path_1 = require("../domain/path/Path");
const PathMap_1 = __importDefault(require("../mappers/PathMap"));
const logger_1 = __importDefault(require("./../loaders/logger"));
const Guard_1 = require("../core/logic/Guard");
const PathSegment_1 = require("../domain/path/PathSegment");
let PathService = class PathService {
    constructor(PathRepo, NodeRepo) {
        this.PathRepo = PathRepo;
        this.NodeRepo = NodeRepo;
    }
    async createPath(pathDTO) {
        try {
            var PathOrError = Path_1.Path.create(pathDTO);
            PathOrError = await this.addSegments(pathDTO, PathOrError);
            logger_1.default.debug("Successfully created the Path domain class.");
            //console.log(pathDTO);
            if (PathOrError.isFailure) {
                logger_1.default.error(PathOrError.error);
                return Result_1.Result.fail(PathOrError.error);
            }
            const PathResult = PathOrError.getValue();
            //console.log(PathResult);
            console.log(PathResult.pathSegments);
            await this.PathRepo.save(PathResult);
            logger_1.default.debug("Successfully saved the path to the database.");
            const PathDTOResult = PathMap_1.default.toDTO(PathResult);
            return Result_1.Result.ok(PathDTOResult);
        }
        catch (err) {
            throw (err);
        }
    }
    async addSegments(pathDTO, PathOrError) {
        for (const elem of pathDTO.segments) {
            var nodeTemp1 = await this.NodeRepo.findByCodeDB(elem.firstNodeID);
            var nodeTemp2 = await this.NodeRepo.findByCodeDB(elem.secondNodeID);
            if (Guard_1.Guard.isNull(nodeTemp1, "node1").succeeded || Guard_1.Guard.isNull(nodeTemp2, "node2").succeeded) {
                return Result_1.Result.fail("erro");
            }
            var segm = PathSegment_1.PathSegment.create(elem);
            segm.getValue().addFirstNodeID(nodeTemp1._id);
            segm.getValue().addSecondNodeID(nodeTemp2._id);
            PathOrError.getValue().addSegment(segm.getValue());
        }
        return PathOrError;
    }
};
PathService = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject(config_1.default.repos.path.name)),
    __param(1, typedi_1.Inject(config_1.default.repos.node.name)),
    __metadata("design:paramtypes", [Object, Object])
], PathService);
exports.default = PathService;
//# sourceMappingURL=PathService.js.map