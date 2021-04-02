using System.Collections.Generic;
using mdv.Domain.Drivers;
using mdv.Domain.DriverTypes;
using mdv.DTO.Drivers;
using mdv.DTO.DriverTypes;

namespace mdv.Mappers
{
    public class DriverMapper
    {

        public DriverDTO DomainToDTO(Driver driver, List<string> listDriverTypes)
        {

            LicenseDTO licenseDTO = new LicenseDTO(driver.driverLicense.Id.AsGuid(), driver.driverLicense.numberDriverLicense.numberDriverLicense, driver.driverLicense.driverLicenseDate.date);

            CitizenCardDTO citizenCardDTO = new CitizenCardDTO(driver.citizenCard.Id.AsGuid(), driver.citizenCard.driverName.driverName, driver.citizenCard.birthDate.date, driver.citizenCard.citizenCardNumber.citizenCardNumber, driver.citizenCard.driverNIF.nif);


            var driverDTO = new DriverDTO(
                driver.Id.AsGuid(),
                driver.mecanographicNumber.mecanographicNumber,
                citizenCardDTO,
                driver.entranceDate.date,
                driver.departureDate.date,
                licenseDTO,
                listDriverTypes
            );

            return driverDTO;
        }

    }
}