using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.Trips {
    public class NodePassageId : EntityId {

        public NodePassageId () : base (null) { }

        [JsonConstructor]
        public NodePassageId (Guid value) : base (value) { }

        public NodePassageId (String value) : base (value) { }

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
                NodePassageId nodePassage = (NodePassageId) obj;
                return (this.Value.Equals (nodePassage.Value));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (base.GetHashCode (), ObjValue, Value);
        }
    }
}