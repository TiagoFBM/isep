import { Result } from "../../core/logic/Result";
import IModuleDTO from "../../dto/IModuleDTO";

export default interface IModuleService {
   createModule(ModuleDTO: IModuleDTO): Promise<Result<IModuleDTO>>;
//   findDriverTypesPaginated({ skip, limit }): Promise<Array<IDriverTypeDTO>>;
//   getNumberOfDriverTypes(): Promise<Number>;
//   getDriverTypeByCode(DriverTypeCode: string): Promise<IDriverTypeDTO>;
  findAllModules(): Promise<Array<IModuleDTO>>;
}