import { Request, Response, NextFunction } from 'express';

export default interface IPathController {
    createPath(req: Request, res: Response, next: NextFunction);
    findAllPaths(req: Request, res: Response, next: NextFunction);
    getNumberOfPaths(req: Request, res: Response, next: NextFunction);
    getPathByCode(req: Request, res: Response, next: NextFunction);
}