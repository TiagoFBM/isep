import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { Module } from "../domain/module/Module";
import IModuleDTO from "../dto/IModuleDTO";

export default class ModuleMapper extends Mapper<Module> {

  public static toDTO( module: Module): IModuleDTO {    
    return {
      name: module.moduleName.value,
      urlPath: module.moduleUrlPath.value,
      viewPath: module.moduleViewPath.value,
      isExact: module.moduleIsExact,
      isNavItem: module.moduleisNavItem,
      needsAdmin: module.needsAdmin
    } as IModuleDTO;
  }

  public static toDomain (module: any | Model<IModuleDTO & Document> ): Module {
    const roleOrError = Module.create(
      module
    );

    if (roleOrError.isFailure){
      console.log("Error Converting toDomain: %s",roleOrError.error);
      return null;
    }

    return roleOrError.getValue();
  }

  public static toPersistence (module: Module): any {
    const driver = {
      id: module.id,
      name: module.moduleName.value,
      urlPath: module.moduleUrlPath.value,
      viewPath: module.moduleViewPath.value,
      isExact: module.moduleIsExact,
      isNavItem: module.moduleisNavItem,
      needsAdmin: module.needsAdmin
    }
    return driver;
  }
}