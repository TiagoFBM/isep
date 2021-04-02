import { Document } from "mongoose";
import { Repo } from "../../core/infra/Repo";
import { IVehicleTypePersistence } from "../../dataschema/IVehicleTypePersistence";
import { VehicleType } from "../../domain/vehicle/VehicleType";
import { VehicleTypeCode } from "../../domain/vehicle/VehicleTypeCode";

export default interface IVehicleTypeRepo extends Repo<VehicleType> {
    save(VehicleType: VehicleType): Promise<VehicleType>;
    findAll({ skip, limit }): Promise<Array<VehicleType>>;
    findByCode(VehicleTypeCode: VehicleTypeCode | string): Promise<VehicleType>;
    findByCodeDB(Code: VehicleTypeCode | string): Promise<Document & IVehicleTypePersistence>;
    getNumberOfDocuments(): Promise<Number>;
}