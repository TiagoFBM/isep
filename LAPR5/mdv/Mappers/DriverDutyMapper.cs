using System.Collections.Generic;
using mdv.Domain.DriverDutys;
using mdv.Domain.WorkBlocks;
using mdv.DTO.DriverDutys;
using mdv.DTO.WorkBlocks;


namespace mdv.Mappers
{

    public class DriverDutyMapper
    {
        private readonly WorkBlockMapper _workBlockMapper;

        public DriverDutyMapper()
        {
            _workBlockMapper = new WorkBlockMapper();
        }

        public DriverDutyDTO DomainToDTO(DriverDuty driverDuty)
        {
            var workBlockList = new List<WorkBlockDTO>();

            foreach (var workBlock in driverDuty.listWorkBlocks)
            {
                var workBlockDTO = _workBlockMapper.DomainToDTO(workBlock);
                workBlockList.Add(workBlockDTO);
            }

            var driverDutyDTO = new DriverDutyDTO(
                driverDuty.Id.AsGuid(),
                driverDuty.driverDutyCode.ToString(),
                workBlockList
            );

            return driverDutyDTO;
        }


    }

}