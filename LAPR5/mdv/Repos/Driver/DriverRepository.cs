using mdv.Domain.Drivers;
using mdv.Repository.Drivers;
using mdv.Infrastructure.Shared;
using Microsoft.EntityFrameworkCore;

namespace mdv.Infrastructure.Drivers
{
    public class DriverRepository : BaseRepository<Driver, DriverID>, IDriverRepository
    {
        DbSet<Driver> drivers;
        public DriverRepository(DDDmdvDBContext context) : base(context.Drivers)
        {
            this.drivers = context.Drivers;
        }
    }
}