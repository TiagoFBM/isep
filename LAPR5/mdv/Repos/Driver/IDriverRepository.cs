using mdv.Domain.Shared;
using mdv.Domain.Drivers;

namespace mdv.Repository.Drivers
{
    public interface IDriverRepository : IRepository<Driver, DriverID> { }
}