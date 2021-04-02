"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const config_1 = __importDefault(require("../../../config"));
const typedi_1 = require("typedi");
const router = express_1.Router();
exports.default = (app) => {
    app.use("/path", router);
    const ctrl = typedi_1.Container.get(config_1.default.controllers.path.name);
    router.post("", (req, res, next) => ctrl.createPath(req, res, next));
    router.get("", (req, res, next) => ctrl.findAllPaths(req, res, next));
};
//# sourceMappingURL=PathRoute.js.map