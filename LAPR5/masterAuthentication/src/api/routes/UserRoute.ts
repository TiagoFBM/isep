import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import IUserController from '../../controllers/IControllers/IUserController';

import config from "../../../config";

const route = Router();

export default (app: Router) => {
    app.use('/user', route);
  
    const userCtrl = Container.get(config.controllers.user.name) as IUserController;
  
    route.delete("/:email",
      (req, res, next) => userCtrl.delete(req, res, next));

    route.get("/", (req, res, next) => userCtrl.findAllUsers(req, res, next));

    route.put("/", (req, res, next) => userCtrl.update(req, res, next));

    route.get("/count", (req, res, next) => userCtrl.getNumberOfUsers(req, res, next));
  };