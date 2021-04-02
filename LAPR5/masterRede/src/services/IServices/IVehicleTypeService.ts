import { Result } from "../../core/logic/Result";
import IVehicleTypeDTO from "../../dto/IVehicleTypeDTO";

export default interface IVehicleTypeService {
    createVehicleType(VehicleTypeDTO: IVehicleTypeDTO): Promise<Result<IVehicleTypeDTO>>;
    findAllVehicleTypes({ skip, limit }): Promise<Array<IVehicleTypeDTO>>;
    getNumberOfVehicleTypes(): Promise<Number>;
    getVehicleTypeByCode(VehicleTypeCode: string): Promise<IVehicleTypeDTO>;
}