using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.DriverDutys
{
    public class DriverDutyId : EntityId
    {

        public DriverDutyId() : base(null) { }

        [JsonConstructor]
        public DriverDutyId(Guid value) : base(value)
        {
        }

        public DriverDutyId(String value) : base(value)
        {
        }

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
                DriverDutyId driverID = (DriverDutyId)obj;
                return this.Value.Equals(driverID.Value);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(base.GetHashCode(), ObjValue, Value);
        }

    }
}