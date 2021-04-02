using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.Drivers;
using mdv.Domain.DriverTypes;
using mdv.DTO.Drivers;
using mdv.DTO.DriverTypes;
using mdv.Repository.Drivers;
using mdv.Mappers;


namespace mdv.Services
{
    public class DriverService : IDriverService
    {
        private readonly IDriverRepository _repo;
        private readonly IUnitOfWork _unitOfWork;


        private readonly DriverMapper _mapper;
        public DriverService(IDriverRepository repo, IUnitOfWork unitOfWork)
        {
            this._repo = repo;
            this._unitOfWork = unitOfWork;
            this._mapper = new DriverMapper();
        }

        public async Task<List<DriverDTO>> GetAll()
        {
            var driverList = await this._repo.GetAllAsync();
            var driverDTOlist = new List<DriverDTO>();


            foreach (var driver in driverList)
            {
                List<string> listDriverTypes = new List<string>();

                foreach (var driverType in driver.driverTypes)
                {
                    listDriverTypes.Add(driverType.ToString());
                }

                DriverDTO driverDTO = this._mapper.DomainToDTO(driver, listDriverTypes);


                driverDTOlist.Add(driverDTO);
            }

            return driverDTOlist;
        }

        public async Task<DriverDTO> AddDriver(CreatingDriverDTO dto, CreatingLicenseDTO licenseDTO, CreatingCitizenCardDTO cardDTO)
        {

            List<DriverTypeId> driverTypes = new List<DriverTypeId>();

            foreach (var driverTypeId in dto.driverTypes)
            {

                driverTypes.Add(new DriverTypeId(driverTypeId));
            }

            var driver = new Driver(
                dto.mecanographicNumber,
                dto.driverName,
                dto.birthDate,
                dto.citizenCardNumber,
                dto.driverNIF,
                dto.entranceDate,
                dto.departureDate,
                dto.numberDriverLicense,
                dto.dateDriverLicense,
                driverTypes
            );

            var savedDriver = await this._repo.AddAsync(driver);
            await this._unitOfWork.CommitAsync();


            List<string> listSavedDriverTypes = new List<string>();

            foreach (var savedDriverType in driver.driverTypes)
            {
                listSavedDriverTypes.Add(savedDriverType.ToString());
            }



            DriverDTO driverDTO = this._mapper.DomainToDTO(savedDriver, listSavedDriverTypes);

            return driverDTO;
        }

        public async Task<DriverDTO> GetById(DriverID id)
        {
            var driver = await this._repo.GetByIdAsync(id);

            if (driver == null)
            {
                return null;
            }

            List<string> listDriverTypes = new List<string>();
            foreach (var driverType in driver.driverTypes)
            {
                listDriverTypes.Add(driverType.ToString());
            }

            DriverDTO driverDTO = this._mapper.DomainToDTO(driver, listDriverTypes);

            return driverDTO;
        }

        public async Task<int> GetNumber()
        {
            return await this._repo.Count();
        }

    }
}