"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const typedi_1 = require("typedi");
const config_1 = __importDefault(require("../../../config"));
const multer_1 = __importDefault(require("multer"));
var upload = multer_1.default({ dest: 'uploads/' });
const route = express_1.Router();
exports.default = (app) => {
    app.use('/import', route);
    const ctrl = typedi_1.Container.get(config_1.default.controllers.import.name);
    route.post('', upload.single('file'), (req, res, next) => ctrl.import(req, res, next));
};
//# sourceMappingURL=ImportRoute.js.map