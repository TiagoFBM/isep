using System;
using mdv.Domain.Shared;
using mdv.Domain.Validators;
using mdv.Domain.NumberDriverLicense;

namespace mdv.Domain.Drivers
{

    public class DriverLicense : Entity<DriverLicenseId>
    {
        public NumberLicense numberDriverLicense;

        public DriverLicenseDate driverLicenseDate;

        public DriverLicense() { }

        public DriverLicense(string numberDriverLicense, string driverLicenseDate)
        {
            if (StringValidator.isStringEmptyOrNull(numberDriverLicense))
            {
                throw new BusinessRuleValidationException(numberDriverLicense + " :invalid. The number of the license driver can't be null or empty");
            }

            if (StringValidator.isStringEmptyOrNull(driverLicenseDate))
            {
                throw new BusinessRuleValidationException(driverLicenseDate + " :invalid. The date of the license driver can't be null or empty");
            }

            this.Id = new DriverLicenseId(Guid.NewGuid());
            this.driverLicenseDate = new DriverLicenseDate(driverLicenseDate);
            this.numberDriverLicense = new NumberLicense(numberDriverLicense);

        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                DriverLicense driverLicense = (DriverLicense)obj;
                return (this.numberDriverLicense.Equals(driverLicense.numberDriverLicense)) &&
                (this.driverLicenseDate.Equals(driverLicense.driverLicenseDate));
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(numberDriverLicense, driverLicenseDate);
        }
    }
}