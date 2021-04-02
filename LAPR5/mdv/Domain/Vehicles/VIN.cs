using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.Vehicles
{
    public class VIN : IValueObject
    {
        public string vin { get; private set; }

        public VIN() { }

        public VIN(string vin)
        {
            if (!StringValidator.isValidVIN(vin))
            {
                throw new BusinessRuleValidationException(vin + "invalid: Vehicle Identification Number does not correspond to the standard format.");
            }

            this.vin = vin;
        }

        public override string ToString () {
            return vin;
        }

    }
}