using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;

namespace mdv.Domain.Drivers
{

    public class EntranceDate : IValueObject
    {

        public string date { get; private set; }

        public EntranceDate() { }

        // Falta uma validação para a data não ser maior do que a data atual
        public EntranceDate(string date)
        {
            if (!StringValidator.isValidEntranceDate(date))
            {
                throw new BusinessRuleValidationException(date + " invalid: Driver Entrance Date is not valid");
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
                EntranceDate entranceDate = (EntranceDate)obj;
                return this.date.Equals(entranceDate.date);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(date);
        }

    }

}