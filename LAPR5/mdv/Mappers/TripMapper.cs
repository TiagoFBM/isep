using System.Collections.Generic;
using mdv.Domain.Trips;
using mdv.DTO.Trips;

namespace mdv.Mappers {
    public class TripMapper {

        public TripDTO DomainToDTO (Trip trip) {

            var nodePassageDTOList = new List<NodePassageDTO> ();

            foreach (var nodePassage in trip.nodePassageList) {

                var nodePassageDTO = new NodePassageDTO (
                    nodePassage.Id.AsGuid (),
                    nodePassage.nodeID.ToString (),
                    nodePassage.passageTime.ToString ());

                nodePassageDTOList.Add (nodePassageDTO);
            }

            var tripDTO = new TripDTO (
                trip.Id.AsGuid (),
                trip.lineID,
                trip.pathID,
                trip.tripDepartureTime.ToString (),
                nodePassageDTOList
            );

            return tripDTO;
        }

    }
}