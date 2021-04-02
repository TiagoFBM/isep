import { AggregateRoot } from "../../core/domain/AggregateRoot";
import { UniqueEntityID } from "../../core/domain/UniqueEntityID";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";
import { DriverTypeCode } from "./DriverTypeCode";
import { DriverTypeDescription } from "./DriverTypeDescription";
import IDriverTypeDTO from "../../dto/IDriverTypeDTO";

interface DriverTypeProps {
  code: DriverTypeCode;
  description: DriverTypeDescription;
}

export class DriverType extends AggregateRoot<DriverTypeProps> {
  
  private constructor (props: DriverTypeProps, id?: UniqueEntityID) {
    super(props, id);
  }
  
  get driverId (): DriverTypeCode {
    return DriverTypeCode.create(this.id.toString()).getValue();
  }

  get driverTypeDescription (): DriverTypeDescription {
    return this.props.description;
  }

  get driverTypeCode (): DriverTypeCode {
    return this.props.code;
  }

  public static create (driverTypeDTO: IDriverTypeDTO): Result<DriverType> {
    if (Guard.isNull(driverTypeDTO,'driverTypeDTO').succeeded){
      return Result.fail<DriverType>("DriverTypeDTO can\'t be null.");
    }

    if (Guard.isNull(driverTypeDTO.code,'driverTypeDTO.code').succeeded || Guard.isEmptyString(driverTypeDTO.code,'driverTypeDTO.code').succeeded){
      return Result.fail<DriverType>("DriverTypeDTO Code is invalid.");
    }

    if (Guard.isNull(driverTypeDTO.description,'driverTypeDTO.description').succeeded || Guard.isEmptyString(driverTypeDTO.description,'driverTypeDTO.description').succeeded){
      return Result.fail<DriverType>("DriverTypeDTO Description is invalid.");
    }

    const driverTypeCode = DriverTypeCode.create(driverTypeDTO.code);
    if(driverTypeCode.isFailure){
      return Result.fail<DriverType>(driverTypeCode.error);
    }

    const driverTypeDescription = DriverTypeDescription.create(driverTypeDTO.description);
    if(driverTypeDescription.isFailure){
      return Result.fail<DriverType>(driverTypeDescription.error);
    }

    return Result.ok<DriverType>(new DriverType({ code: driverTypeCode.getValue(), description: driverTypeDescription.getValue()}));

  }
}
