using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.VehicleDutys;
using mdv.Domain.WorkBlocks;
using mdv.DTO.VehicleDutys;
using mdv.DTO.WorkBlocks;

namespace mdv.Services
{
    public interface IVehicleDutyService
    {

        Task<List<VehicleDutyDTO>> GetAll();

        Task<VehicleDutyDTO> GetById(VehicleDutyId id);

        Task<VehicleDutyDTO> AddVehicleDuty(CreatingVehicleDutyDTO vehicleDutyDTO);

        Task<VehicleDutyDTO> AddWorkBlocksToVehicleDuty(List<WorkBlockDTO> wbDTOList, VehicleDutyId vdId);

        Task<WorkBlockDTO> GetWorkBlockById(WorkBlockID id);

    }
}