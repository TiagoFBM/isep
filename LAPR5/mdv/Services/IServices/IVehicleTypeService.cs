using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.VehicleTypes;
using mdv.DTO.VehicleTypes;

namespace mdv.Services
{
    public interface IVehicleTypeService
    {
        Task<List<VehicleTypeDTO>> GetAll();

        Task<VehicleTypeDTO> GetVehicleTypeByID(string vehicleTypeID);
    }
}