using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Vehicles
{
    public class Registration : IValueObject
    {
        public string vehicleRegistration { get; private set; }

        public Registration() { }

        public Registration(string vehicleRegistration)
        {
            if (!StringValidator.isValidRegistration(vehicleRegistration))
            {
                throw new BusinessRuleValidationException(vehicleRegistration + "invalid: Vehicle Registration does not correspond to the standard format.");
            }

            this.vehicleRegistration = vehicleRegistration;
        }

        public override string ToString () {
            return vehicleRegistration;
        }

    }
}