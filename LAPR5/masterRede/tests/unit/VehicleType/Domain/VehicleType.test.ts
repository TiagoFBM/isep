import { expect } from 'chai';
import { Result } from "../../../../src/core/logic/Result";
import { VehicleType } from "../../../../src/domain/vehicle/VehicleType";

describe('Vehicle Type Test', () => {

    it('Can create VehicleType with code, description, autonomy, fuel type, cost per kilometer, average consuption and average speed.', () => {
        let expected = Result.ok<VehicleType>();
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculo1", description: "description1", autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null DTO)', () => {
        let expected = Result.fail<VehicleType>("vehicleTypeDTO cant be null.");
        let result = VehicleType.create(null);

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null code)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO code is invalid");
        let result = VehicleType.create({ code: null, description: "description1", autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });
        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null description)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO description is invalid");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: null, autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null autonomy)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO autonomy is invalid");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: null, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null fuel type)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO fuel type is invalid");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: null, costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null cost per kilometer)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO cost per kilometer is invalid");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: null, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null average consuption)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO average consuption is invalid");
        let result = VehicleType.create({ code: "code", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: null, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with null average speed)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO average speed is invalid");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: 8, averageSpeed: null });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with invalid code (RegEx))', () => {
        let expected = Result.fail<VehicleType>("code isn't a 20 character alphanumeric string.");
        let result = VehicleType.create({ code: "!Cod#1", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: 8, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });


    it('Cant create VehicleType with invalid code (Size))', () => {
        let expected = Result.fail<VehicleType>("code isn't a 20 character alphanumeric string.");
        let result = VehicleType.create({ code: "VehicleTypeCodeToFailTheSizeTest", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: 8, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with empty code)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO code is invalid");
        let result = VehicleType.create({ code: "", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: 8, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with code above max size limit)', () => {
        let expected = Result.fail<VehicleType>("code isn't a 20 character alphanumeric string.");
        let result = VehicleType.create({ code: "VehicleTypeCodeToFailTheSizeTest", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: 8, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with empty description)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO description is invalid");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "", autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with description above the max size)', () => {

        let randomDesc: string = "KDQBJjulkt5pkn8jNzKw1y1hcW4OpdFIZ6iVmfzzH1HruQYfp6ZS4GccOVjje6NIPvJDhhYGPg4kjNnveVfkHp6ygkUbzpaR3Ke9ul1oZ4rAnxOoyYIut7kCcHd2WHT5tN3Y8dCLpOTcWV5naQc2xe8hv9jhDH8rrJmAIbHmRhrGMflqOnO3UGqY6zxJRG8VcW0bS3NtmsM72WFZTlB61bRWlrKMy59ovV65QUlu1oXs5nUwLFq88B4qWiQbquXkCKgo";

        let expected = Result.fail<VehicleType>("description is above the max size limit.");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: randomDesc, autonomy: 10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with negative autonomy)', () => {

        let expected = Result.fail<VehicleType>("autonomy can not be negative.");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: -10, fuelType: "Gasolina", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with empty fuel type)', () => {
        let expected = Result.fail<VehicleType>("VehicleTypeDTO fuel type is invalid");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with fuel type that doesn´t exist in database)', () => {
        let expected = Result.fail<VehicleType>("Fuel Type doesnt exists in DataBase");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Teste", costPerKilometer: 5, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with negative cost per kilometer)', () => {
        let expected = Result.fail<VehicleType>("Cost per Kilometer can not be negative.");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: -10, averageConsuption: 7, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with negative average consuption)', () => {
        let expected = Result.fail<VehicleType>("Average Consuption can not be negative.");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: -10, averageSpeed: 100 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

    it('Cant create VehicleType with negative average speed)', () => {
        let expected = Result.fail<VehicleType>("Average Speed can not be negative.");
        let result = VehicleType.create({ code: "CodigoTipoDeVeiculoT", description: "description1", autonomy: 10, fuelType: "Diesel", costPerKilometer: 10, averageConsuption: 8, averageSpeed: -10 });

        expect(expected.isFailure).to.equal(result.isFailure);
        expect(expected.isSuccess).to.equal(result.isSuccess);
        expect(expected.error).to.equal(result.error);
    });

});