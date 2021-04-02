import { Result } from "../../core/logic/Result";
import { LineCode } from "../../domain/line/LineCode";
import ILineDTO from "../../dto/ILineDTO";
import ILinePathDTO from "../../dto/ILinePathDTO";

export default interface ILineService  {
  createLine(LineDTO: ILineDTO): Promise<Result<ILineDTO>>;
  findAllLines({ skip, limit }): Promise<Array<ILineDTO>>;
  findAllLinePaths(LineCode: LineCode | string): Promise<Array<ILinePathDTO>>;
  findLineByCode(LineCode: LineCode | string): Promise<ILineDTO>;
  getNumberOfVehicleTypes(): Promise<Number>;
  findLineOfPath(string):Promise<String>;
}