using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.VehicleDutys;
using mdv.Domain.Shared;
using mdv.Domain.Trips;
using mdv.Domain.Vehicles;
using mdv.Domain.WorkBlocks;
using mdv.DTO.VehicleDutys;
using mdv.DTO.WorkBlocks;
using mdv.Repository.VehicleDutys;
using mdv.Repository.Trips;
using mdv.Repository.Vehicles;
using mdv.Repository.WorkBlocks;
using mdv.Mappers;

namespace mdv.Services
{
    public class VehicleDutyService : IVehicleDutyService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IVehicleDutyRepository _vehicleDutyRepository;
        private readonly ITripRepository _tripRepository;
        private readonly IVehicleRepository _vehicleRepository;
        private readonly IWorkBlockRepository _workBlockRepository;
        private readonly VehicleDutyMapper _vehicleDutyMapper;

        private readonly WorkBlockMapper _workBlockMapper;

        public VehicleDutyService(IVehicleDutyRepository vehicleDutyRepo, ITripRepository tripRepository, IVehicleRepository vehicleRepository, IWorkBlockRepository workBlockRepository, IUnitOfWork unitOfWork)
        {
            this._vehicleDutyRepository = vehicleDutyRepo;
            this._tripRepository = tripRepository;
            this._vehicleRepository = vehicleRepository;
            this._workBlockRepository = workBlockRepository;
            this._unitOfWork = unitOfWork;
            this._vehicleDutyMapper = new VehicleDutyMapper();
            this._workBlockMapper = new WorkBlockMapper();
        }

        public async Task<List<VehicleDutyDTO>> GetAll()
        {
            var vehicleDutyList = await this._vehicleDutyRepository.GetAllAsync();
            var vehicleDutyDTOlist = new List<VehicleDutyDTO>();

            foreach (var vehicleDuty in vehicleDutyList)
            {
                var vehicleDutyDTO = _vehicleDutyMapper.DomainToDTO(vehicleDuty);
                vehicleDutyDTOlist.Add(vehicleDutyDTO);
            }

            return vehicleDutyDTOlist;
        }

        public async Task<VehicleDutyDTO> GetById(VehicleDutyId id)
        {
            var vehicleDuty = await this._vehicleDutyRepository.GetByIdAsync(id);

            if (vehicleDuty == null)
            {
                return null;
            }

            return _vehicleDutyMapper.DomainToDTO(vehicleDuty);
        }

        public async Task<WorkBlockDTO> GetWorkBlockById(WorkBlockID id)
        {
            var workblock = await this._workBlockRepository.GetByIdAsync(id);

            if (workblock == null)
            {
                return null;
            }
            return _workBlockMapper.DomainToDTO(workblock);
        }

        public async Task<VehicleDutyDTO> AddVehicleDuty(CreatingVehicleDutyDTO vehicleDutyDTO)
        {

            List<Trip> trips = new List<Trip>();

            foreach (var tripID in vehicleDutyDTO.tripList)
            {
                var trip = await _tripRepository.GetByIdAsync(new TripId(tripID));
                trips.Add(trip);
            }

            var vehicleDuty = new VehicleDuty(
                vehicleDutyDTO.vehicleDutyCode,
                trips
            );

            var savedVehicleDuty = await this._vehicleDutyRepository.AddAsync(vehicleDuty);

            await this._unitOfWork.CommitAsync();

            return _vehicleDutyMapper.DomainToDTO(savedVehicleDuty);
        }

        public async Task<VehicleDutyDTO> AddWorkBlocksToVehicleDuty(List<WorkBlockDTO> wbDTOList, VehicleDutyId vdId)
        {
            List<WorkBlock> createdWorkBlocks = new List<WorkBlock>();
            foreach (var dto in wbDTOList)
            {
                var savedWb = await this._workBlockRepository.GetByIdAsync(new WorkBlockID(dto.Id));
                createdWorkBlocks.Add(savedWb);
            }
            var vd = await this._vehicleDutyRepository.GetByIdAsync(vdId);
            vd.addWorkBlocks(createdWorkBlocks);
            await this._unitOfWork.CommitAsync();
            return _vehicleDutyMapper.DomainToDTO(vd);
        }

    }
}
