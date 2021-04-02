using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace mdv.Domain.Drivers
{
    public class CitizenCardDTO
    {
        [JsonProperty("citizenCardId")]
        public Guid Id { get; set; }

        [JsonProperty("driverName")]
        public string driverName { get; private set; }

        [JsonProperty("birthDate")]
        public string birthDate { get; private set; }

        [JsonProperty("citizenCardNumber")]
        public long citizenCardNumber { get; private set; }

        [JsonProperty("driverNIF")]
        public long driverNIF { get; private set; }

        public CitizenCardDTO(Guid Id, string driverName, string birthDate, long citizenCardNumber, long driverNIF)
        {
            this.Id = Id;
            this.driverName = driverName;
            this.birthDate = birthDate;
            this.citizenCardNumber = citizenCardNumber;
            this.driverNIF = driverNIF;
        }


        public override bool Equals(object obj)
        {

            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }
            CitizenCardDTO citizenCardDTO = (CitizenCardDTO)obj;
            return (this.driverName.Equals(citizenCardDTO.driverName)) &&
            (this.birthDate.Equals(citizenCardDTO.birthDate)) &&
            (this.citizenCardNumber.Equals(citizenCardDTO.citizenCardNumber)) &&
            (this.driverNIF.Equals(citizenCardDTO.driverNIF));
        }

        // override object.GetHashCode
        public override int GetHashCode()
        {
            return HashCode.Combine(driverName, birthDate, citizenCardNumber, driverNIF);
        }

    }
}