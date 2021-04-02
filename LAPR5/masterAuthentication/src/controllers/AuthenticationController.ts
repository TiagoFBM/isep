import { Request, Response, NextFunction } from "express";
import { Inject } from "typedi";
import config from "../../config";
import jwt from "jsonwebtoken";

import IAuthenticationController from "./IControllers/IAuthenticationController";
import IUserService from "../services/IServices/IUserService";
import IUserDTO from '../dto/IUserDTO';

import { Result } from "../core/logic/Result";

import bcrypt from "bcryptjs";
import UserMapper from "../mappers/UserMap";

export default class AuthenticationController
  implements
    IAuthenticationController /* TODO: extends ../core/infra/BaseController */ {
  constructor(
    @Inject(config.services.user.name)
    private UserServiceInstance: IUserService
  ) {}

  public async login(req: Request, res: Response, next: NextFunction) {
    try {
      const user = await this.UserServiceInstance.getUserByEmail(req.body.email);      
      if (!user) {
          res.json({error: "Email not found"}).status(400).send();
          return;
      }
      const passwordCorrect = bcrypt.compareSync(req.body.password, user.userPassword.value);
      if (!passwordCorrect) {
        res.json({error: "Invalid Password"}).status(400);
        return;
      }
      const dto = UserMapper.toDTO(user);
      let jwtSigned = jwt.sign(dto, config.jwtSecret);
      res.json({body: "Successfully logged in", token: jwtSigned}).status(200);
    } catch (err) {
      throw err;
    }
  }

  public async getUserByToken(req: Request, res: Response, next: NextFunction) {
    try {
      const user = jwt.verify(req.headers.authorization, config.jwtSecret);
      res.json(user).status(200);
    } catch (err) {
      return next(err);
    }
  }
}
