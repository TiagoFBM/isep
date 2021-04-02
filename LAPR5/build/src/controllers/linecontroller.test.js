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
const LineController_1 = __importDefault(require("./LineController"));
describe('Line Controller Test', function () {
    it('Create Line Test: returns json with his values', async function () {
        const linePaths = [{
                path: "path1",
                orientation: "Return"
            }, {
                path: "path2",
                orientation: "Go"
            }];
        const body = {
            code: "code",
            description: "description",
            linePaths: linePaths,
            lineColor: "red",
        };
        const linePathSegmentsReturned = [{
                firstNodeID: "first",
                secondNodeID: "second",
                travelTimeBetweenNodes: 5,
                distanceBetweenNodes: 10
            }];
        const linePathsReturned = [{
                path: {
                    code: "code",
                    isEmpty: false,
                    segments: linePathSegmentsReturned
                },
                orientation: "Return"
            }];
        const lineReturned = {
            code: "code",
            description: "description",
            linePaths: linePathsReturned,
            lineColor: "red",
        };
        let req = {};
        req.body = body;
        let res = {
            json: sinon.spy()
        };
        let next = () => { };
        let lineServiceClass = require(config_1.default.services.line.path).default;
        let lineServiceInstance = typedi_1.Container.get(lineServiceClass);
        typedi_1.Container.set(config_1.default.services.line.name, lineServiceInstance);
        lineServiceInstance = typedi_1.Container.get(config_1.default.services.line.name);
        sinon.stub(lineServiceInstance, "createLine").returns(Result_1.Result.ok(lineReturned));
        const ctrl = new LineController_1.default(lineServiceInstance);
        await ctrl.createLine(req, res, next);
        sinon.assert.calledOnce(res.json);
        sinon.assert.calledWith(res.json, sinon.match(lineReturned));
    });
});
//# sourceMappingURL=linecontroller.test.js.map