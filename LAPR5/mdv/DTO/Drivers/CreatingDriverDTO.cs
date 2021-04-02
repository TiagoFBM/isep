using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using mdv.Domain.Drivers;

namespace mdv.DTO.Drivers
{
    public class CreatingDriverDTO
    {
        public string mecanographicNumber { get; set; }

        public string driverName { get; set; }

        public string birthDate { get; set; }

        public long citizenCardNumber { get; set; }

        public long driverNIF { get; set; }

        public string entranceDate { get; set; }

        public string departureDate { get; set; }

        public string numberDriverLicense { get; set; }

        public string dateDriverLicense { get; set; }

        public List<string> driverTypes { get; set; }

        public CreatingDriverDTO(string mecanographicNumber, string driverName, string birthDate, long citizenCardNumber, long driverNIF, string entranceDate, string departureDate, string numberDriverLicense, string dateDriverLicense, List<string> driverTypes)
        {
            this.mecanographicNumber = mecanographicNumber;
            this.driverName = driverName;
            this.birthDate = birthDate;
            this.citizenCardNumber = citizenCardNumber;
            this.driverNIF = driverNIF;
            this.entranceDate = entranceDate;
            this.departureDate = departureDate;
            this.numberDriverLicense = numberDriverLicense;
            this.dateDriverLicense = dateDriverLicense;
            this.driverTypes = new List<string>(driverTypes);
        }

        // override object.Equals
        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            CreatingDriverDTO dto = (CreatingDriverDTO)obj;

            return (this.mecanographicNumber.Equals(dto.mecanographicNumber)) &&
            (this.driverName.Equals(dto.driverName)) &&
            (this.birthDate.Equals(dto.birthDate)) &&
            (this.citizenCardNumber.Equals(dto.citizenCardNumber)) &&
            (this.driverNIF.Equals(dto.driverNIF)) &&
            (this.entranceDate.Equals(dto.entranceDate)) &&
            (this.departureDate.Equals(dto.departureDate)) &&
            (this.numberDriverLicense.Equals(dto.numberDriverLicense)) &&
            (this.dateDriverLicense.Equals(dto.dateDriverLicense));
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(mecanographicNumber, driverName, birthDate, citizenCardNumber, driverNIF, entranceDate, departureDate, numberDriverLicense);
        }
    }
}