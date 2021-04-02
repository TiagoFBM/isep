using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using mdv.DTO.VehicleTypes;
using mdv.Domain.VehicleTypes;

namespace mdv.DTO.Vehicles
{
    public class VehicleDTO
    {
        [JsonProperty("vehicleID")]
        public Guid Id { get; private set; }

        [JsonProperty("registration")]
        public string registration { get; set; }

        [JsonProperty("vin")]
        public string vin { get; set; }

        [JsonProperty("entranceDate")]
        public string entranceDate { get; set; }

        [JsonProperty("vehicleTypes")]
        public string vehicleType { get; set; }

        public VehicleDTO(Guid Id, string registration, string vin, string entranceDate, string vehicleType)
        {
            this.Id = Id;
            this.registration = registration;
            this.vin = vin;
            this.entranceDate = entranceDate;
            this.vehicleType = vehicleType;
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            VehicleDTO vehicleDTO = (VehicleDTO)obj;
            return (this.registration.Equals(vehicleDTO.registration)) &&
            (this.vin.Equals(vehicleDTO.vin)) &&
            (this.entranceDate.Equals(vehicleDTO.entranceDate)) &&
            (this.vehicleType.Equals(vehicleDTO.vehicleType));


        }

        public override int GetHashCode()
        {
            return HashCode.Combine(registration,vin,entranceDate,vehicleType);
        }
    }
}