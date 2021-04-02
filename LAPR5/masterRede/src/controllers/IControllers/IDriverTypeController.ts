import { Request, Response, NextFunction } from 'express';

export default interface IDriverTypeController {
  createDriverType(req: Request, res: Response, next: NextFunction);
  findAllDriverTypes(req: Request, res: Response, next: NextFunction);
  getNumberOfDriverTypes(req: Request, res: Response, next: NextFunction);
  getDriverTypeByCode(req: Request, res: Response, next: NextFunction);
}