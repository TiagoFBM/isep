"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = __importDefault(require("mongoose"));
const Line = new mongoose_1.default.Schema({
    code: {
        type: String,
        required: [true, 'Please enter Line Code.'],
        unique: true
    },
    description: {
        type: String,
        required: [true, 'Please enter Line Description.']
    },
    linePaths: [{
            path: {
                type: mongoose_1.default.Schema.Types.ObjectId,
                ref: 'Path',
                required: [true, 'Please enter LinePath Path.']
            },
            orientation: {
                type: String,
                required: [true, 'Please enter Line Orientation.']
            }
        }],
    allowedVehicles: [{
            type: mongoose_1.default.Schema.Types.ObjectId,
            ref: 'VehicleType'
        }],
    deniedVehicles: [{
            type: {
                type: mongoose_1.default.Schema.Types.ObjectId,
                ref: 'VehicleType'
            }
        }],
    allowedDrivers: [{
            type: {
                type: mongoose_1.default.Schema.Types.ObjectId,
                ref: 'DriverType'
            }
        }],
    deniedDrivers: [{
            type: {
                type: mongoose_1.default.Schema.Types.ObjectId,
                ref: 'VehicleType'
            }
        }],
    lineColor: {
        type: String,
        required: [true, 'Please enter Line Color.']
    }
}, { timestamps: true });
exports.default = mongoose_1.default.model('Line', Line);
//# sourceMappingURL=LineSchema.js.map