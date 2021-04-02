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
    public class WorkBlockService : IWorkBlockService
    {
        private readonly IUnitOfWork _unitOfWork;
        private readonly IWorkBlockRepository _workBlockRepository;
        private readonly ITripRepository _tripRepository;

        private readonly WorkBlockMapper _workBlockMapper;

        public WorkBlockService(IWorkBlockRepository workBlockRepository, IUnitOfWork unitOfWork, ITripRepository tripRepository)
        {
            this._workBlockRepository = workBlockRepository;
            this._tripRepository = tripRepository;
            this._unitOfWork = unitOfWork;
            this._workBlockMapper = new WorkBlockMapper();
        }

        public async Task<WorkBlockDTO> AddWorkBlock(CreatingWorkBlockDTO workBlockDTO)
        {
            List<Trip> tripList = new List<Trip>();
            foreach (var tripId in workBlockDTO.tripList)
            {
                tripList.Add(await this._tripRepository.GetByIdAsync(new TripId(tripId)));
            }

            var wb = new WorkBlock(workBlockDTO.startingTime, workBlockDTO.endingTime, tripList);
            var savedWb = await this._workBlockRepository.AddAsync(wb);
            await this._unitOfWork.CommitAsync();
            return this._workBlockMapper.DomainToDTO(savedWb);
        }
    }
}
