using System;
using System.Collections.Generic;
using mdv.Domain.Lines;
using mdv.Domain.Paths;
using Newtonsoft.Json;

namespace mdv.DTO.Trips {
    public class TripDTO {
        [JsonProperty ("tripId")]
        public Guid Id { get; set; }

        [JsonProperty ("lineID")]
        public LineId lineID { get; set; }

        [JsonProperty ("pathID")]
        public PathId pathID { get; set; }

        [JsonProperty ("tripDepartureTime")]
        public string tripDepartureTime { get; set; }

        [JsonProperty ("nodePassages")]
        public List<NodePassageDTO> nodePassageListDTO { get; set; }

        public TripDTO (Guid Id, LineId lineID, PathId pathID, string tripDepartureTime) {
            this.Id = Id;
            this.lineID = lineID;
            this.pathID = pathID;
            this.tripDepartureTime = tripDepartureTime;
            this.nodePassageListDTO = new List<NodePassageDTO> ();
        }

        public TripDTO (Guid Id, LineId lineID, PathId pathID, string tripDepartureTime, List<NodePassageDTO> nodePassageListDTO) {
            this.Id = Id;
            this.lineID = lineID;
            this.pathID = pathID;
            this.tripDepartureTime = tripDepartureTime;
            this.nodePassageListDTO = new List<NodePassageDTO> (nodePassageListDTO);
        }

        public override bool Equals (Object obj) {
            if ((obj == null) || !this.GetType ().Equals (obj.GetType ())) {
                return false;
            } else {
                TripDTO dto = (TripDTO) obj;
                return (this.Id.ToString ().Equals (dto.Id.ToString ())) &&
                    (this.lineID.ToString ().Equals (dto.lineID.ToString ())) &&
                    (this.pathID.ToString ().Equals (dto.pathID.ToString ())) &&
                    (this.tripDepartureTime.ToString ().Equals (dto.tripDepartureTime.ToString ()));
            }
        }

        public override int GetHashCode () {
            return this.GetHashCode ();
        }

    }
}