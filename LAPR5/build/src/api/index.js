"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = require("express");
const DriverTypeRoute_1 = __importDefault(require("./routes/DriverTypeRoute"));
const NodeRoute_1 = __importDefault(require("./routes/NodeRoute"));
const VehicleTypeRoute_1 = __importDefault(require("./routes/VehicleTypeRoute"));
const LineRoute_1 = __importDefault(require("./routes/LineRoute"));
const PathRoute_1 = __importDefault(require("./routes/PathRoute"));
const ImportRoute_1 = __importDefault(require("./routes/ImportRoute"));
exports.default = () => {
    const app = express_1.Router();
    DriverTypeRoute_1.default(app);
    VehicleTypeRoute_1.default(app);
    NodeRoute_1.default(app);
    LineRoute_1.default(app);
    PathRoute_1.default(app);
    ImportRoute_1.default(app);
    return app;
};
//# sourceMappingURL=index.js.map