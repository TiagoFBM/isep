import { Request, Response, NextFunction } from "express";
import { Inject } from "typedi";
import config from "../../config";

import IUserController from "./IControllers/IUserController";
import IUserService from "../services/IServices/IUserService";
import IUserDTO from '../dto/IUserDTO';

import { Result } from "../core/logic/Result";

import bcrypt from "bcryptjs";

export default class UserController
  implements
    IUserController /* TODO: extends ../core/infra/BaseController */ {
  constructor(
    @Inject(config.services.user.name)
    private UserServiceInstance: IUserService
  ) {}

  public async findAllUsers(req: Request, res: Response, next: NextFunction) {
    try {
      let recs;
      if (req.query.skip != undefined && req.query.limit != undefined) {
        recs = await this.UserServiceInstance.findUsersPaginated({ skip: req.query.skip, limit: req.query.limit });
      } else {
        recs = await this.UserServiceInstance.findAllUsers();
      }
      res.json(recs).status(200);
    } catch (err) {
      throw err;
    }
  }

  public async createUser(req: Request, res: Response, next: NextFunction) {
    try {
        const userDto: IUserDTO = {...req.body};
        const salt = bcrypt.genSaltSync(10);
        const hash = bcrypt.hashSync(userDto.password, salt);
        userDto.password = hash;
        
      const UserOrError = await this.UserServiceInstance.createUser(userDto as IUserDTO) as Result<IUserDTO>;

      if (UserOrError.isFailure) {
        return res.status(402).json({error: UserOrError.error});
      }

      const UserDTO = UserOrError.getValue();
      return res.json(UserDTO).status(201);
    }
    catch (e) {
      return next(e);
    }
  }

  public async delete(req: Request, res: Response, next: NextFunction) {
    try {
      let isDeleted = await this.UserServiceInstance.deleteUser(req.params.email);      
      if (isDeleted) {
        res.status(200).json({message: "User removed with success"});
      } else {
        res.status(402).json({message: "Didn't find user with that email"});
      }
    } catch (err) {
      throw err;
    }
  }

  public async update(req: Request, res: Response, next: NextFunction) {
    try {
      let obj = await this.UserServiceInstance.updateUser(req.body as IUserDTO) as Result<IUserDTO>;
      res.status(200).json(obj.getValue());
    } catch (err) {
      throw err;
    }
  }

  public async getNumberOfUsers(req: Request, res: Response, next: NextFunction) {
    try {
      const number = await this.UserServiceInstance.getNumberOfUsers();
      res.json({ numberOfElems: number }).status(200);
    } catch (err) {
      return next(err);
    }
  }
}
