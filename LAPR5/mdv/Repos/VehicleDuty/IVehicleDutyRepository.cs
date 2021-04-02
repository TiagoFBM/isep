using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.VehicleDutys;
using mdv.Domain.WorkBlocks;

namespace mdv.Repository.VehicleDutys
{
    public interface IVehicleDutyRepository : IRepository<VehicleDuty, VehicleDutyId>
    {
        Task<VehicleDuty> getVehicleDutyByWorkblock(WorkBlock workblock);
    }
}