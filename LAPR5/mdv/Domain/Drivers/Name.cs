using mdv.Domain.Shared;
using mdv.Domain.Validators;
using System;

namespace mdv.Domain.Drivers
{
    public class Name : IValueObject
    {
        public string driverName { get; private set; }

        public Name() { }

        public Name(string driverName)
        {
            if (!StringValidator.hasMinimumSize(driverName))
            {
                throw new BusinessRuleValidationException(driverName + "invalid: Driver Name cant have less than 3 characters");
            }

            this.driverName = driverName;
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                Name driverName = (Name)obj;
                return this.driverName.Equals(driverName.driverName);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(driverName);
        }

    }
}