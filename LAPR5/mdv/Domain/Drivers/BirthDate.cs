using System.Collections.Generic;
using System;
using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Drivers
{
    public class BirthDate : IValueObject
    {

        public string date { get; private set; }

        public BirthDate() { }

        // Falta uma validação para a data não ser maior do que a data atual
        public BirthDate(string date)
        {
            if (!StringValidator.isValidBirthDate(date))
            {
                throw new BusinessRuleValidationException(date + "invalid: Driver Birth Date is not valid");
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
                BirthDate birthDate = (BirthDate)obj;
                return this.date.Equals(birthDate.date);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(date);
        }

    }

}