import { Service, Inject } from 'typedi';

import IVehicleTypeRepo from "../services/IRepos/IVehicleTypeRepo";
import { VehicleType } from "../domain/vehicle/VehicleType";
import { VehicleTypeCode } from "../domain/vehicle/VehicleTypeCode";
import VehicleTypeMap from "../mappers/VehicleTypeMap";

import { Document, Model } from 'mongoose';
import { IVehicleTypePersistence } from '../dataschema/IVehicleTypePersistence';

@Service()
export default class VehicleTypeRepo implements IVehicleTypeRepo {
    private models: any;

    constructor(
        @Inject('VehicleTypeSchema') private VehicleTypeSchema: Model<IVehicleTypePersistence & Document>,
    ) { }

    private createBaseQuery(): any {
        return {
            where: {},
        }
    }

    public async exists(VehicleType: VehicleType): Promise<boolean> {

        const codeX = VehicleType.props.code.value;

        const query = { code: codeX };
        const vehicleTypeFound = await this.VehicleTypeSchema.findOne(query);

        return !!vehicleTypeFound === true;
    }


    public async save(vehicleType: VehicleType): Promise<VehicleType> {
        const query = { code: vehicleType.id.toString() };

        const VehicleTypeDocument = await this.VehicleTypeSchema.findOne(query);

        try {
            if (VehicleTypeDocument === null) {
                const rawVehicleType: any = VehicleTypeMap.toPersistence(vehicleType);

                const VehicleTypeCreated = await this.VehicleTypeSchema.create(rawVehicleType);

                return VehicleTypeMap.toDomain(VehicleTypeCreated);
            } else {
                VehicleTypeDocument.description = vehicleType.props.description.value;
                VehicleTypeDocument.autonomy = vehicleType.props.autonomy.value;
                VehicleTypeDocument.fuelType = vehicleType.props.fuelType.props.value;
                VehicleTypeDocument.costPerKilometer = vehicleType.props.costPerKilometer.value;
                VehicleTypeDocument.averageConsuption = vehicleType.props.averageConsuption.value;
                VehicleTypeDocument.averageSpeed = vehicleType.props.averageSpeed.value;

                await VehicleTypeDocument.save();

                return vehicleType;
            }
        } catch (err) {
            throw err;
        }
    }

    public async findAll({ skip = 0, limit = 10 }: any): Promise<Array<VehicleType>> {
        const VehicleTypeRecords = await this.VehicleTypeSchema.find({}).skip(parseInt(skip)).limit(parseInt(limit));
        var arr = [];
        VehicleTypeRecords.forEach(elem => {
            arr.push(VehicleTypeMap.toDomain(elem));
        });
        return arr;
    }

    public async findByCode(vehicleTypeCode: VehicleTypeCode | string): Promise<VehicleType> {
        const query = { code: vehicleTypeCode.toString() };
        const VehicleTypeRecord = await this.VehicleTypeSchema.findOne(query);

        if (VehicleTypeRecord != null) {
            return VehicleTypeMap.toDomain(VehicleTypeRecord);
        }
        else
            return null;
    }

    public async findByCodeDB(Code: VehicleTypeCode | string): Promise<Document & IVehicleTypePersistence> {
        const query = { code: Code.toString() };
        const vehicleTypeRecord = await this.VehicleTypeSchema.findOne(query);
        if (vehicleTypeRecord != null) {
            return vehicleTypeRecord;
        } else {
            return null;
        }
    }

    public async getNumberOfDocuments(): Promise<Number> {
        const numberOfDocuments = await this.VehicleTypeSchema.count({});

        return numberOfDocuments;
    }

}