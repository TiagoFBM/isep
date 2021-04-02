import { Router } from 'express';
import { celebrate, Joi } from 'celebrate';

import { Container } from 'typedi';
import IVehicleTypeController from '../../controllers/IControllers/IVehicleTypeController';

import config from "../../../config";

const route = Router();

export default (app: Router) => {
    app.use('/vehicleType', route);

    const ctrl = Container.get(config.controllers.vehicleType.name) as IVehicleTypeController;

    route.post('',
        celebrate({
            body: Joi.object({
                code: Joi.string().required(),
                description: Joi.string().required(),
                autonomy: Joi.number().required(),
                fuelType: Joi.string().required(),
                costPerKilometer: Joi.number().required(),
                averageConsuption: Joi.number().required(),
                averageSpeed: Joi.number().required(),
            })
        }),
        (req, res, next) => ctrl.createVehicleType(req, res, next));

    route.get("", (req, res, next) => ctrl.findAllVehicleTypes(req, res, next));

    route.get("/count", (req, res, next) => ctrl.getNumberOfVehicleTypes(req, res, next));

    route.get("/:code", (req, res, next) => ctrl.getVehicleTypeByCode(req, res, next));
};