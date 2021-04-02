import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import IModuleController from '../../controllers/IControllers/IModuleController';

import config from "../../../config";

const route = Router();

export default (app: Router) => {
    app.use('/module', route);
  
    const ctrl = Container.get(config.controllers.module.name) as IModuleController;
  
    route.post('',
      celebrate({
        body: Joi.object({
          name: Joi.string().required(),
          urlPath: Joi.string().required(),
          viewPath: Joi.string().required(),
          isExact: Joi.boolean().required(),
          isNavItem: Joi.boolean().required(),
          needsAdmin: Joi.boolean().required()
        })
      }),
      (req, res, next) => ctrl.createModule(req, res, next));
  
    route.get("", (req, res, next) => ctrl.findAllModules(req, res, next));
  };