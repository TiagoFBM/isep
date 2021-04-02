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
    app.use('/line', route);
    const ctrl = typedi_1.Container.get(config_1.default.controllers.line.name);
    route.post('', celebrate_1.celebrate({
        body: celebrate_1.Joi.object({
            code: celebrate_1.Joi.string().required(),
            description: celebrate_1.Joi.string().required(),
            linePaths: celebrate_1.Joi.array().items().required(),
            allowedVehicles: celebrate_1.Joi.array().items(celebrate_1.Joi.string()),
            deniedVehicles: celebrate_1.Joi.array().items(celebrate_1.Joi.string()),
            allowedDrivers: celebrate_1.Joi.array().items(celebrate_1.Joi.string()),
            deniedDrivers: celebrate_1.Joi.array().items(celebrate_1.Joi.string()),
            lineColor: celebrate_1.Joi.string().required()
        })
    }), (req, res, next) => ctrl.createLine(req, res, next));
    route.get("", (req, res, next) => ctrl.findAllLines(req, res, next));
    route.get("/:code", (req, res, next) => ctrl.findAllLinePaths(req, res, next));
};
//# sourceMappingURL=LineRoute.js.map