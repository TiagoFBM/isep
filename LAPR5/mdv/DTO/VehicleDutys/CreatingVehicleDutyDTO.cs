using System;
using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.Domain.Vehicles;
using Newtonsoft.Json;


namespace mdv.DTO.VehicleDutys {
    public class CreatingVehicleDutyDTO {
        [JsonProperty ("vehicleDutyCode")]
        public string vehicleDutyCode { get; set; }

        [JsonProperty ("trips")]
        public List<string> tripList { get; set; }

        public CreatingVehicleDutyDTO (string vehicleDutyCode) {
            this.vehicleDutyCode = vehicleDutyCode;
            this.tripList = new List<string> ();
        }

        public CreatingVehicleDutyDTO (string vehicleDutyCode, List<string> tripList) {
            this.vehicleDutyCode = vehicleDutyCode;
            this.tripList = new List<string> (tripList);
        }

        public CreatingVehicleDutyDTO() {}

    }
}