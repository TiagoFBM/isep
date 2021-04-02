import IPathSegmentDTO from "./IPathSegmentDTO";

export default interface IPathDTO {
    code: string;
    isEmpty: boolean;
    segments: Array<IPathSegmentDTO>
}