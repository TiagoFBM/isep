import { Router } from 'express';
 import ModuleRoute from './routes/ModuleRoute';
 import AuthenticationRoute from './routes/AuthenticationRoute';
 import UserRoute from './routes/UserRoute';
// import NodeRoute from './routes/NodeRoute';
// import VehicleTypeRoute from './routes/VehicleTypeRoute';
// import LineRoute from './routes/LineRoute';
// import PathRoute from "./routes/PathRoute";
// import ImportRoute from "./routes/ImportRoute";

export default () => {
	const app = Router();

	ModuleRoute(app);
	AuthenticationRoute(app);
	UserRoute(app);
	// VehicleTypeRoute(app);
	// NodeRoute(app);
	// LineRoute(app);
	// PathRoute(app);
	// ImportRoute(app);

	return app
}