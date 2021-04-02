using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace mdv.Domain.Drivers
{
    public class CreatingLicenseDTO
    {

        [JsonProperty("numberDriverLicense")]
        public string numberDriverLicense { get; set; }

        [JsonProperty("dateDriverLicense")]
        public string dateDriverLicense { get; set; }

        public CreatingLicenseDTO(string numberDriverLicense, string dateDriverLicense)
        {
            this.numberDriverLicense = numberDriverLicense;
            this.dateDriverLicense = dateDriverLicense;
        }

        // override object.Equals
        public override bool Equals(object obj)
        {

            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            CreatingLicenseDTO dto = (CreatingLicenseDTO)obj;
            return (this.numberDriverLicense.Equals(dto.numberDriverLicense)) &&
            (this.dateDriverLicense.Equals(dto.dateDriverLicense));
        }

        // override object.GetHashCode
        public override int GetHashCode()
        {
            return HashCode.Combine(numberDriverLicense, dateDriverLicense);
        }
    }
}