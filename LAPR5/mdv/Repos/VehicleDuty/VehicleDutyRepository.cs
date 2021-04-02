using mdv.Domain.VehicleDutys;
using mdv.Domain.WorkBlocks;
using mdv.Repository.VehicleDutys;
using mdv.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;
using System.Linq;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace mdv.Infrastructure.VehicleDutys
{
    public class VehicleDutyRepository : BaseRepository<VehicleDuty, VehicleDutyId>, IVehicleDutyRepository
    {
        DbSet<WorkBlock> workBlocks;
        DbSet<VehicleDuty> vehicleDuties;
        public VehicleDutyRepository(DDDmdvDBContext context) : base(context.VehicleDutys)
        {
            context.VehicleDutys.Include(vd => vd.tripsList).ToList();
            context.VehicleDutys.Include(vd => vd.workBlockList).ToList();

            this.workBlocks = context.WorkBlocks;
            this.vehicleDuties = context.VehicleDutys;
        }

        public Task<VehicleDuty> getVehicleDutyByWorkblock(WorkBlock workblock)
        {

            return this.vehicleDuties.Where(vD => vD.workBlockList.Contains(workblock)).FirstOrDefaultAsync();
        }
    }
}