using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;


namespace mdv.Domain.Drivers
{
    public class DriverNIF : IValueObject
    {
        public long nif { get; private set; }

        public DriverNIF() { }

        public DriverNIF(long nif)
        {
            if (!NumberValidator.isValidNIF(nif))
            {
                throw new BusinessRuleValidationException(nif + " invalid: Driver NIF is invalid");
            }

            if (NumberValidator.isNegative(nif))
            {
                throw new BusinessRuleValidationException(nif + " invalid: Driver NIF can't be negative");
            }

            this.nif = nif;
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                DriverNIF driverNIF = (DriverNIF)obj;
                return this.nif.Equals(driverNIF.nif);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(nif);
        }
    }
}