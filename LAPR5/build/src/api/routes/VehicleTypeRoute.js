"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const celebrate_1 = require("celebrate");
const typedi_1 = require("typedi");
const config_1 = __importDefault(require("../../../config"));
const route = express_1.Router();
exports.default = (app) => {
    app.use('/vehicleType', route);
    const ctrl = typedi_1.Container.get(config_1.default.controllers.vehicleType.name);
    route.post('', celebrate_1.celebrate({
        body: celebrate_1.Joi.object({
            code: celebrate_1.Joi.string().required(),
            description: celebrate_1.Joi.string().required(),
            autonomy: celebrate_1.Joi.number().required(),
            fuelType: celebrate_1.Joi.string().required(),
            costPerKilometer: celebrate_1.Joi.number().required(),
            averageConsuption: celebrate_1.Joi.number().required(),
            averageSpeed: celebrate_1.Joi.number().required(),
        })
    }), (req, res, next) => ctrl.createVehicleType(req, res, next));
};
//# sourceMappingURL=VehicleTypeRoute.js.map