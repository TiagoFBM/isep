"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const dotenv_1 = __importDefault(require("dotenv"));
// Set the NODE_ENV to 'development' by default
process.env.NODE_ENV = process.env.NODE_ENV || 'development';
const envFound = dotenv_1.default.config();
if (!envFound) {
    // This error should crash whole process
    throw new Error("Couldn't find .env file.");
}
exports.default = {
    /**
     * Your favorite port
     */
    port: parseInt(process.env.PORT, 10),
    /**
     * That long string from mlab
     */
    databaseURL: process.env.MONGODB_URI,
    /**
     * Your secret sauce
     */
    jwtSecret: process.env.JWT_SECRET,
    /**
     * Used by winston logger
     */
    logs: {
        level: process.env.LOG_LEVEL || 'silly',
    },
    /**
     * API configs
     */
    api: {
        prefix: '/api',
    },
    schemas: {
        driverType: {
            name: "DriverTypeSchema",
            schema: "../persistence/schemas/DriverTypeSchema"
        },
        vehicleType: {
            name: "VehicleTypeSchema",
            schema: "../persistence/schemas/VehicleTypeSchema"
        },
        node: {
            name: "NodeSchema",
            schema: "../persistence/schemas/NodeSchema"
        },
        line: {
            name: "LineSchema",
            schema: "../persistence/schemas/LineSchema"
        },
        path: {
            name: "PathSchema",
            schema: "../persistence/schemas/PathSchema"
        }
    },
    controllers: {
        driverType: {
            name: "DriverTypeController",
            path: "../controllers/DriverTypeController"
        },
        vehicleType: {
            name: "VehicleTypeController",
            path: "../controllers/VehicleTypeController"
        },
        line: {
            name: "LineController",
            path: "../controllers/LineController"
        },
        import: {
            name: "ImportController",
            path: "../controllers/ImportController"
        },
        node: {
            name: "NodeController",
            path: "../controllers/NodeController"
        },
        line: {
            name: "LineController",
            path: "../controllers/LineController"
        },
        path: {
            name: "PathController",
            path: "../controllers/PathController"
        }
    },
    repos: {
        driverType: {
            name: "DriverTypeRepo",
            path: "../repos/DriverTypeRepo"
        },
        vehicleType: {
            name: "VehicleTypeRepo",
            path: "../repos/VehicleTypeRepo"
        },
        node: {
            name: "NodeRepo",
            path: "../repos/NodeRepo"
        },
        line: {
            name: "LineRepo",
            path: "../repos/LineRepo"
        },
        path: {
            name: "PathRepo",
            path: "../repos/PathRepo"
        }
    },
    services: {
        driverType: {
            name: "DriverTypeService",
            path: "../services/DriverTypeService"
        },
        vehicleType: {
            name: "VehicleTypeService",
            path: "../services/VehicleTypeService"
        },
        node: {
            name: "NodeService",
            path: "../services/NodeService"
        },
        line: {
            name: "LineService",
            path: "../services/LineService"
        },
        import: {
            name: "ImportService",
            path: "../services/ImportService"
        },
        node: {
            name: "NodeService",
            path: "../services/NodeService"
        },
        path: {
            name: "PathService",
            path: "../services/PathService"
        }
    },
};
//# sourceMappingURL=config.js.map