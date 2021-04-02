import { AggregateRoot } from "../../core/domain/AggregateRoot";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";
import { LineCode } from "./LineCode";
import { LineDescription } from "./LineDescription";
import { LineColor } from "./LineColor";
import { LinePath } from "./LinePath";
import { VehicleType } from "../vehicle/VehicleType";
import { DriverType } from "../driver/DriverType";
import ILineDTO from "../../dto/ILineDTO";
import ILinePathDTO from "../../dto/ILinePathDTO";
import IVehicleTypeDTO from "../../dto/IVehicleTypeDTO";
import { isUndefined } from "lodash";
import IDriverTypeDTO from "../../dto/IDriverTypeDTO";

interface LineProps {
  code: LineCode;
  description: LineDescription;
  linePaths: Array<LinePath>;
  allowedVehicles?: Array<string | VehicleType>;
  deniedVehicles?: Array<string | VehicleType>;
  allowedDrivers?: Array<string | DriverType>
  deniedDrivers?: Array<string | DriverType>
  lineColor: LineColor;
}

export class Line extends AggregateRoot<LineProps> {

  private constructor(props: LineProps) {
    super(props);
  }

  get lineCode(): LineCode {
    return this.props.code;
  }

  get lineDescription(): LineDescription {
    return this.props.description;
  }

  get linePaths(): Array<LinePath> {
    return this.props.linePaths;
  }

  get allowedVehicles(): Array<string | VehicleType> {
    return this.props.allowedVehicles;
  }

  get deniedVehicles(): Array<string | VehicleType> {
    return this.props.deniedVehicles;
  }

  get allowedDrivers(): Array<string | DriverType> {
    return this.props.allowedDrivers;
  }

  get deniedDrivers(): Array<string | DriverType> {
    return this.props.deniedDrivers;
  }

  get lineColor(): LineColor {
    return this.props.lineColor;
  }

  public addLinePath(linePath: LinePath): boolean {
    const newLength = this.props.linePaths.length + 1;
    if (this.props.linePaths.push(linePath) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addAllowedVehicleTypeID(vehicleTypeID: string): boolean {
    const newLength = this.props.allowedVehicles.length + 1;
    if (this.props.allowedVehicles.push(vehicleTypeID) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addAllowedVehicleTypeDTO(vehicleType: IVehicleTypeDTO): boolean {
    const newLength = this.props.allowedVehicles.length + 1;

    var newVehicle = VehicleType.create(vehicleType);

    if (newVehicle.isFailure) {
      return false;
    }

    if (this.props.allowedVehicles.push(newVehicle.getValue()) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addDeniedVehicleTypeID(vehicleType: string): boolean {
    const newLength = this.props.deniedVehicles.length + 1;
    if (this.props.deniedVehicles.push(vehicleType) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addDeniedVehicleTypeDTO(vehicleType: IVehicleTypeDTO): boolean {
    const newLength = this.props.deniedVehicles.length + 1;

    var newVehicle = VehicleType.create(vehicleType);

    if (newVehicle.isFailure) {
      return false;
    }

    if (this.props.deniedVehicles.push(newVehicle.getValue()) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addAllowedDriverTypeID(driverType: string): boolean {
    const newLength = this.props.allowedDrivers.length + 1;
    if (this.props.allowedDrivers.push(driverType) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addAllowedDriverTypeDTO(driverType: IDriverTypeDTO): boolean {
    const newLength = this.props.allowedDrivers.length + 1;

    var newDriver = DriverType.create(driverType);

    if (newDriver.isFailure) {
      return false;
    }

    if (this.props.allowedDrivers.push(newDriver.getValue()) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addDeniedDriverTypeID(driverType: string): boolean {
    const newLength = this.props.deniedDrivers.length + 1;
    if (this.props.deniedDrivers.push(driverType) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public addDeniedDriverTypeDTO(driverType: IDriverTypeDTO): boolean {
    const newLength = this.props.deniedDrivers.length + 1;

    var newDriver = DriverType.create(driverType);

    if (newDriver.isFailure) {
      return false;
    }

    if (this.props.deniedDrivers.push(newDriver.getValue()) == newLength) {
      return true;
    } else {
      return false;
    }
  }

  public static create(LineDTO: ILineDTO): Result<Line> {
    if (Guard.isNull(LineDTO, 'LineDTO').succeeded) {
      return Result.fail<Line>("LineDTO can\'t be null.");
    }

    if (Guard.isNull(LineDTO.code, 'LineDTO.code').succeeded || Guard.isEmptyString(LineDTO.code, 'LineDTO.code').succeeded) {
      return Result.fail<Line>("LineDTO Code is invalid.");
    }

    if (Guard.isNull(LineDTO.description, 'LineDTO.description').succeeded || Guard.isEmptyString(LineDTO.description, 'LineDTO.description').succeeded) {
      return Result.fail<Line>("LineDTO Description is invalid.");
    }

    if (Guard.isNull(LineDTO.linePaths, 'LineDTO.description').succeeded) {
      return Result.fail<Line>("LineDTO linePaths is invalid.");
    }

    if (Guard.isNull(LineDTO.allowedDrivers, 'LineDTO.allowedDrivers').succeeded) {
      return Result.fail<Line>("LineDTO allowedDrivers is invalid.");
    }

    if (Guard.isNull(LineDTO.deniedDrivers, 'LineDTO.deniedDrivers').succeeded) {
      return Result.fail<Line>("LineDTO deniedDrivers is invalid.");
    }

    if (Guard.isNull(LineDTO.allowedVehicles, 'LineDTO.allowedVehicles').succeeded) {
      return Result.fail<Line>("LineDTO allowedVehicles is invalid.");
    }

    if (Guard.isNull(LineDTO.deniedVehicles, 'LineDTO.deniedVehicles').succeeded) {
      return Result.fail<Line>("LineDTO deniedVehicles is invalid.");
    }

    if (Guard.isNull(LineDTO.lineColor, 'LineDTO.lineColor').succeeded) {
      return Result.fail<Line>("LineDTO lineColor is invalid.");
    }

    const lineCode = LineCode.create(LineDTO.code);
    if (lineCode.isFailure) {
      return Result.fail<Line>(lineCode.error);
    }

    const lineDescription = LineDescription.create(LineDTO.description);
    if (lineDescription.isFailure) {
      return Result.fail<Line>(lineDescription.error);
    }

    var newLinePaths: Array<LinePath> = [];
    for (let i in LineDTO.linePaths) {

      const element = LineDTO.linePaths[i];

      var linePathOrFailure = LinePath.create(element as ILinePathDTO);
      if (linePathOrFailure.isFailure) {
        return Result.fail<Line>("LinePath couldn't be created:" + linePathOrFailure.error);
      }

      newLinePaths.push(linePathOrFailure.getValue());
    }

    var newAllowedVehicles: Array<string | VehicleType> = [];
    if (!isUndefined(LineDTO.allowedVehicles)) {

      for (let i in LineDTO.allowedVehicles) {
        const element = LineDTO.allowedVehicles[i];

        var vehicleTypeOrFailure = VehicleType.create(element as IVehicleTypeDTO);
        if (vehicleTypeOrFailure.isFailure) {
          return Result.fail<Line>("Allowed Vehicle couldn't be created:" + vehicleTypeOrFailure.error);
        }

        newAllowedVehicles.push(vehicleTypeOrFailure.getValue());
      }
    }

    var newDeniedVehicles: Array<string | VehicleType> = [];
    if (!isUndefined(LineDTO.deniedVehicles)) {

      for (let i in LineDTO.deniedVehicles) {
        const element = LineDTO.deniedVehicles[i];

        var vehicleTypeOrFailure = VehicleType.create(element as IVehicleTypeDTO);
        if (vehicleTypeOrFailure.isFailure) {
          return Result.fail<Line>("Allowed Vehicle couldn't be created:" + vehicleTypeOrFailure.error);
        }

        newDeniedVehicles.push(vehicleTypeOrFailure.getValue());
      }
    }

    var newAllowedDrivers: Array<string | DriverType> = [];
    if (!isUndefined(LineDTO.allowedDrivers)) {

      for (let i in LineDTO.allowedDrivers) {
        const element = LineDTO.allowedDrivers[i];

        var driverTypeOrFailure = DriverType.create(element as IDriverTypeDTO);
        if (driverTypeOrFailure.isFailure) {
          return Result.fail<Line>("Allowed Vehicle couldn't be created:" + driverTypeOrFailure.error);
        }

        newAllowedDrivers.push(driverTypeOrFailure.getValue());
      }
    }

    var newDeniedDrivers: Array<string | DriverType> = [];
    if (!isUndefined(LineDTO.deniedDrivers)) {

      for (let i in LineDTO.deniedDrivers) {
        const element = LineDTO.deniedDrivers[i];

        var driverTypeOrFailure = DriverType.create(element as IDriverTypeDTO);
        if (driverTypeOrFailure.isFailure) {
          return Result.fail<Line>("Allowed Vehicle couldn't be created:" + driverTypeOrFailure.error);
        }

        newDeniedDrivers.push(driverTypeOrFailure.getValue());
      }
    }

    const newColorLine = LineColor.create(LineDTO.lineColor);
    if (newColorLine.isFailure) {
      return Result.fail<Line>(newColorLine.error);
    }

    return Result.ok<Line>(new Line({
      code: lineCode.getValue(),
      description: lineDescription.getValue(),
      linePaths: newLinePaths,
      allowedVehicles: newAllowedVehicles,
      deniedVehicles: newDeniedVehicles,
      allowedDrivers: newAllowedDrivers,
      deniedDrivers: newDeniedDrivers,
      lineColor: newColorLine.getValue()
    }
    ));

  }

}