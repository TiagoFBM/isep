"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    Object.defineProperty(o, k2, { enumerable: true, get: function() { return m[k]; } });
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sinon = __importStar(require("sinon"));
const typedi_1 = require("typedi");
const config_1 = __importDefault(require("../../config"));
const Result_1 = require("../core/logic/Result");
const VehicleTypeController_1 = __importDefault(require("./VehicleTypeController"));
describe('Vehicle Type Controller', function () {
    beforeEach(function () {
    });
    afterEach(function () {
        sinon.restore();
    });
    it('Create Vehicle Type: returns json with his values', async function () {
        let body = {
            "code": "CodeVehicleType11111",
            "description": "Teste",
            "autonomy": 0,
            "fuelType": "Gasolina",
            "costPerKilometer": 20,
            "averageConsuption": 6,
            "averageSpeed": 100
        };
        let req = {};
        req.body = body;
        let res = {
            json: sinon.spy()
        };
        let next = () => { };
        let vehicleTypeServiceClass = require(config_1.default.services.vehicleType.path).default;
        let vehicleTypeServiceInstance = typedi_1.Container.get(vehicleTypeServiceClass);
        typedi_1.Container.set(config_1.default.services.vehicleType.name, vehicleTypeServiceInstance);
        vehicleTypeServiceInstance = typedi_1.Container.get(config_1.default.services.vehicleType.name);
        sinon.stub(vehicleTypeServiceInstance, "createVehicleType").returns(Result_1.Result.ok({ "code": req.body.code, "description": req.body.description, "autonomy": req.body.autonomy, "fuelType": req.body.fuelType, "costPerKilometer": req.body.costPerKilometer, "averageConsuption": req.body.averageConsuption, "averageSpeed": req.body.averageSpeed }));
        const ctrl = new VehicleTypeController_1.default(vehicleTypeServiceInstance);
        await ctrl.createVehicleType(req, res, next);
        sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match({ "code": req.body.code, "description": req.body.description, "autonomy": req.body.autonomy, "fuelType": req.body.fuelType, "costPerKilometer": req.body.costPerKilometer, "averageConsuption": req.body.averageConsuption, "averageSpeed": req.body.averageSpeed }));
    });
});
//# sourceMappingURL=vehicletypecontroller.test.js.map