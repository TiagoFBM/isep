using System;
using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.Domain.Vehicles;
using Newtonsoft.Json;


namespace mdv.DTO.WorkBlocks {
    public class PostWorkblockConfigDTO {
        [JsonProperty ("maximumNumber")]
        public int maximumNumber { get; set; }

        [JsonProperty ("workBlockMaxDuration")]
        public int workBlockMaxDuration { get; set; }

        public PostWorkblockConfigDTO (int maximumNumber, int workBlockMaxDuration) {
            this.maximumNumber = maximumNumber;
            this.workBlockMaxDuration = workBlockMaxDuration;
        }

        public PostWorkblockConfigDTO() {}

    }
}