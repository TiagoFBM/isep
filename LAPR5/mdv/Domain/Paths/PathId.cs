using System;
using mdv.Domain.Shared;
using Newtonsoft.Json;

namespace mdv.Domain.Paths {
    public class PathId {
        public string id { get; set; }

        public PathId () { }

        [JsonConstructor]

        public PathId (string id) {
            this.id = id;
        }

        public override string ToString () {
            return id;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                PathId path = (PathId) obj;
                return (this.id.Equals (path.id));
            }
        }

        public override int GetHashCode () {
            return this.GetHashCode ();
        }

    }
}