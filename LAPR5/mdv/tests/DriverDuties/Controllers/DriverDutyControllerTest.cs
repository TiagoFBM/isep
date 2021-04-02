using System;
using System.Collections.Generic;
using mdv.Controllers;
using mdv.Domain.DriverDutys;
using mdv.Domain.WorkBlocks;
using mdv.Domain.Trips;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.DTO.DriverDutys;
using mdv.DTO.WorkBlocks;
using mdv.DTO.Trips;
using mdv.Services;
using Moq;
using Xunit;

namespace Tests.DriverDuties
{
    public class DriverDutyControllerTest
    {
        [Fact]
        public async void GetAll()
        {
            var service = new Mock<IDriverDutyService>();

            string driverDutyCode = "dDutycode1";
            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);

            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("14:0:0", "18:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };

            var driverDuty = new DriverDuty(driverDutyCode, workBlockList);

            var tripDTO = new TripDTO(trip.Id.AsGuid(), new LineId(lineID), new PathId(pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO>() { tripDTO };


            var listWorkBlocksString = new List<String>() { workBlock1.Id.Value, workBlock2.Id.Value, workBlock3.Id.Value };

            var workBlockDTO1 = new WorkBlockDTO(workBlock1.Id.AsGuid(), workBlock1.startingTime.time, workBlock1.endingTime.time, tripsDTO);
            var workBlockDTO2 = new WorkBlockDTO(workBlock2.Id.AsGuid(), workBlock2.startingTime.time, workBlock2.endingTime.time, tripsDTO);
            var workBlockDTO3 = new WorkBlockDTO(workBlock3.Id.AsGuid(), workBlock3.startingTime.time, workBlock3.endingTime.time, tripsDTO);

            var listWorkBlockDTO = new List<WorkBlockDTO>() { workBlockDTO1, workBlockDTO2, workBlockDTO3 };

            var driverDutyDTO = new DriverDutyDTO(driverDuty.Id.AsGuid(), driverDutyCode, listWorkBlockDTO);

            var listDriverDutyDTOs = new List<DriverDutyDTO>() { driverDutyDTO };

            var listDrivers = new List<DriverDuty>() { driverDuty };

            service.Setup(_ => _.GetAll()).ReturnsAsync(listDriverDutyDTOs);

            var controller = new DriverDutyController(service.Object);


            var actual = await controller.GetAll();

            Assert.Equal(listDriverDutyDTOs, actual.Value);
        }

        [Fact]
        public async void GetById()
        {
            var service = new Mock<IDriverDutyService>();

            string driverDutyCode = "dDutycode1";
            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);

            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("14:0:0", "18:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };

            var driverDuty = new DriverDuty(driverDutyCode, workBlockList);

            var tripDTO = new TripDTO(trip.Id.AsGuid(), new LineId(lineID), new PathId(pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO>() { tripDTO };


            var listWorkBlocksString = new List<String>() { workBlock1.Id.Value, workBlock2.Id.Value, workBlock3.Id.Value };

            var workBlockDTO1 = new WorkBlockDTO(workBlock1.Id.AsGuid(), workBlock1.startingTime.time, workBlock1.endingTime.time, tripsDTO);
            var workBlockDTO2 = new WorkBlockDTO(workBlock2.Id.AsGuid(), workBlock2.startingTime.time, workBlock2.endingTime.time, tripsDTO);
            var workBlockDTO3 = new WorkBlockDTO(workBlock3.Id.AsGuid(), workBlock3.startingTime.time, workBlock3.endingTime.time, tripsDTO);

            var listWorkBlockDTO = new List<WorkBlockDTO>() { workBlockDTO1, workBlockDTO2, workBlockDTO3 };

            var driverDutyDTO = new DriverDutyDTO(driverDuty.Id.AsGuid(), driverDutyCode, listWorkBlockDTO);

            service.Setup(_ => _.GetById(driverDuty.Id)).ReturnsAsync(driverDutyDTO);

            var controller = new DriverDutyController(service.Object);


            var actual = await controller.GetById(driverDuty.Id.AsGuid());

            Assert.Equal(driverDutyDTO, actual.Value);
        }

        [Fact]
        public async void Create()
        {
            var service = new Mock<IDriverDutyService>();

            string driverDutyCode = "dDutycode1";
            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);

            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("14:0:0", "18:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };

            var driverDuty = new DriverDuty(driverDutyCode, workBlockList);

            var tripDTO = new TripDTO(trip.Id.AsGuid(), new LineId(lineID), new PathId(pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO>() { tripDTO };

            var tripsDTOString = new List<string>() { trip.Id.ToString() };

            var cworkBlockDTO1 = new CreatingWorkBlockDTO(tripsDTOString, workBlock1.startingTime.time, workBlock1.endingTime.time);
            var cworkBlockDTO2 = new CreatingWorkBlockDTO(tripsDTOString, workBlock2.startingTime.time, workBlock2.endingTime.time);
            var cworkBlockDTO3 = new CreatingWorkBlockDTO(tripsDTOString, workBlock3.startingTime.time, workBlock3.endingTime.time);


            var listWorkBlocksString = new List<String>() { workBlock1.Id.Value, workBlock2.Id.Value, workBlock3.Id.Value };

            var workBlockDTO1 = new WorkBlockDTO(workBlock1.Id.AsGuid(), workBlock1.startingTime.time, workBlock1.endingTime.time, tripsDTO);
            var workBlockDTO2 = new WorkBlockDTO(workBlock2.Id.AsGuid(), workBlock2.startingTime.time, workBlock2.endingTime.time, tripsDTO);
            var workBlockDTO3 = new WorkBlockDTO(workBlock3.Id.AsGuid(), workBlock3.startingTime.time, workBlock3.endingTime.time, tripsDTO);

            var listWorkBlockDTO = new List<WorkBlockDTO>() { workBlockDTO1, workBlockDTO2, workBlockDTO3 };

            var driverDutyDTO = new DriverDutyDTO(driverDuty.Id.AsGuid(), driverDutyCode, listWorkBlockDTO);

            var creatingDriverDutyDTO = new CreatingDriverDutyDTO(driverDutyCode, listWorkBlocksString);


            service.Setup(_ => _.AddDriverDuty(creatingDriverDutyDTO)).ReturnsAsync(driverDutyDTO);

            var controller = new DriverDutyController(service.Object);

            var actual = await controller.Create(creatingDriverDutyDTO);

            Assert.NotNull(actual);
            Assert.NotNull(actual.Result);

        }

    }
}