import { Result } from "../../core/logic/Result";

export default interface IImportService  {
  toJSON(xml: string): Promise<JSON>;
}