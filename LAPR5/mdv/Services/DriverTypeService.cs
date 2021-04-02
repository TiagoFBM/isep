using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.DriverTypes;
using mdv.DTO.DriverTypes;
using mdv.Service.HttpRequests;

namespace mdv.Services
{
    public class DriverTypeService : IDriverTypeService
    {
        public DriverTypeService() { }

        public async Task<List<DriverTypeDTO>> GetAll()
        {
            return await HttpRequest<DriverTypeDTO>.GetAll("http://localhost:8080/api/driverType");

        }

        public async Task<DriverTypeDTO> GetDriverTypeByID(string driverTypeID)
        {
            DriverTypeDTO driverTypeDTO = await HttpRequest<DriverTypeDTO>.GetByID("http://localhost:8080/api/driverType/" + driverTypeID);

            if (driverTypeDTO == null)
            {
                throw new Exception("Invalid driver type for code " + driverTypeID);
            }
            return driverTypeDTO;

        }
    }
}