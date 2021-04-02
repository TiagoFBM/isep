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
const DriverType_1 = require("../domain/driver/DriverType");
const Result_1 = require("../core/logic/Result");
const DriverTypeMap_1 = __importDefault(require("../mappers/DriverTypeMap"));
let DriverTypeService = class DriverTypeService {
    constructor(DriverTypeRepo) {
        this.DriverTypeRepo = DriverTypeRepo;
    }
    async createDriverType(DriverTypeDTO) {
        try {
            const DriverTypeOrError = await DriverType_1.DriverType.create(DriverTypeDTO);
            if (DriverTypeOrError.isFailure) {
                return Result_1.Result.fail(DriverTypeOrError.errorValue());
            }
            const DriverTypeResult = DriverTypeOrError.getValue();
            await this.DriverTypeRepo.save(DriverTypeResult);
            const DriverTypeDTOResult = DriverTypeMap_1.default.toDTO(DriverTypeResult);
            return Result_1.Result.ok(DriverTypeDTOResult);
        }
        catch (e) {
            throw e;
        }
    }
};
DriverTypeService = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject(config_1.default.repos.driverType.name)),
    __metadata("design:paramtypes", [Object])
], DriverTypeService);
exports.default = DriverTypeService;
//# sourceMappingURL=DriverTypeService.js.map