"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = __importDefault(require("mongoose"));
const VehicleType = new mongoose_1.default.Schema({
    code: {
        type: String,
        required: [true, 'Please enter Vehicle Type Code.'],
        unique: true
    },
    description: {
        type: String,
        required: [true, 'Please enter Vehicle Type Description.']
    },
    autonomy: {
        type: Number,
        required: [true, 'Please enter Autonomy']
    },
    fuelType: {
        type: String,
        required: [true, 'Please enter the Fuel Type']
    },
    costPerKilometer: {
        type: Number,
        required: [true, 'Please enter the cost per kilometer']
    },
    averageConsuption: {
        type: Number,
        required: [true, 'Please enter the average consuption']
    },
    averageSpeed: {
        type: Number,
        required: [true, 'Please enter the average speed']
    }
}, { timestamps: true });
exports.default = mongoose_1.default.model('VehicleType', VehicleType);
//# sourceMappingURL=VehicleTypeSchema.js.map