import * as sinon from 'sinon';

import { Response, Request, NextFunction } from 'express';

import { Container } from 'typedi';
import config from "../../config";

import { Result } from '../core/logic/Result';
import IDriverTypeService from "../services/IServices/IDriverTypeService";
import DriverTypeController from "./DriverTypeController";
import IDriverTypeDTO from '../dto/IDriverTypeDTO';


describe('Driver Type Controller Test', function () {

    it('Create Driver Type Test: returns json with his values', async function () {
        let body = {
            "code": "condutor1",
            "description": "condutor experiente"
        };
        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy()
        };

        let next: Partial<NextFunction> = () => { };

        let driverTypeServiceClass = require(config.services.driverType.path).default;
        let driverTypeServiceInstance = Container.get(driverTypeServiceClass);
        Container.set(config.services.driverType.name, driverTypeServiceInstance);

        driverTypeServiceInstance = Container.get(config.services.driverType.name);

        sinon.stub(driverTypeServiceInstance, "createDriverType").returns(Result.ok<IDriverTypeDTO>({ "code": req.body.code, "description": req.body.description }));

        const ctrl = new DriverTypeController(driverTypeServiceInstance as IDriverTypeService);

        await ctrl.createDriverType(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.calledOnce(res.json);

        sinon.assert.calledWith(res.json, sinon.match({ "code": req.body.code, "description": req.body.description}));

    });
});