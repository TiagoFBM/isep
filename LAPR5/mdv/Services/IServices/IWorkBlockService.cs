using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.VehicleDutys;
using mdv.DTO.VehicleDutys;
using mdv.DTO.WorkBlocks;

namespace mdv.Services
{
    public interface IWorkBlockService
    {

        Task<WorkBlockDTO> AddWorkBlock(CreatingWorkBlockDTO workBlockDTO);

    }
}