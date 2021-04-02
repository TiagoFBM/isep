"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const typedi_1 = require("typedi");
const fs_1 = require("fs");
const xml2json_1 = __importDefault(require("xml2json"));
let ImportService = class ImportService {
    async toJSON(req) {
        var data = await fs_1.promises.readFile('uploads/' + req.filename, "utf8");
        var json = JSON.parse(xml2json_1.default.toJson(data));
        fs_1.promises.unlink('uploads/' + req.filename);
        return json;
    }
};
ImportService = __decorate([
    typedi_1.Service()
], ImportService);
exports.default = ImportService;
//# sourceMappingURL=ImportService.js.map