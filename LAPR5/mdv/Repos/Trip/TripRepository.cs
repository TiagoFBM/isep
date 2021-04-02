using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using mdv.Domain.Lines;
using mdv.Domain.Trips;
using mdv.Infrastructure.Shared;
using mdv.Repository.Trips;
using Microsoft.EntityFrameworkCore;

namespace mdv.Infrastructure.Trips {
    public class TripRepository : BaseRepository<Trip, TripId>, ITripRepository {
        DbSet<Trip> trips;
        public TripRepository (DDDmdvDBContext context) : base (context.Trips) {
            this.trips = context.Trips;
        }
        public Task<List<Trip>> GetTripsByLineID (LineId otherLineID) {
            var query =
                from t in this.trips
            where t.lineID.id == otherLineID.id
            select t;

            return Task.Run (() => query.ToList<Trip> ());
        }
    }
}