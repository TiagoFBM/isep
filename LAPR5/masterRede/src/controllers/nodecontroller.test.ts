import * as sinon from 'sinon';

import { Response, Request, NextFunction } from 'express';

import { Container } from 'typedi';
import config from "../../config";

import { Result } from '../core/logic/Result';
import INodeService from "../services/IServices/INodeService";
import NodeController from "./NodeController";
import INodeDTO from '../dto/INodeDTO';


describe('Node Controller Test', function () {

    it('Create Node Test: returns json with his values', async function () {

        const list = [{node: "Node:11", duration:1234}];
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
        let req: Partial<Request> = {};
        req.body = body;

        let res: Partial<Response> = {
            json: sinon.spy()
        };

        let next: Partial<NextFunction> = () => { };

        let nodeServiceClass = require(config.services.node.path).default;
        let nodeServiceInstance = Container.get(nodeServiceClass);
        Container.set(config.services.node.name, nodeServiceInstance);

        nodeServiceInstance = Container.get(config.services.node.name);

        sinon.stub(nodeServiceInstance, "createNode").returns(Result.ok<INodeDTO>({ "code": req.body.code, "name": req.body.name, "latitude": req.body.latitude, "longitude": req.body.longitude, "shortName":req.body.shortName, "isDepot": req.body.isDepot, "isReliefPoint": req.body.isReliefPoint }));

        const ctrl = new NodeController(nodeServiceInstance as INodeService);

        await ctrl.createNode(<Request>req, <Response>res, <NextFunction>next);

        sinon.assert.calledOnce(res.json);

        sinon.assert.calledWith(res.json, sinon.match({ "code": req.body.code, "name": req.body.name, "latitude": req.body.latitude, "longitude": req.body.longitude, "shortName":req.body.shortName, "isDepot": req.body.isDepot, "isReliefPoint": req.body.isReliefPoint }));

    });
});