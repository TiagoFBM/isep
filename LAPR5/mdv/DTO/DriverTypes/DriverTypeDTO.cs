using Newtonsoft.Json;
using System;

namespace mdv.DTO.DriverTypes
{
    public class DriverTypeDTO
    {
        [JsonProperty("code")]
        public string code { get; set; }

        [JsonProperty("description")]
        public string description { get; set; }

        [JsonConstructor]
        public DriverTypeDTO(string code, string description)
        {
            this.code = code;
            this.description = description;
        }

        // override object.Equals
        public override bool Equals(object obj)
        {

            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }
            DriverTypeDTO driverTypeDTO = (DriverTypeDTO)obj;
            return (this.code.Equals(driverTypeDTO.code)) &&
            (this.description.Equals(driverTypeDTO.description));
        }

        // override object.GetHashCode
        public override int GetHashCode()
        {
            return HashCode.Combine(code, description);
        }
    }
}