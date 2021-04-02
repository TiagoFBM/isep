import { VehicleType } from "../../../../src/domain/vehicle/VehicleType";
import VehicleTypeMap from "../../../../src/mappers/VehicleTypeMap";
import IVehicleTypeDTO from "../../../../src/dto/IVehicleTypeDTO";

describe('Vehicle Type Mapper Test', () => {

    var assert = require('chai').assert

    it('Convert to DTO', () => {

        let vehicleType = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });
        let result: IVehicleTypeDTO = VehicleTypeMap.toDTO(vehicleType.getValue());
        let expected: IVehicleTypeDTO = {
            code: "CodigoTipoDeVeiculoT",
            description: "description1",
            autonomy: 10,
            fuelType: "Gasolina",
            costPerKilometer: 5,
            averageConsuption: 7,
            averageSpeed: 100
        }

        assert.isOk(result, expected);
    }),

        it('Convert to Domain', () => {

            let vehicleDTO: IVehicleTypeDTO = {
                code: "CodigoTipoDeVeiculoT",
                description: "description1",
                autonomy: 10,
                fuelType: "Gasolina",
                costPerKilometer: 5,
                averageConsuption: 7,
                averageSpeed: 100
            }
            let result: VehicleType = VehicleTypeMap.toDomain(vehicleDTO);
            let expected = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

            assert.isOk(result, expected);
        }),

        it('Convert to Persistence', () => {

            let vehicleType = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });
            let result: IVehicleTypeDTO = VehicleTypeMap.toDTO(vehicleType.getValue());
            let expected = {
                code: "CodigoTipoDeVeiculoT",
                description: "description1",
                autonomy: 10,
                fuelType: "Gasolina",
                costPerKilometer: 5,
                averageConsuption: 7,
                averageSpeed: 100
            }

            assert.isOk(result, expected);
        })

});