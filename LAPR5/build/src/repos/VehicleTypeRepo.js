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
const VehicleTypeMap_1 = __importDefault(require("../mappers/VehicleTypeMap"));
const mongoose_1 = require("mongoose");
let VehicleTypeRepo = class VehicleTypeRepo {
    constructor(VehicleTypeSchema) {
        this.VehicleTypeSchema = VehicleTypeSchema;
    }
    createBaseQuery() {
        return {
            where: {},
        };
    }
    async exists(VehicleType) {
        const codeX = VehicleType.props.code.value;
        const query = { code: codeX };
        const vehicleTypeFound = await this.VehicleTypeSchema.findOne(query);
        return !!vehicleTypeFound === true;
    }
    async save(vehicleType) {
        const query = { code: vehicleType.id.toString() };
        const VehicleTypeDocument = await this.VehicleTypeSchema.findOne(query);
        try {
            if (VehicleTypeDocument === null) {
                const rawVehicleType = VehicleTypeMap_1.default.toPersistence(vehicleType);
                const VehicleTypeCreated = await this.VehicleTypeSchema.create(rawVehicleType);
                return VehicleTypeMap_1.default.toDomain(VehicleTypeCreated);
            }
            else {
                VehicleTypeDocument.description = vehicleType.props.description.value;
                VehicleTypeDocument.autonomy = vehicleType.props.autonomy.value;
                VehicleTypeDocument.fuelType = vehicleType.props.fuelType.props.value;
                VehicleTypeDocument.costPerKilometer = vehicleType.props.costPerKilometer.value;
                VehicleTypeDocument.averageConsuption = vehicleType.props.averageConsuption.value;
                VehicleTypeDocument.averageSpeed = vehicleType.props.averageSpeed.value;
                await VehicleTypeDocument.save();
                return vehicleType;
            }
        }
        catch (err) {
            throw err;
        }
    }
    async findByCode(vehicleTypeCode) {
        const query = { code: vehicleTypeCode.toString() };
        const VehicleTypeRecord = await this.VehicleTypeSchema.findOne(query);
        if (VehicleTypeRecord != null) {
            return VehicleTypeMap_1.default.toDomain(VehicleTypeRecord);
        }
        else
            return null;
    }
};
VehicleTypeRepo = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject('VehicleTypeSchema')),
    __metadata("design:paramtypes", [mongoose_1.Model])
], VehicleTypeRepo);
exports.default = VehicleTypeRepo;
//# sourceMappingURL=VehicleTypeRepo.js.map