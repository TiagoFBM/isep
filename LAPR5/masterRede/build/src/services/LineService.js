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
const Line_1 = require("../domain/line/Line");
const Result_1 = require("../core/logic/Result");
const LineMap_1 = __importDefault(require("../mappers/LineMap"));
const lodash_1 = require("lodash");
const LineMap_2 = __importDefault(require("../mappers/LineMap"));
const LinePath_1 = require("../domain/line/LinePath");
let LineService = class LineService {
    constructor(lineRepo, vehicleTypeRepo, driverTypeRepo, pathRepo) {
        this.lineRepo = lineRepo;
        this.vehicleTypeRepo = vehicleTypeRepo;
        this.driverTypeRepo = driverTypeRepo;
        this.pathRepo = pathRepo;
    }
    async createLine(lineDTO) {
        try {
            const lineOrError = Line_1.Line.create({
                code: lineDTO.code,
                description: lineDTO.description,
                linePaths: [],
                allowedVehicles: [],
                deniedVehicles: [],
                allowedDrivers: [],
                deniedDrivers: [],
                lineColor: lineDTO.lineColor
            });
            if (lineOrError.isFailure) {
                return Result_1.Result.fail(lineOrError.errorValue());
            }
            for (let i in lineDTO.linePaths) {
                const element = lineDTO.linePaths[i];
                var newPath = await this.pathRepo.findByCodeDB(element.path.toString());
                if (newPath != null) {
                    let newLinePathOrError = LinePath_1.LinePath.create({
                        path: newPath._id,
                        orientation: element.orientation
                    });
                    if (newLinePathOrError.isFailure) {
                        return Result_1.Result.fail(newLinePathOrError.errorValue());
                    }
                    lineOrError.getValue().addLinePath(newLinePathOrError.getValue());
                }
            }
            if (!lodash_1.isUndefined(lineDTO.allowedVehicles)) {
                for (let i in lineDTO.allowedVehicles) {
                    const element = lineDTO.allowedVehicles[i];
                    var allowedVehicle = await this.vehicleTypeRepo.findByCodeDB(element.toString());
                    if (allowedVehicle != null) {
                        lineOrError.getValue().addAllowedVehicleTypesID(allowedVehicle._id);
                    }
                }
            }
            if (!lodash_1.isUndefined(lineDTO.deniedVehicles)) {
                for (let i in lineDTO.deniedVehicles) {
                    const element = lineDTO.deniedVehicles[i];
                    var deniedVehicleOrFailure = await this.vehicleTypeRepo.findByCode(element);
                    if (deniedVehicleOrFailure != null) {
                        lineOrError.getValue().addDeniedVehicleTypes(deniedVehicleOrFailure);
                    }
                }
            }
            if (!lodash_1.isUndefined(lineDTO.allowedDrivers)) {
                for (let i in lineDTO.allowedDrivers) {
                    const element = lineDTO.allowedDrivers[i];
                    var allowedDriverOrFailure = await this.driverTypeRepo.findByCode(element);
                    if (allowedDriverOrFailure != null) {
                        lineOrError.getValue().addAllowedDriverTypes(allowedDriverOrFailure);
                    }
                }
            }
            if (!lodash_1.isUndefined(lineDTO.deniedDrivers)) {
                for (let i in lineDTO.deniedDrivers) {
                    const element = lineDTO.deniedDrivers[i];
                    var deniedDriverOrFailure = await this.driverTypeRepo.findByCode(element);
                    if (deniedDriverOrFailure != null) {
                        lineOrError.getValue().addDeniedDriverTypes(deniedDriverOrFailure);
                    }
                }
            }
            const lineResult = lineOrError.getValue();
            console.log("lineResult.allowedVehicles");
            console.log(lineResult.allowedVehicles);
            const res = await this.lineRepo.save(lineResult);
            console.log("res");
            console.log(res);
            const lineDTOResult = LineMap_1.default.toDTO(res);
            console.log("lineDTOResult");
            console.log(lineDTOResult);
            return Result_1.Result.ok(lineDTOResult);
        }
        catch (e) {
            throw e;
        }
    }
    async findAllLines() {
        try {
            const recs = await this.lineRepo.findAll();
            var arr = [];
            recs.forEach(elem => {
                const lineDTO = LineMap_1.default.toDTO(elem);
                arr.push(lineDTO);
            });
            return arr;
        }
        catch (err) {
            throw err;
        }
    }
    async findAllLinePaths(LineCode) {
        try {
            const rec = await this.lineRepo.findByCode(LineCode);
            return LineMap_2.default.toDTO(rec).linePaths;
        }
        catch (err) {
            throw err;
        }
    }
};
LineService = __decorate([
    typedi_1.Service(),
    __param(0, typedi_1.Inject(config_1.default.repos.line.name)),
    __param(1, typedi_1.Inject(config_1.default.repos.vehicleType.name)),
    __param(2, typedi_1.Inject(config_1.default.repos.driverType.name)),
    __param(3, typedi_1.Inject(config_1.default.repos.path.name)),
    __metadata("design:paramtypes", [Object, Object, Object, Object])
], LineService);
exports.default = LineService;
//# sourceMappingURL=LineService.js.map