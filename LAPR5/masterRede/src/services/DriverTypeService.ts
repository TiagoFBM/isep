import { Service, Inject } from 'typedi';
import config from "../../config";
import IDriverTypeDTO from '../dto/IDriverTypeDTO';
import { DriverType } from "../domain/driver/DriverType";
import IDriverTypeRepo from '../services/IRepos/IDriverTypeRepo';
import IDriverTypeService from './IServices/IDriverTypeService';
import { Result } from "../core/logic/Result";
import DriverTypeMap from "../mappers/DriverTypeMap";

@Service()
export default class DriverTypeService implements IDriverTypeService {
  constructor(
    @Inject(config.repos.driverType.name) private DriverTypeRepo: IDriverTypeRepo
  ) { }

  public async createDriverType(DriverTypeDTO: IDriverTypeDTO): Promise<Result<IDriverTypeDTO>> {
    try {

      const DriverTypeOrError = await DriverType.create(DriverTypeDTO);

      if (DriverTypeOrError.isFailure) {
        return Result.fail<IDriverTypeDTO>(DriverTypeOrError.errorValue());
      }

      const DriverTypeResult = DriverTypeOrError.getValue();

      await this.DriverTypeRepo.save(DriverTypeResult);

      const DriverTypeDTOResult = DriverTypeMap.toDTO(DriverTypeResult) as IDriverTypeDTO;
      return Result.ok<IDriverTypeDTO>(DriverTypeDTOResult)
    } catch (e) {
      throw e;
    }
  }

  public async findDriverTypesPaginated({ skip, limit }): Promise<Array<IDriverTypeDTO>> {
    try {
      const recs = await this.DriverTypeRepo.findPaginated({ skip, limit });
      var arr = [];
      recs.forEach(elem => {
        arr.push(DriverTypeMap.toDTO(elem));
      });
      return arr;
    } catch (err) {
      throw err;
    }
  }

  public async findAllDriverTypes(): Promise<Array<IDriverTypeDTO>> {
    try {
      const recs = await this.DriverTypeRepo.findAll();
      var arr = [];
      recs.forEach(elem => {
        arr.push(DriverTypeMap.toDTO(elem));
      });
      return arr;
    } catch (err) {
      throw err;
    }
  }

  public async getNumberOfDriverTypes(): Promise<Number> {
    try {
      const numberOfDT = await this.DriverTypeRepo.getNumberOfDocuments();
      return numberOfDT;
    } catch (err) {
      throw err;
    }
  }

  public async getDriverTypeByCode(driverTypeCode: string): Promise<IDriverTypeDTO> {
    try {
      const rec = await this.DriverTypeRepo.findByCode(driverTypeCode);
      return DriverTypeMap.toDTO(rec);
    } catch (err) {
      throw err;
    }
  }

}