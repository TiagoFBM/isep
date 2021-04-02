import { Router } from 'express';
import { VehicleTypeFuelType } from '../domain/vehicle/VehicleTypeFuelType';
import DriverTypeRoute from './routes/DriverTypeRoute';
import NodeRoute from './routes/NodeRoute';
import VehicleTypeRoute from './routes/VehicleTypeRoute';
import LineRoute from './routes/LineRoute';
import PathRoute from "./routes/PathRoute";
import ImportRoute from "./routes/ImportRoute";

export default () => {
	const app = Router();

	DriverTypeRoute(app);
	VehicleTypeRoute(app);
	NodeRoute(app);
	LineRoute(app);
	PathRoute(app);
	ImportRoute(app);

	return app
}