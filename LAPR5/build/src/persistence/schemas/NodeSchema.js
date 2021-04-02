"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = __importDefault(require("mongoose"));
const Node = new mongoose_1.default.Schema({
    code: {
        type: String,
        required: [true, 'Please enter Node Code.'],
        unique: true
    },
    latitude: {
        type: String,
        required: [true, 'Please enter Node Latitude.']
    },
    longitude: {
        type: String,
        required: [true, 'Please enter Node Longitude.']
    },
    name: {
        type: String,
        required: [true, 'Please enter Node Name.']
    },
    shortName: {
        type: String,
        required: [true, 'Please enter Node Short Name.']
    },
    isDepot: {
        type: Boolean,
        required: [true, 'Please enter true if the Node is a Collection Station.']
    },
    isReliefPoint: {
        type: Boolean,
        required: [true, 'Please enter true if the Node is a Relief Point.']
    },
    crewTravelTimes: [{
            node: {
                type: String,
                required: [true, 'Please enter Crew Travel Time Node.']
            },
            duration: {
                type: Number,
                required: [true, 'Please enter Crew Travel Time Duration.']
            }
        }],
}, { timestamps: true });
exports.default = mongoose_1.default.model('Node', Node);
//# sourceMappingURL=NodeSchema.js.map