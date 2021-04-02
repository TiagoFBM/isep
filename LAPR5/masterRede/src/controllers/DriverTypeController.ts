import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";

import IDriverTypeController from "./IControllers/IDriverTypeController";
import IDriverTypeService from '../services/IServices/IDriverTypeService';
import IDriverTypeDTO from '../dto/IDriverTypeDTO';

import { Result } from "../core/logic/Result";

export default class DriverTypeController implements IDriverTypeController /* TODO: extends ../core/infra/BaseController */ {
  constructor(
    @Inject(config.services.driverType.name) private DriverTypeServiceInstance: IDriverTypeService
  ) { }

  public async createDriverType(req: Request, res: Response, next: NextFunction) {
    try {
      const DriverTypeOrError = await this.DriverTypeServiceInstance.createDriverType(req.body as IDriverTypeDTO) as Result<IDriverTypeDTO>;

      if (DriverTypeOrError.isFailure) {
        return res.status(402).send();
      }

      const DriverTypeDTO = DriverTypeOrError.getValue();
      return res.json(DriverTypeDTO).status(201);
    }
    catch (e) {
      return next(e);
    }
  }

  public async findAllDriverTypes(req: Request, res: Response, next: NextFunction) {
    try {
      let recs;
      if (req.query.skip != undefined && req.query.limit != undefined) {
        recs = await this.DriverTypeServiceInstance.findDriverTypesPaginated({ skip: req.query.skip, limit: req.query.limit });
      } else {
        recs = await this.DriverTypeServiceInstance.findAllDriverTypes();
      }
      res.json(recs).status(200);
    } catch (err) {
      throw err;
    }
  }

  public async getNumberOfDriverTypes(req: Request, res: Response, next: NextFunction) {
    try {
      const number = await this.DriverTypeServiceInstance.getNumberOfDriverTypes();
      res.json({ numberOfElems: number }).status(200);
    } catch (err) {
      return next(err);
    }
  }

  public async getDriverTypeByCode(req: Request, res: Response, next: NextFunction) {
    try {
      const driverType = await this.DriverTypeServiceInstance.getDriverTypeByCode(req.params.code);
      res.json(driverType).status(200);
    } catch (err) {
      return next(err);
    }
  }

}