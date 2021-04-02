import { Result } from "../../core/logic/Result";
import IDriverTypeDTO from "../../dto/IDriverTypeDTO";

export default interface IDriverTypeService {
  createDriverType(DriverTypeDTO: IDriverTypeDTO): Promise<Result<IDriverTypeDTO>>;
  findAllDriverTypes(): Promise<Array<IDriverTypeDTO>>;
  findDriverTypesPaginated({ skip, limit }): Promise<Array<IDriverTypeDTO>>;
  getNumberOfDriverTypes(): Promise<Number>;
  getDriverTypeByCode(DriverTypeCode: string): Promise<IDriverTypeDTO>;
}