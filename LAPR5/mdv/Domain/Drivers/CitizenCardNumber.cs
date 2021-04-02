using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;

namespace mdv.Domain.Drivers
{
    public class CitizenCardNumber : IValueObject
    {
        public long citizenCardNumber { get; private set; }

        public CitizenCardNumber() { }

        public CitizenCardNumber(long citizenCardNumber)
        {
            if (!NumberValidator.isValidCitizenCardNumber(citizenCardNumber))
            {
                throw new BusinessRuleValidationException(citizenCardNumber + " invalid: Driver Citizen Card Number invalid");
            }

            if (NumberValidator.isNegative(citizenCardNumber))
            {
                throw new BusinessRuleValidationException(citizenCardNumber + " invalid: Driver Citizen Card Number can't be negative");
            }

            this.citizenCardNumber = citizenCardNumber;
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                CitizenCardNumber citizenCardNumber = (CitizenCardNumber)obj;
                return this.citizenCardNumber.Equals(citizenCardNumber.citizenCardNumber);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(citizenCardNumber);
        }
    }
}