using System;
using System.Collections.Generic;
using mdv.DTO.WorkBlocks;
using Newtonsoft.Json;

namespace mdv.DTO.DriverDutys
{

    public class DriverDutyDTO
    {

        [JsonProperty("driverDutyId")]
        public Guid Id { get; set; }

        [JsonProperty("driverDutyCode")]
        public string driverDutyCode { get; set; }

        [JsonProperty("listWorkBlocks")]
        public List<WorkBlockDTO> listWorkBlocks { get; set; }

        public DriverDutyDTO() { }

        public DriverDutyDTO(Guid id, string driverDutyCode, List<WorkBlockDTO> listWorkBlocks)
        {
            this.Id = id;
            this.driverDutyCode = driverDutyCode;
            this.listWorkBlocks = listWorkBlocks;
        }

        // override object.Equals
        public override bool Equals(object obj)
        {

            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            DriverDutyDTO dto = (DriverDutyDTO)obj;
            return (this.driverDutyCode.Equals(dto.driverDutyCode));
        }

        // override object.GetHashCode
        public override int GetHashCode()
        {
            return HashCode.Combine(driverDutyCode, listWorkBlocks);
        }

    }

}