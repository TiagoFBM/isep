using System.Collections.Generic;
using System.Threading.Tasks;
using mdv.Domain.Shared;
using mdv.Domain.Trips;
using mdv.Domain.Lines;

namespace mdv.Repository.Trips {
    public interface ITripRepository : IRepository<Trip, TripId> {
        Task<List<Trip>> GetTripsByLineID (LineId lineID);
    }
}