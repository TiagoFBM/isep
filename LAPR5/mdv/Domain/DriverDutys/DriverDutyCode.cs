using System;
using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Validators;

namespace mdv.Domain.DriverDutys
{

    public class DriverDutyCode : IValueObject
    {
        public string driverDutyCode { get; private set; }

        public DriverDutyCode()
        {

        }

        public DriverDutyCode(string driverDutyCode)
        {
            if (!StringValidator.isValidAlphanumericString(driverDutyCode))
            {
                throw new BusinessRuleValidationException(driverDutyCode + " invalid: The code of the driver duty must be alphanumeric");
            }

            if (!StringValidator.hasMaximumSize(driverDutyCode))
            {
                throw new BusinessRuleValidationException(driverDutyCode + " invalid: The code of the driver duty exceeds 10 characters");
            }

            this.driverDutyCode = driverDutyCode;
        }

        public override string ToString()
        {
            return driverDutyCode;
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                DriverDutyCode driverDutyCode = (DriverDutyCode)obj;
                return this.driverDutyCode.Equals(driverDutyCode.driverDutyCode);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(driverDutyCode);
        }
    }

}