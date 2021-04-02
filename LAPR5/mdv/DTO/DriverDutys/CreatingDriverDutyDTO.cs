using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using mdv.DTO.WorkBlocks;

namespace mdv.DTO.DriverDutys
{
    public class CreatingDriverDutyDTO
    {
        [JsonProperty("driverDutyCode")]
        public string driverDutyCode { get; set; }

        [JsonProperty("listWorkBlocks")]
        public List<string> listWorkBlocks { get; set; }

        public CreatingDriverDutyDTO()
        {

        }

        public CreatingDriverDutyDTO(string driverDutyCode, List<string> listWorkBlocks)
        {
            this.driverDutyCode = driverDutyCode;
            this.listWorkBlocks = listWorkBlocks;
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            CreatingDriverDutyDTO dto = (CreatingDriverDutyDTO)obj;

            return (this.driverDutyCode.Equals(dto.driverDutyCode));
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(driverDutyCode, listWorkBlocks);
        }

    }
}