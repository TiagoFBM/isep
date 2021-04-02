using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.DriverTypes;
using mdv.DTO.DriverTypes;

namespace mdv.Services
{
    public interface IDriverTypeService
    {
        Task<List<DriverTypeDTO>> GetAll();

        Task<DriverTypeDTO> GetDriverTypeByID(string driverTypeID);
    }
}