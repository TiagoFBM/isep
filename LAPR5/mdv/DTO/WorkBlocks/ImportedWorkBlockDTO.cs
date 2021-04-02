using System;
using System.Collections.Generic;
using mdv.DTO.Trips;
using mdv.DTO.Vehicles;
using mdv.DTO.WorkBlocks;
using Newtonsoft.Json;


namespace mdv.DTO.WorkBlocks {
    public class ImportedWorkBlockDTO {
        [JsonProperty ("key")]
        public string key { get; set; }

        [JsonProperty ("workBlockDTO")]
        public WorkBlockDTO workBlockDTO { get; set; }

        public ImportedWorkBlockDTO (string key, WorkBlockDTO workBlockDTO) {
            this.key = key;
            this.workBlockDTO = workBlockDTO;
        }

    }
}