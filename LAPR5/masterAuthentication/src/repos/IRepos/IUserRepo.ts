import { Repo } from "../../core/infra/Repo";
import { User } from "../../domain/user/User";
import { UserEmail } from "../../domain/user/UserEmail";
import IUserDTO from "../../dto/IUserDTO";

export default interface IUserRepo extends Repo<User> {
  findAll (): Promise<Array<User>>;
  findByEmail(userEmail: UserEmail | string): Promise<User>
  delete(userEmail:UserEmail | string): Promise<boolean>;
  update(user: IUserDTO): Promise<User>;
  findPaginated({skip, limit}: any): Promise<Array<User>>;
  getNumberOfDocuments(): Promise<Number>;
}