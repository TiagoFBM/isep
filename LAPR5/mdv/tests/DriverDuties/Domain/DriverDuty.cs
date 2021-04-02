using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.WorkBlocks;
using mdv.Domain.Trips;
using mdv.Domain.DriverDutys;
using Xunit;

namespace Tests.DriverDuties
{
    public class DriverDutyTest
    {
        [Fact]
        public void createValidDriverDuty()
        {
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

            Assert.True(driverDuty.GetType().Equals(new DriverDuty().GetType()));
        }


        [Fact]
        public void createDriverDutyWithNullCode()
        {
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

            Assert.Throws<BusinessRuleValidationException>(() => new DriverDuty(null, workBlockList));

        }

        [Fact]
        public void createDriverDutyWithEmptyCode()
        {
            string driverDutyCode = "";
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

            Assert.Throws<BusinessRuleValidationException>(() => new DriverDuty(driverDutyCode, workBlockList));

        }

        [Fact]
        public void createDriverDutyWithMoreThan10CharactersCode()
        {
            string driverDutyCode = "driverDutyCodeFail:1";
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

            Assert.Throws<BusinessRuleValidationException>(() => new DriverDuty(driverDutyCode, workBlockList));

        }

        [Fact]
        public void createDriverDutyWithEmptyWorkBlockList()
        {
            string driverDutyCode = "dDutyCode1";

            var workBlockList = new List<WorkBlock>();

            Assert.Throws<BusinessRuleValidationException>(() => new DriverDuty(driverDutyCode, workBlockList));

        }

        [Fact]
        public void createDriverDutyWithMoreThan8Hours()
        {
            string driverDutyCode = "dDutycode1";

            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);
            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("14:0:0", "19:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };


            Assert.Throws<BusinessRuleValidationException>(() => new DriverDuty(driverDutyCode, workBlockList));
        }

        [Fact]
        public void createDriverDutyWithMoreThan4Straight()
        {
            string driverDutyCode = "dDutycode1";

            var tripList = new List<Trip>();


            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip(lineID, pathID, tripDepartureTime);

            tripList.Add(trip);
            var workBlock1 = new WorkBlock("9:0:0", "10:0:0", tripList);
            var workBlock2 = new WorkBlock("10:0:0", "13:0:0", tripList);
            var workBlock3 = new WorkBlock("13:0:0", "17:0:0", tripList);

            var workBlockList = new List<WorkBlock> { workBlock1, workBlock2, workBlock3 };


            Assert.Throws<BusinessRuleValidationException>(() => new DriverDuty(driverDutyCode, workBlockList));
        }

    }
}