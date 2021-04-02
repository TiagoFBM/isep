using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Drivers;
using mdv.DTO.Drivers;

namespace mdv.Services
{
    public interface IDriverService
    {

        Task<List<DriverDTO>> GetAll();

        Task<DriverDTO> AddDriver(CreatingDriverDTO dto, CreatingLicenseDTO licenseDTO, CreatingCitizenCardDTO cardDTO);

        Task<DriverDTO> GetById(DriverID id);

        Task<int> GetNumber();


    }
}