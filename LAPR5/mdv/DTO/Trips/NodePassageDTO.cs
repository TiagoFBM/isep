using System;
using Newtonsoft.Json;

namespace mdv.DTO.Trips {
    public class NodePassageDTO {
        
        [JsonProperty("nodePassageId")]
        public Guid Id { get; set; }
        
        [JsonProperty("nodeId")]
        public string nodeId { get; set; }
        
        [JsonProperty("passageTime")]
        public string passageTime { get; set; }

        public NodePassageDTO (Guid Id, string nodeId, string passageTime) {
            this.Id = Id;
            this.nodeId = nodeId;
            this.passageTime = passageTime;
        }
    }
}