import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import IDriverTypeController from '../../controllers/IControllers/IDriverTypeController';

import config from "../../../config";

const route = Router();

export default (app: Router) => {
  app.use('/driverType', route);

  const ctrl = Container.get(config.controllers.driverType.name) as IDriverTypeController;

  route.post('',
    celebrate({
      body: Joi.object({
        code: Joi.string().required(),
        description: Joi.string().required()
      })
    }),
    (req, res, next) => ctrl.createDriverType(req, res, next));

  route.get("", (req, res, next) => ctrl.findAllDriverTypes(req, res, next));

  route.get("/count", (req, res, next) => ctrl.getNumberOfDriverTypes(req, res, next));

  route.get("/:code", (req, res, next) => ctrl.getDriverTypeByCode(req, res, next));
};