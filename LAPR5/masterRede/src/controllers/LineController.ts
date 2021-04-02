import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";
import ILineController from "./IControllers/ILineController";
import ILineService from '../services/IServices/ILineService';
import ILineDTO from '../dto/ILineDTO';

import { Result } from "../core/logic/Result";

export default class LineController implements ILineController {
  constructor(
    @Inject(config.services.line.name) private LineServiceInstance: ILineService
  ) { }

  public async createLine(req: Request, res: Response, next: NextFunction) {
    try {
      const LineOrError = await this.LineServiceInstance.createLine(req.body as ILineDTO) as Result<ILineDTO>;

      if (LineOrError.isFailure) {
        return res.status(402).send();
      }

      const LineDTO = LineOrError.getValue();
      return res.json(LineDTO).status(201);
    }
    catch (e) {
      return next(e);
    }
  }

  public async findAllLines(req: Request, res: Response, next: NextFunction) {
    try {
      const recs = await this.LineServiceInstance.findAllLines({ skip: req.query.skip, limit: req.query.limit });
      res.json(recs).status(200);
    } catch (err) {
      return next(err);
    }
  }

  public async findAllLinePaths(req: Request, res: Response, next: NextFunction) {
    try {
      const recs = await this.LineServiceInstance.findAllLinePaths(req.params.code);
      res.json(recs).status(200);
    } catch (err) {
      return next(err);
    }
  }

  public async getNumberOfLines(req: Request, res: Response, next: NextFunction) {
    try {
      const number = await this.LineServiceInstance.getNumberOfVehicleTypes();
      res.json({ numberOfElems: number }).status(200);
    } catch (err) {
      return next(err);
    }
  }

  public async findLineByCode(req: Request, res: Response, next: NextFunction) {
    try {
      const line = await this.LineServiceInstance.findLineByCode(req.params.code);
      res.json(line).status(200);
    } catch (err) {
      return next(err);
    }
  }

  public async findLineOfPath(req: Request, res: Response, next: NextFunction) {
    try {
      const line = await this.LineServiceInstance.findLineOfPath(req.params.code);
      res.json(line).status(200);
    } catch (err) {
      return next(err);
    }
  }


}