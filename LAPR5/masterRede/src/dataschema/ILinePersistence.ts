import { IDriverTypePersistence } from './IDriverTypePersistence';
import { IVehicleTypePersistence } from './IVehicleTypePersistence';
import { ILinePathPersistence } from './ILinePathPersistence';

export interface ILinePersistence {
	_id: string;
    code: string;
    description: string;
    linePaths: Array <ILinePathPersistence>;
    allowedVehicles?: Array <IVehicleTypePersistence>;
    deniedVehicles?: Array <IVehicleTypePersistence>;
    allowedDrivers?: Array <IDriverTypePersistence>;
    deniedDrivers?: Array <IDriverTypePersistence>;
    lineColor: string;
}