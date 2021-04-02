using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using mdv.Domain.Drivers;
using mdv.DTO.DriverTypes;


namespace mdv.DTO.Drivers
{
    public class FullDriverDTO
    {
        [JsonProperty("driverID")]
        public Guid Id { get; private set; }

        [JsonProperty("mecanographicNumber")]
        public string mecanographicNumber { get; private set; }

        [JsonProperty("citizenCard")]
        public CitizenCardDTO citizenCardDTO { get; set; }

        [JsonProperty("entranceDate")]
        public string entranceDate { get; set; }

        [JsonProperty("departureDate")]
        public string departureDate { get; set; }

        [JsonProperty("driverLicense")]
        public LicenseDTO driverLicense { get; set; }

        [JsonProperty("driverTypes")]
        public List<DriverTypeDTO> driverTypes { get; set; }

        public FullDriverDTO(Guid Id, string mecanographicNumber, string entranceDate, string departureDate, List<DriverTypeDTO> driverTypes)
        {
            this.Id = Id;
            this.mecanographicNumber = mecanographicNumber;
            this.entranceDate = entranceDate;
            this.departureDate = departureDate;
            this.driverTypes = new List<DriverTypeDTO>(driverTypes);
        }

        public FullDriverDTO(Guid Id, string mecanographicNumber, CitizenCardDTO citizenCardDTO, string entranceDate, string departureDate, LicenseDTO license, List<DriverTypeDTO> driverTypes)
        {
            this.Id = Id;
            this.mecanographicNumber = mecanographicNumber;
            this.citizenCardDTO = citizenCardDTO;
            this.entranceDate = entranceDate;
            this.departureDate = departureDate;
            this.driverLicense = license;
            this.driverTypes = new List<DriverTypeDTO>(driverTypes);
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            FullDriverDTO driverDTO = (FullDriverDTO)obj;
            return (this.mecanographicNumber.Equals(driverDTO.mecanographicNumber)) &&
            (this.citizenCardDTO.Equals(driverDTO.citizenCardDTO)) &&
            (this.entranceDate.Equals(driverDTO.entranceDate)) &&
            (this.departureDate.Equals(driverDTO.departureDate)) &&
            (this.driverLicense.Equals(driverDTO.driverLicense));


        }

        // override object.GetHashCode
        public override int GetHashCode()
        {
            return HashCode.Combine(mecanographicNumber, citizenCardDTO, entranceDate, departureDate, driverLicense, driverTypes);
        }

    }
}