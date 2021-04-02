import * as sinon from 'sinon';

import { Response, Request, NextFunction } from 'express';

import { Container } from 'typedi';
import config from "../../config";

import { Result } from '../core/logic/Result';
import IVehicleTypeService from "../services/IServices/IVehicleTypeService";
import VehicleTypeController from "./VehicleTypeController";
import IVehicleTypeDTO from '../dto/IVehicleTypeDTO';


describe('Vehicle Type Controller', function () {

    beforeEach(function () {

    })

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
        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy()
        };

        let next: Partial<NextFunction> = () => { };

        let vehicleTypeServiceClass = require(config.services.vehicleType.path).default;
        let vehicleTypeServiceInstance = Container.get(vehicleTypeServiceClass);
        Container.set(config.services.vehicleType.name, vehicleTypeServiceInstance);

        vehicleTypeServiceInstance = Container.get(config.services.vehicleType.name);

        sinon.stub(vehicleTypeServiceInstance, "createVehicleType").returns(Result.ok<IVehicleTypeDTO>({ "code": req.body.code, "description": req.body.description, "autonomy": req.body.autonomy, "fuelType": req.body.fuelType, "costPerKilometer": req.body.costPerKilometer, "averageConsuption": req.body.averageConsuption, "averageSpeed": req.body.averageSpeed }));

        const ctrl = new VehicleTypeController(vehicleTypeServiceInstance as IVehicleTypeService);

        await ctrl.createVehicleType(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.calledOnce(res.json);

        sinon.assert.calledWith(res.json, sinon.match({ "code": req.body.code, "description": req.body.description, "autonomy": req.body.autonomy, "fuelType": req.body.fuelType, "costPerKilometer": req.body.costPerKilometer, "averageConsuption": req.body.averageConsuption, "averageSpeed": req.body.averageSpeed }));

    });

});