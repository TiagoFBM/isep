import { Service, Inject } from 'typedi';
import config from "../../config";
import ILineDTO from '../dto/ILineDTO';
import { Line } from "../domain/line/Line";
import ILineRepo from '../services/IRepos/ILineRepo';
import ILineService from './IServices/ILineService';
import { Result } from "../core/logic/Result";
import LineMap from "../mappers/LineMap";
import IVehicleRepo from './IRepos/IVehicleTypeRepo';
import IDriverTypeRepo from './IRepos/IDriverTypeRepo';
import { isUndefined } from "lodash";
import { LineCode } from '../domain/line/LineCode';
import LineMapper from '../mappers/LineMap';
import ILinePathDTO from '../dto/ILinePathDTO';
import IPathRepo from './IRepos/IPathRepo';
import { LinePath } from '../domain/line/LinePath';

@Service()
export default class LineService implements ILineService {
  constructor(
    @Inject(config.repos.line.name) private lineRepo: ILineRepo,
    @Inject(config.repos.vehicleType.name) private vehicleTypeRepo: IVehicleRepo,
    @Inject(config.repos.driverType.name) private driverTypeRepo: IDriverTypeRepo,
    @Inject(config.repos.path.name) private pathRepo: IPathRepo
  ) { }

  public async createLine(lineDTO: ILineDTO): Promise<Result<ILineDTO>> {

    try {
      const lineOrError = Line.create({
        code: lineDTO.code,
        description: lineDTO.description,
        linePaths: [],
        allowedVehicles: [],
        deniedVehicles: [],
        allowedDrivers: [],
        deniedDrivers: [],
        lineColor: lineDTO.lineColor
      });

      if (lineOrError.isFailure) {
        return Result.fail<ILineDTO>(lineOrError.errorValue());
      }

      for (let i in lineDTO.linePaths) {
        const element = lineDTO.linePaths[i];

        var newPath = await this.pathRepo.findByCodeDB(element.path.toString());

        if (newPath != null) {

          let newLinePathOrError = LinePath.create({
            path: newPath._id,
            orientation: element.orientation
          });

          if (newLinePathOrError.isFailure) {
            return Result.fail<ILineDTO>(newLinePathOrError.errorValue());
          }

          lineOrError.getValue().addLinePath(
            newLinePathOrError.getValue()
          );
        }
      }

      if (!isUndefined(lineDTO.allowedVehicles)) {
        for (let i in lineDTO.allowedVehicles) {
          const element = lineDTO.allowedVehicles[i];
          var allowedVehicle = await this.vehicleTypeRepo.findByCodeDB(element.toString());

          if (allowedVehicle != null) {
            lineOrError.getValue().addAllowedVehicleTypeID(allowedVehicle._id);
          }
        }
      }

      if (!isUndefined(lineDTO.deniedVehicles)) {
        for (let i in lineDTO.deniedVehicles) {
          const element = lineDTO.deniedVehicles[i];
          var deniedVehicle = await this.vehicleTypeRepo.findByCodeDB(element.toString());

          if (deniedVehicle != null) {
            lineOrError.getValue().addDeniedVehicleTypeID(deniedVehicle._id);
          }
        }
      }

      if (!isUndefined(lineDTO.allowedDrivers)) {
        for (let i in lineDTO.allowedDrivers) {
          const element = lineDTO.allowedDrivers[i];
          var allowedDriver = await this.driverTypeRepo.findByCodeDB(element.toString());

          if (allowedDriver != null) {
            lineOrError.getValue().addAllowedDriverTypeID(allowedDriver._id);
          }
        }
      }

      if (!isUndefined(lineDTO.deniedDrivers)) {
        for (let i in lineDTO.deniedDrivers) {
          const element = lineDTO.deniedDrivers[i];
          var deniedDriver = await this.driverTypeRepo.findByCodeDB(element.toString());
          if (deniedDriver != null) {
            lineOrError.getValue().addDeniedDriverTypeID(deniedDriver._id);
          }
        }
      }

      const lineResult = lineOrError.getValue();

      const res = await this.lineRepo.save(lineResult);

      const lineDTOResult = LineMap.toDTO(res) as ILineDTO;

      return Result.ok<ILineDTO>(lineDTOResult)
    } catch (e) {
      throw e;
    }
  }

  public async findAllLines({ skip = 0, limit = 10 }): Promise<Array<ILineDTO>> {
    try {
      const recs = await this.lineRepo.findAll({ skip, limit });

      var arr = [];
      recs.forEach(elem => {
        const lineDTO: ILineDTO = LineMap.toDTO(elem);
        arr.push(lineDTO);
      });
      return arr;
    } catch (err) {
      throw err
    }
  }

  public async findAllLinePaths(LineCode: LineCode | string): Promise<Array<ILinePathDTO>> {
    try {
      const rec = await this.lineRepo.findByCode(LineCode);
      return LineMapper.toDTO(rec).linePaths;
    } catch (err) {
      throw err;
    }
  }

  public async findLineByCode(LineCode: LineCode | string): Promise<ILineDTO> {
    try {
      const rec = await this.lineRepo.findByCode(LineCode);
      return LineMapper.toDTO(rec);
    } catch (err) {
      throw err;
    }
  }

  public async findLineOfPath(pathId: string): Promise<string> {
    try {
      return await this.lineRepo.findLineByPath(pathId);
    } catch (err) {
      throw err;
    }
  }

  public async getNumberOfVehicleTypes(): Promise<Number> {
    try {
      const numberOfDT = await this.lineRepo.getNumberOfDocuments();
      return numberOfDT;
    } catch (err) {
      throw err;
    }
  }
}