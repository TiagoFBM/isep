"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = __importDefault(require("mongoose"));
const DriverType = new mongoose_1.default.Schema({
    code: {
        type: String,
        required: [true, 'Please enter Driver Type Code.'],
        unique: true
    },
    description: {
        type: String,
        required: [true, 'Please enter Driver Type Description.']
    }
}, { timestamps: true });
exports.default = mongoose_1.default.model('DriverType', DriverType);
//# sourceMappingURL=DriverTypeSchema.js.map