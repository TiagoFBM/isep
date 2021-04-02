using mdv.DTO.Nodes;
using Newtonsoft.Json;

namespace mdv.DTO.Paths {
    public class SegmentDTO {
        
        [JsonProperty("firstNodeID")]
        public NodeDTO firstNodeID { get; set; }
        
        [JsonProperty("secondNodeID")]
        public NodeDTO secondNodeID { get; set; }
        
        [JsonProperty("travelTimeBetweenNodes")]
        public int travelTimeBetweenNodes { get; set; }
        
        [JsonProperty("distanceBetweenNodes")]
        public double distanceBetweenNodes { get; set; }

        public SegmentDTO (NodeDTO firstNodeID, NodeDTO secondNodeID, int travelTimeBetweenNodes, double distanceBetweenNodes) {
            this.firstNodeID = firstNodeID;
            this.secondNodeID = secondNodeID;
            this.travelTimeBetweenNodes = travelTimeBetweenNodes;
            this.distanceBetweenNodes = distanceBetweenNodes;
        }
    }
}