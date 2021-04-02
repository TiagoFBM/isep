using System;
using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Validators;
using mdv.Domain.DriverTypes;

namespace mdv.Domain.Drivers
{

    public class Driver : Entity<DriverID>, IAggregateRoot
    {
        public MecanographicNumber mecanographicNumber { get; private set; }

        public CitizenCard citizenCard { get; private set; }
        public EntranceDate entranceDate { get; private set; }

        public DepartureDate departureDate { get; private set; }

        public DriverLicense driverLicense { get; private set; }

        public List<DriverTypeId> driverTypes { get; private set; }

        public Driver() { }

        public Driver(string mecanographicNumber, string driverName, string birthDate, long citizenCardNumber, long citizenCardNIF, string entranceDate, string departureDate, string driverLicenseNumber, string driverLicenseDate, List<DriverTypeId> driverTypes)
        {
            if (StringValidator.isStringEmptyOrNull(mecanographicNumber))
            {
                throw new BusinessRuleValidationException(mecanographicNumber + " invalid: Mecanographic number can't be null or empty.");
            }

            if (driverTypes == null || driverTypes.Count == 0)
            {
                throw new BusinessRuleValidationException(driverLicense + " invalid: You need to add at least onde driver type");

            }

            if (StringValidator.isStringEmptyOrNull(entranceDate))
            {
                throw new BusinessRuleValidationException(entranceDate + " invalid: Entrance Date: can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull(departureDate))
            {
                throw new BusinessRuleValidationException(departureDate + " invalid: Departure Date: can't be null or empty.");
            }

            this.Id = new DriverID(Guid.NewGuid());
            this.mecanographicNumber = new MecanographicNumber(mecanographicNumber);
            this.citizenCard = new CitizenCard(driverName, birthDate, citizenCardNumber, citizenCardNIF);
            this.entranceDate = new EntranceDate(entranceDate);
            this.departureDate = new DepartureDate(departureDate);
            this.driverTypes = new List<DriverTypeId>(driverTypes);
            this.driverLicense = new DriverLicense(driverLicenseNumber, driverLicenseDate);
        }


        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                Driver driver = (Driver)obj;
                return (this.mecanographicNumber.Equals(driver.mecanographicNumber)) &&
                (this.citizenCard.Equals(driver.citizenCard)) &&
                (this.entranceDate.Equals(driver.entranceDate)) &&
                (this.departureDate.Equals(driver.departureDate)) &&
                (this.driverLicense.Equals(driver.driverLicense));
            }

        }

        public override int GetHashCode()
        {
            return HashCode.Combine(mecanographicNumber, citizenCard, entranceDate, departureDate, driverLicense, driverTypes);
        }

    }

}