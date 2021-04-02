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
const DriverTypeController_1 = __importDefault(require("./DriverTypeController"));
describe('Driver Type Controller Test', function () {
    it('Create Driver Type Test: returns json with his values', async function () {
        let body = {
            "code": "condutor1",
            "description": "condutor experiente"
        };
        let req = {};
        req.body = body;
        let res = {
            json: sinon.spy()
        };
        let next = () => { };
        let driverTypeServiceClass = require(config_1.default.services.driverType.path).default;
        let driverTypeServiceInstance = typedi_1.Container.get(driverTypeServiceClass);
        typedi_1.Container.set(config_1.default.services.driverType.name, driverTypeServiceInstance);
        driverTypeServiceInstance = typedi_1.Container.get(config_1.default.services.driverType.name);
        sinon.stub(driverTypeServiceInstance, "createDriverType").returns(Result_1.Result.ok({ "code": req.body.code, "description": req.body.description }));
        const ctrl = new DriverTypeController_1.default(driverTypeServiceInstance);
        await ctrl.createDriverType(req, res, next);
        sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match({ "code": req.body.code, "description": req.body.description }));
    });
});
//# sourceMappingURL=drivertypecontroller.test.js.map