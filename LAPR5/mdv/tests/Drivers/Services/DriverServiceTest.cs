using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using mdv.Controllers;
using mdv.Domain.Drivers;
using mdv.Domain.DriverTypes;
using mdv.Domain.Shared;
using mdv.DTO.Drivers;
using mdv.DTO.DriverTypes;
using mdv.Services;
using mdv.Repository.Drivers;
using Moq;
using Xunit;
using System;



namespace Tests.Drivers
{

    public class DriverServiceTest
    {

        [Fact]
        public async void GetAllTest()
        {
            var repo = new Mock<IDriverRepository>();
            var uow = new Mock<IUnitOfWork>();

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

            CitizenCardDTO citizenCardDTO = new CitizenCardDTO(driver.citizenCard.Id.AsGuid(), driverName, birthDate, citizenCardNumber, driverNIF);

            LicenseDTO licenseDTO = new LicenseDTO(driver.driverLicense.Id.AsGuid(), numberDriverLicense, dateDriverLicense);

            List<string> driverTypeDTOs = new List<string>() { driverType.id };

            var driverDTO = new DriverDTO(driver.Id.AsGuid(), mecanographicNumber, citizenCardDTO, entranceDate, departureDate, licenseDTO, driverTypeDTOs);

            var driversDTO = new List<DriverDTO>() { driverDTO };

            var drivers = new List<Driver>() { driver };

            repo.Setup(_ => _.GetAllAsync()).ReturnsAsync(drivers);

            var driverService = new DriverService(repo.Object, uow.Object);

            var actual = await driverService.GetAll();

            Assert.Equal(driversDTO, actual);

        }

        [Fact]
        public async void GetByIdTest()
        {
            var repo = new Mock<IDriverRepository>();
            var uow = new Mock<IUnitOfWork>();

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

            CitizenCardDTO citizenCardDTO = new CitizenCardDTO(driver.citizenCard.Id.AsGuid(), driverName, birthDate, citizenCardNumber, driverNIF);

            LicenseDTO licenseDTO = new LicenseDTO(driver.driverLicense.Id.AsGuid(), numberDriverLicense, dateDriverLicense);

            List<string> driverTypeDTOs = new List<string>() { driverType.id };

            var driverDTO = new DriverDTO(driver.Id.AsGuid(), mecanographicNumber, citizenCardDTO, entranceDate, departureDate, licenseDTO, driverTypeDTOs);

            repo.Setup(_ => _.GetByIdAsync(driver.Id)).ReturnsAsync(driver);

            var driverService = new DriverService(repo.Object, uow.Object);

            var actual = await driverService.GetById(driver.Id);

            Assert.Equal(driverDTO, actual);
        }

        [Fact]
        public async void AddDriver()
        {
            var repo = new Mock<IDriverRepository>();
            var uow = new Mock<IUnitOfWork>();

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

            CitizenCardDTO citizenCardDTO = new CitizenCardDTO(driver.citizenCard.Id.AsGuid(), driverName, birthDate, citizenCardNumber, driverNIF);

            LicenseDTO licenseDTO = new LicenseDTO(driver.driverLicense.Id.AsGuid(), numberDriverLicense, dateDriverLicense);

            List<string> driverTypeDTOs = new List<string>() { driverType.id };

            var driverDTO = new DriverDTO(driver.Id.AsGuid(), mecanographicNumber, citizenCardDTO, entranceDate, departureDate, licenseDTO, driverTypeDTOs);

            var creatingDriverDTO = new CreatingDriverDTO(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, driverTypeDTOs);

            var creatingCitizenCardDTO = new CreatingCitizenCardDTO(driverName, birthDate, citizenCardNumber, driverNIF);

            var creatingLicenseDTO = new CreatingLicenseDTO(numberDriverLicense, dateDriverLicense);

            repo.Setup(_ => _.AddAsync(driver)).ReturnsAsync(driver);

            var service = new DriverService(repo.Object, uow.Object);

            var actual = await service.AddDriver(creatingDriverDTO, creatingLicenseDTO, creatingCitizenCardDTO);

            Assert.Equal(driverDTO, actual);
        }

    }


}