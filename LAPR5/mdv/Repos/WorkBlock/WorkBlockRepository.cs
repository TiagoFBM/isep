using mdv.Domain.WorkBlocks;
using mdv.Repository.WorkBlocks;
using mdv.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace mdv.Infrastructure.WorkBlocks
{
    public class WorkBlockRepository : BaseRepository<WorkBlock, WorkBlockID>, IWorkBlockRepository
    {
        public WorkBlockRepository(DDDmdvDBContext context) : base(context.WorkBlocks)
        {
            context.WorkBlocks.Include(vd => vd.tripList).ToList();
        }
    }
}