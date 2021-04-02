import { Request, Response, NextFunction } from 'express';

export default interface INodeController  {
  createNode(req: Request, res: Response, next: NextFunction);
  findAllNodes(req: Request, res: Response, next: NextFunction);
  getNumberOfNodes(req: Request, res: Response, next: NextFunction);
  getNodeByCode(req: Request, res: Response, next: NextFunction);
}