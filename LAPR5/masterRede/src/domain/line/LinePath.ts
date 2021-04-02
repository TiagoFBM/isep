import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";
import { LinePathOrientation } from "./LinePathOrientation";

import ILinePathDTO from "../../dto/ILinePathDTO";
import { Entity } from "../../core/domain/Entity";
import { Path } from "../path/Path";
import { PathSegment } from "../path/PathSegment";
import PathMapper from "../../mappers/PathMap";
import { PathCode } from "../path/PathCode";
import IPathDTO from "../../dto/IPathDTO";
import { isUndefined } from "lodash";
import INodeDTO from "../../dto/INodeDTO";


interface LinePathProps {
  path: Path | string;
  orientation: LinePathOrientation;
}

export class LinePath extends Entity<LinePathProps> {

  private constructor(props: LinePathProps) {
    super(props);
  }

  get path(): Path | string {
    return this.props.path;
  }

  get linePathOrientation(): LinePathOrientation {
    return this.props.orientation;
  }

  public static create(LineDTO: ILinePathDTO): Result<LinePath> {

    if (Guard.isNull(LineDTO, 'LineDTO').succeeded) {
      return Result.fail<LinePath>("LineDTO can\'t be null.");
    }

    if (Guard.isNull(LineDTO.path, 'LineDTO.path').succeeded) {
      return Result.fail<LinePath>("LineDTO Path is invalid.");
    }

    if (Guard.isNull(LineDTO.orientation, 'LineDTO.linePathOrientation').succeeded) {
      return Result.fail<LinePath>("LineDTO LinePathOrientation is invalid.");
    }

    const newLinePathOrientation = LinePathOrientation.create(LineDTO.orientation);
    if (newLinePathOrientation.isFailure) {
      return Result.fail<LinePath>(newLinePathOrientation.error);
    }

    var path;
    if (!isUndefined((LineDTO.path as IPathDTO).code)) {

      path = Path.create(LineDTO.path as IPathDTO).getValue();

      let pathSegments = (LineDTO.path as IPathDTO).segments;
      pathSegments.forEach(elem => {
        var segm = PathSegment.create(elem).getValue();
        segm.addFirstNodeCode(elem.firstNodeID as INodeDTO);
        segm.addSecondNodeCode(elem.secondNodeID as INodeDTO);
        path.addSegment(segm);
      });
      
    } else {
      path = LineDTO.path
    }

    return Result.ok<LinePath>(new LinePath({
      path: path,
      orientation: newLinePathOrientation.getValue()
    }
    ));
  }
}
