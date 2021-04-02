"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sinon = require('sinon');
const typedi_1 = __importDefault(require("typedi"));
const config_1 = __importDefault(require("../../config"));
const Result_1 = require("../core/logic/Result");
const VehicleType_1 = require("../domain/vehicle/VehicleType");
const VehicleTypeCode_1 = require("../domain/vehicle/VehicleTypeCode");
const VehicleTypeDescription_1 = require("../domain/vehicle/VehicleTypeDescription");
const VehicleTypeAutonomy_1 = require("../domain/vehicle/VehicleTypeAutonomy");
const VehicleTypeFuelType_1 = require("../domain/vehicle/VehicleTypeFuelType");
const VehicleTypeCostPerKilometer_1 = require("../domain/vehicle/VehicleTypeCostPerKilometer");
const VehicleTypeAverageConsuption_1 = require("../domain/vehicle/VehicleTypeAverageConsuption");
const VehicleTypeAverageSpeed_1 = require("../domain/vehicle/VehicleTypeAverageSpeed");
const VehicleTypeService_1 = __importDefault(require("./VehicleTypeService"));
describe('VehicleType service create', () => {
    const codeV = VehicleTypeCode_1.VehicleTypeCode.create('CodeVehicleType11111');
    const descriptionV = VehicleTypeDescription_1.VehicleTypeDescription.create("Service Test Description");
    const autonomyV = VehicleTypeAutonomy_1.VehicleTypeAutonomy.create(50000);
    const fuelTypeV = VehicleTypeFuelType_1.VehicleTypeFuelType.create("Gasolina");
    const costPerKilometerV = VehicleTypeCostPerKilometer_1.VehicleTypeCostPerKilometer.create(10);
    const averageConsuptionV = VehicleTypeAverageConsuption_1.VehicleTypeAverageConsuption.create(10);
    const averageSpeedV = VehicleTypeAverageSpeed_1.VehicleTypeAverageSpeed.create(100);
    let vehicleType = VehicleType_1.VehicleType.create({
        code: codeV.getValue().value,
        description: descriptionV.getValue().value,
        autonomy: autonomyV.getValue().value,
        fuelType: fuelTypeV.getValue().value,
        costPerKilometer: costPerKilometerV.getValue().value,
        averageConsuption: averageConsuptionV.getValue().value,
        averageSpeed: averageSpeedV.getValue().value
    });
    let res = Result_1.Result.ok({
        code: 'CodeVehicleType11111',
        description: 'Service Test Description',
        autonomy: 50000,
        fuelType: 'Gasolina',
        costPerKilometer: 10,
        averageConsuption: 10,
        averageSpeed: 100
    });
    const dto = {
        code: 'CodeVehicleType11111',
        description: 'Service Test Description',
        autonomy: 50000,
        fuelType: 'Gasolina',
        costPerKilometer: 10,
        averageConsuption: 10,
        averageSpeed: 100
    };
    let VehicleTypeRepoClass = require(config_1.default.repos.vehicleType.path).default;
    let VehicleTypeRepoInstance = typedi_1.default.get(VehicleTypeRepoClass);
    typedi_1.default.set(config_1.default.repos.vehicleType.name, VehicleTypeRepoInstance);
    VehicleTypeRepoInstance = typedi_1.default.get(config_1.default.repos.vehicleType.name);
    let VehicleTypeServiceInstance = new VehicleTypeService_1.default(VehicleTypeRepoInstance);
    beforeEach(() => {
    });
    afterEach(function () {
        sinon.restore();
    });
    it('should create ', async () => {
        sinon.stub(VehicleTypeRepoInstance, "save").returns(vehicleType.getValue());
        sinon.assert.match((await VehicleTypeServiceInstance.createVehicleType(dto)), res);
    });
});
//# sourceMappingURL=vehicleTypeService.test.js.map