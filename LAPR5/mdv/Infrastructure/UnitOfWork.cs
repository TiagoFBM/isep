using System.Threading.Tasks;
using mdv.Domain.Shared;

namespace mdv.Infrastructure
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly DDDmdvDBContext _context;

        public UnitOfWork(DDDmdvDBContext context)
        {
            this._context = context;
        }

        public async Task<int> CommitAsync()
        {
            return await this._context.SaveChangesAsync();
        }
    }
}