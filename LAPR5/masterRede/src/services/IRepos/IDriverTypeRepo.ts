import { Document } from "mongoose";
import { Repo } from "../../core/infra/Repo";
import { IDriverTypePersistence } from "../../dataschema/IDriverTypePersistence";
import { DriverType } from "../../domain/driver/DriverType";
import { DriverTypeCode } from "../../domain/driver/DriverTypeCode";

export default interface IDriverTypeRepo extends Repo<DriverType> {
  save(DriverType: DriverType): Promise<DriverType>;
  findByCode (DriverTypeCode: DriverTypeCode | string): Promise<DriverType>;
  findPaginated ({skip, limit}): Promise<Array<DriverType>>;
  findAll (): Promise<Array<DriverType>>;
  findByCodeDB(Code: DriverTypeCode | string): Promise<Document & IDriverTypePersistence>;
  getNumberOfDocuments(): Promise<Number>;
}