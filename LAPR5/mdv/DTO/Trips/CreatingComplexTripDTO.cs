using System;
using System.Collections.Generic;
using mdv.Domain.Paths;
using Newtonsoft.Json;

namespace mdv.DTO.Trips {
    public class CreatingComplexTripDTO {

        [JsonProperty ("lineID")]
        public string lineID { get; set; }

        [JsonProperty ("outwardPathID")]
        public string outwardPathID { get; set; }

        [JsonProperty ("returnPathID")]
        public string returnPathID { get; set; }

        [JsonProperty ("tripDepartureTime")]
        public string tripDepartureTime { get; set; }

        [JsonProperty ("frequency")]
        public int frequency { get; set; }

        [JsonProperty ("nTrips")]
        public int nTrips { get; set; }

        public CreatingComplexTripDTO (string lineID,string outwardPathID, string returnPathID, string tripDepartureTime, int frequency, int nTrips) {
            this.lineID = lineID;
            this.outwardPathID = outwardPathID;
            this.returnPathID = returnPathID;
            this.tripDepartureTime = tripDepartureTime;
            this.frequency = frequency;
            this.nTrips = nTrips;
        }
    }
}