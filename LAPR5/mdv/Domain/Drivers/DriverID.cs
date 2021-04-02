using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.Drivers
{
    public class DriverID : EntityId
    {

        public DriverID() : base(null) { }

        [JsonConstructor]
        public DriverID(Guid value) : base(value)
        {
        }

        public DriverID(String value) : base(value)
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
                DriverID driverID = (DriverID)obj;
                return this.Value.Equals(driverID.Value);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(base.GetHashCode(), ObjValue, Value);
        }

    }
}