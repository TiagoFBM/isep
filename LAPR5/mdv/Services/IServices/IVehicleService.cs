using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Vehicles;
using mdv.DTO.Vehicles;

namespace mdv.Services
{
    public interface IVehicleService
    {

        Task<List<VehicleDTO>> GetAll();

        Task<VehicleDTO> AddVehicle(VehicleDTO dto);

        Task<VehicleDTO> GetById(VehicleID id);

        Task<int> GetNumber ();


    }
}