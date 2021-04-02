import { Service, Inject } from 'typedi';

import IDriverTypeRepo from "../services/IRepos/IDriverTypeRepo";
import { DriverType } from "../domain/driver/DriverType";
import { DriverTypeCode } from "../domain/driver/DriverTypeCode";
import DriverTypeMap from "../mappers/DriverTypeMap";

import { Document, Model } from 'mongoose';
import { IDriverTypePersistence } from '../dataschema/IDriverTypePersistence';

@Service()
export default class DriverTypeRepo implements IDriverTypeRepo {
  private models: any;

  constructor(
    @Inject('DriverTypeSchema') private DriverTypeSchema: Model<IDriverTypePersistence & Document>,
  ) { }

  private createBaseQuery(): any {
    return {
      where: {},
    }
  }

  public async exists(driverType: DriverType): Promise<boolean> {

    const codeX = driverType.props.code.value;

    const query = { code: codeX };
    const driverTypeFound = await this.DriverTypeSchema.findOne(query);

    return !!driverTypeFound === true;
  }


  public async save(driverType: DriverType): Promise<DriverType> {
    const query = { code: driverType.id.toString() };

    const DriverTypeDocument = await this.DriverTypeSchema.findOne(query);

    try {
      if (DriverTypeDocument === null) {
        const rawDriverType: any = DriverTypeMap.toPersistence(driverType);

        const DriverTypeCreated = await this.DriverTypeSchema.create(rawDriverType);

        return DriverTypeMap.toDomain(DriverTypeCreated);
      } else {
        DriverTypeDocument.description = driverType.props.description.value;
        await DriverTypeDocument.save();

        return driverType;
      }
    } catch (err) {
      throw err;
    }
  }

  public async findByCode(driverTypeCode: DriverTypeCode | string): Promise<DriverType> {
    const query = { code: driverTypeCode.toString() };
    const DriverTypeRecord = await this.DriverTypeSchema.findOne(query);

    if (DriverTypeRecord != null) {
      return DriverTypeMap.toDomain(DriverTypeRecord);
    }
    else
      return null;
  }

  public async findPaginated({skip, limit}: any): Promise<Array<DriverType>> {
    const DriverTypeRecords = await this.DriverTypeSchema.find({}).skip(parseInt(skip)).limit(parseInt(limit));
    var arr = [];
    DriverTypeRecords.forEach(elem => {
      arr.push(DriverTypeMap.toDomain(elem));
    });
    return arr;
  }

  public async findAll(): Promise<Array<DriverType>> {
    const DriverTypeRecords = await this.DriverTypeSchema.find({});
    var arr = [];
    DriverTypeRecords.forEach(elem => {
      arr.push(DriverTypeMap.toDomain(elem));
    });
    return arr;
  }
  
  public async findByCodeDB(Code: DriverTypeCode | string): Promise<Document & IDriverTypePersistence> {
    const query = { code: Code.toString() };
    const driverTypeRecord = await this.DriverTypeSchema.findOne(query);
    if (driverTypeRecord != null) {
      return driverTypeRecord;
    } else {
      return null;
    }
  }

  public async getNumberOfDocuments(): Promise<Number> {
    const numberOfDocuments = await this.DriverTypeSchema.countDocuments({});

    return numberOfDocuments;
  }
}