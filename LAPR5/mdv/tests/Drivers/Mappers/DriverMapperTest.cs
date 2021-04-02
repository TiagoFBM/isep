using mdv.Domain.Drivers;
using mdv.Domain.DriverTypes;
using mdv.DTO.Drivers;
using mdv.DTO.DriverTypes;
using mdv.Mappers;
using System.Collections.Generic;
using Xunit;

namespace Tests.Drivers
{
    public class DriverMapTest
    {

        [Fact]
        public void DTOtoDomain()
        {
            var mapper = new DriverMapper();

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

            List<string> list = new List<string>();
            list.Add(driverType.id);
            var driver = new Driver(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense, dateDriverLicense, listDriverTypes);

            var expected = new DriverDTO(
                driver.Id.AsGuid(),
                driver.mecanographicNumber.mecanographicNumber,
                new CitizenCardDTO(driver.citizenCard.Id.AsGuid(), driver.citizenCard.driverName.driverName, driver.citizenCard.birthDate.date, driver.citizenCard.citizenCardNumber.citizenCardNumber, driver.citizenCard.driverNIF.nif),
                driver.entranceDate.date,
                driver.departureDate.date,
                new LicenseDTO(driver.driverLicense.Id.AsGuid(), driver.driverLicense.numberDriverLicense.numberDriverLicense, driver.driverLicense.driverLicenseDate.date),
                list);

            var actual = mapper.DomainToDTO(driver, list);

            Assert.Equal(actual, expected);


        }
    }
}