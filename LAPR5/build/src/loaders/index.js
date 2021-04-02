"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("./express"));
const dependencyInjector_1 = __importDefault(require("./dependencyInjector"));
const mongoose_1 = __importDefault(require("./mongoose"));
const logger_1 = __importDefault(require("./logger"));
const config_1 = __importDefault(require("../../config"));
exports.default = async ({ expressApp }) => {
    const mongoConnection = await mongoose_1.default();
    logger_1.default.info('✌️ DB loaded and connected!');
    const driverTypeSchema = {
        name: config_1.default.schemas.driverType.name,
        schema: config_1.default.schemas.driverType.schema
    };
    const driverTypeController = {
        name: config_1.default.controllers.driverType.name,
        path: config_1.default.controllers.driverType.path
    };
    const driverTypeRepo = {
        name: config_1.default.repos.driverType.name,
        path: config_1.default.repos.driverType.path
    };
    const driverTypeService = {
        name: config_1.default.services.driverType.name,
        path: config_1.default.services.driverType.path
    };
    const vehicleTypeSchema = {
        name: config_1.default.schemas.vehicleType.name,
        schema: config_1.default.schemas.vehicleType.schema
    };
    const vehicleTypeController = {
        name: config_1.default.controllers.vehicleType.name,
        path: config_1.default.controllers.vehicleType.path
    };
    const vehicleTypeRepo = {
        name: config_1.default.repos.vehicleType.name,
        path: config_1.default.repos.vehicleType.path
    };
    const vehicleTypeService = {
        name: config_1.default.services.vehicleType.name,
        path: config_1.default.services.vehicleType.path
    };
    const lineSchema = {
        name: config_1.default.schemas.line.name,
        schema: config_1.default.schemas.line.schema
    };
    const lineController = {
        name: config_1.default.controllers.line.name,
        path: config_1.default.controllers.line.path
    };
    const lineRepo = {
        name: config_1.default.repos.line.name,
        path: config_1.default.repos.line.path
    };
    const lineService = {
        name: config_1.default.services.line.name,
        path: config_1.default.services.line.path
    };
    const nodeSchema = {
        name: config_1.default.schemas.node.name,
        schema: config_1.default.schemas.node.schema
    };
    const nodeController = {
        name: config_1.default.controllers.node.name,
        path: config_1.default.controllers.node.path
    };
    const nodeRepo = {
        name: config_1.default.repos.node.name,
        path: config_1.default.repos.node.path
    };
    const nodeService = {
        name: config_1.default.services.node.name,
        path: config_1.default.services.node.path
    };
    const pathSchema = {
        name: config_1.default.schemas.path.name,
        schema: config_1.default.schemas.path.schema
    };
    const pathController = {
        name: config_1.default.controllers.path.name,
        path: config_1.default.controllers.path.path
    };
    const pathRepo = {
        name: config_1.default.repos.path.name,
        path: config_1.default.repos.path.path
    };
    const pathService = {
        name: config_1.default.services.path.name,
        path: config_1.default.services.path.path
    };
    const importService = {
        name: config_1.default.services.import.name,
        path: config_1.default.services.import.path
    };
    const importController = {
        name: config_1.default.controllers.import.name,
        path: config_1.default.controllers.import.path
    };
    await dependencyInjector_1.default({
        mongoConnection,
        schemas: [
            driverTypeSchema,
            vehicleTypeSchema,
            nodeSchema,
            lineSchema,
            pathSchema
        ],
        controllers: [
            driverTypeController,
            vehicleTypeController,
            nodeController,
            lineController,
            pathController,
            importController
        ],
        repos: [
            driverTypeRepo,
            vehicleTypeRepo,
            nodeRepo,
            lineRepo,
            pathRepo
        ],
        services: [
            driverTypeService,
            vehicleTypeService,
            nodeService,
            lineService,
            pathService,
            importService
        ]
    });
    logger_1.default.info('✌️ Schemas, Controllers, Repositories, Services, etc. loaded');
    await express_1.default({ app: expressApp });
    logger_1.default.info('✌️ Express loaded');
};
//# sourceMappingURL=index.js.map