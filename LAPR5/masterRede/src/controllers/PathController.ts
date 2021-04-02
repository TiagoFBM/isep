import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";

import IPathController from "./IControllers/IPathController";
import IPathService from '../services/IServices/IPathService';

import { Result } from "../core/logic/Result";
import IPathDTO from '../dto/IPathDTO';

export default class PathController implements IPathController {
    constructor(
        @Inject(config.services.path.name) private PathServiceInstance : IPathService
    ) {}

    public async createPath(req: Request, res: Response, next: NextFunction) {
        try {
            const PathOrError = await this.PathServiceInstance.createPath(req.body as IPathDTO) as Result<IPathDTO>;
            if (PathOrError.isFailure) {
                return res.status(402).send();
            }
            const PathDTO = PathOrError.getValue();
            return res.json(PathDTO).status(201);
        } catch(err) {
            throw next(err);
        }
    }

    public async findAllPaths(req: Request, res: Response, next: NextFunction) {
        try {
            let recs;
            if (req.query.skip != undefined && req.query.limit != undefined ) {
              recs = await this.PathServiceInstance.findPathsPaginated({skip: req.query.skip, limit: req.query.limit});
            } else {
              recs = await this.PathServiceInstance.findAllPaths();
            }
            res.json(recs).status(200);
          } catch (err) {
            throw err;
          }
    }

    public async getNumberOfPaths(req: Request, res: Response, next: NextFunction) {
        try {
          const number = await this.PathServiceInstance.getNumberOfPaths();
          res.json({numberOfElems: number}).status(200);
        } catch (err) {
          return next(err);
        }
    }
    
    public async getPathByCode(req: Request, res: Response, next: NextFunction) {
      try {
        const path = await this.PathServiceInstance.getPathByCode(req.params.code);
        if (path.isFailure) {
          res.status(402).send();
        }
        res.json(path.getValue()).status(200);
      } catch(err) {
        next(err);
      }
    }
}