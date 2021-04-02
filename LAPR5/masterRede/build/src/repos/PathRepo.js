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
const mongoose_1 = require("mongoose");
const typedi_1 = require("typedi");
const PathMap_1 = __importDefault(require("../mappers/PathMap"));
let PathRepo = class PathRepo {
    constructor(PathSchema) {
        this.PathSchema = PathSchema;
    }
    createBaseQuery() {
        return {
            where: {},
        };
    }
    async exists(path) {
        const codeX = path.pathCode.value;
        const query = { code: codeX };
        const pathFound = await this.PathSchema.findOne(query);
        return !!pathFound === true;
    }
    async save(path) {
        const query = { code: path.pathCode.value };
        const PathDocument = await this.PathSchema.findOne(query);
        try {
            if (PathDocument === null) {
                const rawPath = PathMap_1.default.toPersistence(path);
                let pathCreated = await this.PathSchema.create(rawPath);
                pathCreated = await this.PathSchema.populate(pathCreated, {
                    path: "segments",
                    populate: {
                        path: "firstNodeID secondNodeID"
                    }
                });
                return PathMap_1.default.toDomain(pathCreated);
            }
            else {
                /*PathDocument.isEmpty = path.pathIsEmpty;
                PathDocument.segments = [];
                path.pathSegments.forEach(elem => {

                });*/
            }
        }
        catch (err) {
            console.log(err);
            throw err;
        }
    }
    async findByCode(pathCode) {
        const query = { code: pathCode.toString() };
        const PathRecord = await this.PathSchema.findOne(query).populate({
            path: "segments",
            populate: {
                path: "firstNodeID secondNodeID"
            }
        });
        if (PathRecord != null) {
            return PathMap_1.default.toDomain(PathRecord);
        }
        return null;
    }
    async findByCodeDB(Code) {
        const query = { code: Code.toString() };
        const pathRecord = await this.PathSchema.findOne(query);
        if (pathRecord != null) {
            return pathRecord;
        }
        else {
            return null;
        }
    }
    async findAll() {
        const PathRecords = await this.PathSchema.find({}).populate({
            path: "segments",
            populate: {
                path: "firstNodeID secondNodeID"
            }
        });
        var arr = [];
        PathRecords.forEach(elem => {
            arr.push(PathMap_1.default.toDomain(elem));
        });
        return arr;
    }
};
PathRepo = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject("PathSchema")),
    __metadata("design:paramtypes", [mongoose_1.Model])
], PathRepo);
exports.default = PathRepo;
//# sourceMappingURL=PathRepo.js.map