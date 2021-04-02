using System.Threading.Tasks;

namespace mdv.Domain.Shared
{
    public interface IUnitOfWork
    {
        Task<int> CommitAsync();
    }
}