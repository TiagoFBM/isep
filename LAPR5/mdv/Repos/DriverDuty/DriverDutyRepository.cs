using mdv.Domain.DriverDutys;
using mdv.Domain.Nodes;
using mdv.Domain.WorkBlocks;
using mdv.Domain.VehicleDutys;
using mdv.Domain.Trips;
using mdv.Repository.DriverDutys;
using mdv.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;
using System.Linq;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace mdv.Infrastructure.DriverDutys
{
    public class DriverDutyRepository : BaseRepository<DriverDuty, DriverDutyId>, IDriverDutyRepository
    {


        public DriverDutyRepository(DDDmdvDBContext context) : base(context.DriverDutys)
        {
            context.DriverDutys.Include(dd => dd.listWorkBlocks).ToList();

        }
    }
}