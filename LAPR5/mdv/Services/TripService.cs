using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Lines;
using mdv.Domain.Shared;
using mdv.Domain.Trips;
using mdv.DTO.Trips;
using mdv.Mappers;
using mdv.Repository.Trips;
using mdv.Utils.UtilsTime;

namespace mdv.Services {
    public class TripService : ITripService {
        private readonly IUnitOfWork _unitOfWork;
        private readonly ITripRepository _repo;
        private TripMapper _mapper;

        public TripService (ITripRepository repo, IUnitOfWork unitOfWork) {
            this._repo = repo;
            this._unitOfWork = unitOfWork;
            this._mapper = new TripMapper ();
        }

        public async Task<List<TripDTO>> GetAll () {
            var tripList = await this._repo.GetAllAsync ();
            var tripDTOlist = new List<TripDTO> ();

            foreach (var trip in tripList) {

                TripDTO tripDTO = _mapper.DomainToDTO (trip);

                tripDTOlist.Add (tripDTO);
            }

            foreach (var tripDTO in tripDTOlist){
                tripDTO.nodePassageListDTO.Sort(delegate (NodePassageDTO x, NodePassageDTO y){
                    var xSec = TimeUtils.fromStringToSec(x.passageTime);
                    var ySec = TimeUtils.fromStringToSec(y.passageTime);
                    return xSec.CompareTo(ySec);
                });
            }

            return tripDTOlist;
        }

        public async Task<List<TripDTO>> GetTripsByLineID (LineId lineId) {
            var tripList = await this._repo.GetTripsByLineID (lineId);
            var tripDTOlist = new List<TripDTO> ();

            foreach (var trip in tripList) {

                TripDTO tripDTO = _mapper.DomainToDTO (trip);

                tripDTOlist.Add (tripDTO);
            }

            return tripDTOlist;
        }

        public async Task<TripDTO> GetById (TripId id) {
            var trip = await this._repo.GetByIdAsync (id);

            if (trip == null) {
                return null;
            }

            return _mapper.DomainToDTO (trip);
        }

        public async Task<int> GetNumber () {
            return await this._repo.Count ();
        }

        public async Task<TripDTO> AddTrip (CreatingTripDTO creatingTripDTO, List<CreatingNodePassageDTO> creatingNodePassageListDTO) {

            List<NodePassage> nodePassagesList = new List<NodePassage> ();

            foreach (var item in creatingNodePassageListDTO) {
                NodePassage nodePassage = new NodePassage (
                    item.nodeId,
                    item.passageTime
                );

                nodePassagesList.Add (nodePassage);
            }

            var trip = new Trip (
                creatingTripDTO.lineID,
                creatingTripDTO.pathID,
                creatingTripDTO.tripDepartureTime,
                nodePassagesList
            );

            var savedTrip = await this._repo.AddAsync (trip);

            await this._unitOfWork.CommitAsync ();

            return _mapper.DomainToDTO (savedTrip);
        }

    }
}