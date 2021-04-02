using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Lines;
using mdv.Domain.Trips;
using mdv.DTO.Trips;

namespace mdv.Services {
    public interface ITripService {

        Task<List<TripDTO>> GetAll ();

        Task<List<TripDTO>> GetTripsByLineID (LineId lineId);

        Task<TripDTO> GetById (TripId id);

        Task<TripDTO> AddTrip (CreatingTripDTO creatingTripDTO, List<CreatingNodePassageDTO> creatingNodePassageListDTO);

        Task<int> GetNumber ();
    }
}