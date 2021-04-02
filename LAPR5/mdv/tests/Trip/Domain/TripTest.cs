using System;
using System.Collections.Generic;
using mdv.Domain.Shared;
using mdv.Domain.Trips;
using Xunit;

namespace Tests.Trips {
    public class TripTest {

        [Fact]
        public void CreateValidTrip () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            var trip = new Trip (lineID, pathID, tripDepartureTime);
            Assert.NotNull (trip);
        }

        [Fact]
        public void CreateInvalidTrip_EmptyLine () {
            string lineID = "";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateInvalidTrip_NullLine () {
            string lineID = null;
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateInvalidTrip_EmptyPath () {
            string lineID = "Line:1";
            string pathID = "";
            string tripDepartureTime = "20:12:10";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateInvalidTrip_NullPath () {
            string lineID = "Line:1";
            string pathID = null;
            string tripDepartureTime = "20:12:10";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateInvalidTrip_NullTime () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = null;

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateInvalidTrip_EmptyTime () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateInvalidTrip_InvalidTime1 () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "12:20";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateInvalidTrip_InvalidTime2 () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime));
        }

        [Fact]
        public void CreateValidTripWithValidNodePassages () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            string nodeID = "Node:1";
            string passageTime = "20:20:10";
            var nodePassage = new NodePassage (nodeID, passageTime);
            var nodePassageList = new List<NodePassage> () { nodePassage };

            var trip = new Trip (lineID, pathID, tripDepartureTime, nodePassageList);
            Assert.NotNull (trip);
        }

        [Fact]
        public void CreateValidTripWithInvalidNodePassageNullNode () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            string nodeID = null;
            string passageTime = "20:20:10";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime,
                new List<NodePassage> () { new NodePassage (nodeID, passageTime) }));
        }

        [Fact]
        public void CreateValidTripWithInvalidNodePassageEmptyNode () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            string nodeID = "";
            string passageTime = "20:20:10";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime,
                new List<NodePassage> () { new NodePassage (nodeID, passageTime) }));
        }

        [Fact]
        public void CreateValidTripWithInvalidNodePassageNullTime () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            string nodeID = "Node:1";
            string passageTime = null;

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime,
                new List<NodePassage> () { new NodePassage (nodeID, passageTime) }));
        }

        [Fact]
        public void CreateValidTripWithInvalidNodePassageEmptyTime () {
            string lineID = "Line:1";
            string pathID = "Path:1";
            string tripDepartureTime = "20:12:10";

            string nodeID = "Node:1";
            string passageTime = "";

            Assert.Throws<BusinessRuleValidationException> (() => new Trip (lineID, pathID, tripDepartureTime,
                new List<NodePassage> () { new NodePassage (nodeID, passageTime) }));
        }

    }
}