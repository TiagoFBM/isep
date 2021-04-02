import * as sinon from 'sinon';

import { Response, Request, NextFunction } from 'express';

import { Container } from 'typedi';
import config from "../../config";

import { Result } from '../core/logic/Result';
import ILineService from "../services/IServices/ILineService";
import LineController from "./LineController";
import ILineDTO from '../dto/ILineDTO';
import IPathSegmentDTO from '../dto/IPathSegmentDTO';
import IPathDTO from '../dto/IPathDTO';
import ILinePathDTO from '../dto/ILinePathDTO';

describe('Line Controller Test', function () {

   /* it('Create Line Test: returns json with his values', async function () {
        const linePaths: Array<ILinePathCreateDTO> = [{
            path: "path1",
            orientation: "Return"
        }, {
            path: "path2",
            orientation: "Go"
        }]

        const body: ILineCreateDTO = {
            code: "code",
            description: "description",
            linePaths: linePaths,
            lineColor: "red",
        }

        const linePathSegmentsReturned: Array<IPathSegmentDTO> = [{
            firstNodeID: "first",
            secondNodeID: "second",
            travelTimeBetweenNodes: 5,
            distanceBetweenNodes: 10
        }]

        const linePathsReturned: Array<ILinePathDTO> = [{
            path: {
                code: "code",
                isEmpty: false,
                segments: linePathSegmentsReturned
            },
            orientation: "Return"
        }]

        const lineReturned: ILineDTO = {
            code: "code",
            description: "description",
            linePaths: linePathsReturned,
            lineColor: "red",
        }

        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy()
        };

        let next: Partial<NextFunction> = () => { };

        let lineServiceClass = require(config.services.line.path).default;
        let lineServiceInstance = Container.get(lineServiceClass);
        Container.set(config.services.line.name, lineServiceInstance);

        lineServiceInstance = Container.get(config.services.line.name);

        sinon.stub(lineServiceInstance, "createLine").returns(Result.ok<ILineDTO>(lineReturned));

        const ctrl = new LineController(lineServiceInstance as ILineService);

        await ctrl.createLine(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.calledOnce(res.json);

        sinon.assert.calledWith(res.json, sinon.match(lineReturned));

    });*/
});