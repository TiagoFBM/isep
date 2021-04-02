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
const PathSegment_1 = require("../domain/path/PathSegment");
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
                allowedVehicles: lineDTO.allowedDrivers,
                deniedVehicles: lineDTO.deniedDrivers,
                allowedDrivers: lineDTO.allowedDrivers,
                deniedDrivers: lineDTO.deniedDrivers,
                lineColor: lineDTO.lineColor
            });
            if (lineOrError.isFailure) {
                return Result_1.Result.fail(lineOrError.errorValue());
            }
            lineDTO.linePaths.forEach(async (element) => {
                var pathOrFailure = await this.pathRepo.findByCode(element.path);
                if (pathOrFailure != null) {
                    console.log("pathOrFailure Service: \n" + pathOrFailure);
                    var newPath = {
                        code: pathOrFailure.props.code.value,
                        isEmpty: pathOrFailure.props.isEmpty,
                        segments: []
                    };
                    var newLinePath = LinePath_1.LinePath.create({
                        path: newPath,
                        orientation: element.orientation
                    });
                    pathOrFailure.pathSegments.forEach(async (element) => {
                        var segment = {
                            firstNodeID: null,
                            secondNodeID: null,
                            travelTimeBetweenNodes: element.travelTimeBetweenNodes.value,
                            distanceBetweenNodes: element.distanceBetweenNodes.value
                        };
                        var segmento = PathSegment_1.PathSegment.create(segment).getValue();
                        segmento.addFirstNodeID(element.firstNodeCode);
                        segmento.addSecondNodeID(element.secondNodeCode);
                        newLinePath.getValue().path.addSegment(segmento);
                    });
                    if (newLinePath.isSuccess) {
                        lineOrError.getValue().addLinePath(newLinePath.getValue());
                    }
                }
            });
            if (!lodash_1.isUndefined(lineDTO.allowedVehicles)) {
                lineDTO.allowedVehicles.forEach(async (element) => {
                    var allowedVehicleOrFailure = await this.vehicleTypeRepo.findByCode(element);
                    if (allowedVehicleOrFailure != null) {
                        lineOrError.getValue().addAllowedVehicleTypes(allowedVehicleOrFailure);
                    }
                });
            }
            if (!lodash_1.isUndefined(lineDTO.deniedVehicles)) {
                lineDTO.deniedVehicles.forEach(async (element) => {
                    var deniedVehicleOrFailure = await this.vehicleTypeRepo.findByCode(element);
                    if (deniedVehicleOrFailure != null) {
                        lineOrError.getValue().addDeniedVehicleTypes(deniedVehicleOrFailure);
                    }
                });
            }
            if (!lodash_1.isUndefined(lineDTO.allowedDrivers)) {
                lineDTO.allowedDrivers.forEach(async (element) => {
                    var allowedDriverOrFailure = await this.driverTypeRepo.findByCode(element);
                    if (allowedDriverOrFailure != null) {
                        lineOrError.getValue().addAllowedDriverTypes(allowedDriverOrFailure);
                    }
                });
            }
            if (!lodash_1.isUndefined(lineDTO.deniedDrivers)) {
                lineDTO.deniedDrivers.forEach(async (element) => {
                    var deniedDriverOrFailure = await this.driverTypeRepo.findByCode(element);
                    if (deniedDriverOrFailure != null) {
                        lineOrError.getValue().addDeniedDriverTypes(deniedDriverOrFailure);
                    }
                });
            }
            const lineResult = lineOrError.getValue();
            await this.lineRepo.save(lineResult);
            const lineDTOResult = LineMap_1.default.toDTO(lineResult);
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
                arr.push(LineMap_1.default.toDTO(elem));
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