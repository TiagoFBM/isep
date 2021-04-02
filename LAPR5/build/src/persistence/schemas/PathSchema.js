"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = __importDefault(require("mongoose"));
const Path = new mongoose_1.default.Schema({
    code: {
        type: String,
        required: [true, "Please enter the Path Code."],
        unique: true
    },
    isEmpty: {
        type: Boolean,
        required: [true, "Please enther whether the Path is empty or not."]
    },
    segments: [{
            firstNodeID: { type: mongoose_1.default.Schema.Types.ObjectId, ref: "Node", required: true },
            secondNodeID: { type: mongoose_1.default.Schema.Types.ObjectId, ref: "Node", required: true },
            travelTimeBetweenNodes: { type: Number, required: true },
            distanceBetweenNodes: { type: Number, required: true },
        }]
}, { timestamps: true });
exports.default = mongoose_1.default.model("Path", Path);
//# sourceMappingURL=PathSchema.js.map