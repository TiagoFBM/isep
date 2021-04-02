import { Request, Response, NextFunction } from 'express';
import { Inject } from 'typedi';
import config from "../../config";

import INodeController from "./IControllers/INodeController";
import INodeService from '../services/IServices/INodeService';
import INodeDTO from '../dto/INodeDTO';

import { Result } from "../core/logic/Result";

export default class NodeController implements INodeController /* TODO: extends ../core/infra/BaseController */ {
  constructor(
      @Inject(config.services.node.name) private NodeServiceInstance : INodeService
  ) {}

  public async createNode(req: Request, res: Response, next: NextFunction) {
    try {
      const NodeOrError = await this.NodeServiceInstance.createNode(req.body as INodeDTO) as Result<INodeDTO>;
      if (NodeOrError.isFailure) {
        return res.status(402).send();
      }

      const NodeDTO = NodeOrError.getValue();
      return res.json( NodeDTO ).status(201);
    }
    catch (e) {
      return next(e);
    }
  }

  public async findAllNodes(req: Request, res:Response, next: NextFunction) {
    try {
      let recs;
      if (req.query.skip != undefined && req.query.limit != undefined ) {
        recs = await this.NodeServiceInstance.findNodesPaginated({skip: req.query.skip, limit: req.query.limit});
      } else {
        recs = await this.NodeServiceInstance.findAllNodes();
      }
      res.json(recs).status(200);
    } catch(err) {
      return next(err);
    }
  }
  
  public async getNumberOfNodes(req: Request, res: Response, next: NextFunction) {
    try {
      const number = await this.NodeServiceInstance.getNumberOfNodes();
      res.json({numberOfElems: number}).status(200);
    } catch (err) {
      return next(err);
    }
  }

  public async getNodeByCode(req: Request, res: Response, next: NextFunction) {
    try {
      const node = await this.NodeServiceInstance.getNodeByCode(req.params.code);
      if (node.isFailure) {
        res.status(402).send();
      }
      res.json(node.getValue()).status(200);
    } catch(err) {
      next(err);
    }
  }
  
}