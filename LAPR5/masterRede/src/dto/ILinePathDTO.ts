import { PathCode } from "../domain/path/PathCode";
import IPathDTO from "./IPathDTO";

export default interface ILinePathDTO {
    path: string | IPathDTO;
    orientation: string;
}