using System.Collections.Generic;
using mdv.Controllers;
using mdv.Domain.Drivers;
using mdv.Domain.DriverTypes;
using mdv.DTO.Drivers;
using mdv.DTO.DriverTypes;
using mdv.Services;
using Moq;
using Xunit;

namespace Tests.Drivers
{
    public class DriverControllerTest
    {

        [Fact]
        public async void GetAll()
        {

            // Drivers

            var driverServiceMock = new Mock<IDriverService>();
            var driverTypeServiceMock = new Mock<IDriverTypeService>();

            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "27/01/1978";
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>();

            listDriverTypes.Add(driverType);

            var driver = new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes);

            // DTOs

            CitizenCardDTO citizenCardDTO = new CitizenCardDTO(driver.citizenCard.Id.AsGuid(), driverName, birthDate, citizenCardNumber, driverNIF);

            LicenseDTO licenseDTO = new LicenseDTO(driver.driverLicense.Id.AsGuid(), numberDriverLicense, dateDriverLicense);

            DriverTypeDTO driverTypeDTO = new DriverTypeDTO("driverType1", "Teste");

            // List Driver Types DTO -> Full Driver DTO

            List<DriverTypeDTO> driverTypeDTOs = new List<DriverTypeDTO>();
            driverTypeDTOs.Add(driverTypeDTO);

            List<string> driverTypesString = new List<string>() { driverType.id };

            var driverDTO = new DriverDTO(driver.Id.AsGuid(), mecanographicNumber, citizenCardDTO, entranceDate, departureDate, licenseDTO, driverTypesString);

            var listDriverDTOs = new List<DriverDTO>() { driverDTO };

            var fullDriverDTO = new FullDriverDTO(driver.Id.AsGuid(), mecanographicNumber, citizenCardDTO, entranceDate, departureDate, licenseDTO, driverTypeDTOs);

            var driversDTO = new List<FullDriverDTO>() { fullDriverDTO };

            // Mocks

            driverTypeServiceMock.Setup(_ => _.GetDriverTypeByID(driverType.id)).ReturnsAsync(driverTypeDTO);


            driverServiceMock.Setup(_ => _.GetAll()).ReturnsAsync(listDriverDTOs);


            var controller = new DriverController(driverServiceMock.Object, driverTypeServiceMock.Object);


            var actual = await controller.GetAll();

            Assert.Equal(driversDTO, actual.Value);
        }


        [Fact]
        public async void create()
        {
            var driverServiceMock = new Mock<IDriverService>();
            var driverTypeServiceMock = new Mock<IDriverTypeService>();

            string mecanographicNumber = "D-103";
            string driverName = "Driver Teste";
            string birthDate = "12/12/1971";
            long citizenCardNumber = 11144477;
            long driverNIF = 159951159;
            string entranceDate = "27/01/1978";
            string departureDate = "31/05/2020";
            string numberDriverLicense = "P-1111111 1";
            string dateDriverLicense = "12/10/2020";

            var driverType = new DriverTypeId("driverType1");

            List<DriverTypeId> listDriverTypes = new List<DriverTypeId>() { driverType };

            var driver = new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes);

            CitizenCardDTO citizenCardDTO = new CitizenCardDTO(driver.citizenCard.Id.AsGuid(), driverName, birthDate, citizenCardNumber, driverNIF);

            LicenseDTO licenseDTO = new LicenseDTO(driver.driverLicense.Id.AsGuid(), numberDriverLicense, dateDriverLicense);

            List<string> driverTypeDTOs = new List<string>() { driverType.id };


            var driverDTO = new DriverDTO(driver.Id.AsGuid(), mecanographicNumber, citizenCardDTO, entranceDate, departureDate, licenseDTO, driverTypeDTOs);

            var driverTypeDTO = new DriverTypeDTO("driverType1", "Teste");

            var creatingDriverDTO = new CreatingDriverDTO(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, driverTypeDTOs);

            var creatingCitizenCardDTO = new CreatingCitizenCardDTO(driverName, birthDate, citizenCardNumber, driverNIF);

            var creatingLicenseDTO = new CreatingLicenseDTO(numberDriverLicense, dateDriverLicense);

            driverTypeServiceMock.Setup(_ => _.GetDriverTypeByID("driverType1")).ReturnsAsync(driverTypeDTO);

            driverServiceMock.Setup(_ => _.AddDriver(creatingDriverDTO, creatingLicenseDTO, creatingCitizenCardDTO)).ReturnsAsync(driverDTO);

            var controller = new DriverController(driverServiceMock.Object, driverTypeServiceMock.Object);

            var actual = await controller.Create(creatingDriverDTO);

            Assert.NotNull(actual);
            Assert.NotNull(actual.Result);

        }


    }
}
