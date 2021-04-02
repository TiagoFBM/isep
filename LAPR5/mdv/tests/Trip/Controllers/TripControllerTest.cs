using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using mdv.Controllers;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.Domain.Trips;
using mdv.DTO.Paths;
using mdv.DTO.Trips;
using mdv.Services;
using Moq;
using Xunit;

namespace Tests.Trips {
    public class TripControllerTest {

        [Fact]
        public async void GetAll () {

            var tripServiceMock = new Mock<ITripService> ();
            var pathServiceMock = new Mock<IPathService> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);
            var trips = new List<Trip> () { trip };

            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO> () { tripDTO };

            tripServiceMock.Setup (_ => _.GetAll ()).ReturnsAsync (tripsDTO);

            var controller = new TripController (tripServiceMock.Object, pathServiceMock.Object);

            var actual = await controller.GetAll ();

            Assert.Equal (tripsDTO, actual.Value);
        }

        [Fact]
        public async void GetByLineId () {

            var tripServiceMock = new Mock<ITripService> ();
            var pathServiceMock = new Mock<IPathService> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);

            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO> () { tripDTO };

            var line = new LineId (lineID);

            tripServiceMock.Setup (_ => _.GetTripsByLineID (line)).ReturnsAsync (tripsDTO);

            var controller = new TripController (tripServiceMock.Object, pathServiceMock.Object);

            var actual = await controller.GetByLineId (lineID);

            Assert.Equal (tripsDTO, actual.Value);
        }

        [Fact]
        public void GetById () {

            var tripServiceMock = new Mock<ITripService> ();
            var pathServiceMock = new Mock<IPathService> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);

            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);

            var tripID = new TripId (trip.Id.AsGuid ());

            tripServiceMock.Setup (_ => _.GetById (tripID)).ReturnsAsync (tripDTO);

            var controller = new TripController (tripServiceMock.Object, pathServiceMock.Object);

            var actual = controller.GetById (trip.Id.AsGuid ());

            Assert.Equal (tripDTO, actual.Result.Value);
        }

        [Fact]
        public async void Create () {

            var tripServiceMock = new Mock<ITripService> ();
            var pathServiceMock = new Mock<IPathService> ();

            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);
            var tripDTO = new TripDTO (trip.Id.AsGuid (), new LineId (lineID), new PathId (pathID), tripDepartureTime);

            var path = new PathId (pathID);
            var pathDTO = new PathDTO (pathID, true, new List<SegmentDTO> ());
            var creatingTrip = new CreatingTripDTO (lineID, pathID, tripDepartureTime);

            pathServiceMock.Setup (_ => _.GetById (path)).ReturnsAsync (pathDTO);
            tripServiceMock.Setup (_ => _.AddTrip (creatingTrip, new List<CreatingNodePassageDTO> ())).ReturnsAsync (tripDTO);

            var controller = new TripController (tripServiceMock.Object, pathServiceMock.Object);

            var actual = await controller.Create (creatingTrip);

            Assert.NotNull(actual);
            Assert.NotNull(actual.Result);
        }

    }
}