import { Repo } from "../../core/infra/Repo";
import { Line } from "../../domain/line/Line";
import { LineCode } from "../../domain/line/LineCode";
import { Path } from "../../domain/path/Path";

export default interface ILineRepo extends Repo<Line> {
  save(Line: Line): Promise<Line>;
  findByCode(LineCode: LineCode | string): Promise<Line>;
  findAll({ skip, limit }): Promise<Array<Line>>;
  getNumberOfDocuments(): Promise<Number>;
  findLineByPath(path: string):Promise<string>;
}