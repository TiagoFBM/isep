using System;
using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.Domain.Vehicles;
using Newtonsoft.Json;


namespace mdv.DTO.WorkBlocks {
    public class PostWorkBlockDTO {
        [JsonProperty ("vehicleDutyId")]
        public string vehicleDutyId { get; set; }
        
        [JsonProperty ("workblockConfigs")]
        public List<PostWorkblockConfigDTO> workblockConfigList { get; set; }


        public PostWorkBlockDTO (string vehicleDutyId, List<PostWorkblockConfigDTO> workblockConfigList) {
            this.vehicleDutyId = vehicleDutyId;
            this.workblockConfigList = new List<PostWorkblockConfigDTO>(workblockConfigList);
        }

        public PostWorkBlockDTO() {}

    }
}