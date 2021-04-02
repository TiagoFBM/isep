import { Service, Inject } from 'typedi';
import config from "../../config";
import IUserDTO from '../dto/IUserDTO';
import { User } from "../domain/user/User";
import IUserRepo from '../repos/IRepos/IUserRepo';
import IUserService from './IServices/IUserService';
import { Result } from "../core/logic/Result";
import UserMap from "../mappers/UserMap";

@Service()
export default class UserService implements IUserService {
  constructor(
    @Inject(config.repos.user.name) private UserRepo: IUserRepo
  ) { }

  public async findAllUsers(): Promise<Array<IUserDTO>> {
    try {
      const recs = await this.UserRepo.findAll();
      var arr = [];
      recs.forEach(elem => {
        arr.push(UserMap.toDTO(elem));
      });
      return arr;
    } catch (err) {
      throw err;
    }
  }

  public async findUsersPaginated({ skip, limit }): Promise<Array<IUserDTO>> {
    try {
      const recs = await this.UserRepo.findPaginated({ skip, limit });
      var arr = [];
      recs.forEach(elem => {
        arr.push(UserMap.toDTO(elem));
      });
      return arr;
    } catch (err) {
      throw err;
    }
  }

  public async createUser(UserDTO: IUserDTO): Promise<Result<IUserDTO>> {
    try {      
      const UserOrError = await User.create(UserDTO);

      if (UserOrError.isFailure) {
        return Result.fail<IUserDTO>(UserOrError.errorValue());
      }

      const UserResult = UserOrError.getValue();

      await this.UserRepo.save(UserResult);

      const UserDTOResult = UserMap.toDTO(UserResult) as IUserDTO;
      return Result.ok<IUserDTO>(UserDTOResult)
    } catch (e) {
      throw e;
    }
  }

  public async updateUser(UserDTO: IUserDTO): Promise<Result<IUserDTO>> {
    try {

      let updatedUser = await this.UserRepo.update(UserDTO);

      console.log(updatedUser);

      const UserDTOResult = UserMap.toDTO(updatedUser) as IUserDTO;
      return Result.ok<IUserDTO>(UserDTOResult)
    } catch (e) {
      throw e;
    }
  }

  public async deleteUser(userEmail: string): Promise<boolean> {
    try {
      const rec = await this.UserRepo.delete(userEmail);
      return rec;
    } catch (err) {
      throw err;
    }
  }

  public async getUserByEmail(userEmail: string): Promise<User> {
    try {
      const rec = await this.UserRepo.findByEmail(userEmail);
      return rec;
    } catch (err) {
      throw err;
    }
  }

  public async getNumberOfUsers(): Promise<Number> {
    try {
      const numberOfU = await this.UserRepo.getNumberOfDocuments();
      return numberOfU;
    } catch (err) {
      throw err;
    }
  }

}