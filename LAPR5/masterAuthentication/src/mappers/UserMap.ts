import { UniqueEntityID } from "../core/domain/UniqueEntityID";
import { Mapper } from "../core/infra/Mapper";
import { Document, Model } from 'mongoose';
import { User } from "../domain/user/User";
import IUserDTO from "../dto/IUserDTO";

export default class UserMapper extends Mapper<User> {

  public static toDTO( user: User): IUserDTO {    
    return {
      email: user.userEmail.value,
      username: user.userUsername.value,
      name: user.userName.value,
      birthDate: user.userBirthDate.value,
      acceptedTerms: user.acceptedTerms,
      isDataAdmin: user.isDataAdmin
    } as IUserDTO;
  }

  public static toDomain (user: any | Model<IUserDTO & Document> ): User {
    const roleOrError = User.create(
      user
    );

    if (roleOrError.isFailure){
      console.log("Error Converting toDomain: %s",roleOrError.error);
      return null;
    }

    return roleOrError.getValue();
  }

  public static toPersistence (user: User): any {
    const driver = {
      email: user.userEmail.value,
      username: user.userUsername.value,
      password: user.userPassword.value,
      name: user.userName.value,
      birthDate: user.userBirthDate.value,
      acceptedTerms: user.acceptedTerms,
      isDataAdmin: user.isDataAdmin
    }
    return driver;
  }
}