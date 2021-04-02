import ILinePathDTO from "./ILinePathDTO";
import IVechileTypeDTO from "./IVehicleTypeDTO";
import IDriverTypeDTO from "./IDriverTypeDTO";

export default interface ILineDTO {
    code: string;
    description: string;
    linePaths: Array<ILinePathDTO>;
    allowedVehicles?: Array<string | IVechileTypeDTO>;
    deniedVehicles?: Array<string | IVechileTypeDTO>
    allowedDrivers?: Array<string | IDriverTypeDTO>;
    deniedDrivers?: Array<string | IDriverTypeDTO>;
    lineColor: string;
}