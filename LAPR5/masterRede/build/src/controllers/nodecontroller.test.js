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
const NodeController_1 = __importDefault(require("./NodeController"));
describe('Node Controller Test', function () {
    it('Create Node Test: returns json with his values', async function () {
        const list = [{ node: "Node:11", duration: 1234 }];
        const body = {
            code: "Node:11",
            name: "Baltar",
            latitude: 41.1937898023744,
            longitude: -8.38716802227697,
            shortName: "BALTR",
            isDepot: false,
            isReliefPoint: false,
            crewTravelTimes: list
        };
        let req = {};
        req.body = body;
        let res = {
            json: sinon.spy()
        };
        let next = () => { };
        let nodeServiceClass = require(config_1.default.services.node.path).default;
        let nodeServiceInstance = typedi_1.Container.get(nodeServiceClass);
        typedi_1.Container.set(config_1.default.services.node.name, nodeServiceInstance);
        nodeServiceInstance = typedi_1.Container.get(config_1.default.services.node.name);
        sinon.stub(nodeServiceInstance, "createNode").returns(Result_1.Result.ok({ "code": req.body.code, "name": req.body.name, "latitude": req.body.latitude, "longitude": req.body.longitude, "shortName": req.body.shortName, "isDepot": req.body.isDepot, "isReliefPoint": req.body.isReliefPoint }));
        const ctrl = new NodeController_1.default(nodeServiceInstance);
        await ctrl.createNode(req, res, next);
        sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match({ "code": req.body.code, "name": req.body.name, "latitude": req.body.latitude, "longitude": req.body.longitude, "shortName": req.body.shortName, "isDepot": req.body.isDepot, "isReliefPoint": req.body.isReliefPoint }));
    });
});
//# sourceMappingURL=nodecontroller.test.js.map