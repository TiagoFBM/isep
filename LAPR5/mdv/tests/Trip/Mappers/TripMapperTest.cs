using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.Domain.Trips;
using mdv.DTO.Trips;
using mdv.Mappers;
using Xunit;

namespace Tests.Trips {
    public class TripMapperTest {

        [Fact]
        public void DTOtoDomain () {
            var mapper = new TripMapper ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);

            var expected = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);

            var actual = mapper.DomainToDTO (trip);

            Assert.True (expected.Equals (actual));
        }
    }
}