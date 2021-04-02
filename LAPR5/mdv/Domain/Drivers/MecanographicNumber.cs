using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;

namespace mdv.Domain.Drivers
{
    public class MecanographicNumber : IValueObject
    {

        public string mecanographicNumber { get; private set; }

        public MecanographicNumber() { }

        public MecanographicNumber(string mecanographicNumber)
        {
            if (StringValidator.isValidMecanographicNumber(mecanographicNumber))
            {
                throw new BusinessRuleValidationException(mecanographicNumber + "invalid: Mecanographic number can't be negative.");
            }

            this.mecanographicNumber = mecanographicNumber;
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                MecanographicNumber mecanographicNumber = (MecanographicNumber)obj;
                return this.mecanographicNumber.Equals(mecanographicNumber.mecanographicNumber);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(mecanographicNumber);
        }

    }
}