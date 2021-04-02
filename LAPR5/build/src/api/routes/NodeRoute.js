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
    app.use('/node', route);
    const ctrl = typedi_1.Container.get(config_1.default.controllers.node.name);
    route.post('', celebrate_1.celebrate({
        body: celebrate_1.Joi.object({
            code: celebrate_1.Joi.string().required(),
            name: celebrate_1.Joi.string().required(),
            latitude: celebrate_1.Joi.string().required(),
            longitude: celebrate_1.Joi.string().required(),
            shortName: celebrate_1.Joi.string().required(),
            isDepot: celebrate_1.Joi.boolean().required(),
            isReliefPoint: celebrate_1.Joi.boolean().required(),
            crewTravelTime: celebrate_1.Joi.array().items(),
        })
    }), (req, res, next) => ctrl.createNode(req, res, next));
    route.get("", (req, res, next) => ctrl.findAllNodes(req, res, next));
};
//# sourceMappingURL=NodeRoute.js.map