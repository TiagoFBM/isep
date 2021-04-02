"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const sinon = require('sinon');
const typedi_1 = __importDefault(require("typedi"));
const config_1 = __importDefault(require("../../config"));
const Result_1 = require("../core/logic/Result");
const DriverType_1 = require("../domain/driver/DriverType");
const DriverTypeCode_1 = require("../domain/driver/DriverTypeCode");
const DriverTypeDescription_1 = require("../domain/driver/DriverTypeDescription");
const DriverTypeService_1 = __importDefault(require("./DriverTypeService"));
describe('DriverType service create', () => {
    const codeV = DriverTypeCode_1.DriverTypeCode.create('CodeDriverType1');
    const descriptionV = DriverTypeDescription_1.DriverTypeDescription.create("Service Test Description");
    let driverType = DriverType_1.DriverType.create({
        code: codeV.getValue().value,
        description: descriptionV.getValue().value,
    });
    let res = Result_1.Result.ok({
        code: 'CodeDriverType11111',
        description: 'Service Test Description'
    });
    const dto = {
        code: 'CodeDriverType11111',
        description: 'Service Test Description'
    };
    let DriverTypeRepoClass = require(config_1.default.repos.driverType.path).default;
    let DriverTypeRepoInstance = typedi_1.default.get(DriverTypeRepoClass);
    typedi_1.default.set(config_1.default.repos.driverType.name, DriverTypeRepoInstance);
    DriverTypeRepoInstance = typedi_1.default.get(config_1.default.repos.driverType.name);
    let driverTypeServiceInstance = new DriverTypeService_1.default(DriverTypeRepoInstance);
    beforeEach(() => {
    });
    afterEach(function () {
        sinon.restore();
    });
    it('should create ', async () => {
        sinon.stub(DriverTypeRepoInstance, "save").returns(driverType.getValue());
        sinon.assert.match((await driverTypeServiceInstance.createDriverType(dto)), res);
    });
});
//# sourceMappingURL=driverTypeService.test.js.map