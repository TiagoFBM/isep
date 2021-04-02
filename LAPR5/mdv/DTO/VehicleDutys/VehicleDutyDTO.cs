using System;
using System.Collections.Generic;
using mdv.DTO.Trips;
using mdv.DTO.Vehicles;
using mdv.DTO.WorkBlocks;
using Newtonsoft.Json;


namespace mdv.DTO.VehicleDutys {
    public class VehicleDutyDTO {
        [JsonProperty ("vehicleDutyId")]
        public Guid Id { get; set; }

        [JsonProperty ("vehicleDutyCode")]
        public string vehicleDutyCode { get; set; }

        [JsonProperty ("trips")]
        public List<TripDTO> tripList { get; set; }

        [JsonProperty ("workBlocks")]
        public List<WorkBlockDTO> workBlockList { get; set; }

        public VehicleDutyDTO (Guid Id, string vehicleDutyCode, List<WorkBlockDTO> workBlockList) {
            this.Id = Id;
            this.vehicleDutyCode = vehicleDutyCode;
            this.tripList = new List<TripDTO> ();
            this.workBlockList = workBlockList;
        }

        public VehicleDutyDTO (Guid Id, string vehicleDutyCode, List<TripDTO> tripList, List<WorkBlockDTO> workBlockList) {
            this.Id = Id;
            this.vehicleDutyCode = vehicleDutyCode;
            this.tripList = new List<TripDTO> (tripList);
            this.workBlockList = workBlockList;
        }

    }
}