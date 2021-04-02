using System;
using System.Collections.Generic;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using mdv.Domain.Shared;
using mdv.Domain.Times;
using mdv.Domain.Validators;

namespace mdv.Domain.Trips {
    public class Trip : Entity<TripId>, IAggregateRoot {
        public PathId pathID { get; private set; }
        public LineId lineID { get; private set; }
        public Time tripDepartureTime { get; private set; }
        public List<NodePassage> nodePassageList { get; private set; }

        public Trip () { }
        public Trip (string lineID, string pathID, string tripDepartureTime) {

            if (StringValidator.isStringEmptyOrNull (lineID)) {
                throw new BusinessRuleValidationException (lineID + " invalid: Trip Line can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull (pathID)) {
                throw new BusinessRuleValidationException (pathID + " invalid: Trip Line Path can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull (tripDepartureTime)) {
                throw new BusinessRuleValidationException (tripDepartureTime + " invalid: Trip Departure Time can't be null or empty.");
            }

            this.Id = new TripId (Guid.NewGuid ());
            this.lineID = new LineId (lineID);
            this.pathID = new PathId (pathID);
            this.tripDepartureTime = new Time (tripDepartureTime);
            this.nodePassageList = new List<NodePassage> ();
        }

        public Trip (string lineID, string pathID, string tripDepartureTime, List<NodePassage> nodePassageList) {

            if (StringValidator.isStringEmptyOrNull (lineID)) {
                throw new BusinessRuleValidationException (lineID + " invalid: Trip Line can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull (pathID)) {
                throw new BusinessRuleValidationException (pathID + " invalid: Trip Line Path can't be null or empty.");
            }

            if (StringValidator.isStringEmptyOrNull (tripDepartureTime)) {
                throw new BusinessRuleValidationException (tripDepartureTime + " invalid: Trip Departure Time can't be null or empty.");
            }

            this.Id = new TripId (Guid.NewGuid ());
            this.lineID = new LineId (lineID);
            this.pathID = new PathId (pathID);
            this.tripDepartureTime = new Time (tripDepartureTime);
            this.nodePassageList = new List<NodePassage> (nodePassageList);
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                Trip trip = (Trip) obj;
                return (this.lineID.Equals (trip.lineID)) &&
                    (this.pathID.Equals (trip.pathID)) &&
                    (this.tripDepartureTime.Equals (trip.tripDepartureTime));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (Id, pathID, lineID, tripDepartureTime, nodePassageList);
        }
    }
}