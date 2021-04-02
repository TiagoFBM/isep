using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.VehicleTypes;
using mdv.DTO.VehicleTypes;
using mdv.Service.HttpRequests;

namespace mdv.Services
{
    public class VehicleTypeService : IVehicleTypeService
    {
        public VehicleTypeService() { }

        public async Task<List<VehicleTypeDTO>> GetAll()
        {
            return await HttpRequest<VehicleTypeDTO>.GetAll("http://localhost:8080/api/vehicleType");

        }

        public async Task<VehicleTypeDTO> GetVehicleTypeByID(string vehicleTypeID)
        {
            VehicleTypeDTO vehicleTypeDTO = await HttpRequest<VehicleTypeDTO>.GetByID("http://localhost:8080/api/vehicleType/" + vehicleTypeID);

            if (vehicleTypeDTO == null)
            {
                throw new Exception("Invalid vehicle type for code " + vehicleTypeID);
            }
            return vehicleTypeDTO;

        }
    }
}