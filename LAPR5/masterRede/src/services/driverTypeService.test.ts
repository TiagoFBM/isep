const sinon = require('sinon');
import Container from 'typedi';
import config from '../../config'
import IDriverTypeService from './IServices/IDriverTypeService'
import IDriverTypeDTO from "../dto/IDriverTypeDTO";
import { Result } from '../core/logic/Result';
import IDriverTypeRepo from './IRepos/IDriverTypeRepo';
import { DriverType } from "../domain/driver/DriverType"
import { DriverTypeCode } from "../domain/driver/DriverTypeCode";
import { DriverTypeDescription } from "../domain/driver/DriverTypeDescription";
import DriverTypeService from './DriverTypeService';

describe('DriverType service create', () => {

    const codeV = DriverTypeCode.create('CodeDriverType1');
    const descriptionV = DriverTypeDescription.create("Service Test Description");

    let driverType: Result<DriverType> = DriverType.create({
        code: codeV.getValue().value,
        description: descriptionV.getValue().value,
    })

    let res = Result.ok<IDriverTypeDTO>({
        code: 'CodeDriverType11111',
        description: 'Service Test Description'
    })

    const dto: IDriverTypeDTO = {
        code: 'CodeDriverType11111',
        description: 'Service Test Description'
    }

    let DriverTypeRepoClass = require(config.repos.driverType.path).default
    let DriverTypeRepoInstance: IDriverTypeRepo = Container.get(DriverTypeRepoClass)
    Container.set(config.repos.driverType.name, DriverTypeRepoInstance)
    DriverTypeRepoInstance = Container.get(config.repos.driverType.name);

    let driverTypeServiceInstance = new DriverTypeService(DriverTypeRepoInstance)



    beforeEach(() => {
    });

    afterEach(function () {
        sinon.restore();
    });


    it('should create ', async () => {
        sinon.stub(DriverTypeRepoInstance, "save").returns(driverType.getValue())
        sinon.assert.match((await driverTypeServiceInstance.createDriverType(dto)), res);
    })
})