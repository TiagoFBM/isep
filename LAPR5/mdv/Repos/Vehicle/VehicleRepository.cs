using mdv.Domain.Vehicles;
using mdv.Repository.Vehicles;
using mdv.Infrastructure.Shared;

namespace mdv.Infrastructure.Vehicles
{
    public class VehicleRepository : BaseRepository<Vehicle, VehicleID>, IVehicleRepository
    {
        public VehicleRepository(DDDmdvDBContext context) : base(context.Vehicles)
        {

        }
    }
}