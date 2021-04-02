using mdv.Domain.Shared;
using mdv.Domain.Vehicles;

namespace mdv.Repository.Vehicles
{
    public interface IVehicleRepository : IRepository<Vehicle, VehicleID> { }
}