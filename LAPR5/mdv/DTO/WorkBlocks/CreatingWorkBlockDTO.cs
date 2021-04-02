using System;
using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.Domain.Vehicles;
using Newtonsoft.Json;


namespace mdv.DTO.WorkBlocks {
    public class CreatingWorkBlockDTO {
        [JsonProperty ("trips")]
        public List<string> tripList { get; set; }

        [JsonProperty ("startingTime")]
        public string startingTime { get; set; }
        
        [JsonProperty ("endingTime")]
        public string endingTime { get; set; }

        public CreatingWorkBlockDTO (List<string> tripList, string startingTime, string endingTime) {
            this.tripList = new List<string> (tripList);
            this.startingTime = startingTime;
            this.endingTime = endingTime;
        }

        public CreatingWorkBlockDTO() {}

    }
}