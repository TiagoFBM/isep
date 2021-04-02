import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import ILineController from '../../controllers/IControllers/ILineController'; 

import config from "../../../config";

const route = Router();

export default (app: Router) => {
  app.use('/line', route);

  const ctrl = Container.get(config.controllers.line.name) as ILineController;

  route.post('',
    celebrate({
      body: Joi.object({
        code: Joi.string().required(),
        description: Joi.string().required(),
        linePaths: Joi.array().items().required(),
        allowedVehicles: Joi.array().items(Joi.string()),
        deniedVehicles: Joi.array().items(Joi.string()),
        allowedDrivers: Joi.array().items(Joi.string()),
        deniedDrivers: Joi.array().items(Joi.string()),
        lineColor: Joi.string().required()
      })
    }),
    (req, res, next) => ctrl.createLine(req, res, next) );

    route.get("", (req, res, next) => ctrl.findAllLines(req, res, next));

    route.get("/count", (req, res, next) => ctrl.getNumberOfLines(req, res, next));
    
    route.get("/:code/paths", (req, res, next) => ctrl.findAllLinePaths(req, res, next));
    
    route.get("/path/:code", (req, res, next) => ctrl.findLineOfPath(req, res, next));

    route.get("/:code", (req, res, next) => ctrl.findLineByCode(req, res, next));

};