using System.Collections.Generic;
using Newtonsoft.Json;

namespace mdv.DTO.Nodes {
    public class NodeDTO {
        [JsonProperty("code")]
        public string code { get; set; }
        [JsonProperty("name")]
        public string name { get; set; }
        [JsonProperty("latitude")]
        public string latitude { get; set; }
        [JsonProperty("longitude")]
        public string longitude { get; set; }
        [JsonProperty("shortName")]
        public string shortName { get; set; }
        [JsonProperty("isDepot")]
        public bool isDepot { get; set; }
        [JsonProperty("isReliefPoint")]
        public bool isReliefPoint { get; set; }
        [JsonProperty("crewTravelTimes")]
        public List<CrewTravelTimeDTO> crewTravelTimes { get; set; }

        [JsonConstructor]
        public NodeDTO (string code, string name, string latitude, string longitude, string shortName, bool isDepot, bool isReliefPoint) {
            this.code = code;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.shortName = shortName;
            this.isDepot = isDepot;
            this.isReliefPoint = isReliefPoint;
            this.crewTravelTimes = new List<CrewTravelTimeDTO>();
        }

        public NodeDTO (string code, string name, string latitude, string longitude, string shortName, bool isDepot, bool isReliefPoint, List<CrewTravelTimeDTO> crewTravelTimes) {
            this.code = code;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.shortName = shortName;
            this.isDepot = isDepot;
            this.isReliefPoint = isReliefPoint;
            this.crewTravelTimes = new List<CrewTravelTimeDTO>(crewTravelTimes);
        }

    }
}