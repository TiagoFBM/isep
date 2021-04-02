using System;
using Newtonsoft.Json;

namespace mdv.Domain.Lines {
    public class LineId {
        public string id { get; set; }

        public LineId () { }

        [JsonConstructor]

        public LineId (string id) {
            this.id = id;
        }

        public override string ToString () {
            return id;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                LineId line = (LineId) obj;
                return (this.id.Equals (line.id));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (id);
        }
    }
}