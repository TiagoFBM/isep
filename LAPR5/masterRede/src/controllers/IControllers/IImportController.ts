import { Request, Response, NextFunction } from 'express';

export default interface IImportController  {
  import(req: Request, res: Response, next: NextFunction);
}