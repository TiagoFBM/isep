using System.Collections.Generic;
using mdv.Controllers;
using mdv.Domain.Vehicles;
using mdv.Domain.VehicleTypes;
using mdv.DTO.Vehicles;
using mdv.DTO.VehicleTypes;
using mdv.Services;
using Moq;
using Xunit;

namespace Tests.Drivers
{
    public class VehicleControllerTest
    {

        [Fact]
        public async void GetAll()
        {

            // Vehicles

            var vehicleServiceMock = new Mock<IVehicleService>();
            var vehicleTypeServiceMock = new Mock<IVehicleTypeService>();

            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            var vehicle = new Vehicle(registration,vin,entranceDate,vehicleType);



            // DTO

            var vehicleDTO = new VehicleDTO(vehicle.Id.AsGuid(), registration, vin, entranceDate, vehicleType);

            var vehiclesDTO = new List<VehicleDTO>() { vehicleDTO };

            // Mocks

            vehicleServiceMock.Setup(_ => _.GetAll()).ReturnsAsync(vehiclesDTO);


            var controller = new VehicleController(vehicleServiceMock.Object, vehicleTypeServiceMock.Object);


            var actual = await controller.GetAll();

            Assert.Equal(vehiclesDTO, actual.Value);
        }

        [Fact]
        public async void create()
        {
            var vehicleServiceMock = new Mock<IVehicleService>();
            var vehicleTypeServiceMock = new Mock<IVehicleTypeService>();

            string registration = "AA11AA";
            string vin = "123456789AAAAAAAA";
            string entranceDate = "27/01/1978";
            string vehicleType = "vehicleType1";


            var vehicle = new Vehicle(registration,vin,entranceDate,vehicleType);

            var vehicleTypeDTO = new VehicleTypeDTO("vehicleType1", "description", 123, "Gasolina", 30, 12, 15);

            var vehicleDTO = new VehicleDTO(vehicle.Id.AsGuid(), registration, vin, entranceDate, vehicleType);

            vehicleTypeServiceMock.Setup(_ => _.GetVehicleTypeByID("vehicleType1")).ReturnsAsync(vehicleTypeDTO);

            vehicleServiceMock.Setup(_ => _.AddVehicle(vehicleDTO)).ReturnsAsync(vehicleDTO);

            var controller = new VehicleController(vehicleServiceMock.Object, vehicleTypeServiceMock.Object);

            var actual = await controller.Create(vehicleDTO);

            Assert.NotNull(actual);
            Assert.NotNull(actual.Result);

        }


    }
}
