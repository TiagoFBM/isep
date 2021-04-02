import { Request, Response, NextFunction } from 'express';

export default interface IAuthenticationController {
  login(req: Request, res: Response, next: NextFunction);
  getUserByToken(req: Request, res: Response, next: NextFunction);
}