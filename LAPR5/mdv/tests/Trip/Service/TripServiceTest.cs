using System.Collections.Generic;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.Domain.Shared;
using mdv.Domain.Trips;
using mdv.DTO.Trips;
using mdv.Mappers;
using mdv.Repository.Trips;
using mdv.Services;
using Moq;
using Xunit;

namespace Tests.Trips {
    public class TripServiceTest {

        [Fact]
        public async void GetAllTest () {

            var repo = new Mock<ITripRepository> ();
            var uow = new Mock<IUnitOfWork> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);
            var trips = new List<Trip> () { trip };

            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO> () { tripDTO };

            repo.Setup (_ => _.GetAllAsync ()).ReturnsAsync (trips);

            var service = new TripService (repo.Object, uow.Object);

            var actual = await service.GetAll ();

            Assert.Equal (tripsDTO, actual);
        }

        [Fact]
        public async void GetTripsByLineID_Exists () {

            var repo = new Mock<ITripRepository> ();
            var uow = new Mock<IUnitOfWork> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            LineId line = new LineId (lineID);

            var trip = new Trip (lineID, pathID, tripDepartureTime);
            var trips = new List<Trip> () { trip };

            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO> () { tripDTO };

            repo.Setup (_ => _.GetTripsByLineID (line)).ReturnsAsync (trips);

            var service = new TripService (repo.Object, uow.Object);

            var actual = await service.GetTripsByLineID (line);

            Assert.Equal (tripsDTO, actual);
        }

        [Fact]
        public async void GetTripsByLineID_Empty () {

            var repo = new Mock<ITripRepository> ();
            var uow = new Mock<IUnitOfWork> ();

            LineId line = new LineId ("Line:2");

            var expected = new List<TripDTO> () { };

            repo.Setup (_ => _.GetTripsByLineID (line)).ReturnsAsync (new List<Trip> ());

            var service = new TripService (repo.Object, uow.Object);

            var actual = await service.GetTripsByLineID (line);

            Assert.Equal (expected, actual);
        }

        [Fact]
        public async void GetById_Exists () {

            var repo = new Mock<ITripRepository> ();
            var uow = new Mock<IUnitOfWork> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);

            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);

            repo.Setup (_ => _.GetByIdAsync (trip.Id)).ReturnsAsync (trip);

            var service = new TripService (repo.Object, uow.Object);

            var actual = await service.GetById (trip.Id);

            Assert.Equal (tripDTO, actual);
        }

        [Fact]
        public async void AddTrip () {
            var repo = new Mock<ITripRepository> ();
            var uow = new Mock<IUnitOfWork> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);

            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);

            var creatingTrip = new CreatingTripDTO (lineID, pathID, tripDepartureTime);

            repo.Setup (_ => _.AddAsync (trip)).ReturnsAsync (trip);

            var service = new TripService (repo.Object, uow.Object);

            var actual = await service.AddTrip (creatingTrip, new List<CreatingNodePassageDTO> ());

            Assert.Equal (tripDTO, actual);
        }

    }
}