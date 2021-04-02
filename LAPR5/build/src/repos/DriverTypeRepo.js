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
const DriverTypeMap_1 = __importDefault(require("../mappers/DriverTypeMap"));
const mongoose_1 = require("mongoose");
let DriverTypeRepo = class DriverTypeRepo {
    constructor(DriverTypeSchema) {
        this.DriverTypeSchema = DriverTypeSchema;
    }
    createBaseQuery() {
        return {
            where: {},
        };
    }
    async exists(driverType) {
        const codeX = driverType.props.code.value;
        const query = { code: codeX };
        const driverTypeFound = await this.DriverTypeSchema.findOne(query);
        return !!driverTypeFound === true;
    }
    async save(driverType) {
        const query = { code: driverType.id.toString() };
        const DriverTypeDocument = await this.DriverTypeSchema.findOne(query);
        try {
            if (DriverTypeDocument === null) {
                const rawDriverType = DriverTypeMap_1.default.toPersistence(driverType);
                const DriverTypeCreated = await this.DriverTypeSchema.create(rawDriverType);
                return DriverTypeMap_1.default.toDomain(DriverTypeCreated);
            }
            else {
                DriverTypeDocument.description = driverType.props.description.value;
                await DriverTypeDocument.save();
                return driverType;
            }
        }
        catch (err) {
            throw err;
        }
    }
    async findByCode(driverTypeCode) {
        const query = { code: driverTypeCode.toString() };
        const DriverTypeRecord = await this.DriverTypeSchema.findOne(query);
        if (DriverTypeRecord != null) {
            return DriverTypeMap_1.default.toDomain(DriverTypeRecord);
        }
        else
            return null;
    }
};
DriverTypeRepo = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject('DriverTypeSchema')),
    __metadata("design:paramtypes", [mongoose_1.Model])
], DriverTypeRepo);
exports.default = DriverTypeRepo;
//# sourceMappingURL=DriverTypeRepo.js.map