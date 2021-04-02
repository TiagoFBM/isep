import { Result } from "../../core/logic/Result";
import { User } from "../../domain/user/User";
import IUserDTO from "../../dto/IUserDTO";

export default interface IUserService {
    createUser(UserDTO: IUserDTO): Promise<Result<IUserDTO>>;
    updateUser(UserDTO: IUserDTO): Promise<Result<IUserDTO>>;
    getUserByEmail(userEmail: string): Promise<User>;
    deleteUser(userEmail: string): Promise<boolean>;
    findAllUsers(): Promise<Array<IUserDTO>>;
    findUsersPaginated({ skip, limit }): Promise<Array<IUserDTO>>;
    getNumberOfUsers(): Promise<Number>;
}