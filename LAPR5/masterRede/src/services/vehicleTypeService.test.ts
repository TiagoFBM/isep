const sinon = require('sinon');
import Container from 'typedi';
import config from '../../config'
import IVehicleTypeService from './IServices/IVehicleTypeService'
import IVehicleTypeDTO from "../dto/IVehicleTypeDTO";
import { Result } from '../core/logic/Result';
import IVehicleTypeRepo from './IRepos/IVehicleTypeRepo';
import { VehicleType } from "../domain/vehicle/VehicleType"
import { VehicleTypeCode } from "../domain/vehicle/VehicleTypeCode";
import { VehicleTypeDescription } from "../domain/vehicle/VehicleTypeDescription";
import { VehicleTypeAutonomy } from "../domain/vehicle/VehicleTypeAutonomy";
import { VehicleTypeFuelType } from "../domain/vehicle/VehicleTypeFuelType";
import { VehicleTypeCostPerKilometer } from "../domain/vehicle/VehicleTypeCostPerKilometer";
import { VehicleTypeAverageConsuption } from "../domain/vehicle/VehicleTypeAverageConsuption";
import { VehicleTypeAverageSpeed } from "../domain/vehicle/VehicleTypeAverageSpeed";
import VehicleTypeService from './VehicleTypeService';
import VehicleTypeRepo from '../repos/VehicleTypeRepo';

describe('VehicleType service create', () => {

    const codeV = VehicleTypeCode.create('CodeVehicleType11111');
    const descriptionV = VehicleTypeDescription.create("Service Test Description");
    const autonomyV = VehicleTypeAutonomy.create(50000);
    const fuelTypeV = VehicleTypeFuelType.create("Gasolina");
    const costPerKilometerV = VehicleTypeCostPerKilometer.create(10);
    const averageConsuptionV = VehicleTypeAverageConsuption.create(10);
    const averageSpeedV = VehicleTypeAverageSpeed.create(100);

    let vehicleType: Result<VehicleType> = VehicleType.create({
        code: codeV.getValue().value,
        description: descriptionV.getValue().value,
        autonomy: autonomyV.getValue().value,
        fuelType: fuelTypeV.getValue().value,
        costPerKilometer: costPerKilometerV.getValue().value,
        averageConsuption: averageConsuptionV.getValue().value,
        averageSpeed: averageSpeedV.getValue().value

    })

    let res = Result.ok<IVehicleTypeDTO>({
        code: 'CodeVehicleType11111',
        description: 'Service Test Description',
        autonomy: 50000,
        fuelType: 'Gasolina',
        costPerKilometer: 10,
        averageConsuption: 10,
        averageSpeed: 100

    })

    const dto: IVehicleTypeDTO = {
        code: 'CodeVehicleType11111',
        description: 'Service Test Description',
        autonomy: 50000,
        fuelType: 'Gasolina',
        costPerKilometer: 10,
        averageConsuption: 10,
        averageSpeed: 100
    }



    let VehicleTypeRepoClass = require(config.repos.vehicleType.path).default
    let VehicleTypeRepoInstance: IVehicleTypeRepo = Container.get(VehicleTypeRepoClass)
    Container.set(config.repos.vehicleType.name, VehicleTypeRepoInstance)
    VehicleTypeRepoInstance = Container.get(config.repos.vehicleType.name);

    let VehicleTypeServiceInstance = new VehicleTypeService(VehicleTypeRepoInstance);

    beforeEach(() => {
    });

    afterEach(function () {
        sinon.restore();
    });

    it('should create ', async () => {
        sinon.stub(VehicleTypeRepoInstance, "save").returns(vehicleType.getValue())
        sinon.assert.match((await VehicleTypeServiceInstance.createVehicleType(dto)), res);
    })
})