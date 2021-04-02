import { Request, Response, NextFunction } from 'express';

export default interface IUserController {
  findAllUsers(req: Request, res: Response, next: NextFunction);
  createUser(req: Request, res: Response, next: NextFunction);
  delete(req: Request, res: Response, next: NextFunction);
  update(req: Request, res: Response, next: NextFunction);
  getNumberOfUsers(req: Request, res: Response, next: NextFunction);
}