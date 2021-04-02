import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import IUserController from '../../controllers/IControllers/IUserController';

import config from "../../../config";
import IAuthenticationController from '../../controllers/IControllers/IAuthenticationController';

const route = Router();

export default (app: Router) => {
    app.use('/authentication', route);
  
    const userCtrl = Container.get(config.controllers.user.name) as IUserController;
    const authCtrl = Container.get(config.controllers.authentication.name) as IAuthenticationController;
  
    route.post('/register',
      celebrate({
        body: Joi.object({
          email: Joi.string().required(),
          username: Joi.string().required(),
          password: Joi.string().required(),
          name: Joi.string().required(),
          birthDate: Joi.string().required(),
          acceptedTerms: Joi.boolean().required(),
          isDataAdmin: Joi.boolean().required()
        })
      }),
    (req, res, next) => userCtrl.createUser(req, res, next));
  
    route.post("/login",
    celebrate({
      body: Joi.object({
        email: Joi.string().required(),
        password: Joi.string().required(),
      })
    }), (req, res, next) => authCtrl.login(req, res, next));

    route.get("/user", (req, res, next) => authCtrl.getUserByToken(req,res,next));
    
  };