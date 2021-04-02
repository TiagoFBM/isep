import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import INodeController from '../../controllers/IControllers/INodeController';

import config from "../../../config";

const route = Router();

export default (app: Router) => {
    app.use('/node', route);

    const ctrl = Container.get(config.controllers.node.name) as INodeController;
    
    route.post('',
        celebrate({
            body: Joi.object({
                code: Joi.string().required(),
                name: Joi.string().required(),
                latitude: Joi.string().required(),
                longitude: Joi.string().required(),
                shortName: Joi.string().required(),
                isDepot: Joi.boolean().required(),
                isReliefPoint: Joi.boolean().required(),
                crewTravelTimes: Joi.array().items(),
            })
        }),
        (req, res, next) => ctrl.createNode(req, res, next));

    route.get("", (req, res, next) => ctrl.findAllNodes(req, res, next));

    route.get("/count", (req, res, next) => ctrl.getNumberOfNodes(req, res, next));

    route.get("/:code", (req, res, next) => ctrl.getNodeByCode(req, res, next));
};