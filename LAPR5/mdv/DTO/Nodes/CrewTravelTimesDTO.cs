using Newtonsoft.Json;

namespace mdv.DTO.Nodes {
    public class CrewTravelTimeDTO {
        [JsonProperty("code")]
        public string code { get; set; }
        [JsonProperty("duration")]
        public int duration { get; set; }

        public CrewTravelTimeDTO (string code, int duration) {
            this.code = code;
            this.duration = duration;
        }

    }
}