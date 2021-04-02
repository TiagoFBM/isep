using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace mdv.Domain.Drivers
{
    public class CreatingCitizenCardDTO
    {

        [JsonProperty("driverName")]
        public string driverName { get; private set; }

        [JsonProperty("birthDate")]
        public string birthDate { get; private set; }

        [JsonProperty("citizenCardNumber")]
        public long citizenCardNumber { get; private set; }

        [JsonProperty("driverNIF")]
        public long driverNIF { get; private set; }

        public CreatingCitizenCardDTO(string driverName, string birthDate, long citizenCardNumber, long driverNIF)
        {
            this.driverName = driverName;
            this.birthDate = birthDate;
            this.citizenCardNumber = citizenCardNumber;
            this.driverNIF = driverNIF;
        }

        // override object.Equals
        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }
            CreatingCitizenCardDTO dto = (CreatingCitizenCardDTO)obj;
            return (this.driverName.Equals(dto.driverName)) &&
            (this.birthDate.Equals(dto.birthDate)) &&
            (this.citizenCardNumber.Equals(dto.citizenCardNumber)) &&
            (this.driverNIF.Equals(dto.driverNIF));
        }

        // override object.GetHashCode
        public override int GetHashCode()
        {
            return HashCode.Combine(driverName, birthDate, citizenCardNumber, driverNIF);
        }
    }
}