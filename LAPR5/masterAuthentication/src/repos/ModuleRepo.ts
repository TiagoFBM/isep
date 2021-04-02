import { Service, Inject } from 'typedi';

import IModuleRepo from "./IRepos/IModuleRepo";
import { Module } from "../domain/module/Module";
import ModuleMap from "../mappers/ModuleMap";

import { Document, Model } from 'mongoose';
import { IModulePersistence } from '../dataschema/IModulePersistence';

@Service()
export default class ModuleRepo implements IModuleRepo {
  private models: any;

  constructor(
    @Inject('ModuleSchema') private ModuleSchema: Model<IModulePersistence & Document>,
  ) { }

  private createBaseQuery(): any {
    return {
      where: {},
    }
  }

  public async exists(module: Module): Promise<boolean> {

    const codeX = module.id;

    const query = { id: codeX };
    const moduleFound = await this.ModuleSchema.findOne(query);

    return !!moduleFound === true;
  }


  public async save(module: Module): Promise<Module> {
    const query = { id: module.id };

    const ModuleDocument = await this.ModuleSchema.findOne(query);
    
    try {
      if (ModuleDocument === null) {
        const rawModule: any = ModuleMap.toPersistence(module);
        
        const ModuleCreated = await this.ModuleSchema.create(rawModule);
        
        return ModuleMap.toDomain(ModuleCreated);
      } else {
        // ModuleDocument.description = module.props.description.value;
        // await ModuleDocument.save();

        return module;
      }
    } catch (err) {
      throw err;
    }
  }

  public async findAll(): Promise<Array<Module>> {
    const ModuleRecords = await this.ModuleSchema.find({});
    var arr = [];
    ModuleRecords.forEach(elem => {
      arr.push(ModuleMap.toDomain(elem));
    });
    return arr;
  }
}