import { AggregateRoot } from "../../core/domain/AggregateRoot";
import { UniqueEntityID } from "../../core/domain/UniqueEntityID";
import { Guard } from "../../core/logic/Guard";
import { Result } from "../../core/logic/Result";
import { UserEmail } from "./UserEmail";
import { UserUsername } from "./UserUsername";
import { UserPassword } from "./UserPassword";
import { UserName } from "./UserName";
import { UserBirthDate } from "./UserBirthDate";
import IUserDTO from "../../dto/IUserDTO";

interface UserProps {
  email: UserEmail;
  username: UserUsername;
  password?: UserPassword;
  name: UserName;
  birthDate: UserBirthDate;
  acceptedTerms: boolean;
  isDataAdmin: boolean;
}

export class User extends AggregateRoot<UserProps> {
  
  private constructor (props: UserProps, id?: UniqueEntityID) {
    super(props, id);
  }
  
  get userId (): string {
    return this.id.toString();
  }

  get userEmail (): UserEmail {
    return this.props.email;
  }

  get userUsername (): UserUsername {
    return this.props.username;
  }

  get userPassword (): UserPassword {
    return this.props.password;
  }
  
  get userName (): UserName {
    return this.props.name;
  }

  get userBirthDate (): UserBirthDate {
    return this.props.birthDate;
  }

  get acceptedTerms (): boolean {
    return this.props.acceptedTerms;
  }

  get isDataAdmin (): boolean {
    return this.props.isDataAdmin;
  }


  public static create (userDTO: IUserDTO): Result<User> {
    if (Guard.isNull(userDTO,'userDTO').succeeded){
      return Result.fail<User>("UserDTO can\'t be null.");
    }

    if (Guard.isNull(userDTO.email,'userDTO.email').succeeded || Guard.isEmptyString(userDTO.email,'userDTO.email').succeeded){
      return Result.fail<User>("UserDTO Email is invalid.");
    }

    if (Guard.isNull(userDTO.username,'userDTO.username').succeeded || Guard.isEmptyString(userDTO.username,'userDTO.username').succeeded){
      return Result.fail<User>("UserDTO Username is invalid.");
    }

    if (Guard.isNull(userDTO.password,'userDTO.password').succeeded || Guard.isEmptyString(userDTO.password,'userDTO.password').succeeded){
        return Result.fail<User>("UserDTO Password is invalid.");
    }

    if (Guard.isNull(userDTO.name,'userDTO.name').succeeded || Guard.isEmptyString(userDTO.name,'userDTO.name').succeeded){
        return Result.fail<User>("UserDTO Name is invalid.");
    }

    if (Guard.isNull(userDTO.birthDate,'userDTO.birthDate').succeeded || Guard.isEmptyString(userDTO.birthDate,'userDTO.birthDate').succeeded){
        return Result.fail<User>("UserDTO Birth Date is invalid.");
    }

    const userEmail = UserEmail.create(userDTO.email);
    if(userEmail.isFailure){
      return Result.fail<User>(userEmail.error);
    }

    const userUsername = UserUsername.create(userDTO.username);
    if(userUsername.isFailure){
      return Result.fail<User>(userUsername.error);
    }

    const userPassword = UserPassword.create(userDTO.password);
    if(userPassword.isFailure){
      return Result.fail<User>(userPassword.error);
    }

    const userName = UserName.create(userDTO.name);
    if(userName.isFailure){
      return Result.fail<User>(userName.error);
    }

    const userBirthDate = UserBirthDate.create(userDTO.birthDate);
    if(userBirthDate.isFailure){
      return Result.fail<User>(userBirthDate.error);
    }

    if (userDTO.acceptedTerms == false) {
      return Result.fail<User>("Can't create an user without accepting the terms of service");
    }

    return Result.ok<User>(new User({ email: userEmail.getValue(), username: userUsername.getValue(), password: userPassword.getValue(), name: userName.getValue(), birthDate: userBirthDate.getValue(), acceptedTerms: userDTO.acceptedTerms, isDataAdmin: userDTO.isDataAdmin}));

  }
}
