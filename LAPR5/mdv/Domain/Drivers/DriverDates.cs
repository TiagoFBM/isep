using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Drivers
{

    public class DriverDates : IValueObject
    {

        public string date { get; private set; }

        public DriverDates() { }

        // Falta uma validação para a data não ser maior do que a data atual
        public DriverDates(string date)
        {
            if (!StringValidator.isValidBirthDate(date))
            {
                throw new BusinessRuleValidationException(date + "invalid: Driver Birth Date is not valid");
            }

            this.date = date;

        }

    }

}