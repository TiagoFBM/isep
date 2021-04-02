import { Service, Inject } from 'typedi';
import config from "../../config";
import IModuleDTO from '../dto/IModuleDTO';
import { Module } from "../domain/module/Module";
import IModuleRepo from '../repos/IRepos/IModuleRepo';
import IModuleService from './IServices/IModuleService';
import { Result } from "../core/logic/Result";
import ModuleMap from "../mappers/ModuleMap";

@Service()
export default class ModuleService implements IModuleService {
  constructor(
    @Inject(config.repos.module.name) private ModuleRepo: IModuleRepo
  ) { }

  public async findAllModules(): Promise<Array<IModuleDTO>> {
    try {
      const recs = await this.ModuleRepo.findAll();
      var arr = [];
      recs.forEach(elem => {
        arr.push(ModuleMap.toDTO(elem));
      });
      return arr;
    } catch (err) {
      throw err;
    }
  }

  public async createModule(ModuleDTO: IModuleDTO): Promise<Result<IModuleDTO>> {
    try {      
      const ModuleOrError = await Module.create(ModuleDTO);

      if (ModuleOrError.isFailure) {
        return Result.fail<IModuleDTO>(ModuleOrError.errorValue());
      }

      const ModuleResult = ModuleOrError.getValue();

      await this.ModuleRepo.save(ModuleResult);

      const ModuleDTOResult = ModuleMap.toDTO(ModuleResult) as IModuleDTO;
      return Result.ok<IModuleDTO>(ModuleDTOResult)
    } catch (e) {
      throw e;
    }
  }

}