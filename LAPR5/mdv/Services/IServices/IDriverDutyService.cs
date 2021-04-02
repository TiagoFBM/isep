using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.DriverDutys;
using mdv.Domain.WorkBlocks;
using mdv.DTO.DriverDutys;


namespace mdv.Services
{

    public interface IDriverDutyService
    {

        Task<List<DriverDutyDTO>> GetAll();

        Task<DriverDutyDTO> GetById(DriverDutyId id);

        Task<DriverDutyDTO> AddDriverDuty(CreatingDriverDutyDTO driverDutyDTO);

        Task<List<DriverDutyDTO>> DeleteAllAsync();

    }

}