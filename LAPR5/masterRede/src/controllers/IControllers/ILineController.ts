import { Request, Response, NextFunction } from 'express';

export default interface ILineController  {
  createLine(req: Request, res: Response, next: NextFunction);
  findAllLines(req: Request, res: Response, next: NextFunction);
  findAllLinePaths(req: Request, res: Response, next: NextFunction);
  findLineByCode(req: Request, res: Response, next: NextFunction); 
  getNumberOfLines(req: Request, res: Response, next: NextFunction);
  findLineOfPath(req: Request, res: Response, next: NextFunction);
}