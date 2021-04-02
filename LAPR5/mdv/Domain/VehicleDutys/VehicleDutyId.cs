using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.VehicleDutys {
    public class VehicleDutyId : EntityId{

        public VehicleDutyId () : base (null) { }
        
        [JsonConstructor]
        public VehicleDutyId(Guid value) : base(value){
        }

        public VehicleDutyId(String value) : base(value){
        }

        override
        protected  Object createFromString(String text){
            return new Guid(text);
        }
        
        override
        public String AsString(){
            Guid obj = (Guid) base.ObjValue;
            return obj.ToString();
        }
        public Guid AsGuid(){
            return (Guid) base.ObjValue;
        }
    }
}