import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { DriverType } from "../domain/driver/DriverType";
import IDriverTypeDTO from "../dto/IDriverTypeDTO";

export default class DriverTypeMapper extends Mapper<DriverType> {

  public static toDTO( driverType: DriverType): IDriverTypeDTO {
    return {
      code: driverType.driverTypeCode.value,
      description: driverType.driverTypeDescription.value,
    } as IDriverTypeDTO;
  }

  public static toDomain (driverType: any | Model<IDriverTypeDTO & Document> ): DriverType {
    const roleOrError = DriverType.create(
      driverType
    );

    if (roleOrError.isFailure){
      console.log("Error Converting toDomain: %s",roleOrError.error);
      return null;
    }

    return roleOrError.getValue();
  }

  public static toPersistence (driverType: DriverType): any {
    const driver = {
      code: driverType.driverTypeCode.value,
      description: driverType.driverTypeDescription.value
    }
    return driver;
  }
}