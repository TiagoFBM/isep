import { Request, Response, NextFunction } from 'express';

export default interface IModuleController {
  findAllModules(req: Request, res: Response, next: NextFunction);
  createModule(req: Request, res: Response, next: NextFunction);
}