using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.Drivers
{
    public class DriverLicenseId : EntityId
    {

        public DriverLicenseId() : base(null) { }

        [JsonConstructor]
        public DriverLicenseId(Guid value) : base(value) { }

        public DriverLicenseId(String value) : base(value) { }

        override
        protected Object createFromString(String text)
        {
            return new Guid(text);
        }

        override
        public String AsString()
        {
            Guid obj = (Guid)base.ObjValue;
            return obj.ToString();
        }
        public Guid AsGuid()
        {
            return (Guid)base.ObjValue;
        }

        public override bool Equals(object obj)
        {
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                DriverLicenseId driverLicenseId = (DriverLicenseId)obj;
                return this.Value.Equals(driverLicenseId.Value);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(base.GetHashCode(), ObjValue, Value);
        }
    }
}