using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.Drivers
{
    public class CitizenCardId : EntityId
    {

        public CitizenCardId() : base(null) { }

        [JsonConstructor]
        public CitizenCardId(Guid value) : base(value) { }

        public CitizenCardId(String value) : base(value) { }

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
                CitizenCardId citizenCardId = (CitizenCardId)obj;
                return this.Value.Equals(citizenCardId.Value);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(base.GetHashCode(), ObjValue, Value);
        }
    }
}