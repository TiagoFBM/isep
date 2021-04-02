using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;

namespace mdv.Domain.Drivers
{

    public class DepartureDate : IValueObject
    {

        public string date { get; private set; }

        public DepartureDate() { }

        // Falta uma validação para a data não ser maior do que a data atual
        public DepartureDate(string date)
        {
            if (!StringValidator.isValidDate(date))
            {
                throw new BusinessRuleValidationException(date + "invalid: Driver Departure Date is not valid");
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
                DepartureDate departureDate = (DepartureDate)obj;
                return this.date.Equals(departureDate.date);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(date);
        }

    }

}