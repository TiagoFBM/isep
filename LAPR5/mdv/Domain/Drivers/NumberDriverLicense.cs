using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;

namespace mdv.Domain.NumberDriverLicense
{

    public class NumberLicense : IValueObject
    {

        public string numberDriverLicense { get; private set; }

        public NumberLicense() { }

        public NumberLicense(string numberDriverLicense)
        {
            if (!StringValidator.isValidLicenseNumber(numberDriverLicense))
            {
                throw new BusinessRuleValidationException(numberDriverLicense + " invalid: Invalid format for the driver license number");
            }
            this.numberDriverLicense = numberDriverLicense;
        }

        public override string ToString()
        {
            return numberDriverLicense.ToString();
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                NumberLicense numberDriverLicense = (NumberLicense)obj;
                return this.numberDriverLicense.Equals(numberDriverLicense.numberDriverLicense);

            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(numberDriverLicense);
        }

    }

}