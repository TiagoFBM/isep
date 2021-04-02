import { Result } from "../../core/logic/Result";
import IPathDTO from "../../dto/IPathDTO";
import { PathCode } from "../../domain/path/PathCode";

export default interface IPathService  {
  createPath(DriverTypeDTO: IPathDTO): Promise<Result<IPathDTO>>;
  findAllPaths(): Promise<Array<IPathDTO>>;
  findPathsPaginated({skip, limit}): Promise<Array<IPathDTO>>;
  getNumberOfPaths(): Promise<Number>;
  getPathByCode(PathCode: string): Promise<Result<IPathDTO>>;
}