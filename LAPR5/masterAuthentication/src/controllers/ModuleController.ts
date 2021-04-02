import { Request, Response, NextFunction } from "express";
import { Inject } from "typedi";
import config from "../../config";

import IModuleController from "./IControllers/IModuleController";
import IModuleService from "../services/IServices/IModuleService";
import IModuleDTO from '../dto/IModuleDTO';

import { Result } from "../core/logic/Result";

export default class ModuleController
  implements
    IModuleController /* TODO: extends ../core/infra/BaseController */ {
  constructor(
    @Inject(config.services.module.name)
    private ModuleServiceInstance: IModuleService
  ) {}

  public async findAllModules(req: Request, res: Response, next: NextFunction) {
    try {
      let recs;
      recs = await this.ModuleServiceInstance.findAllModules();
      res.json(recs).status(200);
    } catch (err) {
      throw err;
    }
  }

  public async createModule(req: Request, res: Response, next: NextFunction) {
    try {
      const ModuleOrError = await this.ModuleServiceInstance.createModule(req.body as IModuleDTO) as Result<IModuleDTO>;

      if (ModuleOrError.isFailure) {
        return res.status(402).json({error: ModuleOrError.error});
      }

      const ModuleDTO = ModuleOrError.getValue();
      return res.json(ModuleDTO).status(201);
    }
    catch (e) {
      return next(e);
    }
  }
}
