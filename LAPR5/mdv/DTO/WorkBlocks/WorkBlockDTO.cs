using System;
using System.Collections.Generic;
using mdv.DTO.Trips;
using mdv.DTO.Vehicles;
using Newtonsoft.Json;


namespace mdv.DTO.WorkBlocks {
    public class WorkBlockDTO {
        [JsonProperty ("workBlockId")]
        public Guid Id { get; set; }

        [JsonProperty ("startingTime")]
        public string startingTime { get; set; }

        [JsonProperty ("endingTime")]
        public string endingTime { get; set; }

        [JsonProperty ("trips")]
        public List<TripDTO> tripList { get; set; }

        public WorkBlockDTO (Guid Id, string startingTime, string endingTime, List<TripDTO> tripList) {
            this.Id = Id;
            this.startingTime = startingTime;
            this.endingTime = endingTime;
            this.tripList = new List<TripDTO> (tripList);
        }

    }
}