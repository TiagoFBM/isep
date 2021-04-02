using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.WorkBlocks;

namespace mdv.Repository.WorkBlocks {
    public interface IWorkBlockRepository : IRepository<WorkBlock, WorkBlockID> {
    }
}