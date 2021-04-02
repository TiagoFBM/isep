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
const LineMap_1 = __importDefault(require("../mappers/LineMap"));
const mongoose_1 = require("mongoose");
let LineRepo = class LineRepo {
    constructor(LineSchema) {
        this.LineSchema = LineSchema;
    }
    createBaseQuery() {
        return {
            where: {},
        };
    }
    async exists(Line) {
        const codeX = Line.props.code.value;
        const query = { code: codeX };
        const LineFound = await this.LineSchema.findOne(query);
        return !!LineFound === true;
    }
    async save(Line) {
        const query = { code: Line.lineCode.value };
        const LineDocument = await this.LineSchema.findOne(query);
        try {
            if (LineDocument === null) {
                console.log("a");
                const rawLine = await LineMap_1.default.toPersistence(Line);
                let LineCreated = await this.LineSchema.create(rawLine);
                LineCreated = await this.LineSchema.populate(LineCreated, {
                    path: "linePaths",
                    populate: {
                        path: "path",
                        populate: {
                            path: "segments",
                            populate: { path: "firstNodeID secondNodeID" }
                        }
                    }
                });
                let lineDomain = LineMap_1.default.toDomain(LineCreated);
                return lineDomain;
            }
            else {
                //TODO
                LineDocument.description = Line.props.description.value;
                await LineDocument.save();
                return Line;
            }
        }
        catch (err) {
            throw err;
        }
    }
    async findByCode(LineCode) {
        const query = { code: LineCode.toString() };
        const LineRecord = await this.LineSchema.findOne(query).populate({
            path: "linePaths",
            populate: {
                path: "path",
                populate: {
                    path: "segments",
                    populate: { path: "firstNodeID secondNodeID" }
                }
            }
        });
        if (LineRecord != null) {
            return LineMap_1.default.toDomain(LineRecord);
        }
        else
            return null;
    }
    async findAll() {
        const records = await this.LineSchema.find({}).populate({
            path: "linePaths",
            populate: {
                path: "path",
                populate: {
                    path: "segments",
                    populate: { path: "firstNodeID secondNodeID" }
                }
            }
        });
        var arr = [];
        records.forEach(elem => {
            arr.push(LineMap_1.default.toDomain(elem));
        });
        return arr;
    }
};
LineRepo = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject(config_1.default.schemas.line.name)),
    __metadata("design:paramtypes", [mongoose_1.Model])
], LineRepo);
exports.default = LineRepo;
//# sourceMappingURL=LineRepo.js.map