using System.Collections.Generic;
using mdv.Domain.Vehicles;
using mdv.Domain.Shared;
using mdv.DTO.Vehicles;
using mdv.Services;
using mdv.Repository.Vehicles;
using Moq;
using Xunit;



namespace Tests.Vehicles
{

    public class VehicleServiceTest
    {

        [Fact]
        public async void GetAllTest()
        {
            var repo = new Mock<IVehicleRepository>();
            var uow = new Mock<IUnitOfWork>();

            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            var vehicle = new Vehicle(registration,vin,entranceDate,vehicleType);

            var vehicleDTO = new VehicleDTO(vehicle.Id.AsGuid(), registration, vin, entranceDate, vehicleType);

            var vehiclesDTO = new List<VehicleDTO>() { vehicleDTO };

            var vehicles = new List<Vehicle>() { vehicle };

            repo.Setup(_ => _.GetAllAsync()).ReturnsAsync(vehicles);

            var vehicleService = new VehicleService(repo.Object, uow.Object);

            var actual = await vehicleService.GetAll();

            Assert.Equal(vehiclesDTO, actual);

        }

        [Fact]
        public async void GetByIdTest()
        {
            var repo = new Mock<IVehicleRepository>();
            var uow = new Mock<IUnitOfWork>();

            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            var vehicle = new Vehicle(registration,vin,entranceDate,vehicleType);

            var vehicleDTO = new VehicleDTO(vehicle.Id.AsGuid(), registration, vin, entranceDate, vehicleType);

            repo.Setup(_ => _.GetByIdAsync(vehicle.Id)).ReturnsAsync(vehicle);

            var vehicleService = new VehicleService(repo.Object, uow.Object);

            var actual = await vehicleService.GetById(vehicle.Id);

            Assert.Equal(vehicleDTO, actual);
        }

        [Fact]
        public async void AddVehicle()
        {
            var repo = new Mock<IVehicleRepository>();
            var uow = new Mock<IUnitOfWork>();

            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            var vehicle = new Vehicle(registration,vin,entranceDate,vehicleType);

            var vehicleDTO = new VehicleDTO(vehicle.Id.AsGuid(), registration, vin, entranceDate, vehicleType);

            repo.Setup(_ => _.AddAsync(vehicle)).ReturnsAsync(vehicle);

            var service = new VehicleService(repo.Object, uow.Object);

            var actual = await service.AddVehicle(vehicleDTO);

            Assert.Equal(vehicleDTO, actual);
        }

    }


}