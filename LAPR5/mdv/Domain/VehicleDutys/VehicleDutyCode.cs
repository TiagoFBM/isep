using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.VehicleDutys
{
    public class VehicleDutyCode : IValueObject
    {
        public string vehicleDutyCode { get; private set; }

        public VehicleDutyCode() { }

        public VehicleDutyCode(string vehicleDutyCode)
        {
            if (!StringValidator.isValidAlphanumericString(vehicleDutyCode))
            {
                throw new BusinessRuleValidationException(vehicleDutyCode + "invalid: Vehicle Duty Code does not correspond to the standard format.");
            }

            if (!StringValidator.hasMaximumSize(vehicleDutyCode))
            {
                throw new BusinessRuleValidationException(vehicleDutyCode + "invalid: Vehicle Duty Code exceeds 10 characters.");
            }

            this.vehicleDutyCode = vehicleDutyCode;
        }

        public override string ToString () {
            return vehicleDutyCode;
        }

    }
}