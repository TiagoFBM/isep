using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.WorkBlocks {
    public class WorkBlockID : EntityId {

        public WorkBlockID () : base (null) { }

        [JsonConstructor]
        public WorkBlockID (Guid value) : base (value) { }

        public WorkBlockID (String value) : base (value) { }

        override
        protected Object createFromString (String text) {
            return new Guid (text);
        }

        override
        public String AsString () {
            Guid obj = (Guid) base.ObjValue;
            return obj.ToString ();
        }
        public Guid AsGuid () {
            return (Guid) base.ObjValue;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                WorkBlockID trip = (WorkBlockID) obj;
                return (base.Value.Equals (trip.Value));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (base.GetHashCode (), ObjValue, Value);
        }
    }
}