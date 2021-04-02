using System;
using Newtonsoft.Json;

namespace mdv.DTO.Trips {
    public class CreatingNodePassageDTO {

        [JsonProperty ("nodeId")]
        public string nodeId { get; set; }

        [JsonProperty ("passageTime")]
        public string passageTime { get; set; }
        public CreatingNodePassageDTO (string nodeId, string passageTime) {
            this.nodeId = nodeId;
            this.passageTime = passageTime;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                CreatingNodePassageDTO nodePassageDTO = (CreatingNodePassageDTO) obj;
                return (this.nodeId.Equals (nodePassageDTO.nodeId)) &&
                    (this.passageTime.Equals (nodePassageDTO.passageTime));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (nodeId, passageTime);
        }
    }
}