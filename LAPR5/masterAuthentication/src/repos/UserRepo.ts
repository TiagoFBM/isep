import { Service, Inject } from 'typedi';

import IUserRepo from "./IRepos/IUserRepo";
import { User } from "../domain/user/User";
import UserMap from "../mappers/UserMap";

import { Document, Model } from 'mongoose';
import { IUserPersistence } from '../dataschema/IUserPersistence';
import { UserEmail } from '../domain/user/UserEmail';
import IUserDTO from '../dto/IUserDTO';

@Service()
export default class UserRepo implements IUserRepo {
  private models: any;

  constructor(
    @Inject('UserSchema') private UserSchema: Model<IUserPersistence & Document>,
  ) { }

  private createBaseQuery(): any {
    return {
      where: {},
    }
  }

  public async exists(user: User): Promise<boolean> {

    const codeX = user.id;

    const query = { id: codeX };
    const userFound = await this.UserSchema.findOne(query);

    return !!userFound === true;
  }


  public async save(user: User): Promise<User> {
    const query = { id: user.id };

    const UserDocument = await this.UserSchema.findOne(query);
    
    try {
      if (UserDocument === null) {
        const rawUser: any = UserMap.toPersistence(user);
        
        const UserCreated = await this.UserSchema.create(rawUser);
        
        return UserMap.toDomain(UserCreated);
      } else {
        // UserDocument.description = user.props.description.value;
        // await UserDocument.save();

        return user;
      }
    } catch (err) {
      throw err;
    }
  }

  public async update(user: IUserDTO): Promise<User> {
    const query = { email: user.email };    
    const UserDocument = await this.UserSchema.findOne(query);
    try {
        UserDocument.email = user.email;
        UserDocument.username = user.username;
        UserDocument.name = user.name;
        UserDocument.birthDate = user.birthDate;
        UserDocument.isDataAdmin = user.isDataAdmin;
        let a = await UserDocument.save();
        console.log(a);
        return UserMap.toDomain(a);
    } catch (err) {
      throw err;
    }
  }

  public async findByEmail(userEmail: UserEmail | string): Promise<User> {
    const query = { email: userEmail.toString() };
    const UserRecord = await this.UserSchema.findOne(query);

    if (UserRecord != null) {
        return UserMap.toDomain(UserRecord);
    }
    return null;
}

public async delete(userEmail: UserEmail | string): Promise<boolean> {
  const query = { email: userEmail.toString() };

  try{

    let res = await this.UserSchema.deleteOne(query);
    return res.deletedCount != 0;

  }catch(err){

    throw err;
  }
}

  public async findAll(): Promise<Array<User>> {
    const UserRecords = await this.UserSchema.find({});
    var arr = [];
    UserRecords.forEach(elem => {
      arr.push(UserMap.toDomain(elem));
    });
    return arr;
  }

  public async findPaginated({skip, limit}: any): Promise<Array<User>> {
    const UserRecords = await this.UserSchema.find({}).skip(parseInt(skip)).limit(parseInt(limit));
    var arr = [];
    UserRecords.forEach(elem => {
      arr.push(UserMap.toDomain(elem));
    });
    return arr;
  }

  public async getNumberOfDocuments(): Promise<Number> {
    const numberOfDocuments = await this.UserSchema.countDocuments({});

    return numberOfDocuments;
  }
}