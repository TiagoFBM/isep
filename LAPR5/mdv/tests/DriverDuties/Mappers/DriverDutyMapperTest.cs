using System;
using mdv.Domain.DriverDutys;
using mdv.Domain.WorkBlocks;
using mdv.Domain.Trips;
using mdv.Domain.Paths;
using mdv.Domain.Lines;
using mdv.DTO.DriverDutys;
using mdv.DTO.WorkBlocks;
using mdv.DTO.Trips;
using mdv.Mappers;
using System.Collections.Generic;
using Xunit;

namespace Tests.DriverDuties
{
    public class DriverDutyMapperTest
    {
        [Fact]
        public void DTOtoDomain()
        {
            var mapper = new DriverDutyMapper();

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

            var tripDTO = new TripDTO(trip.Id.AsGuid(), new LineId(lineID), new PathId(pathID), tripDepartureTime);
            var tripsDTO = new List<TripDTO>() { tripDTO };


            var listWorkBlocksString = new List<String>() { workBlock1.Id.Value, workBlock2.Id.Value, workBlock3.Id.Value };

            var workBlockDTO1 = new WorkBlockDTO(workBlock1.Id.AsGuid(), workBlock1.startingTime.time, workBlock1.endingTime.time, tripsDTO);
            var workBlockDTO2 = new WorkBlockDTO(workBlock2.Id.AsGuid(), workBlock2.startingTime.time, workBlock2.endingTime.time, tripsDTO);
            var workBlockDTO3 = new WorkBlockDTO(workBlock3.Id.AsGuid(), workBlock3.startingTime.time, workBlock3.endingTime.time, tripsDTO);

            var listWorkBlockDTO = new List<WorkBlockDTO>() { workBlockDTO1, workBlockDTO2, workBlockDTO3 };


            var driverDuty = new DriverDuty(driverDutyCode, workBlockList);

            var expected = new DriverDutyDTO(
                driverDuty.Id.AsGuid(),
                driverDutyCode,
                listWorkBlockDTO
            );

            var actual = mapper.DomainToDTO(driverDuty);

            Assert.Equal(actual, expected);

        }
    }
}