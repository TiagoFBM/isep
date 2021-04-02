using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;

namespace mdv.Domain.Drivers
{

    public class DriverLicenseDate : IValueObject
    {

        public string date { get; private set; }

        public DriverLicenseDate() { }

        // Falta uma validação para a data não ser maior do que a data atual
        public DriverLicenseDate(string date)
        {
            if (!StringValidator.isValidDate(date))
            {
                throw new BusinessRuleValidationException(date + "invalid: Driver License Date is not valid");
            }

            this.date = date;

        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                DriverLicenseDate date = (DriverLicenseDate)obj;
                return this.date.Equals(date.date);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(date);
        }

    }

}