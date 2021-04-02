using System;

namespace mdv.DTO.Trips {
    public class CreatingTripDTO {
        public string lineID { get; set; }
        public string pathID { get; set; }
        public string tripDepartureTime { get; set; }

        public CreatingTripDTO (string lineID, string pathID, string tripDepartureTime) {
            this.lineID = lineID;
            this.pathID = pathID;
            this.tripDepartureTime = tripDepartureTime;
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                CreatingTripDTO trip = (CreatingTripDTO) obj;
                return (this.lineID.Equals (trip.lineID)) &&
                    (this.pathID.Equals (trip.pathID)) &&
                    (this.tripDepartureTime.Equals (trip.tripDepartureTime));
            }
        }

        public override int GetHashCode () {
            return HashCode.Combine (lineID, pathID, tripDepartureTime);
        }
    }
}