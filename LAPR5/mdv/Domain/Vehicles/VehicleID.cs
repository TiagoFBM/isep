using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.Vehicles
{
    public class VehicleID : EntityId
    {

        public VehicleID() : base(null) { }

        [JsonConstructor]
        public VehicleID(Guid value) : base(value)
        {
        }

        public VehicleID(String value) : base(value)
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

    }
}