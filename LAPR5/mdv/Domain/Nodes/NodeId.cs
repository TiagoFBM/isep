using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.Nodes {
    public class NodeId {
        public string id { get; set; }

        public NodeId () { }

        [JsonConstructor]

        public NodeId (string id) {
            this.id = id;
        }

        public override string ToString () {
            return id;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                NodeId node = (NodeId) obj;
                return (this.id.Equals (node.id));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (id);
        }
    }
}