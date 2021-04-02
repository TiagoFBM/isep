import { Service, Inject } from 'typedi';
import config from "../../config";
import IVehicleTypeDTO from '../dto/IVehicleTypeDTO';
import { VehicleType } from '../domain/vehicle/VehicleType';
import IVehicleTypeRepo from '../services/IRepos/IVehicleTypeRepo';
import IVehicleTypeService from '../services/IServices/IVehicleTypeService';
import { Result } from '../core/logic/Result';
import VehicleTypeMap from '../mappers/VehicleTypeMap';

@Service()
export default class VehicleTypeService implements IVehicleTypeService {
    constructor(
        @Inject(config.repos.vehicleType.name) private VehicleTypeRepo: IVehicleTypeRepo
    ) { }

    public async createVehicleType(VehicleTypeDTO: IVehicleTypeDTO): Promise<Result<IVehicleTypeDTO>> {
        try {

            const VehicleTypeOrError = await VehicleType.create(VehicleTypeDTO);

            if (VehicleTypeOrError.isFailure) {
                return Result.fail<IVehicleTypeDTO>(VehicleTypeOrError.errorValue());
            }

            const VehicleTypeResult = VehicleTypeOrError.getValue();

            await this.VehicleTypeRepo.save(VehicleTypeResult);

            const VehicleTypeDTOResult = VehicleTypeMap.toDTO(VehicleTypeResult) as IVehicleTypeDTO;
            return Result.ok<IVehicleTypeDTO>(VehicleTypeDTOResult)
        } catch (e) {
            throw e;
        }
    }

    public async findAllVehicleTypes({ skip = 0, limit = 10 }): Promise<Array<IVehicleTypeDTO>> {
        try {
            const recs = await this.VehicleTypeRepo.findAll({ skip, limit });
            var arr = [];
            recs.forEach(elem => {
                arr.push(VehicleTypeMap.toDTO(elem));
            });
            return arr;
        } catch (err) {
            throw err;
        }
    }

    public async getNumberOfVehicleTypes(): Promise<Number> {
        try {
            const numberOfDT = await this.VehicleTypeRepo.getNumberOfDocuments();
            return numberOfDT;
        } catch (err) {
            throw err;
        }
    }

    public async getVehicleTypeByCode(vehicleTypeCode: string): Promise<IVehicleTypeDTO> {
        try {
          const rec = await this.VehicleTypeRepo.findByCode(vehicleTypeCode);
          return VehicleTypeMap.toDTO(rec);
        } catch (err) {
          throw err;
        }
      }

}