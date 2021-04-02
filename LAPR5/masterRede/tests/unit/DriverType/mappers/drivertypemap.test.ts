import { DriverType } from "../../../../src/domain/driver/DriverType";
import  DriverTypeMap from "../../../../src/mappers/DriverTypeMap";
import IDriverTypeDTO from "../../../../src/dto/IDriverTypeDTO";

describe('Driver Type Mapper Test', () => {

    var assert = require('chai').assert

    it('Convert to DTO', () => {

        let driverType = DriverType.create({code:"Condutor1",description:"description1"});
        let result:IDriverTypeDTO = DriverTypeMap.toDTO(driverType.getValue());
        let expected:IDriverTypeDTO = {
            code: "Condutor1",
            description: "description1"
         }

         assert.isOk(result, expected);
    }),

    it('Convert to Domain', () => {

        let driverDTO:IDriverTypeDTO = {
            code: "Condutor1",
            description: "description1"
        }
        let result:DriverType = DriverTypeMap.toDomain(driverDTO);
        let expected = DriverType.create({code:"Condutor1",description:"description1"});

         assert.isOk(result, expected);
    }),

    it('Convert to Persistence', () => {

        let driverType = DriverType.create({code:"Condutor1",description:"description1"});
        let result:IDriverTypeDTO = DriverTypeMap.toDTO(driverType.getValue());
        let expected = {
            code: "Condutor1",
            description: "description1"
         }

         assert.isOk(result, expected);
    })

});