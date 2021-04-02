using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.Vehicles;
using mdv.Domain.VehicleTypes;
using mdv.DTO.Vehicles;
using mdv.DTO.VehicleTypes;
using mdv.Repository.Vehicles;
using mdv.Mappers;


namespace mdv.Services
{
    public class VehicleService : IVehicleService
    {
        private readonly IVehicleRepository _repo;
        private readonly IUnitOfWork _unitOfWork;
        private readonly VehicleTypeService vehicleTypeService;
        private readonly VehicleMapper _mapper;

        public VehicleService(IVehicleRepository repo, IUnitOfWork unitOfWork)
        {
            this._repo = repo;
            this.vehicleTypeService = new VehicleTypeService();
            this._unitOfWork = unitOfWork;
            _mapper = new VehicleMapper();
        }

        public async Task<List<VehicleDTO>> GetAll()
        {
            var vehicleList = await this._repo.GetAllAsync();
            var vehicleDTOlist = new List<VehicleDTO>();


            foreach (var vehicle in vehicleList)
            {
                //var driverType = await driverTypeService.GetDriverTypeByID(driver.driverType.ToString());
                var vehicleDTO = _mapper.DomainToDTO(vehicle);

                vehicleDTOlist.Add(vehicleDTO);
            }

            return vehicleDTOlist;
        }

        public async Task<VehicleDTO> AddVehicle(VehicleDTO dto)
        {
            var vehicle = new Vehicle(
                dto.registration,
                dto.vin,
                dto.entranceDate,
                dto.vehicleType
            );

            var savedVehicle = await this._repo.AddAsync(vehicle);

            await this._unitOfWork.CommitAsync();

            return dto;
        }

        public async Task<int> GetNumber () {
            return await this._repo.Count();
        }

        public async Task<VehicleDTO> GetById(VehicleID id)
        {
            var vehicle = await this._repo.GetByIdAsync(id);

            if (vehicle == null)
            {
                return null;
            }


            return _mapper.DomainToDTO(vehicle);
        }
    }
}