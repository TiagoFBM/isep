using System.Collections.Generic;
using mdv.Domain.WorkBlocks;
using mdv.DTO.WorkBlocks;
using mdv.DTO.Trips;
using mdv.Mappers;

namespace mdv.Mappers {
    public class WorkBlockMapper {

        private readonly TripMapper _tripMapper;

        public WorkBlockMapper() {
            _tripMapper = new TripMapper();
        }

        public WorkBlockDTO DomainToDTO (WorkBlock workBlock) {
            var tripDTOList = new List<TripDTO> ();

            foreach (var trip in workBlock.tripList) {
                var tripDTO = _tripMapper.DomainToDTO(trip);
                tripDTOList.Add(tripDTO);
            }

            var workBlockDTO = new WorkBlockDTO (
                workBlock.Id.AsGuid(),
                workBlock.startingTime.ToString(),
                workBlock.endingTime.ToString(),
                tripDTOList
            );

            return workBlockDTO;
        }

    }
}