import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";

import IVehicleTypeController from "./IControllers/IVehicleTypeController";
import IVehicleTypeService from '../services/IServices/IVehicleTypeService';
import IVehicleTypeDTO from '../dto/IVehicleTypeDTO';

import { Result } from "../core/logic/Result";
import { Console } from 'console';

export default class VehicleTypeController implements IVehicleTypeController /* TODO: extends ../core/infra/BaseController */ {
    constructor(
        @Inject(config.services.vehicleType.name) private VehicleTypeServiceInstance: IVehicleTypeService
    ) { }

    public async createVehicleType(req: Request, res: Response, next: NextFunction) {

        try {
            const VehicleTypeOrError = await this.VehicleTypeServiceInstance.createVehicleType(req.body as IVehicleTypeDTO) as Result<IVehicleTypeDTO>;

            if (VehicleTypeOrError.isFailure) {
                return res.status(402).send();
            }

            const VehicleTypeDTO = VehicleTypeOrError.getValue();
            return res.json(VehicleTypeDTO).status(201);
        }
        catch (e) {
            return next(e);
        }
    };

    public async findAllVehicleTypes(req: Request, res: Response, next: NextFunction) {
        try {
            const recs = await this.VehicleTypeServiceInstance.findAllVehicleTypes({ skip: req.query.skip, limit: req.query.limit });
            res.json(recs).status(200);
        } catch (err) {
            throw err;
        }
    }

    public async getNumberOfVehicleTypes(req: Request, res: Response, next: NextFunction) {
        try {
            const number = await this.VehicleTypeServiceInstance.getNumberOfVehicleTypes();
            res.json({ numberOfElems: number }).status(200);
        } catch (err) {
            return next(err);
        }
    }

    public async getVehicleTypeByCode(req: Request, res: Response, next: NextFunction) {
        try {
          const vehicleType = await this.VehicleTypeServiceInstance.getVehicleTypeByCode(req.params.code);
          res.json(vehicleType).status(200);
        } catch (err) {
          return next(err);
        }
      }

}