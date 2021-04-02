import { AggregateRoot } from "../../core/domain/AggregateRoot";
import { UniqueEntityID } from "../../core/domain/UniqueEntityID";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";
import { ModuleName } from "./ModuleName";
import { ModuleUrlPath } from "./ModuleUrlPath";
import { ModuleViewPath } from "./ModuleViewPath";
import IModuleDTO from "../../dto/IModuleDTO";

interface ModuleProps {
  name: ModuleName;
  urlPath: ModuleUrlPath;
  viewPath: ModuleViewPath;
  isExact: boolean;
  isNavItem: boolean;
  needsAdmin: boolean;
}

export class Module extends AggregateRoot<ModuleProps> {
  
  private constructor (props: ModuleProps, id?: UniqueEntityID) {
    super(props, id);
  }
  
  get moduleId (): string {
    return this.id.toString();
  }

  get moduleName (): ModuleName {
    return this.props.name;
  }

  get moduleUrlPath (): ModuleUrlPath {
    return this.props.urlPath;
  }

  get moduleViewPath (): ModuleViewPath {
    return this.props.viewPath;
  }
  
  get moduleIsExact (): boolean {
    return this.props.isExact;
  }

  get moduleisNavItem (): boolean {
    return this.props.isNavItem;
  }

  get needsAdmin (): boolean {
    return this.props.needsAdmin;
  }
  

  public static create (moduleDTO: IModuleDTO): Result<Module> {
    if (Guard.isNull(moduleDTO,'moduleDTO').succeeded){
      return Result.fail<Module>("ModuleDTO can\'t be null.");
    }

    if (Guard.isNull(moduleDTO.name,'moduleDTO.name').succeeded || Guard.isEmptyString(moduleDTO.name,'moduleDTO.name').succeeded){
      return Result.fail<Module>("ModuleDTO Name is invalid.");
    }

    if (Guard.isNull(moduleDTO.urlPath,'moduleDTO.urlPath').succeeded || Guard.isEmptyString(moduleDTO.urlPath,'moduleDTO.urlPath').succeeded){
      return Result.fail<Module>("ModuleDTO UrlPath is invalid.");
    }

    if (Guard.isNull(moduleDTO.viewPath,'moduleDTO.viewPath').succeeded || Guard.isEmptyString(moduleDTO.viewPath,'moduleDTO.viewPath').succeeded){
        return Result.fail<Module>("ModuleDTO ViewPath is invalid.");
    }

    if (Guard.isNull(moduleDTO.isExact,'moduleDTO.isExact').succeeded){
        return Result.fail<Module>("ModuleDTO IsExact is invalid.");
    }

    if (Guard.isNull(moduleDTO.isNavItem,'moduleDTO.isNavItem').succeeded){
        return Result.fail<Module>("ModuleDTO IsNavItem is invalid.");
    }

    const moduleName = ModuleName.create(moduleDTO.name);
    if(moduleName.isFailure){
      return Result.fail<Module>(moduleName.error);
    }

    const moduleUrlPath = ModuleUrlPath.create(moduleDTO.urlPath);
    if(moduleUrlPath.isFailure){
      return Result.fail<Module>(moduleUrlPath.error);
    }

    const moduleViewPath = ModuleUrlPath.create(moduleDTO.viewPath);
    if(moduleViewPath.isFailure){
      return Result.fail<Module>(moduleViewPath.error);
    }

    return Result.ok<Module>(new Module({ name: moduleName.getValue(), urlPath: moduleUrlPath.getValue(), viewPath: moduleViewPath.getValue(), isExact: moduleDTO.isExact, isNavItem: moduleDTO.isNavItem, needsAdmin: moduleDTO.needsAdmin}));

  }
}
