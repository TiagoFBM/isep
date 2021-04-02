using System;
using System.Collections.Generic;
using Newtonsoft.Json;

namespace mdv.Domain.Drivers
{
    public class LicenseDTO
    {
        [JsonProperty("driverLicenseId")]
        public Guid Id { get; set; }

        [JsonProperty("numberDriverLicense")]
        public string numberDriverLicense { get; set; }

        [JsonProperty("dateDriverLicense")]
        public string dateDriverLicense { get; set; }

        public LicenseDTO(Guid Id, string numberDriverLicense, string dateDriverLicense)
        {
            this.Id = Id;
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
            LicenseDTO driverLicenseDTO = (LicenseDTO)obj;
            return (this.numberDriverLicense.Equals(driverLicenseDTO.numberDriverLicense)) &&
            (this.dateDriverLicense.Equals(driverLicenseDTO.dateDriverLicense));
        }

        // override object.GetHashCode
        public override int GetHashCode()
        {
            return HashCode.Combine(numberDriverLicense, dateDriverLicense);
        }
    }
}