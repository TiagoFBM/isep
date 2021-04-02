using System.Collections.Generic;
using Newtonsoft.Json;

namespace mdv.DTO.Paths {
    public class PathDTO {

        [JsonProperty("code")]
        public string code { get; set; }

        [JsonProperty("isEmpty")]
        public bool isEmpty { get; set; }
        
        [JsonProperty("segments")]
        public List<SegmentDTO> segments { get; set; }

        public PathDTO (string code, bool isEmpty, List<SegmentDTO> segments) {
            this.code = code;
            this.isEmpty = isEmpty;
            this.segments = new List<SegmentDTO> (segments);
        }
    }
}